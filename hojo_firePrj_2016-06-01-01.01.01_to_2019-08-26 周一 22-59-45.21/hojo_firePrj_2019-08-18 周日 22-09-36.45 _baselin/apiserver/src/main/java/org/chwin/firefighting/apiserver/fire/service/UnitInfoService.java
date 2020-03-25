package org.chwin.firefighting.apiserver.fire.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.chwin.firefighting.apiserver.core.util.ReadExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitInfoService {
    @Autowired
    private ScriptComponent scriptCmp;
    @Autowired
    private JdbcDao jdbcDao;

    public Object doProcess(Map param){
        Map params = new HashMap();
        params.put("db", jdbcDao);
        params.put("prm", param);
        String url = (String)param.get("url");
        Object rtmap =  scriptCmp.excute(url, params);
        return rtmap;
    }

    public Map<Integer, Map<Integer,Object>> addCustomerInfo(MultipartFile file) {
        Map<Integer, Map<Integer,Object>> map;
        Map<Object,Object> param;
        Map<Object,Object> relparam;
        List<String> list = new ArrayList<>();
        Map m = new HashMap<>();
        m.put("url","prj.smokePlatform.getMaxUnitCode");
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
                    relparam = new HashMap<>();
                    for(int k=0;k<data.size();k++){
                        if(list.get(k).equals("USER_ID")){
                            relparam.put(list.get(k),data.get(k));
                        }else if (list.get(k).equals("DEPT_NAME") || list.get(k).equals("USER_NAME")){
                            continue;
                        }else{
                            param.put(list.get(k),data.get(k));
                        }
                    }
                    //单位编码自动生成+1
                    Map code = (Map)doProcess(m);
                    param.put("UNIT_CODE",code.get("newCode"));
                    //添加记录，单位表
                    Map entity = jdbcDao.add("UNIT_INFO",param);
                    relparam.put("UNIT_ID",entity.get("ID"));
                    //添加记录，单位管理员关联表
                    jdbcDao.add("SYS_USER_UNIT_REL",relparam);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //excel数据存在map里，map.get(0).get(0)为excel第1行第1列的值，此处可对数据进行处理
        return null;
    }

    public Map qryUnitBySSID(String ssid, String _in_unit_id){
        Map param = new HashMap();
        if (null != _in_unit_id && !_in_unit_id.equals("")){
            param.put("UNIT_ID", _in_unit_id);
        }else{
            param.put("ssid",ssid);
        }
        //根据ssid查到单位id
        List<Map> units = jdbcDao.loadListByCode("qry_unit_by_ssid",param);
        if ( units.size() > 1 ){
            throw new BusinessException("查询到多个单位,请登录单位账号!");
        }
        return units.get(0);
    }

}
