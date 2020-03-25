package org.chwin.firefighting.apiserver.fire.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WebSocketService {
    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private RedisTemplate redisTemplate;

    //初始化加载redis数据
    public void initRedisData(String id){
        redisTemplate.opsForValue().set(id, JSONObject.fromObject(getData(id)).toString(),10, TimeUnit.MINUTES);//10分钟失效
        System.out.println(id + ": 连接初始化加载数据......");
    }

    public JSONObject getAll(String id){
        return JSONObject.fromObject(redisTemplate.opsForValue().get(id));
    }


    //获取各项指标数据
    public Map<String,Object> getData(String openId) {
        Map<String, Object> result = new HashMap<>();
        result.put("alarm", todayCount("alarm"));//今日 火警
        result.put("fault", todayCount("fault"));//今日 故障
        result.put("rank", unDealRank());//今日未处理报警数据排行
        result.put("current", currentData());//实时数据 最新5条
        return result;
    }

    //今日 火警或故障数据-总数、待处理、完成处理 type(is_alarm,is_fault)
    public  Map<String,Long> todayCount(String type){
        Map<String,String> param = new HashMap<>();
        param.put(type,"");
        Map<String,Long> mapT = jdbcDao.loadRowByCode("today_data_total",param);
        Map<String,Long> mapD = jdbcDao.loadRowByCode("today_data_deal",param);
        long total = mapT.get("count");
        long deal = mapD.get("count");
        long unDeal = total - deal;
        Map<String,Long> result = new HashMap<>();
        result.put("total",total);
        result.put("deal",deal);
        result.put("unDeal",unDeal);
        return result;
    }


    //今日未处理报警数据排行-取前5降序排列
    public Map<String,Long> unDealRank(){
        List<Map> list = jdbcDao.loadListByCode("un_deal_rank",new HashMap<>());
        Map<String,Long> result = new HashMap<>();
        if(list != null && list.size() >0)
        for(int i=0;i<((list.size()>=5) ? 5 : list.size());i++){
            result.put(list.get(i).get("name") + "",Long.parseLong(list.get(i).get("count").toString()));
        }
        return result;
    }

    //实时数据 最新5条
    public List<Map> currentData(){
        List<Map> list = jdbcDao.loadListByCode("current_data",new HashMap<>());
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
        boolean rankFlag = false;
        boolean flag = false;
        JSONObject alarm = old.getJSONObject("alarm");
        JSONObject fault = old.getJSONObject("fault");
        JSONObject rank = old.getJSONObject("rank");
        JSONArray current = old.getJSONArray("current");

        List<Map> cList = currentData();
        List<String> ids = new ArrayList<>();
        for(int i=0;i<cList.size();i++){
            ids.add(cList.get(i).get("ID") + "");
        }
        for(int i=0;i<current.size();i++){
            JSONObject obj = current.getJSONObject(i);
            if(!ids.contains(obj.get("ID"))){
                currentFlag = true;
                break;
            }
        }
        Map<String, Long> map = unDealRank();
        Set set = rank.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if ( map.containsKey(key) && map.get(key) == rank.getLong(key)) {
                rankFlag = false;
            }else{
                rankFlag = true;
                break;
            }
        }
        if (alarm.getLong("total") == todayCount("alarm").get("total") &&
            alarm.getLong("deal") == todayCount("alarm").get("deal") &&
            fault.getLong("total") == todayCount("fault").get("total") &&
            fault.getLong("deal") == todayCount("fault").get("deal")) {
            //System.out.println(id + ":今天火警、故障-数据没变化");
        }else{
            flag = true;
        }
        if (flag || rankFlag || currentFlag) {
            Map<String, Object> result = new HashMap<>();
            result.put("alarm", todayCount("alarm"));//今日 火警
            result.put("fault", todayCount("fault"));//今日 故障
            result.put("rank", unDealRank());//今日未处理报警数据排行
            result.put("current", currentData());//实时数据 最新5条
            redisTemplate.opsForValue().set(id, JSONObject.fromObject(result).toString(), 10, TimeUnit.MINUTES);//10分钟失效
            System.out.println(id + ":redis缓存更新了");
            change = true;
        }
        return change;
    }
}
