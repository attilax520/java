package org.chwin.firefighting.apiserver.core.spring.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liming on 2017/9/20.
 */

public class ThreadVariable {
    private final static Logger logger = LoggerFactory.getLogger(ThreadVariable.class);
    private static ThreadVariable instance=null;
    private ThreadLocal<Map<Object, Object>> threadLocal;

    public static synchronized ThreadVariable getInstance() {
        if (instance == null) {
logger.info("ThreadLocal instance");
            instance = new ThreadVariable();
        }
        return instance;
    }

    public ThreadLocal<Map<Object,Object>> getThreadLocal(){
        if(threadLocal==null){
logger.info("ThreadLocal null");
            threadLocal=new ThreadLocal();
logger.info("ThreadLocal created");
            threadLocal.set(new HashMap());
        }
        return threadLocal;
    }

    public void put(String key,Object value){
        Map map = (Map)this.getThreadLocal().get();
        if(map==null) map = new HashMap();
        map.put(key, value);
        this.getThreadLocal().set( map);
    }

    public Object get(String key){
        Map map = (Map)this.getThreadLocal().get();
        if(map==null) return null;
        return map.get(key);
    }

    public void remove(String key){
        Map map = (Map)this.getThreadLocal().get();
        map.remove(key);
        this.getThreadLocal().set(map);
    }

    public void clear(){
        Map map = (Map)this.getThreadLocal().get();
        map.clear();
        this.getThreadLocal().set(map);
    }

}
