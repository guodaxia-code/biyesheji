package xyz.worldzhile.service;

import org.springframework.stereotype.Service;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.OrderItem;

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




}
