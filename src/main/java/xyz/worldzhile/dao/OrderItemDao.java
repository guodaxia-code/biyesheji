package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.domain.OrderItem;

import java.util.List;

@Repository
public interface OrderItemDao {

    @Insert("insert into orderitem values(#{oid},#{pid},#{oiid},#{number},#{money})")
    void add(OrderItem orderItem);


    @Select("select * from orderitem where oiid=#{oiid}")
    @Results(id = "OrderItemMap",value = {
            @Result(id = true,property = "oiid",column = "oiid"),
//            @Result(property = "order" ,column="order_oid",one = @One(select = "xyz.worldzhile.dao.OrderDao.findOneByOid",fetchType = FetchType.EAGER)),
            @Result(property = "product" ,column="product_pid",one = @One(select = "xyz.worldzhile.dao.ProductDao.findByPid",fetchType = FetchType.EAGER)),
            @Result(property = "number",column = "number"),
            @Result(property = "money",column = "money"),

    })
    OrderItem findOneByOiid(String oiid);


    @Select("select * from orderitem where order_oid=#{oid}")
    @ResultMap("OrderItemMap")
    List<OrderItem> findAllByOid(String oid);

    @Delete("delete from orderitem where order_oid=#{oid}")
    void delete(String oid);
}
