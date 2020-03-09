package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.service.CategoryService;

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

    @GetMapping("findLunBoTu")
    public @ResponseBody List<Category>  findLunBoTu(){
        return categoryService.findLunBoTu();
    }




}
