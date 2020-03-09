package xyz.worldzhile.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.dao.CategoryDao;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    @Qualifier( "JedisPool")
    JedisPool jedisPool;
    @Autowired
    CategoryDao categoryDao;
    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category> findAllFromRedis(){
        //对不经常改变的分类进行缓存 减少对数据库的访问次数
        List<Category> list = null;
        Jedis jedis = jedisPool.getResource();
        String categorys = jedis.get(Constant.STORE_CATEGORY_LIST);
        ObjectMapper json = new ObjectMapper();
        if (categorys==null){
            //第一次访问
            System.out.println("数据库中查询...");
             list = categoryDao.findAll();
            try {
                jedis.set(Constant.STORE_CATEGORY_LIST, json.writeValueAsString(list).toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("redis中查询...");
            JavaType javaType = json.getTypeFactory().constructParametricType(List.class, Category.class);
            try {
                list= json.readValue(categorys, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


        jedis.close();
        return list ;
    }

    @Override
    public List<Category> findLunBoTu() {
        return categoryDao.findLunBoTu();
    }

    @Override
    public void update(String cid) {
        categoryDao.update(cid);
    }

}
