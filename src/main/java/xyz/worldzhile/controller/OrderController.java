package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.*;
import xyz.worldzhile.service.OrderService;
import xyz.worldzhile.util.UuidUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(tags = "订单接口")
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("addOrder")
    public ModelAndView addOrder(@RequestParam("pids") String[] pids, ModelAndView model, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute(Constant.USER_LOGIN_SESSION);
        if (user==null){
            System.out.println("没有登录");
            model.setViewName("msg");
            model.addObject("user_login_error","没有登录不可以提交订单");
            return model;
        }

        //添加订单
        System.out.println(Arrays.toString(pids));
        //移除和获取购物车里的数据



        Cart cart = (Cart) request.getSession().getAttribute(Constant.USER_CART_SESSION);
        double totalMany=0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (String pid : pids) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOiid(UuidUtil.getUuid());
            CartItem cartItem = cart.getcartItem(pid);
            if (cartItem!=null){
            totalMany+=cartItem.getSubtotal();
            orderItem.setMoney(cartItem.getSubtotal()*1.0);
            orderItem.setNumber(cartItem.getCount());

            orderItem.setPid(pid);
            orderItems.add(orderItem);
            cart.removeFromCart(pid);

            }else {
                System.out.println("单前购物车里没有这个商品项");
                model.setViewName("msg");
                model.addObject("user_login_error","单前购物车里没有这个商品项");
                return model;
            }
        }

        String oid = orderService.saveOrder(user.getUid(), orderItems, totalMany);//下单

        Order nowOrder = orderService.findOrderByOid(oid);
        model.addObject("nowOrder",nowOrder);

        model.setViewName("order");

        return model;

    }
    @GetMapping("seeOrder")
    public ModelAndView seeOrder( ModelAndView model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Constant.USER_LOGIN_SESSION);
        if (user==null){
            System.out.println("没有登录");
            model.setViewName("msg");
            model.addObject("user_login_error","没有登录不可以查看订单");
            return model;
        }

        List<Order> allOrdersByUid = orderService.findAllOrdersByUid(user.getUid());

        model.addObject("allOrders",allOrdersByUid);


        System.out.println(allOrdersByUid);

        model.setViewName("orderxx");

        return model;



    }




}
