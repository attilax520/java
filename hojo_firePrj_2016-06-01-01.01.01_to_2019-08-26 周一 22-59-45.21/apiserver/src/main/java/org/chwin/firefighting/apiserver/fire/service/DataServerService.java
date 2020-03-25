package org.chwin.firefighting.apiserver.fire.service;

/*import com.hy.apiclient.client.ApiClient;
import com.hy.apiclient.client.ClientInfo;
import com.hy.apiclient.dto.AppDTO;
import com.hy.apiclient.dto.MyResponseDTO;
import com.hy.apiclient.dto.OperateAppInDTO;
import com.hy.apiclient.dto.QueryAppInDTO;
import com.hy.apiclient.invokeapi.App;*/
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.Response;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.core.util.OkHttpUtil;
import org.chwin.firefighting.apiserver.core.util.OpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DataServerService {

    @Autowired
    private JdbcDao jdbcDao;

    private String getAppUrl(String tenant_id){
        return SpringUtil.getProp("dataserver.url")+"v3/tenants/"+tenant_id+"/apps";
    }

    public Map addApp(Map param){
        System.out.print(param);
        Response response = null;
        String tenant_id = "";
        Map loginInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if (loginInfo != null && loginInfo.containsKey(CONSTANTS.TENANT_ID)) {
            tenant_id = (String) loginInfo.get(CONSTANTS.TENANT_ID);
        }
        if ("".equals(tenant_id)){
            throw new BusinessException("请确认操作者属于一个有效的租户!");
        }
        String url = getAppUrl(tenant_id);
        try {
            JSONObject object = JSONObject.fromObject(param);
            response = OkHttpUtil.postJson(url,object.toString());
            System.out.println(response.code()+"==="+response.body().string());
            if (!response.isSuccessful()){
                if (response.body()!=null){
                    throw new BusinessException(response.body().string());
                }else{
                    throw new BusinessException("数据交互失败!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return param;
    }

    public Map getAppList(Map params){
        Map result = new HashMap();
        Response response = null;
        String tenant_id = "";
        Map loginInfo = jdbcDao.loadRowByCode("SELECT_USER_INFO_BY_SSID", params);
//        Map loginInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if (loginInfo != null && loginInfo.containsKey(CONSTANTS.TENANT_ID)) {
            tenant_id = (String) loginInfo.get(CONSTANTS.TENANT_ID);
        }
        if ("".equals(tenant_id)){
            throw new BusinessException("请确认操作者属于一个有效的租户!");
        }
        String url = getAppUrl(tenant_id);;
        try {
            response = OkHttpUtil.doGet(url);
            if (response.isSuccessful()){
                JSONArray array = JSONArray.fromObject(response.body().string());
                result.put("list",array);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

    /**============================使用通用方法,调用http请求,并有jsonschema验证==============================**/

    /*public Map addAppV4(Map param) {
        System.out.print("新增： "+param);
        Response response = null;
        String tenant_id = "";
//        Map loginInfo = jdbcDao.loadRowByCode("SELECT_USER_INFO_BY_SSID", params);
        Map loginInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if (loginInfo != null && loginInfo.containsKey(CONSTANTS.TENANT_ID)) {
            tenant_id = (String) loginInfo.get(CONSTANTS.TENANT_ID);
        }
        if ("".equals(tenant_id)) {
            throw new BusinessException("请确认操作者属于一个有效的租户!");
        }
        param.put("ip", SpringUtil.getProp("dataserver.app.ip"));
        param.put("port", Integer.valueOf(SpringUtil.getProp("dataserver.app.port")));
        param.put("tenant_id", tenant_id);
        param.put("app_id", "");
        param.put("app_secret", "");
        ApiClient apiClient = OpenApi.getInstance(param);
        String accessToken = "";
        App app = new App(apiClient);
        AppDTO appDTO = new AppDTO();
        appDTO.setName((String) param.get("name"));
        appDTO.setDesc((String) param.get("desc"));
        List<AppDTO> appDTOList = new ArrayList<>();
        appDTOList.add(appDTO);
        OperateAppInDTO operateAppInDTO = new OperateAppInDTO();
        operateAppInDTO.setCreate(appDTOList);
        MyResponseDTO myResponseDTO = null;
        try {
            myResponseDTO = app.operate(operateAppInDTO, accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        System.out.println(myResponseDTO.toString());

        if (myResponseDTO.getHttpStatus() != 201) {
            throw new BusinessException(myResponseDTO.getBody());
        }
        return param;
    }

    public Map getAppListV4(Map params){
        Map result = new HashMap();
        Map<String,Object> param = new HashMap<>();
        String tenant_id = "";
        Map loginInfo = jdbcDao.loadRowByCode("SELECT_USER_INFO_BY_SSID", params);
        System.out.println(loginInfo);
//        Map loginInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if (loginInfo != null && loginInfo.containsKey(CONSTANTS.TENANT_ID)) {
            tenant_id = (String) loginInfo.get(CONSTANTS.TENANT_ID);
        }
        if ("".equals(tenant_id)){
            throw new BusinessException("请确认操作者属于一个有效的租户!");
        }
        param.put("ip",SpringUtil.getProp("dataserver.app.ip"));
        param.put("port",Integer.valueOf(SpringUtil.getProp("dataserver.app.port")));
        param.put("tenant_id",tenant_id);
        param.put("app_id","");
        param.put("app_secret","");
        ApiClient apiClient = OpenApi.getInstance(param);
        String accessToken = "";
        App app = new App(apiClient);
        QueryAppInDTO queryAppInDTO = new QueryAppInDTO();
        MyResponseDTO myResponseDTO = app.getList(queryAppInDTO, accessToken);
        System.out.println(myResponseDTO.toString());
        if (myResponseDTO.getHttpStatus() == 200){
            JSONArray array = JSONArray.fromObject(myResponseDTO.getBody());
            result.put("list",array);
        }
        return result;
    }*/

}

