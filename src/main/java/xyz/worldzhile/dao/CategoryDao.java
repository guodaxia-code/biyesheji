package xyz.worldzhile.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.util.PageBean;

import java.util.List;

@Repository
public interface CategoryDao {
    @Select("select * from category ORDER BY hot desc")
    @Results(id = "CategoryMap",value = {
            @Result(id = true,property = "cid",column = "cid"),
            @Result(property = "cname",column = "cname"),
            @Result(property = "picture",column = "picture"),
            @Result(property = "hot",column = "hot"),
    })
    List<Category> findAll();

    /*
     根据cid查询分类
     */
    @Select("select * from category where cid=#{cid}")
    @ResultMap("CategoryMap")
    Category findOneByCid(String cid);


    /*
        主页五个轮播图查询
     */
    @Select("    select * from category   where picture is not null   ORDER BY hot desc  limit 0 ,5")
    @ResultMap("CategoryMap")
    List<Category> findLunBoTu();

    /*
        删除分类
     */
   @Delete("delete from category where cid=#{cid}")
    void update(@Param("cid") String cid);


   @Select("SELECT  count(cid) from category")
    int findCount();


    /*layui 分页查询*/
    @Select("select * from category   limit #{start},#{pageCount}")
    @ResultMap("CategoryMap")
    List<Category> findAllByLayuiByPage(@Param("start") int start, @Param("pageCount")Integer pageCount);
}
