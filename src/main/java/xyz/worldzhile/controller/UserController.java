package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Cart;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.service.UserSerice;
import xyz.worldzhile.util.UuidUtil;

import javax.security.auth.Subject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
@Api(tags = "用户接口")
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserSerice userService;

    @ApiOperation(value = "用户注册页面")
    @GetMapping("register")
    public ModelAndView register(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/templates/pages/register.html");
        return modelAndView;
    }
    @ApiOperation(value = "用户注册提交数据")
    @ApiImplicitParam(paramType = "query",name="code",value = "提交的验证码",required = true)
    @PostMapping("register")
    public ModelAndView register(User user, ModelAndView model, @RequestParam("checkCode")String code, HttpServletRequest request){


        String sessionCheckCode = (String) request.getSession().getAttribute(Constant.CHECKCODE_SESSION);
        request.getSession().removeAttribute(Constant.CHECKCODE_SESSION);
        if (sessionCheckCode==null){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"验证码不存在");
            model.setViewName("register");
            return model;
        }
        if (!sessionCheckCode.equalsIgnoreCase(code)){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"验证码错误");
            model.setViewName("register");
            return model;
        }

        if (!userService.isUnique(user.getUsername())){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"用户名已经被注册过了或者被改名使用过了");
            model.setViewName("register");
            return model;
        }


        //用户服务
        user.setUid(UuidUtil.getUuid());
        user.setCode(UuidUtil.getUuid());
        user.setStates(Constant.USER_NO_ACTIVE);
        boolean isSuccessSendQQEmailAndInsert = userService.register(user);
        if (!isSuccessSendQQEmailAndInsert) {
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"亲，你没有联网,我们无法给你发送激活邮件");
            model.setViewName("register");
            return model;
        }
        model.setViewName("registerOrActive-ok");
        request.setAttribute(Constant.TITLE, "账号注册成功页面");
        model.addObject(Constant.USER_MESSAGEG_SUCCESS, "恭喜你，成功注册了,请登录邮箱进行账号激活");
        return model;
    }

    @ApiOperation(value = "用户激活账号")
    @ApiImplicitParam(paramType = "query",name="code",value = "提交的验证码" ,required = true)
    @GetMapping("active")
    public ModelAndView register(@RequestParam("code")String code,ModelAndView model,HttpServletRequest request){
        if (code==null||code.length()!=32){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"不要搞事,激活码输入格式有误");
            model.setViewName("msg");
              return model;
        }
        if (!userService.isExist(code)){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"不要搞事,激活码不存在哦");
            model.setViewName("msg");
            return model;
        }



        //激活用户状态
        boolean isActive = userService.isActive(code);
        if (isActive){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"不要搞事,账户已经激活过,不要重复激活哦");
            model.setViewName("msg");
            return model;
        }

        request.setAttribute(Constant.TITLE,"账号激活成功页面");
        request.setAttribute(Constant.USER_MESSAGEG_SUCCESS,"恭喜你，账号激活成功,请登录至乐购购物商城使用");
        model.setViewName("registerOrActive-ok");
        return model;


    }



    @ApiOperation(value = "用户登录页面")
    @GetMapping("login")
    public String login( ){
        return "redirect:/templates/pages/login.html";
    }


//    @ApiOperation(value = "用户登录信息提交")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(paramType = "query",name="code",value = "提交的验证码",required = true),
//            @ApiImplicitParam(paramType = "query",name="user",value = "提交的用户信息",required = true) //不知道的类型 默认String
//    })



    @GetMapping("exit")
    public ModelAndView exit(ModelAndView model){
        SecurityUtils.getSubject().logout();
        model.setViewName("redirect:/templates/pages/index.html");
        return model;
    }


    @PostMapping("login")
    public ModelAndView login(ModelAndView model, User user, @RequestParam("checkCode")String code, HttpServletRequest request, HttpServletResponse response){

//        String sessionCheckCode = (String) request.getSession().getAttribute(Constant.CHECKCODE_SESSION);
//        request.getSession().removeAttribute(Constant.CHECKCODE_SESSION);
//        if (sessionCheckCode==null){
//            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"验证码不存在");
//            model.setViewName("login");
//            return model;
//        }
//        if (!sessionCheckCode.equalsIgnoreCase(code)){
//            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"验证码错误");
//            model.setViewName("login");
//            return model;
//        }


        String loginUsername=user.getUsername();
        if (userService.isUnique(loginUsername)){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"用户名不存在");
            model.setViewName("login");
            return model;
        }
        User byUsername = userService.findByUsername(loginUsername);

        //激活
        if (byUsername.getStates()==Constant.USER_NO_ACTIVE){
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"账号暂时没有激活,请先激活后再登录");
            model.setViewName("login");
            return model;
        }

        boolean login = userService.login(user);


        if (login){
            Session session = SecurityUtils.getSubject().getSession();
            System.out.println("-------------登录成功--------------");
            Cart cart = new Cart();
            session.setAttribute(Constant.USER_CART_SESSION,cart);
        }else {
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"登陆失败");
            model.setViewName("login");
            return model;
        }

        //是否勾选了记住用户名
        if(Constant.SAVE_USERNAME.equals(request.getParameter("rememberUsername"))){
            try {
                Cookie cookie = new Cookie("rememberUsername", URLEncoder.encode(user.getUsername(),"utf-8"));
                cookie.setMaxAge(Integer.MAX_VALUE);
                cookie.setPath(request.getContextPath()+"/");
                response.addCookie(cookie);
                System.out.println("加了cookie记住用户名");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        model.setViewName("redirect:/index");
        return model;
    }









    @GetMapping("testOrder")
    public ModelAndView aa(HttpServletRequest request,ModelAndView model){
        model.setViewName("testOrder");
        Session session = SecurityUtils.getSubject().getSession();
        String shiro = (String) session.getAttribute("shirosession");


        System.out.println(shiro+"-----------------------");

//        ShiroUser user = SecurityUtils.getSubject().getPrincipal();

        String loginAccount = SecurityUtils.getSubject().getPrincipal().toString();
        System.out.println(loginAccount);
        return model;
    }


}
