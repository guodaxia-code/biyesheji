package xyz.worldzhile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.worldzhile.dao.RoleDao;
import xyz.worldzhile.dao.UserDao;
import xyz.worldzhile.domain.Role;
import xyz.worldzhile.domain.User;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserDao userDao;

    @RequestMapping("role1")
    @ResponseBody
    public Role test1(){
        Role admin1 = roleDao.findByRid("1");
        System.out.println(" rid 1的角色-------------------------------------");
        System.out.println(admin1);
        System.out.println("rid 1的角色-------------------------------------");
        return admin1;
    }

    @RequestMapping("role2")
    @ResponseBody
    public List<Role> test2(){
        List<Role> admin11 = roleDao.findAllByUid("admin1");

        System.out.println("admin1的所有角色-------------------------------------");
        System.out.println(admin11);
        System.out.println("admin1的所有角色-------------------------------------");
        return admin11;
    }

    @RequestMapping("role3")
    @ResponseBody
    public User test3(){
        User admin1 = userDao.findUserByUid("admin1");
        return admin1;
    }

    @RequestMapping("role4")
    @ResponseBody
    public User test4(){

        User admin1 = userDao.findUserByUsername("admin1");
        return admin1;
    }

}
