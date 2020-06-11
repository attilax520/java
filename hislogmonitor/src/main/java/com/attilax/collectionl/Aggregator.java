package com.attilax.collectionl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * 聚合操作
 *
 * Created by Brandon on 2016/7/21.
 */
public interface Aggregator<T> {

    /**
     * 每一组的聚合操作
     *
     * @param key 组别标识key
     * @param values 属于该组的元素集合
     * @return
     */
    Object aggregate(Object key , Collection<T> list_map);

 

 

	 
}