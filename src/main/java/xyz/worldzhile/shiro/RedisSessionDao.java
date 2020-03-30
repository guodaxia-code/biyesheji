package xyz.worldzhile.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component("sessiondao")
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    @Qualifier("JedisUtils")
    JedisUtils jedisUtils;

    private String shiro_session_prefix="store-session:";

    private  void saveSession(Session session){
        if (session!=null&&session.getId()!=null){
            byte[] key = getKey(session.getId().toString());
            byte[] value= SerializationUtils.serialize(session);
            jedisUtils.set(key,value);
            jedisUtils.expire(key,60*15);

        }
    }

    private byte[] getKey(String key){
        return  (shiro_session_prefix+key).getBytes();
    }

    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("创建了session"+session);
        Serializable sessionId = generateSessionId(session);//获取sessionid
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("读session："+sessionId);
        if (sessionId==null)
        return null;
        byte[] key = getKey(sessionId.toString());
        byte[] value=jedisUtils.get(key);
        return (Session) SerializationUtils.deserialize(value);

    }

    @Override
    public void update(Session session) throws UnknownSessionException {
    saveSession(session);
    }

    @Override
    public void delete(Session session) {
    if (session==null||session.getId()==null){
        return;
    }
        byte[] key = getKey(session.getId().toString());
        jedisUtils.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys=jedisUtils.keys(shiro_session_prefix);
        Set<Session> sessions=new HashSet<>();
        if (CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for(byte[] key:keys){
            Session session= (Session) SerializationUtils.deserialize(jedisUtils.get(key));
            sessions.add(session);
        }
        return sessions;
    }
}
