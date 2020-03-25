package org.chwin.firefighting.apiserver.fire.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.chwin.firefighting.apiserver.core.service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreeService {

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private PublicService publicService;

    @Autowired
    private CacheService cacheService;

    private static final String TYPE_PARK_SECTION = "PARK_SECTION";
    private static final String TYPE_DEPT = "DEPT";


    public List queryPermissionList(String ssid) {
        Map<String, Object> param = authControl(ssid,"new_permission_query");
        //Map<String, Object> param = new HashMap<>();
        Map userInfo = cacheService.findUInfo(ssid);
        String IS_SUPER_ADMIN = (String) userInfo.get("IS_SUPER_ADMIN");
        if(("3").equals(IS_SUPER_ADMIN)){
            param = new HashMap<>();
        }
        return jdbcDao.loadListByCode("sys_permission_all", param);
    }

    /**
     * 获取当前用户下的角色树
     *
     * @return List
     */
    public List queryRoleList(String ssid) {

        Map<String, Object> param = authControl(ssid,"new_role_query");
        //Map<String, Object> param = new HashMap<>();
        Map userInfo = cacheService.findUInfo(ssid);
        String IS_SUPER_ADMIN = (String) userInfo.get("IS_SUPER_ADMIN");
        if(("3").equals(IS_SUPER_ADMIN)){
            param = new HashMap<>();
        }
        List<Map<String, Object>> roleList = jdbcDao.loadListByCode("sys_role_list", param);
        roleList.forEach(entity -> {
            entity.put("TREE_TYPE", "ROLE");
        });
        return roleList;
    }


    /**
     * 获取当前用户的机构获取权限树
     *
     * @return List
     */
    public List queryDeptList(String ssid) {

        Map<String, Object> param = authControl(ssid,"new_dept_query");
        //Map<String, Object> param = new HashMap<>();
        Map userInfo = cacheService.findUInfo(ssid);
        String IS_SUPER_ADMIN = (String) userInfo.get("IS_SUPER_ADMIN");
        if(("3").equals(IS_SUPER_ADMIN)){
            param = new HashMap<>();
        }else if(((List)param.get("ID")).size()==0){
            param = new HashMap<>();
            param.put("TENANT_ID",userInfo.get("TENANT_ID"));
        }
        List<Map<String, Object>> deptList = jdbcDao.loadListByCode("dept_query_list", param);
        deptList.forEach(entity -> {
            entity.put("TREE_TYPE", "DEPT");
        });
        return deptList;
    }

    /**
     * 查询区域树结构
     * @return
     */
    public List queryAreaList(Map datamap){
        Object parent_id = datamap.get("PARENT_ID");
        Object level = datamap.get("LEVEL");
        Map param = new HashMap();
        param.put("PARENT_ID", parent_id);
        param.put("ID", parent_id);
        param.put("LEVEL",level);
        List areaList = jdbcDao.loadListByCode("sys_query_area_list", param);
        return areaList;
    }

    /**
     * 获取列表入口
     *
     * @param inputParam CODE
     * @return Object
     */
    public Object queryList(Map inputParam,String ssid) {
        Map datamap = (HashMap)inputParam.get("DATA");
        List result;
        String treeName = (String) inputParam.get("CODE");
        switch (treeName) {
            case "ROLE":
                result = queryRoleList(ssid);
                break;
            case "PERMISSION":
                result = queryPermissionList(ssid);
                break;
            case "DEPT":
                result = queryDeptList(ssid);
                break;
            case "AREA":
                result = queryAreaList(datamap);
                break;
            default:
                result = publicService.queryList(inputParam,ssid);
                break;
        }
        return result;
    }
    //权限控制，根据登录账户的部门、角色、权限，控制展示的信息
    public Map<String, Object> authControl(String ssid,String sqlName){
        Map<String, Object> param = new HashMap<>(1);
        Map userInfo = cacheService.findUInfo(ssid);
        String userId = (String) userInfo.get("ID");
        String IS_SUPER_ADMIN = (String) userInfo.get("IS_SUPER_ADMIN");
        List<String> deptIds = new ArrayList<>();
        List list;
        if(!"3".equals(IS_SUPER_ADMIN)){
            param.put("USER_ID",userId);
            list = jdbcDao.loadListByCode(sqlName, param);
        }else{
            if("new_dept_query".equals(sqlName)){
                list = jdbcDao.loadListByCode("query_dept_all", param);
            }else{
                list = jdbcDao.loadListByCode(sqlName, param);
            }
        }
        param = new HashMap<>(1);
        for(int i=0;i<list.size();i++){
            Map map = (Map) list.get(i);
            deptIds.add((String) map.get("ID"));
        }
        if(deptIds.size()>0){
            param.put("ID",deptIds);
        }else{
            deptIds.add("");
            param.put("ID",deptIds);
        }
        return  param;
    }

}
