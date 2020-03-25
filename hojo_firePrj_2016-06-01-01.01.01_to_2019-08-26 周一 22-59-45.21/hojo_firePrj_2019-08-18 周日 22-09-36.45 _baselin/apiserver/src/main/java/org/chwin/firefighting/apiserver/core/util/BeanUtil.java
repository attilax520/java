package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liming on 2017/9/18.
 */
public class BeanUtil {
    public static Map cpyProp(Map source,String keys){
        Map target=new HashMap();
        String[] ks= StringUtils.split(keys,",");
        for(int i=0;i<ks.length;i++){
            target.put(ks[i],source.get(ks[i]));
        }
        return target;
    }

    public static String list2Str(List list){
        StringBuffer buffer=new StringBuffer("");
        int i=0;
        for(Object item : list){
            if(i>0)
                buffer.append(",");
            buffer.append(item);
            i++;
        }
        return buffer.toString();
    }

    public static boolean isInstance(Object obj, Class clazz) {
        return clazz.isInstance(obj);
    }

    public static <T> T map2Ent(Map map,Class<T> clz){
        try {
            T t= clz.newInstance();
            BeanUtils.populate(t,map);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 两个Map比较
     * <p>将map中相同key，不同值提取</p>
     * @param param1 比较参数map1
     * @param param2 比较参数map2
     * @return map比较结果集
     */
    public static Map<String,Object> compareMap(Map<String, Object> param1, Map<String, Object> param2) {
        Map<String,Object> resultMap = new HashMap<>(16);
        Map<String, Object> oldMap = new HashMap<>(16);
        Map<String, Object> newMap = new HashMap<>(16);
        for (Map.Entry<String, Object> entry1:param1.entrySet()) {
            String column = entry1.getKey();
            Object argValue = entry1.getValue() == null ? "" : entry1.getValue();
            Object resultValue = param2.get(entry1.getKey()) == null ? "" : param2.get(entry1.getKey());
            if (!argValue.equals(resultValue)) {
                newMap.put(column, argValue);
                oldMap.put(column, resultValue);
            }
        }
        resultMap.put("oldMap", oldMap);
        resultMap.put("newMap", newMap);
        return resultMap;
    }


    public static List list2Tree(List list){
        List nodes=new ArrayList();
        Map nodeMap=new HashMap();
        list.forEach(o->{
            nodeMap.put(((Map)o).get("ID"),o);
        });

        nodeMap.forEach((key,node)->{
            String pid=(String)((Map)node).get("PARENT_ID");
            Map pnode=(Map)nodeMap.get(pid);
            if(pnode==null){
                nodes.add(node);
            }else{
                List children=(List)pnode.get("children");
                if(children==null) {
                    children = new ArrayList();
                    pnode.put("children",children);
                }
                children.add(node);
            }
        });

        return nodes;
    }
}
