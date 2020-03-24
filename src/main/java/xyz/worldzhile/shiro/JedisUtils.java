package xyz.worldzhile.shiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

@Component("JedisUtils")
public class JedisUtils {


    @Autowired
     @Qualifier("JedisPool")
    JedisPool JedisPool;

    private Jedis getJedis(){
       return JedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            return  value;
        }finally {
            jedis.close();
        }


    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, i); //设置秒

        }finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        try {

            return  jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
            Jedis jedis = getJedis();
        try {
           return jedis.keys((shiro_session_prefix+"*").getBytes());
        }finally {
            jedis.close();
        }
    }
}
