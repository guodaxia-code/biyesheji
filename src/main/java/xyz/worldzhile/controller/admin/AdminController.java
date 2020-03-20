package xyz.worldzhile.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Cart;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.service.UserSerice;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserSerice userSerice;


    @GetMapping("login")
    public ModelAndView adminlogin(HttpServletRequest request, ModelAndView model){
        model.setViewName("/admin/login");
        return model;
    }



    @PostMapping("login")
    public ModelAndView adminloginIn(User user, HttpServletRequest request, ModelAndView model){
//        String sessionCheckCode = (String) request.getSession().getAttribute(Constant.CHECKCODE_SESSION);
//        request.getSession().removeAttribute(Constant.CHECKCODE_SESSION);
//        if (sessionCheckCode==null){
//            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"验证码不存在");
//            model.setViewName("/admin/login");
//            return model;
//        }
//        if (!sessionCheckCode.equalsIgnoreCase(code)){
//            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"验证码错误");
//            model.setViewName("/admin/login");
//            return model;
//        }
        String loginUsername=user.getUsername();
        if (userSerice.isUnique(loginUsername)){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"用户名不存在");
            model.setViewName("/admin/login");
            return model;
        }
        User userInMysql = userSerice.findByUsername(user.getUsername());
        //密码
        boolean passwordLogin = userSerice.passwordLogin(user);
        if (!passwordLogin){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"密码错误");
            model.setViewName("/admin/login");
            return model;
        }
        //激活
        if (userInMysql.getStates()==Constant.USER_NO_ACTIVE){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"账号暂时没有激活,请先激活后再登录");
            model.setViewName("/admin/login");
            return model;
        }
        if (!userInMysql.getUid().equals("admin")){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"账号没有管理员权限");
            model.setViewName("/admin/login");
            return model;
        }

        //跳转登录页面 地址要变的
        request.getSession().setAttribute(Constant.USER_LOGIN_SESSION,userInMysql);
        //为登录用户添加购物车
        Cart cart = new Cart();
        request.getSession().setAttribute(Constant.USER_CART_SESSION,cart);
        model.setViewName("redirect:/admin/adminindex");
        return model;
    }

    @GetMapping("adminindex")
    public ModelAndView adminindex(HttpServletRequest request, ModelAndView model){
        if (request.getSession().getAttribute(Constant.USER_LOGIN_SESSION)!=null){
            model.setViewName("/admin/adminindex");
            return model;
        }else {
            request.setAttribute(Constant.USER_MESSAGEG_ERROR, "账号没有管理员权限");
            model.setViewName("/admin/login");
            return model;
        }
    }



}
