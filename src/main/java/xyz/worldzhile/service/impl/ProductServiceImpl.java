package xyz.worldzhile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.dao.ProductDao;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.service.ProductService;
import xyz.worldzhile.util.PageBean;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;
    @Override
    public List<Product> findHot(Integer number) {
        return productDao.findHot(number);
    }

    @Override
    public List<Product> findNew(Integer number) {
        return productDao.findNew(number);
    }

    @Override
    public Product findOneByPid(String pid) {
        return productDao.findByPid(pid);
    }

    @Override
    public PageBean<Product> findPageBean(String cid,Integer currentPage,Integer pageCount ) {
        Integer totalCount = productDao.findTotalCount(cid);
        PageBean<Product> productPageBean = new PageBean<Product>(currentPage, pageCount, totalCount);
        List<Product> pageList = productDao.findPageList(cid,productPageBean.getStart(),productPageBean.getPageCount());
        productPageBean.setList(pageList);
        return productPageBean;
    }


}
