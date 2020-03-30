package xyz.worldzhile.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.dao.CategoryDao;
import xyz.worldzhile.dao.OrderDao;
import xyz.worldzhile.dao.OrderItemDao;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    OrderDao orderDao;

    @GetMapping("test1")
    public ModelAndView test1(ModelAndView model){
        Subject currentUser = SecurityUtils.getSubject();
        boolean permitted=false;
        if (currentUser!=null) {
             permitted = SecurityUtils.getSubject().isPermitted("category:addcategoty");
            System.out.println(permitted);
        }
//        currentUser.getPreviousPrincipals()
        PrincipalCollection previousPrincipals = SecurityUtils.getSubject().getPreviousPrincipals();
        System.out.println(previousPrincipals+"-----");

        model.setViewName("test1");
    model.addObject("one","onezhi");
    model.addObject("permitted",permitted);


    List<User> list=new ArrayList<>();

        User user = new User();
         user.setName("王五二");

        User user1 = new User();
        user.setName("王五留");
        list.add(user);
                list.add(user1);
                model.addObject("list",list);




        List<Category> all = categoryDao.findAll();
        model.addObject("all",all);

        System.out.println(list);
        System.out.println("---------------------------");
        System.out.println(all);

        return model;
    }


    @GetMapping("test2")
    public ModelAndView test2(ModelAndView model){
        Subject currentUser = SecurityUtils.getSubject();
        boolean permitted=false;
        if (currentUser!=null) {
            permitted = SecurityUtils.getSubject().isPermitted("category:addcategoty");
            System.out.println(permitted);
        }
        model.setViewName("redirect:/templates/pages/test2.html");
        model.addObject("two","twozhi");
        model.addObject("permitted",permitted);
        return model;
    }

//    @GetMapping("test2")
//    @ResponseBody
//    public Order test2(){
//        Order cee174de368149c0b404f9362bfb8b3d = orderDao.findOneByOid("cee174de368149c0b404f9362bfb8b3d");
//        return cee174de368149c0b404f9362bfb8b3d;
//    }

    @GetMapping("test3")
    @ResponseBody
    public List<Order> test3(){
        List<Order> allOrdersByUid = orderDao.findAllOrdersByUid("12345");
        System.out.println(allOrdersByUid);
        return allOrdersByUid;
    }

    @RequestMapping("test4")
    public ModelAndView test4(ModelAndView modelAndView){
        modelAndView.setViewName("  indexpage");
        return  modelAndView;
    }


}
