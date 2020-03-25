package org.chwin.firefighting.apiserver.fire.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WisdomSocketService {
    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private RedisTemplate redisTemplate;

    //初始化加载redis数据
    public void initRedisData(String id){
        redisTemplate.opsForValue().set(id, JSONObject.fromObject(getData()).toString(),10, TimeUnit.MINUTES);//10分钟失效
        System.out.println(id + ": 连接初始化加载数据......");
    }

    public JSONObject getAll(String id){
        return JSONObject.fromObject(redisTemplate.opsForValue().get(id));
    }


    //获取各项指标数据
    public Map<String,Object> getData() {
        Map<String, Object> result = new HashMap<>();
        result.put("alarm", todayCount());//
        result.put("current", currentData());//实时数据 最新5条
        return result;
    }

    //今日 插座超阈值报警、断电报警统计
    public  Map<String,BigDecimal> todayCount(){
        Map<String,String> param = new HashMap<>();
        Map<String, BigDecimal> map = jdbcDao.loadRowByCode("socket_alarm_count",param);
        return map;
    }


    //实时数据 最新5条
    public List<Map> currentData(){
        List<Map> list = jdbcDao.loadListByCode("socket_top_five",new HashMap<>());
        return list;
    }

    //判断缓存数据是否改变
    public boolean updateRedis(String id) {
        boolean change = false;
        if(!redisTemplate.hasKey(id)){
            initRedisData(id);
            System.out.println("失效时间已到，重新初始化数据！");
        }
        JSONObject old = JSONObject.fromObject(redisTemplate.opsForValue().get(id));
        boolean currentFlag = false;
        boolean flag = false;
        JSONObject alarm = old.getJSONObject("alarm");
        JSONArray current = old.getJSONArray("current");

        List<Map> cList = currentData();
        List<String> ids = new ArrayList<>();
        for(int i=0;i<cList.size();i++){
            ids.add(cList.get(i).get("id") + "");
        }
        for(int i=0;i<current.size();i++){
            JSONObject obj = current.getJSONObject(i);
            if(!ids.contains(obj.get("id"))){
                currentFlag = true;
                break;
            }
        }
        Map<String, BigDecimal> map = todayCount();
        long threshold = map.get("threshold").longValue();
        long outage = map.get("outage").longValue();
        if (    alarm.getLong("threshold") == threshold &&
                alarm.getLong("outage") == outage) {
            //System.out.println(id + ":今天火警、故障-数据没变化");
        }else{
            flag = true;
        }
        if (flag || currentFlag) {
            Map<String, Object> result = new HashMap<>();
            result.put("alarm", todayCount());//今日 火警
            result.put("current", currentData());//实时数据 最新5条
            redisTemplate.opsForValue().set(id, JSONObject.fromObject(result).toString(), 10, TimeUnit.MINUTES);//10分钟失效
            System.out.println(id + ":redis缓存更新了");
            change = true;
        }
        return change;
    }
}
