package xyz.worldzhile.service;

import org.springframework.stereotype.Service;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.OrderItem;
import xyz.worldzhile.util.PageBean;

import java.util.List;


public interface OrderService {

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
        我的订单分页查询
     */
    PageBean<Order> findPageBean(String uid, Integer currentPage, Integer pageCount);


    /*
        修改订单 包括 状态 和收货人
     */
    void updateOrder(Order order);

    List<Order> findAll();



    PageBean<Order> findAllByLayuiByPage(Integer page, Integer limit, String pname);


    /*
     删除订单
     */
    void updatedelete(String oid);

    /*立即购买商品不经过购物车 返回oid*/
    String saveOrderOneProduct(String pid, Integer pcount);
}
