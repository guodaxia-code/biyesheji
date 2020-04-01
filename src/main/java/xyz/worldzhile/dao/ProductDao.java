package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.Product;

import java.util.List;

@Repository
public interface ProductDao {

    /**
     * 查询所有
     */
    @Select("select * from product ")
    @Results(id = "productMap",value = {
            @Result(id = true,property = "pid",column = "pid"),
            @Result(property = "category" ,column="category_cid",one = @One(select = "xyz.worldzhile.dao.CategoryDao.findOneByCid",fetchType = FetchType.EAGER))
    })
    List<Product> findAll();

    /**
     * 根据pid查询一个商品
     * 立即加载分类
     */
    @Select("select * from product where pid=#{pid}")
    @Results( id = "productWithCategory",value = {
            @Result(id = true,property = "pid",column = "pid"),
            @Result(property = "category" ,column="category_cid",one = @One(select = "xyz.worldzhile.dao.CategoryDao.findOneByCid",fetchType = FetchType.EAGER))
    })
    Product findByPid(String pid);

    /*
      查询热门商品9个
     */
/*    @Select({" <script>" ,
            "select * from product " ,
             "where is_hot= "+"#{Constant.IS_HOT_PRODUCT}" ,
             "and pflag="+"#{Constant.IS_UP_TO_SELL}",
            "order by pdate desc",
            "limit 0,}"+"#{number}",
             "</script>"})*/
    @Select("select * from product where is_hot= 1 and pflag=1 order by pdate desc limit 0,#{number}")
    @ResultMap("productMap")
    List<Product> findHot(Integer number);

    /*
    查询最新商品9个
     */
    @Select({" <script>" ,
            "select * from product " ,
            "where pflag=1",
            "order by pdate desc",
            "limit 0,"+"#{number}",
            "</script>"})
    @ResultMap("productMap")
    List<Product> findNew(Integer number);


    /*
     按照商品分类查找数量
    * */
    @Select("select count(pid) from product where category_cid=#{cid} and pflag=1 ")
    Integer findTotalCount(String cid);


    /*
      无条件分页数据
     */
    @Select("select * from product where category_cid=#{cid} and pflag=1 limit #{start},#{pageCount}")
    @ResultMap("productWithCategory")
    List<Product> findPageList(@Param("cid") String cid,@Param("start")Integer start,@Param("pageCount")Integer pageCount);













    @Select("<script>"+"SELECT count(pid) FROM product  where 1=1 "
            +" and pname like CONCAT('%',#{panme},'%') "
            +"</script>"
    )
    int findCountByCname(String pname);

    /*layui 分页查询 cname weitiaojiao */
    @Select("<script>"+"SELECT * FROM product  where 1=1 "
            +"<if test='pname!=null'> and pname like CONCAT('%',#{pname},'%')</if>"
            +"limit #{start},#{pageCount}"
            +"</script>"
    )
    @ResultMap("productMap")
    List<Product> findAllByLayuiByPage(@Param("start") int start, @Param("pageCount") Integer pageCount, @Param("pname") String pname);


    /*修改*/
    @Update("update product set pname=#{pname},ppicture=#{ppicture},is_hot=#{is_hot},store_price=#{store_price},rel_price=#{rel_price},pdesc=#{pdesc},pflag=#{pflag} ,category_cid=#{category.cid} where pid=#{pid}")
    void updatePicture(Product byPid);




    /*添加*/
    @Insert("insert into product (pid,pname,store_price,rel_price,ppicture,pdesc,is_hot,pflag,category_cid,pdate) values ( #{pid},#{pname},#{store_price},#{rel_price},#{ppicture},#{pdesc},#{is_hot},#{pflag},#{category.cid},#{pdate}) ")
    void insert(Product product);

    @Delete("delete from product where pid=#{pid}")
    void del(String pid);


    /**
     *
            商品查询 按名称 和价格排序
     */
    @Select("<script>"+"SELECT * FROM product  where 1=1 "
            +"<if test='pname!=null'> and pname like CONCAT('%',#{pname},'%')</if>"
            +"  order by rel_price ${pricesort} "
            +" limit #{start},#{pageCount} "
            +"</script>"
    )
    @ResultMap("productMap")
    List<Product> findAllByLayuiByPageOrderByPrice(@Param("start") int start, @Param("pageCount") Integer pageCount, @Param("pname") String pname,@Param("pricesort") String pricesort);


    @Select("select count(pid) from product")
    Integer findSum();
}
