package xyz.worldzhile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.x509.OIDMap;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.dao.OrderDao;
import xyz.worldzhile.dao.OrderItemDao;
import xyz.worldzhile.dao.UserDao;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.OrderItem;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.service.OrderService;
import xyz.worldzhile.util.DateUtil;
import xyz.worldzhile.util.UuidUtil;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Override
    public String   saveOrder(String uid, List<OrderItem> orderItems,Double totalMany) {

        Order order = new Order();
        String oid= UuidUtil.getUuid();
        order.setOid(oid);
         order.setUser(userDao.findUserByUid(uid));
        order.setUid(uid);

        order.setStates(Constant.NO_PAY);
        order.setTime(new Date());
        //总金额
        order.setTotalMoney(totalMany);
         //其他数据
        System.out.println(order);
        orderDao.add(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(oid);
            orderItemDao.add(orderItem);
        }

        return  oid;


    }

    @Override
    public List<Order> findAllOrdersByUid(String uid) {
        return orderDao.findAllOrdersByUid(uid);
    }

    @Override
    public Order findOrderByOid(String oid) {
        return orderDao.findOneByOid(oid);
    }


}
