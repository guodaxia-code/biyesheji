package xyz.worldzhile.service;

import xyz.worldzhile.util.PageBean;
import xyz.worldzhile.domain.Product;

import java.util.List;

public interface ProductService {
    /**
     *  主页上的热门商品
     */
    List<Product> findHot(Integer number);

    /**
     *  主页上的推荐商品
     */
    List<Product> findNew(Integer number);

    /**
     * 根据pid获取商品
     */
    Product findOneByPid(String pid);


    /*
       按照商品分类获取分页商品列表
     */
     PageBean<Product> findPageBean(String cid,Integer currentPage,Integer pageCount);

}
