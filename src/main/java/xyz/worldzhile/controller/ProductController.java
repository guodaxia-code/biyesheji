package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.service.CategoryService;
import xyz.worldzhile.service.ProductService;
import xyz.worldzhile.util.PageBean;

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
     public ModelAndView getProductByPid(@RequestParam("pid") String pid,ModelAndView model){

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
















    @ApiOperation(value = "商品按分类就行分页查询test")
    /*
    test
     */
    @GetMapping("getProductsByPage1")
    public @ResponseBody PageBean<Product> getProductsByPage1(@RequestParam("cid") String cid,@RequestParam("currentPage") Integer currentPage,@RequestParam("pageCount") Integer pageCount,ModelAndView model){
        PageBean<Product> pageBean = productService.findPageBean(cid, currentPage, pageCount);
        return pageBean;

    }

}
