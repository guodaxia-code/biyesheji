package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.domain.User;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDao {

    @Insert("insert into orders (oid,receiver_address,time,total_money,states,user_uid,name,phone) values(#{oid},#{address},#{time},#{totalMoney},#{states},#{uid},#{name},#{phone})")
    void add(Order order);

    /**
     user_uid 不给
     */
    @Update("update  orders set  receiver_address=#{address} , time=#{time}, total_money=#{totalMoney}, states=#{states}, name=#{name}, phone=#{phone} where oid=#{oid}")
    void update(Order order);



    @Select("SELECT * FROM orders where oid=#{oid}")
    @Results(id = "OrderMap",value = {
            @Result(id = true,property = "oid",column = "oid"),
            @Result(property = "address",column = "receiver_address"),
            @Result(property = "time",column = "time"),
            @Result(property = "totalMoney",column = "total_money"),
            @Result(property = "states",column = "states"),
            @Result(property = "name",column = "name"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "user" ,column="user_uid",one = @One(select = "xyz.worldzhile.dao.UserDao.findUserByUid",fetchType = FetchType.EAGER)),
            @Result(property = "orderItems" ,column="oid",many = @Many(select = "xyz.worldzhile.dao.OrderItemDao.findAllByOid",fetchType = FetchType.EAGER))

    })
    Order findOneByOid(String oid);


    @Select("select * from orders where user_uid=#{uid}")
    @ResultMap("OrderMap")
    List<Order> findAllOrdersByUid(String uid);

    /*
     查询用户的订单数量
     */
    @Select("select count(oid) from orders where user_uid=#{uid}")
    Integer findTotalCount(String uid);

    /*
     查询用户的订单分页
     */
    @Select("select * from orders where user_uid=#{uid} ORDER BY time desc  limit #{start},#{pageCount} " )
    @ResultMap("OrderMap")
    List<Order> findPageList(@Param("uid")String uid, @Param("start")int start,  @Param("pageCount")Integer pageCount);


    @Select("select * from orders ")
    @ResultMap("OrderMap")
    List<Order> findAll();





    /*
    查询所有的订单数量
    */

    @Select("<script>"+"SELECT count(oid) FROM orders  where 1=1 "
            +"</script>"
    )
    int findCount();

    @Select("<script>"+"SELECT count(*) FROM  orders Left join users  On users.uid=orders.user_uid   where 1=1  "
            +"<if test='username!=null'> and username like CONCAT('%',#{username},'%')</if>"
            +"<if test='oid!=null'> and oid like CONCAT('%',#{oid},'%')</if>"
            +"</script>"
    )
    int findCountSearch( @Param("username") String username,@Param("oid") String oid);

    /*



//    /*layui 分页查询  weitiaojiao */
//    @Select("<script>"+"SELECT * FROM orders  where 1=1 "
////            +"<if test='username!=null'> and username like CONCAT('%',#{username},'%')</if>"
//            +"<if test='oid!=null'> and oid like CONCAT('%',#{oid},'%')</if>"
//            +"limit #{start},#{pageCount}"
//            +"</script>"
//    )

    /*layui 分页查询  weitiaojiao */
    @Select("<script>"+"SELECT orders.*,users.uid,users.username FROM  orders Left join users  On users.uid=orders.user_uid   where 1=1  "
            +"<if test='username!=null'> and username like CONCAT('%',#{username},'%')</if>"
            +"<if test='oid!=null'> and oid like CONCAT('%',#{oid},'%')</if>"
            +"limit #{start},#{pageCount}"
            +"</script>"
    )
    @ResultMap("OrderMap")
    List<Order> findAllByLayuiByPage(@Param("start") int start, @Param("pageCount") Integer pageCount, @Param("username") String username,@Param("oid") String oid);





    /**
     * 删除
     * @param oid
     */
    @Delete("delete from orders where oid=#{oid}")
    void delete(String oid);


    /*
     查询用户的订单分页
     */
    @Select("select * from orders where user_uid=#{uid} and states=#{states} ORDER BY time desc  limit #{start}, #{pageCount}  " )
    @ResultMap("OrderMap")
    List<Order> findPageListWithStates(@Param("uid")String uid, @Param("start")int start,  @Param("pageCount")Integer pageCount, @Param("states") Integer states);


    @Select("select count(oid) from orders where user_uid=#{uid} and states=#{states}")
    Integer findTotalCountWithStates(@Param("uid") String uid, @Param("states") Integer states);


    /*某一时间之前的数量*/
    @Select("select count(oid) from orders where time<  #{parse} ")
    Integer findCountBeforeTime (String parse);


    /*某一时间之前的数量*/
    @Select("select count(oid) from orders where time<  #{end} and time > #{start} ")
    int findCountBetWin(@Param("start") String start, @Param("end")String end);

}
