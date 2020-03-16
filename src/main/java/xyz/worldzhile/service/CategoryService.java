package xyz.worldzhile.service;

import xyz.worldzhile.domain.Category;

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
}
