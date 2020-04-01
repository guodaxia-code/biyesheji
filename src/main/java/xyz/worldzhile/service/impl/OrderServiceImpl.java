package xyz.worldzhile.service.impl;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.x509.OIDMap;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.dao.OrderDao;
import xyz.worldzhile.dao.OrderItemDao;
import xyz.worldzhile.dao.ProductDao;
import xyz.worldzhile.dao.UserDao;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.OrderItem;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.service.OrderService;
import xyz.worldzhile.util.DateUtil;
import xyz.worldzhile.util.PageBean;
import xyz.worldzhile.util.UuidUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    ProductDao productDao;

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

    @Override
    public PageBean<Order> findPageBean(String uid, Integer currentPage, Integer pageCount,Integer states) {

       if (states<-1||states>3)
           return null;


        Integer totalCount = 0;

        PageBean<Order> orderPageBean = null;

        List<Order> pageList=null;
        System.out.println(states+"-----------------------------------------------------------------------------------------------------");
        if (states==-1){
            totalCount= orderDao.findTotalCount(uid);
            orderPageBean = new PageBean<>(currentPage, pageCount, totalCount);
             pageList = orderDao.findPageList(uid,orderPageBean.getStart(),orderPageBean.getPageCount());
        }else if (states!=-1){
            totalCount= orderDao.findTotalCountWithStates(uid,states);
            orderPageBean = new PageBean<>(currentPage, pageCount, totalCount);
            pageList = orderDao.findPageListWithStates(uid,orderPageBean.getStart(),orderPageBean.getPageCount(),states);
        }


        System.out.println(pageList+"*******************");

        orderPageBean.setList(pageList);
        return orderPageBean;
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }



    @Override
    public PageBean<Order> findAllByLayuiByPage(Integer page, Integer limit, String pname) {


            System.out.println(pname);



            int count = orderDao.findCount();
            System.out.println(count+"dao");
            PageBean<Order> pageBean = new PageBean<Order>(page,limit,count);

            List<Order> allByLayuiByPage = orderDao.findAllByLayuiByPage(pageBean.getStart(), pageBean.getPageCount(),pname);

            System.out.println(allByLayuiByPage.toString());
            pageBean.setList(allByLayuiByPage);


            return pageBean;

    }

    @Override
    public void updatedelete(String oid) {
        orderItemDao.delete(oid);
        orderDao.delete(oid);
    }

    @Override
    public String saveOrderOneProduct(String pid, Integer pcount) {

        String oid = UuidUtil.getUuid();

        String oiid=UuidUtil.getUuid();
        Float rel_price = productDao.findByPid(pid).getRel_price();
        OrderItem orderItem = new OrderItem();
        orderItem.setOiid(oiid);
        orderItem.setPid(pid);
        orderItem.setOid(oid);
        orderItem.setNumber(pcount);
        orderItem.setMoney((double) (pcount*rel_price));


        Order order = new Order();
        order.setStates(Constant.NO_PAY);
        order.setOid(oid);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        order.setUid(user.getUid());
        order.setTotalMoney(rel_price*pcount);
        order.setTime(new Date());
        orderDao.add(order);
        orderItemDao.add(orderItem);
        return oid;
    }

    @Override
    public Integer findSum() {
        return orderDao.findCount();
    }

    @Override
    public Integer findNewOrderSum() {
        int sum = orderDao.findCount();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
        String  format2= simpleDateFormat2.format(new Date());
        format2+=" 0:0:0  ";
        Integer countBeforeTime = orderDao.findCountBeforeTime(format2);
         return sum-countBeforeTime;

    }

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
        String format1 = simpleDateFormat1.format(new Date());
        String format2 = simpleDateFormat2.format(new Date());
        System.out.println(format1);

        format2+=" 0:0:0";
        System.out.println(format2);

    }


}
