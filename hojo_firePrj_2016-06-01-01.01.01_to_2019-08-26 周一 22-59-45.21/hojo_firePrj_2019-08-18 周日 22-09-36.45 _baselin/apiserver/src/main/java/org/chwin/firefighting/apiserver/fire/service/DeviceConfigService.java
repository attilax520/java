package org.chwin.firefighting.apiserver.fire.service;

import net.sf.json.JSONObject;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.chwin.firefighting.apiserver.core.util.ReadExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class DeviceConfigService {
    @Autowired
    private ScriptComponent scriptCmp;
    @Autowired
    private JdbcDao jdbcDao;

    public Object doProcess(Map param){
        Map params = new HashMap();
        String url = (String)param.get("url");
        params.put("db", jdbcDao);
        params.put("prm", param);
        Object rtmap =  scriptCmp.excute(getRuleByUrl(url), params);
        return rtmap;
    }
    public Map<Integer, Map<Integer,Object>> addCustomerInfo(MultipartFile file) {
        Map<Integer, Map<Integer,Object>> map;
        Map<Object,Object> param;
        List<String> list = new ArrayList<>();
        try {
            map = ReadExcelUtil.readExcelContentz(file);
            for(int i=1;i<=map.size();i++){
                if(i==1){
                    Map<Integer,Object> title = map.get(i);
                    for(int j=0;j<title.size();j++){
                        list.add((String) title.get(j));
                    }
                }else{
                    Map<Integer,Object> data = map.get(i);
                    param = new HashMap<>();
                    for(int k=0;k<data.size();k++){
                        if(list.get(k).equals("create_time")){
                            param.put("CREATE_TIME",11);
                        }else{
                            param.put(list.get(k),data.get(k));
                        }
                    }
                    jdbcDao.add("DATA_DEVICE",param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //excel数据存在map里，map.get(0).get(0)为excel第1行第1列的值，此处可对数据进行处理
        return null;
    }


    public JSONObject addQrCode(MultipartFile file) {
        JSONObject obj = new JSONObject();
        Map<Integer, Map<Integer,Object>> map;
        Map<Object,Object> param;
        List<String> list = new ArrayList<>();
        try {
            map = ReadExcelUtil.readExcelContentz(file);
            for(int i=1;i<=map.size();i++){
                if(i==1){
                    Map<Integer,Object> title = map.get(i);
                    for(int j=0;j<title.size();j++){
                        if(!"qrcode_name".equals(title.get(0))){
                            throw new BusinessException("excel 模板格式不正确！");
                        }
                        list.add((String) title.get(j));//取表字段
                    }
                }else{
                    Map<Integer,Object> data = map.get(i);
                    param = new HashMap<>();
                    for(int k=0;k<data.size();k++){
                        param.put(list.get(k),data.get(k));//取数据信息
                    }
                    param.put("update_time",new Date());
                    jdbcDao.add("TROUNT_QRCODE",param);
                }
            }
        } catch (Exception e) {
            obj.put("errcode","-1");
            obj.put("msg",e.getMessage());
            return obj;
        }
        //excel数据存在map里，map.get(0).get(0)为excel第1行第1列的值，此处可对数据进行处理
        obj.put("errcode","1");
        obj.put("msg","批量导入成功！");
        return obj;
    }

    public Map<Integer, Map<Integer,Object>> addResidentInfo(MultipartFile file,String table) {
        Map<Integer, Map<Integer,Object>> map;
        Map<Object,Object> param;
        List<String> list = new ArrayList<>();
        try {
            map = ReadExcelUtil.readExcelContentz(file);
            for(int i=1;i<=map.size();i++){
                if(i==1){
                    Map<Integer,Object> title = map.get(i);
                    for(int j=0;j<title.size();j++){
                        list.add((String) title.get(j));
                    }
                }else{
                    Map<Integer,Object> data = map.get(i);
                    param = new HashMap<>();
                    for(int k=0;k<data.size();k++){
                        param.put(list.get(k),data.get(k));
                    }
                    jdbcDao.add(table,param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //初始化数据方法
    public void initData(String TABLE_SCHEMA){
        Map map = new HashMap();
        map.put("TABLE_SCHEMA",TABLE_SCHEMA);
        List<Map> list = jdbcDao.loadListByCode("query_table_names", map);
        for(int i=0;i<list.size();i++){
            Map m = list.get(i);
            Map param = new HashMap();
            param.put("TABLE_NAME",m.get("NAME"));
            param.put("TABLE_COMMENT",m.get("COMMENT"));
            param.put("ACTION","ADD");
            param.put("DESCRIPTION",m.get("COMMENT") + "添加数据");
            param.put("CREATE_BY","1");
            param.put("CREATE_TIME",new Date());
            jdbcDao.add("SYS_LOG_CFG",param);
            param.put("ACTION","DELETE");
            param.put("DESCRIPTION",m.get("COMMENT") + "删除数据");
            param.remove("ID");
            jdbcDao.add("SYS_LOG_CFG",param);
            param.put("ACTION","UPDATE");
            param.put("DESCRIPTION",m.get("COMMENT") + "更新数据");
            param.remove("ID");
            jdbcDao.add("SYS_LOG_CFG",param);
        }
    }


    private String getRuleByUrl(String url){
        return "pub.cc." + url;
    }
}
