package xyz.worldzhile.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import xyz.worldzhile.realm.MyRealm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfiguration {


    /*
     shiro对象生命周期

     */

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getlifecycleBeanPostProcessor(){
          return   new LifecycleBeanPostProcessor();
    }

    /*
     权限注解
     最大的容器交给 通知
     */
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("DefaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /*
     shiro配置拦截
    * */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier( "DefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //最大的容器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //未登录 跳转登录页面
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        //未认证的跳转页面  没有对应的的角色 不会跳配置了error page 500
        shiroFilterFactoryBean.setUnauthorizedUrl("/msg");

        //有序拦截
        HashMap<String, String> urls = new LinkedHashMap<>();
        urls.put("/msg","anon");
        urls.put("/user/login","anon");

        urls.put("/order/payOrder","authc");
        urls.put("/order/seeMyOrdersByPage","authc");
        urls.put("order/seeMyOrders","authc");
        urls.put("order/lijigou","authc");
        urls.put("/order/seeOneOrder","authc");
        urls.put("/order/addOrder","authc");

        urls.put("/cart/**","authc");

        urls.put("/admin/index","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(urls);
        return shiroFilterFactoryBean;
    }


    /*
     最大的管理器
     */

    @Bean("DefaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myRealm")MyRealm myRealm,@Qualifier("sessionManager")DefaultWebSessionManager defaultWebSessionManager){

        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        //还可以 redis 缓存sessionManager
        // defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);

        return defaultWebSecurityManager;

    }


    /*session*/
    @Bean("sessionManager")
    public DefaultWebSessionManager getSessionManager(@Qualifier("sessiondao") RedisSessionDao redisSessionDao,@Qualifier("sessionIdCookie") SimpleCookie simpleCookie,
                                                      @Qualifier("sessionListener") ShiroSessionListener shiroSessionListener,@Qualifier("sessionDAO") SessionDAO sessionDAO
                                                      ){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //redis sessiondao
//        defaultWebSessionManager.setSessionDAO(redisSessionDao);
        //自定义cookie
        defaultWebSessionManager.setSessionIdCookie(simpleCookie);


        //加上在线人数监听器
        ArrayList<SessionListener> listeners = new ArrayList<>();
        listeners.add(shiroSessionListener);  //监听器的加

        defaultWebSessionManager.setSessionDAO(sessionDAO);

//        defaultWebSessionManager.setCacheManager(cacheManager());

        return defaultWebSessionManager;


    }



    /*
      自定义cookie
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("shiroCookie");
        cookie.setHttpOnly(true);//默认是false，设为true后，只允许http访问，不允许JavaScript访问
        cookie.setMaxAge(-1);//表示浏览器关闭，cookie失效
        return cookie;
    }


    /**
     *  普通 sessiondao
     */
    @Bean("sessionDAO")
    public SessionDAO sessionDAO(@Qualifier("sessionIdGenerator") SessionIdGenerator sessionIdGenerator) {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
//        sessionDAO.setCacheManager(cacheManager());
        sessionDAO.setActiveSessionsCacheName("myShiroCacheManager");
        return sessionDAO;
    }
    @Bean("sessionIdGenerator")
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

//    @Bean("CcacheManager")
//    public CacheManager cacheManager() {
//        return new EhCacheManager();
//    }






    /**
     myRealm 登录和权限验证
     */
    @Bean("myRealm")
    public MyRealm getmyRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;

    }


    /*shiro 加密器*/
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher gethashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);

        return matcher;
    }


//    /*shiro标签*/
//    @Bean(name = "shiroDialect")
//    public ShiroDialect shiroDialect(){
//        return new ShiroDialect();
//    }



}
