package xyz.worldzhile.controller;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Cart;
import xyz.worldzhile.domain.CartItem;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "购物车接口")
@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    ProductService productService;

    @GetMapping("addToCart")
    public ModelAndView addToCart(@RequestParam("pid") String pid, @RequestParam("pcount") Integer pcount, ModelAndView model, HttpServletRequest request){

        Product product = productService.findOneByPid(pid);
        CartItem cartItem = new CartItem(product,pcount);
        //权限校验重复
        Cart hasCart = (Cart)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_CART_SESSION);
        hasCart.addToCart(cartItem);
        model.addObject("cartItem",cartItem);
        model.setViewName("addToCartSuccess");
        return model;

    }

    /**
     *修改商品的数量
     */
    @GetMapping("updateProductNumberFromCart")
    @ResponseBody
    public void updateProductNumberFromCart(@RequestParam("pid") String pid, @RequestParam("pcount") Integer pcount, HttpServletRequest request){

        System.out.println("要查的商品："+pid);

        System.out.println("要查的商品number："+pcount);

        //权限校验重复
        Cart hasCart = (Cart)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_CART_SESSION);
        hasCart.updateNumberByPid(pid,pcount);


    }

    @GetMapping("seeCart")
    public ModelAndView seeCart(HttpServletRequest request,ModelAndView model){
        //权限校验
        Cart hasCart = (Cart)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_CART_SESSION);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addObject("user",user);


        model.setViewName("cart");
        return model;
    }
    @GetMapping("removeProductFromCart")
    @ResponseBody
    public void removeProductFromCart(@RequestParam("pid") String pid,HttpServletRequest request,ModelAndView model){
        //权限校验
        Cart hasCart = (Cart)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_CART_SESSION);
        hasCart.removeFromCart(pid);

    }

    @GetMapping("cleanCart")
    public ModelAndView cleanCart(HttpServletRequest request,ModelAndView model){
        //权限校验
        Cart hasCart = (Cart)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_CART_SESSION);
        hasCart.cleanCart();
        model.setViewName("redirect:/cart/seeCart");
        return model;

    }


}
