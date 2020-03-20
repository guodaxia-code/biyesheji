package xyz.worldzhile.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.dao.UserDao;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.service.UserSerice;
import xyz.worldzhile.util.MailUtils;

@Service
public class UserServiceImpl implements UserSerice {
    @Autowired
    UserDao userDao;

    @Override
    public boolean isUnique(String username) {
        User userInMysql = userDao.findUserByUsername(username);
        return userInMysql==null;
    }

    @Override
    public boolean isExist(String code) {
        User userInMysql = userDao.findUserByCode(code);
       return userInMysql!=null;
    }

    @Override
    public boolean register(User user) {

        boolean isSendSuccess = MailUtils.sendMail("2561587813@qq.com", "<h1>欢迎您成为至乐购商城的一员</h1><h2>来自至乐购商城网站的激活邮件,激活请点击以下链接：</h2><br/><h3><a href='http://localhost:8080/store/user/active?code=" + user.getCode() + "'>激活</a></h3>", "至乐购用户注册");
        if (isSendSuccess) {

            userDao.insert(user);
            return true;
        }
        return false;



    }

    @Override
    public boolean isActive(String code) {
        User userByCodeInMysql = userDao.findUserByCode(code);
        if (userByCodeInMysql.getStates()==0){
            userDao.active(code);
        }
        return userByCodeInMysql.getStates()==1;
    }

    @Override
    public boolean passwordLogin(User user) {
        User userByUsernameInMysql = userDao.findUserByUsername(user.getUsername());
        return userByUsernameInMysql.getPassword().equals(user.getPassword());

    }

    @Override
    public User findByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public boolean login(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());

        try {
            subject.login(token);
        }catch (UnknownAccountException e){
            System.out.println("---用户名不存在---");
            return false;
        } catch (IncorrectCredentialsException e){
            System.out.println("---密码错误--");
            return false;
        }

        boolean role1 = subject.hasRole("role1");
        System.out.println(user.getUsername()+"有role1角色："+role1);

        try {
            subject.checkPermission("InRoom:xiaoFei");
            System.out.println(user.getUsername()+"有："+"InRoom:xiaoFei");
        } catch (AuthorizationException e) {
            System.out.println(user.getUsername()+"没有权限："+"InRoom:xiaoFei");
            e.printStackTrace();
        }

        return true;
    }


}
