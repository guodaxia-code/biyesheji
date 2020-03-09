package xyz.worldzhile.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.service.CategoryService;

import java.util.List;

@RequestMapping("admin")
@Controller
public class AdminCategotyController {

    @Autowired
    CategoryService categoryService;

    /**
     * 管理员管理分类
     */
    @GetMapping("findAll")
    public ModelAndView findAll(ModelAndView model){
        List<Category> all = categoryService.findAll();
        model.addObject("categorys",all);
        model.setViewName("admin/categoryManager");
        return model;
    }
    @GetMapping("update/{cid}")
    @ResponseBody
    public void findAll(@PathVariable("cid")String cid){
        System.out.println("删除分类");
        categoryService.update(cid);
        System.out.println("删除分类成功");
    }
}
