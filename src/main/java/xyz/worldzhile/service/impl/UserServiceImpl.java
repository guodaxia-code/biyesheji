package xyz.worldzhile.service.impl;

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

        boolean isSendSuccess = MailUtils.sendMail("2561587813@qq.com", "<h1>欢迎您成为至乐购商城的一员</h1><h2>来自至乐购商城网站的激活邮件,激活请点击以下链接：</h2><br/><h3><a href='http://www.worldzhile.xyz/store/user/active?code=" + user.getCode() + "'>激活</a></h3>", "至乐购用户注册");
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


}
