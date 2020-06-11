package com.attilax.collectionl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.attilax.util.jsonuti4fc;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Collection分组工具类
 */
public class GroupbyUtil {
	
	
	public static void main(String[] args) {
		List<Map> li = Lists.newArrayList();
		li.add(new Function<Object, Map>() {

			@Override
			public Map apply(Object t) {
				Map m = Maps.newConcurrentMap();
				m.put("c1", "c1v");	m.put("cate", "ct1");
				m.put("rowid", "r2");
				return m;
			}
		}.apply(null));
		li.add(new Function<Object, Map>() {

			@Override
			public Map apply(Object t) {
				Map m = Maps.newConcurrentMap();
				m.put("c1", "c1v3");	m.put("cate", "ct2");
				m.put("rowid", "r3");
				return m;
			}
		}.apply(null));

		li.add(new Function<Object, Map>() {

			@Override
			public Map apply(Object t) {
				Map m = Maps.newConcurrentMap();
				m.put("c1", "c1v2");m.put("cate", "ct1");
				m.put("rowid", "r1");
				return m;
			}
		}.apply(null));
		
		Aggregator aggregator=new CountAggregator();;
	Object rzt=	GroupbyUtil.groupByProperty(li, "cate", aggregator,"cnts");
	System.out.println(JSON.toJSONString(rzt));
	}


    public static List<Map> groupByProperty(List<Map> listToDeal, String groupbyCol, Aggregator aggregator,String aggredShowColName) {
    	  Map<Object, Collection> groupResult = new HashMap<Object, Collection>();

          for (Map ele : listToDeal) {
             
              Object key = groupbyCol;
              Object val=ele.get(groupbyCol);
              if (!groupResult.containsKey(val)) {
                  groupResult.put(val, new ArrayList<Map>());
              }
              groupResult.get(val).add(ele);
          }


          return (List<Map>) invokeAggregators(groupResult, aggregator,groupbyCol,aggredShowColName);
		
	}

//   groupResult>>key,list
	private static Object invokeAggregators(Map<Object, Collection> groupResult, Aggregator aggregator, String groupbyCol, String aggredShowColName) {
		List rzt=Lists.newArrayList();
		 
	        for (Object colVal : groupResult.keySet()) {
	            Collection  group_list = groupResult.get(colVal);
	            Object aggValues = aggregator.aggregate(colVal, group_list);
	            Map m=Maps.newConcurrentMap();
	            m.put(groupbyCol,colVal);
	            m.put(aggredShowColName, aggValues);
	            rzt.add(m);
	        }
	        return rzt;

	}
	
	
    private static <T> Object doInvokeAggregators_getAggredVal(Object key, Collection<Map> group, Aggregator<Map> aggregator) {
       Object  aggResults =new Object();

      

            // 调用当前key的每一个聚合函数
       aggResults = aggregator.aggregate(key, group);
				
			 

        return aggResults;

    }


	/**
     * 分组聚合
     *
     * @param listToDeal    待分组的数据，相当于SQL中的原始表
     * @param clazz         带分组数据元素类型
     * @param groupBy       分组的属性名称
     * @param aggregatorMap 聚合器，key为聚合器名称，作为返回结果中聚合值map中的key
     * @param <T>           元素类型Class
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> Map<Object, Map<String, Object>> groupByProperty(
            Collection<T> listToDeal, Class<T> clazz, String groupBy,
            Map<String, Aggregator<T>> aggregatorMap) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Map<Object, Collection<T>> groupResult = new HashMap<Object, Collection<T>>();

        for (T ele : listToDeal) {
            Field field = clazz.getDeclaredField(groupBy);
            field.setAccessible(true);
            Object key = field.get(ele);

            if (!groupResult.containsKey(key)) {
                groupResult.put(key, new ArrayList<T>());
            }
            groupResult.get(key).add(ele);
        }


        return invokeAggregators(groupResult, aggregatorMap);
    }


    public static <T> Map<Object, Map<String, Object>> groupByMethod(
            Collection<T> listToDeal, Class<T> clazz, String groupByMethodName,
            Map<String, Aggregator<T>> aggregatorMap) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Map<Object, Collection<T>> groupResult = new HashMap<Object, Collection<T>>();

        for (T ele : listToDeal) {
            Method groupByMenthod = clazz.getDeclaredMethod(groupByMethodName);
            groupByMenthod.setAccessible(true);
            Object key = groupByMenthod.invoke(ele);

            if (!groupResult.containsKey(key)) {
                groupResult.put(key, new ArrayList<T>());
            }
            groupResult.get(key).add(ele);
        }


        return invokeAggregators(groupResult, aggregatorMap);
    }

 
    private static <T> Map<Object, Map<String, Object>> invokeAggregators(Map<Object, Collection<T>> groupResult, Map<String, Aggregator<T>> aggregatorMap) {

        Map<Object, Map<String, Object>> aggResults = new HashMap<>();
        for (Object key : groupResult.keySet()) {
            Collection<T> group = groupResult.get(key);
            Map<String, Object> aggValues = doInvokeAggregators(key, group, aggregatorMap);
            if (aggValues != null && aggValues.size() > 0) {
                aggResults.put(key, aggValues);
            }

        }
        return aggResults;

    }


    private static <T> Map<String, Object> doInvokeAggregators(Object key, Collection<T> group, Map<String, Aggregator<T>> aggregatorMap) {
        Map<String, Object> aggResults = new HashMap<String, Object>();

        if (group != null && group.size() > 0) {

            // 调用当前key的每一个聚合函数
            for (String aggKey : aggregatorMap.keySet()) {
                Aggregator<T> aggregator = aggregatorMap.get(aggKey);
                Object aggResult = aggregator.aggregate(key, Collections.unmodifiableList(new ArrayList<T>(group)));
                aggResults.put(aggKey, aggResult);
            }

        }

        return aggResults;

    }

}