package xyz.worldzhile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.dao.CategoryDao;
import xyz.worldzhile.dao.ProductDao;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.service.ProductService;
import xyz.worldzhile.util.PageBean;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    CategoryDao categoryDao;
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



    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public PageBean<Product> findAllByLayuiByPage(Integer page, Integer limit, String pname) {
        System.out.println(pname);

        int count = productDao.findCountByCname(pname);
        System.out.println(count+"dao");
        PageBean<Product> pageBean = new PageBean<Product>(page,limit,count);

        List<Product> allByLayuiByPage = productDao.findAllByLayuiByPage(pageBean.getStart(), pageBean.getPageCount(),pname);

        System.out.println(allByLayuiByPage.toString());
        pageBean.setList(allByLayuiByPage);


        return pageBean;
    }

    @Override
    public PageBean<Product> findAllByLayuiByPageOrderByPrice(Integer page, Integer limit, String pname, String pricesort) {
        System.out.println(pname);

        System.out.println(pricesort+"排序方式--------------------------------------------------------------");

        int count = productDao.findCountByCname(pname);
        System.out.println(count+"dao");
        PageBean<Product> pageBean = new PageBean<Product>(page,limit,count);
        List<Product> allByLayuiByPage=null;
            if (pricesort.equals("no")){
                allByLayuiByPage=productDao.findAllByLayuiByPage(pageBean.getStart(), pageBean.getPageCount(),pname);
            }else {
                allByLayuiByPage = productDao.findAllByLayuiByPageOrderByPrice(pageBean.getStart(), pageBean.getPageCount(),pname,pricesort);
            }


        System.out.println(allByLayuiByPage.toString());
        pageBean.setList(allByLayuiByPage);


        return pageBean;
    }


    @Override
    public void updatePicture(Product product) {
        Product byPid = productDao.findByPid(product.getPid());
        byPid.setPpicture(product.getPpicture());
        productDao.updatePicture(byPid);
    }

    @Override
    public void updateProduct(Product product) {
        Product byPid = productDao.findByPid(product.getPid());
        byPid.setCategory(categoryDao.findOneByCid(product.getCategory().getCid()));
        System.out.println(product.getCategory().getCid()+"------");
        byPid.setIs_hot(product.getIs_hot());
        byPid.setPdesc(product.getPdesc());
        System.out.println(product.getPdesc());
        byPid.setPname(product.getPname());
        byPid.setPflag(product.getPflag());
        byPid.setRel_price(product.getRel_price());
        byPid.setStore_price(product.getStore_price());
        productDao.updatePicture(byPid);
    }

    @Override
    public void add(Product product) {
        System.out.println(product);
        productDao.insert(product);
    }

    @Override
    public void del(String pid) {
        productDao.del(pid);
    }


}
