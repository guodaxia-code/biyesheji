package xyz.worldzhile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.dao.OrderDao;
import xyz.worldzhile.dao.OrderItemDao;
import xyz.worldzhile.domain.Order;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    OrderDao orderDao;

    @GetMapping("test1")
    public ModelAndView test1(ModelAndView model){
    model.setViewName("test1");
        return model;
    }

    @GetMapping("test2")
    @ResponseBody
    public Order test2(){
        Order cee174de368149c0b404f9362bfb8b3d = orderDao.findOneByOid("cee174de368149c0b404f9362bfb8b3d");
        return cee174de368149c0b404f9362bfb8b3d;
    }

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
