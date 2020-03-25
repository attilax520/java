package org.chwin.firefighting.apiserver.fire.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import javax.annotation.Resource;
import java.util.*;

/**
 * @author fll
 * @date 2017/10/30
 */
@Service
public class RoleService {


    @Resource
    private JdbcDao jdbcDao;

    public static final String BASE_TABLE_NAME = "SYS_ROLE";

    /**
     * 新增
     *
     * @param inputData 列信息
     * @return 列信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Map add(Map inputData) {
        Map role = jdbcDao.add(BASE_TABLE_NAME, inputData);
//        List<String> permissionIds = (List<String>) inputData.get("PERMISSION_IDS");
        List<Map<String,String>> permissionList = (List<Map<String,String>>) inputData.get("PERMISSION_IDS");
        createRolePermissionRel((String) role.get("ID"), permissionList, role);
        return role;
    }

    /**
     * 创建角色权限关联
     *
     * @param roleId        角色ID
     * @param permissionIdsList 权限ID对象 List<Map<String, String>
     * @param role          记录
     */
    /*private void createRolePermissionRel(String roleId, List<String> permissionIds, Map role) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return;
        }
        List rolePermissionRel = new ArrayList();
        System.out.print(permissionIds);
        for (String permissionId : permissionIds) {
            Map param = new HashMap(2);
            param.put("ROLE_ID", roleId);
            param.put("PERMISSION_ID", permissionId);
            if(StringUtils.isNotBlank (permissionId))
                rolePermissionRel.add(param);
        }
        jdbcDao.addBatch("SYS_ROLE_PERMISSION_REL", rolePermissionRel);
        role.put("PERMISSION_IDS", permissionIds);
    }*/

    private void createRolePermissionRel(String roleId, List<Map<String, String>> permissionIdsList, Map role) {
        if (permissionIdsList == null || permissionIdsList.size() == 0) {
            return;
        }
        System.out.print("JSON: "+JSONParser.obj2Json(permissionIdsList));
        String jsonStr = JSONParser.obj2Json(permissionIdsList);
        JSONArray json = JSONArray.fromObject(jsonStr ); // 首先把字符串转成 JSONArray  对象
        List rolePermissionRel = new ArrayList();  //角色权限关联
        List permissionIds = new ArrayList();  //所有权限ID
        if(null != json && json.size() != 0){
            for(int i = 0;i < json.size(); i ++){
                JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                Map param = new HashMap(3);
                param.put("ROLE_ID", roleId);
                param.put("PERMISSION_ID", job.get("PERMISSION_ID"));
                param.put("IS_CHECKED",job.get("IS_CHECKED"));
                permissionIds.add(job.get("PERMISSION_ID"));
                if(StringUtils.isNotBlank (job.get("PERMISSION_ID").toString())){
                    rolePermissionRel.add(param);
                }
            }
        }
//        for (String permissionId : permissionIds) {
//            Map param = new HashMap(2);
//            param.put("ROLE_ID", roleId);
//            param.put("PERMISSION_ID", permissionId);
//            if(StringUtils.isNotBlank (permissionId))
//                rolePermissionRel.add(param);
//        }
        jdbcDao.addBatch("SYS_ROLE_PERMISSION_REL", rolePermissionRel);
        role.put("PERMISSION_IDS", permissionIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map update(Map inputData) {
        Map role = jdbcDao.update(BASE_TABLE_NAME, inputData);
        if (inputData.containsKey("PERMISSION_IDS")) {
            inputData.put("ROLE_ID", role.get("ID"));
            //获取原来的权限ID
            List permissionIds = jdbcDao.loadListByCode("permission_query_role_id", inputData);
            List permissionIdsOld = new ArrayList();
            permissionIds.forEach(permission -> {
                permissionIdsOld.add(((Map) permission).get("PERMISSION_ID"));
            });

            //将获取的所有角色树节点通过IS_CHECKED区分，是否是真的勾选中
            List<Map<String, String>> permissionIdsList = (List<Map<String, String>>) inputData.get("PERMISSION_IDS");
            List permissionIdsNew = new ArrayList();
            List isPerminssChecked = new ArrayList();

            String jsonStr = JSONParser.obj2Json(permissionIdsList);
            JSONArray json = JSONArray.fromObject(jsonStr ); // 首先把字符串转成 JSONArray  对象
            if(json.size() != 0){
                for(int i = 0;i < json.size(); i ++){
                    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    System.out.println(job.get("PERMISSION_ID")+ " = " + job.get("IS_CHECKED")) ;  // 得到 每个对象中的属性值
                    isPerminssChecked.add(job.get("IS_CHECKED"));
                    permissionIdsNew.add(job.get("PERMISSION_ID"));
                }
            }
//            for (Map<String,String> map : permissionIdsList) {
//                for (String s:map.keySet()) {
//                    if(s.equals("IS_CHECKED")){   //判断此节点是真选择还是假选中
//                        isPerminssChecked.add(map.get(s));
//                    }
//                    if(s.equals("PERMISSION_ID")){  //所有的权限ID
//                        permissionIdsNew.add(map.get(s));
//                    }
//                }
//            }
            //对比筛选出旧权限删除的权限
            permissionIdsOld.removeAll(permissionIdsNew);
            //删除子角色下面的删除的权限
            if (!permissionIdsOld.isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("PERMISSION_IDS", permissionIdsOld);
                param.put("ROLE_ID", role.get("ID"));
                jdbcDao.excuteByCode("role_remove_dept_chirdren_rel", param);
            }
            // 删除原来的权限关联
            inputData.put("ROLE_ID", role.get("ID"));
            jdbcDao.excuteByCode("role_remove_per_rel", inputData);
//            //添加新的关联
            createRolePermissionRel((String) role.get("ID"), permissionIdsList, role);
        }
        return role;
    }

    /**
     * 删除
     *
     * @param inputData ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void del(Map inputData) {
        jdbcDao.remove(BASE_TABLE_NAME, inputData);
        //删除关联记录
        inputData.put("ROLE_ID", inputData.get("ID"));
        jdbcDao.excuteByCode("role_remove_per_rel", inputData);   //删除关联的用户角色和权限

        //TODO 是否删除子集
    }


    /**
     * 根据ID获取记录
     *
     * @param inputData ID
     * @return 列信息
     */
    public Map queryById(Map inputData) {
        String roleID = (String) inputData.get("ID");
        if(StringUtils.isBlank(roleID)) return null;
        Map role = jdbcDao.loadRT(BASE_TABLE_NAME, roleID);
        inputData.put("ROLE_ID", roleID);
        List permissionIds = jdbcDao.loadListByCode("permission_query_role_id", inputData);
        role.put("PERMISSION_IDS", permissionIds);
        return role;

    }


}
