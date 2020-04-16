package xyz.worldzhile.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 监听器
 */

public class ShiroSessionListener implements SessionListener {

    //CAS原理的乐观锁（自旋锁）  compare and swap
    private AtomicInteger count = new AtomicInteger(0);
    @Override
    public void onStart(Session session) {
        System.out.println("新用户上线了！！");
        count.incrementAndGet();
        System.out.println("当前用户数：" + count.intValue());
    }

    @Override
    public void onStop(Session session) {
        System.out.println("一个用户下线了！！");
        count.decrementAndGet();
        System.out.println("当前用户数：" + count.intValue());
    }

    //会话过期时候的方法
    @Override
    public void onExpiration(Session session) {
        System.out.println("一个用户过期！！");
        count.decrementAndGet();
        System.out.println("当前用户数：" + count.intValue());
    }
    //获取当前在线人数
    public AtomicInteger getCount() {
        return count;
    }
}
