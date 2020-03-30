package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.LayuiData;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.service.CategoryService;
import xyz.worldzhile.service.ProductService;
import xyz.worldzhile.util.DateUtil;
import xyz.worldzhile.util.PageBean;
import xyz.worldzhile.util.UuidUtil;

import java.util.Date;
import java.util.List;

@Api(tags = "商品接口")
@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "根据商品pid获取商品详细")
    @ApiImplicitParam(paramType = "query",name="pid",value = "商品的pid",required = true)//paramType query--》@RequestParam path --》@Pathvariable   dataType：参数类型，默认String，其它值dataType="Integer" defaultValue：参数的默认值
    @GetMapping("getProductByPid")
     public ModelAndView getProductByPid(@RequestParam("pid") String pid, ModelAndView model){

        if (pid==null||pid.length()<6){
            model.addObject(Constant.USER_MESSAGEG_ERROR,"商品的pid格式不正确");
            model.setViewName("msg");
            return model;
        }
        Product product = productService.findOneByPid(pid);
        if (product==null){
            model.addObject(Constant.USER_MESSAGEG_ERROR,"商品为"+pid+"的商品不存在");
            model.setViewName("msg");
            return model;
        }
        model.addObject("product",product);
        model.setViewName("product_info");
        return model;

    }
    /*
    * 分类下的商品无条件分页查询
    * */
    @ApiOperation(value = "商品按分类就行分页查询")
    @ApiImplicitParams(
            value = { @ApiImplicitParam(paramType = "query",name="cid",value = "分类的cid",required = true),
            @ApiImplicitParam(paramType = "query",name="currentPage",value = "查询的页码",dataType = "int",defaultValue = "1",required = true),
            @ApiImplicitParam(paramType = "query",name="pageCount",value = "每页的商品数据量",dataType = "int", defaultValue = "8",required = true)
    })
    @GetMapping("getProductsByPage")
    public ModelAndView getProductsByPage(@RequestParam("cid") String cid,@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,@RequestParam(value = "pageCount",defaultValue = "8") Integer pageCount,ModelAndView model){

        if (cid==null||cid.length()<6){
            model.addObject(Constant.USER_MESSAGEG_ERROR,"商品的cid格式不正确");
            model.setViewName("msg");
            return model;
        }
        PageBean<Product> pageBean = productService.findPageBean(cid, currentPage, pageCount);
        model.addObject("pageBean",pageBean);
        model.setViewName("product_list");


        model.addObject("cname",categoryService.findNameByCid(cid));
        return model;

    }



    @GetMapping("findAll")
    public ModelAndView toProductList(ModelAndView modelAndView){
        List<Product> all = productService.findAll();

        modelAndView.addObject("all",all);

        modelAndView.setViewName("admin/product/productList");
        return modelAndView;

    }



    @GetMapping("findAllPage")
    public ModelAndView findAllPage(ModelAndView model){
        //这里必需要要跳转
        model.setViewName("redirect:/templates/pages/admin/product/productManager.html");
        return model;
    }


    /**
     * 分页查询   pname 为条件
     * @param page
     * @param limit
     * @param pname
     * @return
     */
    @GetMapping("findAllByLayui")
    public @ResponseBody
    LayuiData<Product> findAllByLayuiByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                             @RequestParam(value = "limit",defaultValue = "8") Integer limit,
                                             @RequestParam(value = "pname",required = false) String pname){
//        接口地址。默认会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
//        page 代表当前页码、limit 代表每页数据量
        //条件校验
        if (pname==null||pname.length()==0){
            pname="";
        }
        PageBean<Product> allByLayuiByPage = productService.findAllByLayuiByPage(page, limit,pname);
        LayuiData<Product> productLayuiData = new LayuiData<>();
        productLayuiData.setCode(0);
        productLayuiData.setData(allByLayuiByPage.getList());
        productLayuiData.setMsg("");
        productLayuiData.setCount(allByLayuiByPage.getTotalCount());
        return productLayuiData;
    }




    @GetMapping("updateOneProduct")
    public String update(@RequestParam("pid") String pid, Model model){

        List<Category> all = categoryService.findAll();
        model.addAttribute("all",all);
        System.out.println(all);
        Product one = productService.findOneByPid(pid);
        model.addAttribute("product",one);
        System.out.println(one);
    //http://localhost:8080/store/product/updateOneProduct?pid=90e9372366ef400f8589b74f1cfb9123
        System.out.println(all);
        System.out.println(one.getCategory().getCname());
        return "admin/product/bianjiProduct";
    }

    /*修改商品ppicture*/
    @GetMapping("updateProductPicture")
    @ResponseBody
    public void updateCategoryPicture(Product product){
        System.out.println(product.getPid());
        System.out.println(product.getPpicture());
        productService.updatePicture(product);
    }



    /*修改除了url*/

    @GetMapping("updateProduct")
    @ResponseBody
    public void updateProduct(Product product){
        productService.updateProduct(product);
    }

    /*添加*/
    @GetMapping("addProduct")
    public ModelAndView addProduct(ModelAndView model){
        List<Category> all = categoryService.findAll();
        model.addObject("all",all);
        model.setViewName("admin/product/addProduct");
        return model;
    }


    /*add*/
    @GetMapping("addProductMsg")
    @ResponseBody
    public void addProductMsg(Product product){

        String uuid = UuidUtil.getUuid();
        if (product.getPid()==null||product.getPid().equals("")){
        product.setPid(uuid);
        product.setPdate(new Date());
        }
        System.out.println(product.getPdate());
        productService.add(product);
    }

    /*del*/
    @GetMapping("del/{pid}")
    @ResponseBody
    public void del(@PathVariable("pid")String pid){
        System.out.println("删除分类");
        productService.del(pid);
        System.out.println("删除分类成功");
    }




}
