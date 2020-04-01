package xyz.worldzhile.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.dao.UserDao;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.domain.ResultList;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.service.UserSerice;
import xyz.worldzhile.util.MailUtils;
import xyz.worldzhile.util.PageBean;

import java.text.SimpleDateFormat;
import java.util.*;

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
            String username = user.getUsername();
            String password = user.getPassword();
            Md5Hash md5Hash = new Md5Hash(password, username);
            //加密
           user.setPassword(md5Hash.toString());
           user.setCreateTime(new Date());
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
        catch (Exception e){
            System.out.println("---未知错误--");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

//        boolean role1 = subject.hasRole("role1");
//        System.out.println(user.getUsername()+"有role1角色："+role1);
//
//        try {
//            subject.checkPermission("InRoom:xiaoFei");
//            System.out.println(user.getUsername()+"有："+"InRoom:xiaoFei");
//        } catch (AuthorizationException e) {
//            System.out.println(user.getUsername()+"没有权限："+"InRoom:xiaoFei");
//            e.printStackTrace();
//        }

        return true;
    }

    @Override
    public void updateUserPicture(String uid, String url) {
        User userByUid = userDao.findUserByUid(uid);
        userByUid.setUrl(url);
        userDao.update(userByUid);
    }

    @Override
    public void updatenameandphone(String uid, String name, String phone) {
        User userByUid = userDao.findUserByUid(uid);
        userByUid.setName(name);
        userByUid.setPhone(phone);
        userDao.update(userByUid);
    }

    @Override
    public PageBean<User> findAllByLayuiByPage(Integer page, Integer limit, String username) {

        int count = userDao.findCount(username);
        System.out.println(count+"dao");
        PageBean<User> pageBean = new PageBean<User>(page,limit,count);

        List<User> allByLayuiByPage = userDao.findAllByLayuiByPage(pageBean.getStart(), pageBean.getPageCount(),username);

        System.out.println(allByLayuiByPage.toString());
        pageBean.setList(allByLayuiByPage);


        return pageBean;
    }

    @Override
    public void delete(String uid) {
        userDao.delete(uid);
    }

    @Override
    public Integer findSum() {
        return userDao.findSum();
    }

    @Override
    public Integer findnewUserSum() {
        int sum = userDao.findSum();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
        String  format2= simpleDateFormat2.format(new Date());
        format2+=" 0:0:0  ";
        Integer countBeforeTime = userDao.findCountBeforeTime(format2);
        return sum-countBeforeTime;

    }

    @Override
    public ResultList findResultList() {
        ResultList resultList = new ResultList();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();

        HashMap<Integer,String> source=new HashMap<>();
        source.put(0,"星期日");
        source.put(1,"星期一");
        source.put(2,"星期二");
        source.put(3,"星期三");
        source.put(4,"星期四");
        source.put(5,"星期五");
        source.put(6,"星期六");
        int flag;

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        Date date = new Date();

        int sum = userDao.findSum();

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        String  format2= simpleDateFormat2.format(date);

        Integer countBeforeTime = userDao.findCountBeforeTime(format2);
        flag=date.getDay();
        names.add(source.get(flag));
        values.add(sum-countBeforeTime);

        String end;
        String start=format2;

        for (int i = 1; i <=6 ; i++) {
            end=start;
            date.setDate(date.getDate()-1);
           start=simpleDateFormat2.format(date);
            flag=date.getDay();
            int count=userDao.findCountBetWin(start,end);

           names.add(source.get(flag));
           values.add(count);
        }


//        倒叙
        Collections.reverse(values);
        Collections.reverse(names);

        resultList.setValues(values);
        resultList.setNames(names);

        return resultList;
    }


}
