package xyz.worldzhile.controller;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

        System.out.println("要添加的商品："+pid);

        System.out.println("要添加的商品number："+pcount);

        Product product = productService.findOneByPid(pid);

        CartItem cartItem = new CartItem(product,pcount);

        HttpSession session = request.getSession();
        //权限校验重复
        Cart hasCart = (Cart) request.getSession().getAttribute(Constant.USER_CART_SESSION);
        if (hasCart==null){
            System.out.println("不要意思,你没有登录没有购物车的权限");
            model.addObject(Constant.USER_MESSAGEG_ERROR,"不要意思,你没有登录没有购物车的权限");
            model.setViewName("msg");
            return model;
        }

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

        HttpSession session = request.getSession();
        //权限校验重复
        Cart hasCart = (Cart) request.getSession().getAttribute(Constant.USER_CART_SESSION);
        if (hasCart==null){
            System.out.println("不要意思,你没有登录没有购物车的权限");
            return;
        }
        hasCart.updateNumberByPid(pid,pcount);


    }

    @GetMapping("seeCart")
    public ModelAndView seeCart(HttpServletRequest request,ModelAndView model){

        //权限校验
        Cart hasCart = (Cart) request.getSession().getAttribute(Constant.USER_CART_SESSION);
        if (hasCart==null){
            System.out.println("不要意思,你没有登录没有购物车的权限");
            model.addObject(Constant.USER_MESSAGEG_ERROR,"不要意思,你没有登录没有购物车的权限");
            model.setViewName("msg");
            return model;
        }

        model.setViewName("cart");
        return model;
    }
    @GetMapping("removeProductFromCart")
    public ModelAndView removeProductFromCart(@RequestParam("pid") String pid,HttpServletRequest request,ModelAndView model){
        //权限校验
        Cart hasCart = (Cart) request.getSession().getAttribute(Constant.USER_CART_SESSION);
        if (hasCart==null){
            System.out.println("不要意思,你没有登录没有购物车的权限");
            model.addObject(Constant.USER_MESSAGEG_ERROR,"不要意思,你没有登录没有购物车的权限");
            model.setViewName("msg");
            return model;
        }
        hasCart.removeFromCart(pid);
        model.setViewName("redirect:/cart/seeCart");
        return model;
    }

    @GetMapping("cleanCart")
    public ModelAndView cleanCart(HttpServletRequest request,ModelAndView model){
        //权限校验
        Cart hasCart = (Cart) request.getSession().getAttribute(Constant.USER_CART_SESSION);
        if (hasCart==null){
            System.out.println("不要意思,你没有登录没有购物车的权限");
            model.addObject(Constant.USER_MESSAGEG_ERROR,"不要意思,你没有登录没有购物车的权限");
            model.setViewName("msg");
            return model;
        }
        hasCart.cleanCart();
        model.setViewName("redirect:/cart/seeCart");
        return model;

    }


}
