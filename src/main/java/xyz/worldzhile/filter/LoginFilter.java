package xyz.worldzhile.filter;

import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //1.强装
      HttpServletRequest request= (HttpServletRequest) servletRequest;
      HttpServletResponse response= (HttpServletResponse) servletResponse;
        //2.逻辑
        User user = (User) request.getSession().getAttribute(Constant.USER_LOGIN_SESSION);
        if (user==null){
            System.out.println("拦截器没登录");
            request.setAttribute(Constant.USER_MESSAGEG_ERROR,"您还没有登录不可以访问");
            request.getRequestDispatcher("/templates/pages/msg.html").forward(request,response);
            return ;
        }


        //3.放行
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
