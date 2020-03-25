package org.chwin.firefighting.apiserver.core.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;

/**
 * Created by liming on 2017/9/26.
 */
public class CacheUtil {
    private static CacheUtil _instance=null;
    private CacheManager cacheManager ;// new CacheManager();
    private Cache cache ;//= cacheManager.getCache("local");
    public static synchronized CacheUtil instance(){
        if(_instance==null)
            _instance=new CacheUtil();
        return _instance;
    }

    public CacheUtil(){
        Resource resource = new ClassPathResource("ehcache.xml");
        try {
            URL url = resource.getURL();
            cacheManager = new CacheManager(url);
            cache = cacheManager.getCache("local");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getKey(String key){
        Element element = cache.get(key);
        if(element==null)
            return null;

        return element.getObjectValue();
    }


    public void putKey(String key,Object value){
        cache.put(new Element(key,value));
    }

    public void removeKey(String key){
        cache.remove(key);
    }

}
