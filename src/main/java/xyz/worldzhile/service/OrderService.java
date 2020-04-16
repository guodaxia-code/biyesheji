package xyz.worldzhile.service;

import org.springframework.stereotype.Service;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.OrderItem;
import xyz.worldzhile.domain.ResultList;
import xyz.worldzhile.util.PageBean;

import java.util.List;


public interface OrderService {

     ResultList findResultList();

    /*
            下单
            返回订单的oid
         */
    String saveOrder(String uid, List<OrderItem> orderItems,Double totalMany);

    /**
     * 查询用户的所有订单
     */
    List<Order> findAllOrdersByUid(String uid);



    Order findOrderByOid(String oid);


    /**
     *
        我的订单分页查询 states为订单状态
     */
    PageBean<Order> findPageBean(String uid, Integer currentPage, Integer pageCount,Integer states);


    /*
        修改订单 包括 状态 和收货人
     */
    void updateOrder(Order order);

    List<Order> findAll();



    PageBean<Order> findAllByLayuiByPage(Integer page, Integer limit, String username,String oid);


    /*
     删除订单
     */
    void updatedelete(String oid);

    /*立即购买商品不经过购物车 返回oid*/
    String saveOrderOneProduct(String pid, Integer pcount);


    Integer findSum();


//    今日新增订单数
    Integer findNewOrderSum();


}
