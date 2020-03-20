package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.LayuiData;
import xyz.worldzhile.service.CategoryService;
import xyz.worldzhile.util.PageBean;

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

//    @GetMapping("findAllByLayui")
//    public @ResponseBody LayuiData<Category> findAllByLayui(){
//
//        LayuiData<Category> categoryLayuiData = new LayuiData<>();
//        List<Category> all = categoryService.findAll();
//        categoryLayuiData.setCode(0);
//        categoryLayuiData.setData(all);
//        categoryLayuiData.setMsg("");
//        categoryLayuiData.setCount(800);
//        return categoryLayuiData;
//    }



    @GetMapping("findAllByLayui")
    public @ResponseBody LayuiData<Category> findAllByLayuiByPage(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "8") Integer limit){

//        接口地址。默认会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
//        page 代表当前页码、limit 代表每页数据量

//        categoryService
        PageBean<Category> allByLayuiByPage = categoryService.findAllByLayuiByPage(page, limit);


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


    @GetMapping("updateOneCategory")
    public ModelAndView update(String cid,ModelAndView model){

        Category one = categoryService.findOne(cid);
        model.addObject("category",one);
        model.setViewName("admin/bianjiCategory");
        return model;
    }

}
