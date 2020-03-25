package org.chwin.firefighting.apiserver.fire.service;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import okhttp3.Response;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.chwin.firefighting.apiserver.core.util.HttpclientUtilsV2;
import org.chwin.firefighting.apiserver.core.util.OkHttpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceInfoService {

    @Resource
    private JdbcDao jdbcDao;

    @Resource
    private ScriptComponent scriptCmp;

    public Map saveDeviceInfo(Map param, String url){
        Map params = new HashMap();
        params.put("db",jdbcDao);
        params.put("prm",param);
        Map result = (Map) scriptCmp.excute(getRuleByUrl(url),params);
        return result;
    }


    /**
     * 跳转URL对应的js文件
     * @param url
     * @return
     */
    private String getRuleByUrl(String url){
        return "pub.rpt.iot."+url;
    }

    //本地测试地址
    private final String hardwareUrl = "http://192.168.1.33:8655/hardware-devices/";
    //服务器地址
    //private final String hardwareUrl = "http://192.168.98.80:8655/hardware-devices/";

    public Map hardwareDevices_position(Map inputParam){
        String app_id = (String)inputParam.get("app_id");
        String device_code = (String)inputParam.get("device_code");

        String url = hardwareUrl+app_id+"/"+device_code;

        Map result = new HashMap();
        Date start_time = new Date();
        Response response = null;
        JSONObject json = new JSONObject();
        json.put("data","91");
        try {
            response = OkHttpUtil.postJson(url,json.toString());
            String resultS = response.body().string();
            System.out.println(response.code()+"======resultS:"+resultS);
            JSONObject resultJson = JSONObject.fromObject(resultS);
            String r = (String) resultJson.get("result");
            if (response.code()==200 && "success".equals(r)){
                result.putAll(resultJson);
            }else{
                throw new BusinessException((String) resultJson.get("desc"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        Date end_time = new Date();
        long time_diff = end_time.getTime() - start_time.getTime();
        result.put("time_diff",time_diff);
        return result;
    }

    public Map hardwareDevices_routing(Map inputParam){
        String app_id = (String)inputParam.get("app_id");
        String device_code = (String)inputParam.get("device_code");

        String url = hardwareUrl+app_id+"/"+device_code;

        Map result = new HashMap();
        Response response = null;
        JSONObject json = new JSONObject();
        json.put("data","81");
        try {
            response = OkHttpUtil.postJson(url,json.toString());
            String resultS = response.body().string();
            System.out.println(response.code()+"======resultS:"+resultS);
            JSONObject resultJson = JSONObject.fromObject(resultS);
            String r = (String) resultJson.get("result");
            if (response.code()==200 && "success".equals(r)){
                result.putAll(resultJson);
                result.put("machine_info",jdbcDao.loadRT("DATA_MACHINE_INFO",(String)inputParam.get("device_id")));
            }else{
                throw new BusinessException((String) resultJson.get("desc"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return result;
    }
}
