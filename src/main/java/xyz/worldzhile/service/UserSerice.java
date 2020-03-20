package xyz.worldzhile.service;

import org.springframework.stereotype.Service;
import xyz.worldzhile.domain.User;


public interface UserSerice {
    /**
     * 用户username是否唯一
     *
     */
    boolean isUnique(String username);

    /**
     * 激活码是否存在
     *
     */
    boolean isExist(String code);

    /**
     * 用户注册
     */
    boolean register(User user);

    /**
     * 激活用户状态
     */
     boolean isActive(String code);

    /**
     * 用户登录密码
     */
    boolean passwordLogin(User user);


    User findByUsername(String username);

    /**
     *
    shiro login
     */
    boolean login(User user);



}

