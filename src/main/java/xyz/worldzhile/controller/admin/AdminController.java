package xyz.worldzhile.controller.admin;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

        String loginUsername=user.getUsername();
        if (userSerice.isUnique(loginUsername)){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"用户名不存在");
            model.setViewName("/admin/login");
            return model;
        }
        User byUsername = userSerice.findByUsername(loginUsername);

        //激活
        if (byUsername.getStates()==Constant.USER_NO_ACTIVE){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"账号暂时没有激活,请先激活后再登录");
            model.setViewName("/admin/login");
            return model;
        }

        boolean login = userSerice.login(user);


        if (login){
            Session session = SecurityUtils.getSubject().getSession();
            System.out.println("-------------登录成功--------------");
            Cart cart = new Cart();
            session.setAttribute(Constant.USER_CART_SESSION,cart);
        }else {
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"登陆失败");
            model.setViewName("/admin/login");
            return model;
        }

        //是否勾选了记住用户名
//        if(Constant.SAVE_USERNAME.equals(request.getParameter("rememberUsername"))){
//            try {
//                Cookie cookie = new Cookie("rememberUsername", URLEncoder.encode(user.getUsername(),"utf-8"));
//                cookie.setMaxAge(Integer.MAX_VALUE);
//                cookie.setPath(request.getContextPath()+"/");
//                response.addCookie(cookie);
//                System.out.println("加了cookie记住用户名");
//
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }

        model.setViewName("redirect:/admin/index");
        return model;



    }



    @RequiresPermissions("seeManagerPage")
    @GetMapping(value = {"index"})
    public ModelAndView adminindex(HttpServletRequest request, ModelAndView model){

        System.out.println("aaaquindex");
        model.setViewName("redirect:/templates/pages/admin/indexpage.html");
        return model;
    }



}
