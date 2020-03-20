package xyz.worldzhile.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringMvcLoginFilter implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(Constant.USER_LOGIN_SESSION);
        if (user==null){
            System.out.println("springmvc拦截器没登录");
//            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"您还没有登录不可以访问");
//            request.getRequestDispatcher("msg").forward(request,response);
            response.sendRedirect("/store/user/login");
            return false;
        }
        return true;
    }
}
