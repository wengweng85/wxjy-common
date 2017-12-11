package com.insigma.common.util;

import java.io.InputStream;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCacheUtil
 *
 * @author wengsh
 * @version 1.0
 * @date 2016-07-23
 */
public class EhCacheUtil {

    private static CacheManager manager;

    static {
        if (manager == null) {
            InputStream in = EhCacheUtil.class.getClassLoader().getResourceAsStream("ehcache-shiro.xml");
            manager = CacheManager.create(in);
        }
    }

    public static CacheManager getManager() {
        return manager;
    }


    public static void setManager(CacheManager manager) {
        EhCacheUtil.manager = manager;
    }
    
    public static Object getParamFromCache(String key){
    	Element element=EhCacheUtil.getManager().getCache("webcache").get(key);
    	if(element!=null){
    		return (Object)element.getValue();
    	}else{
    		return null;
    	}
    }
}