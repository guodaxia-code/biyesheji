package xyz.worldzhile.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.util.PageBean;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有分类并放回json
     */
    List<Category> findAll();

    /**
     * 优化 减少对数据库的访问压力
     */
    List<Category> findAllFromRedis();


    /**
     * 主页轮播图5个

     */
    List<Category> findLunBoTu();

    /*
        删除分类
     */
    void update(String cid);

    /**
     * 查询分类名
     */
    String   findNameByCid(String cid);





 /*layui分页 cname 为条件*/
//    PageBean<Category> findAllByLayuiByPage(int page, int limit);


      /*查看分类*/
    Category findOne(String cid);


    /**
     *
        修改分类图片url
     */
    void updatePicture(Category category);


    /**
     * 修改分类 除了url
     * @param category
     */

    void updateCategory(Category category);


    void insertOne(Category category);

    /*layui分页 cname 为条件*/
    PageBean<Category> findAllByLayuiByPage(Integer page, Integer limit, String cname);
}
