package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.LayuiData;
import xyz.worldzhile.service.CategoryService;
import xyz.worldzhile.util.PageBean;
import xyz.worldzhile.util.UuidUtil;

import java.util.List;

@Api(tags = "Category接口")
@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /*findAllFromRedis优化 */
    @ApiOperation(value="获取所有的分类",notes = "获取所有的分类信息")
    @GetMapping("findAll")
    public @ResponseBody List<Category>  findAll(){
        return categoryService.findAll();
    }


    @GetMapping("findAllPage")
    public ModelAndView findAllPage(ModelAndView model){
        //这里必需要要跳转
        model.setViewName("redirect:/templates/pages/admin/categoryManager.html");
        return model;
    }


    /**
     * 分页查询   cname 为条件
     * @param page
     * @param limit
     * @param cname
     * @return
     */
    @GetMapping("findAllByLayui")
    public @ResponseBody LayuiData<Category> findAllByLayuiByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                  @RequestParam(value = "limit",defaultValue = "8") Integer limit,
                                                                  @RequestParam(value = "cname",required = false) String cname){
//        接口地址。默认会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
//        page 代表当前页码、limit 代表每页数据量
        //条件校验
        if (cname==null||cname.length()==0){
           cname="";
        }
        PageBean<Category> allByLayuiByPage = categoryService.findAllByLayuiByPage(page, limit,cname);
        LayuiData<Category> categoryLayuiData = new LayuiData<>();
        categoryLayuiData.setCode(0);
        categoryLayuiData.setData(allByLayuiByPage.getList());
        categoryLayuiData.setMsg("");
        categoryLayuiData.setCount(allByLayuiByPage.getTotalCount());
        return categoryLayuiData;
    }


    @GetMapping("findLunBoTu")
    public @ResponseBody List<Category>  findLunBoTu(){
        return categoryService.findLunBoTu();
    }



    @GetMapping("findOneCategory")
    public ModelAndView see(String cid,ModelAndView model){

        Category one = categoryService.findOne(cid);
        model.addObject("category",one);
        model.setViewName("admin/category");
        return model;
    }


    @RequiresPermissions("category:updatecategoty")
    @GetMapping("updateOneCategory")
    public ModelAndView update(String cid,ModelAndView model){
        Category one = categoryService.findOne(cid);
        model.addObject("category",one);
        model.setViewName("admin/bianjiCategory");
        return model;
    }


    /*添加分类*/
    @RequiresPermissions("category:addcategoty")
    @GetMapping("addCategory")
    public ModelAndView addCategory(ModelAndView model){
        model.setViewName("admin/addCategory");
        return model;
    }


    /*添加分类*/
    @RequiresPermissions("category:addcategoty")
    @GetMapping("addCategoryMsg")
    @ResponseBody
    public void addCategoryMsg(Category category){
       categoryService.insertOne(category);

    }


    /*修改分类url*/
    @RequiresPermissions("category:updatecategoty")
    @GetMapping("updateCategoryPicture")
    @ResponseBody
    public void updateCategoryPicture(Category category){
        if (category.getCid()==null||category.getCid().equals("")){

            String uuid = UuidUtil.getUuid();
            category.setCid(uuid);
            categoryService.insertOne(category);

        }else {
            System.out.println(category.getCid());
            System.out.println(category.getPicture());
            categoryService.updatePicture(category);
        }


    }

    /*修改分类除了url*/
    @RequiresPermissions("category:updatecategoty")
    @GetMapping("updateCategory")
    @ResponseBody
    public void updateCategory(Category category){
        categoryService.updateCategory(category);
    }

    @RequiresPermissions("category:deletecategoty")
    @GetMapping("del/{cid}")
    @ResponseBody
    public void del(@PathVariable("cid")String cid){
        System.out.println("删除分类");
        categoryService.update(cid);
        System.out.println("删除分类成功");
    }



}
