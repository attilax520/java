package org.chwin.firefighting.apiserver.fire.service;

import net.sf.json.JSONObject;
import okhttp3.Response;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.OkHttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HardwareService {

    @Resource
    private JdbcDao jdbcDao;

    /**
     * 统一下发指令接口
     * @param inputParam
     * @return
     */
    public Map hardwareDevices(Map inputParam,String url,Map<String, Long> timeoutMap){
        Response response = null;
        JSONObject json = new JSONObject();
        //根据不同的指令,做出不同的动作
        json.put("data",inputParam.get("data"));
        try {
            response = OkHttpUtil.postJson(url,json.toString(),timeoutMap.get("connnectTimeout"),timeoutMap.get("readTimeout"));
            String resultString = response.body().string();
            System.out.println(response.code()+"======resultString:"+resultString);
            JSONObject resultJson = JSONObject.fromObject(resultString);
            String r = (String) resultJson.get("result");
            if (response.code() == 200 && "success".equals(r)){
                return resultJson;
            }else{
                throw new BusinessException(resultString);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JSONObject err = new JSONObject();
            err.put("result","exception");
            err.put("desc",e.getMessage());
            throw new BusinessException(err.toString());
        }
    }

    public Map hardwareDevicesRouting(Map param){
        String app_id = (String)param.get("app_id");
        String device_code = (String)param.get("device_code");
        Integer timeout = (Integer)param.get("timeout");
        //巡检下发,同步
        String url = SpringUtil.getProp("hardware.devices_url")+app_id+"/"+device_code+"/"+timeout;
        //下发指令,更新数据库记录状态
        Map<String, Long> timeoutMap = new HashMap();
        timeoutMap.put("connnectTimeout", (long) 10);
        timeoutMap.put("readTimeout", (long) 120);
        Map result = hardwareDevices(param,url,timeoutMap);

        //获得记录状态
        List list = jdbcDao.loadListByCode("qry_data_machine_info_list",param);
        if ( list==null || list.size() <= 0){
            JSONObject err = new JSONObject();
            err.put("result","exception");
            err.put("desc","查询不到用传设备信息");
            throw new BusinessException(err.toString());
        }
        Map params = new HashMap();
        params.put("record_type",1);
        params.put("machine_info_id",((Map)list.get(0)).get("id"));
        List recordList = jdbcDao.loadListByCode("machine_record_list",params);
        result.put("routing_times",recordList.size()); //当前巡检次数
        Map p = new HashMap();
        if(recordList!=null && recordList.size()>0) {
            Map map = (Map) recordList.get(0);
            p.put("run_status", map.get("run_status"));
            p.put("fire_status", map.get("fire_status"));
            p.put("fault_status", map.get("fault_status"));
            p.put("electric_status", map.get("electric_status"));
            p.put("electric_status2", map.get("electric_status2"));
            p.put("channel_status", map.get("channel_status"));
            p.put("line_status", map.get("line_status"));
        }else{
            p.put("run_status", null);
            p.put("fire_status", null);
            p.put("fault_status", null);
            p.put("electric_status", null);
            p.put("electric_status2", null);
            p.put("channel_status", null);
            p.put("line_status", null);
        }
        result.put("machine_info",p);
        return result;
    }

    public Map hardwareDevicesInspection(Map param){
        List list = jdbcDao.loadListByCode("qry_data_machine_info_list",param);
        if ( list==null || list.size() <= 0){
            JSONObject err = new JSONObject();
            err.put("result","exception");
            err.put("desc","查询不到用传设备信息");
            throw new BusinessException(err.toString());
        }

        String app_id = (String)param.get("app_id");
        String device_code = (String)param.get("device_code");
        //查岗下发,异步
        String url = SpringUtil.getProp("hardware.commanddown_url")+app_id+"/"+device_code;
        //下发指令
        Map<String, Long> timeoutMap = new HashMap();
        timeoutMap.put("connnectTimeout", (long) 10);
        timeoutMap.put("readTimeout", (long) 60);
        Map result = hardwareDevices(param,url,timeoutMap);
        //更新数据库,查岗状态和时间
        Map entity = new HashMap();
        entity.put("ID",((Map)list.get(0)).get("id"));
        entity.put("INSPECTION_STATUS",0); //状态
        Date now = new Date();
        entity.put("INSPECTION_TIME",now); //查岗时间
        entity.put("INSPECTION_RES", null); //查岗响应时间
        jdbcDao.update("DATA_MACHINE_INFO",entity);

        result.put("inspection_status",0);
        result.put("inspection_time",new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(now));
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> hardwareDevicesInspectionTime(Map<String,String> data){
        JSONObject result = new JSONObject();
        //校验用传信息,根据app_id和device_code
        List list = jdbcDao.loadListByCode("qry_data_machine_info_list",data);
        if ( list==null || list.size() <= 0){
            result.put("code","fail");
            result.put("errmsg","查询不到用传设备信息!");
            return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //校验用传是否下发查岗指令,并处于等待中
        if (((Map)list.get(0)).get("inspection_status") == null ||
                (int)((Map)list.get(0)).get("inspection_status") != 0){
            result.put("code","fail");
            result.put("errmsg","该装置未下发查岗指令!");
            return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //更新数据库,查岗状态和时间
        Map entity = new HashMap();
        entity.put("ID",((Map)list.get(0)).get("id"));
        entity.put("INSPECTION_STATUS",1); //状态
        Date start_time = (Timestamp) ((Map) list.get(0)).get("inspection_time");
        Date end_time = new Date();
        entity.put("INSPECTION_RES", String.valueOf(end_time.getTime() - start_time.getTime())); //查岗响应时间
        jdbcDao.update("DATA_MACHINE_INFO",entity);

        //使用websocket推送
        try {
            JSONObject json = new JSONObject();
            json.put("type","InspectionTime");//用于给前端区分不同的数据
            json.put("data",entity);//实际推送数据
            Response response = OkHttpUtil.postJson(SpringUtil.getProp("websocket.url"),json.toString());
            if (!(response.code() == 200)){
                result.put("code","fail");
                result.put("errmsg","websocket服务调用失败!");
                return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("errmsg",e.getMessage());
            return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        result.put("code","success");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    public Map hardwareSyncInspection(Map param){
        List list = jdbcDao.loadListByCode("qry_data_machine_info_list",param);
        if ( list==null || list.size() <= 0){
            JSONObject err = new JSONObject();
            err.put("result","exception");
            err.put("desc","查询不到用传设备信息");
            throw new BusinessException(err.toString());
        }

        String app_id = (String)param.get("app_id");
        String device_code = (String)param.get("device_code");Integer timeout = (Integer)param.get("timeout");
        //查岗下发,同步
        String url = SpringUtil.getProp("hardware.devices_url")+app_id+"/"+device_code+"/"+timeout;
        //下发指令
        Map<String, Long> timeoutMap = new HashMap();
        timeoutMap.put("connnectTimeout", (long) 10);
        timeoutMap.put("readTimeout", (long) 120);
        Date start_time = new Date();
        Map result = hardwareDevices(param,url,timeoutMap);
        Date end_time = new Date();
        long time_diff = end_time.getTime() - start_time.getTime();
        //更新数据库,查岗状态和时间
        Map entity = new HashMap();
        entity.put("ID",((Map)list.get(0)).get("id"));
        entity.put("INSPECTION_STATUS",1); //状态
        entity.put("INSPECTION_TIME",start_time); //查岗时间
        entity.put("INSPECTION_RES", time_diff); //查岗响应时间
        jdbcDao.update("DATA_MACHINE_INFO",entity);

        result.put("inspection_status",1);
        result.put("inspection_time",new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(start_time));
        result.put("inspection_res",time_diff);
        return result;
    }
}
