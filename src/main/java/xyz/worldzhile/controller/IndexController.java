package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.service.CategoryService;
import xyz.worldzhile.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "主页接口",description = "主页上的热门和最新商品")
@Controller
public class IndexController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "主页",notes = "主页商品及用户登录信息展示")
    @GetMapping(value = {"/index",""})
    public ModelAndView index(HttpServletRequest request, ModelAndView model){
        model.setViewName("index");

        //轮播图
        List<Category> lunBoTu = categoryService.findLunBoTu();
        model.addObject("lunBoTu",lunBoTu);


        //主页里面的各种模块 热门商品 最新产品 推荐产品等等
        List<Product> hotList = productService.findHot(Constant.INDEX_HOT_PRODUCT_NUMBER);
        List<Product> newList = productService.findNew(Constant.INDEX_HOT_PRODUCT_NUMBER);
        model.addObject("hotList",hotList);
        model.addObject("newList",newList);
        return model;
    }








}
