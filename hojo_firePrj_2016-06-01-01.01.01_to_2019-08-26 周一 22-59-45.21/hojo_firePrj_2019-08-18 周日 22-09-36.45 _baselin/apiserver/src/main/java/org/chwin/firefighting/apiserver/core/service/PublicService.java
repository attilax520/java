package org.chwin.firefighting.apiserver.core.service;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.util.AliOSSUtil;
import org.chwin.firefighting.apiserver.core.util.BeanUtil;
import org.chwin.firefighting.apiserver.core.util.DateUtil;
import org.chwin.firefighting.apiserver.core.util.ValidatePrm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PublicService {

    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private ScriptComponent scriptCmp;
    @Autowired
    private CacheService cacheService;

    /**
     * 根据条件查询结果（不分页）
     *
     * @param inputParam CODE ->sqlName DATA ->查询参数
     * @return
     */
    public List queryList(Map inputParam,String ssid) {
        String sqlName = (String) inputParam.get("CODE");
        Map dataParam = (Map) inputParam.get("DATA");
        String PGFLG= (String)inputParam.get("PGFLG");
        if (dataParam == null) {
            dataParam = new HashMap();
        }
        Map userInfo = cacheService.findUInfo(ssid);
        String IS_SUPER_ADMIN = (String) userInfo.get("IS_SUPER_ADMIN");
        if(!"3".equals(IS_SUPER_ADMIN)){
            String id = (String) userInfo.get("ID");
            String deptId = (String) jdbcDao.loadRowByMap("SELECT DEPT_ID AS ID FROM SYS_USER U WHERE U.ID='" + id + "'", null).get("ID");
            dataParam.put("LOGIN_USER_DEPT",getSubDeptIds(deptId));
        }
        if(StringUtils.isBlank(PGFLG)){
            return jdbcDao.loadListByCode(sqlName, dataParam);
        }else{
            Map pgMap = new HashMap();
            if(inputParam.containsKey("start")) pgMap.put("start",inputParam.get("start"));
            if(inputParam.containsKey("limit")) pgMap.put("limit",inputParam.get("limit"));
            if(inputParam.containsKey("tcount"))pgMap.put("tcount",inputParam.get("tcount"));
            Map result = jdbcDao.loadPage(sqlName,dataParam,pgMap);
            if(result!=null){
                return (List)result.get("list");
            }else{
                return new ArrayList();
            }
        }
    }

    //获得所有子部门ID集合
    public List<String> getSubDeptIds(String deptId){
        List<String> list = new ArrayList<>();
        list.add(deptId);
        //遍历子部门
        Map param = new HashMap();
        param.put("PARENT_ID", deptId);
        List subList = jdbcDao.loadListByCode("query_sub_dept", param);//查询子部门
        for(int i=0;i<subList.size();i++){
            Map map = (Map) subList.get(i);
            String id = (String) map.get("ID");
            list.addAll(getSubDeptIds(id));
        }
        return list;
    }



    /**
     * 根据条件查询结果（分页）
     *
     * @param inputParam 不为空
     * @return
     */
    public Map queryPage(String sqlName, Map inputParam) {
        Map inputData = new HashMap();
        if (inputParam.get("DATA") != null) {
            inputData = (Map) inputParam.get("DATA");
        }
        return jdbcDao.loadPage(sqlName, inputData, inputParam);
    }


    /**
     * 基础操作（增删改）
     *
     * @param inputParam
     * @return
     */
    @Transactional
    public Map execute(Map inputParam) {
        //表名字
        String tableName = (String) inputParam.get("CLZ");
        //操作类型
        String operate = (String) inputParam.get("CODE");
        operate = StringUtils.isEmpty(operate) ? CONSTANTS.NULL : operate;
        //js操作
        String script = (String) inputParam.get("SCRIPT");
        //参数
        Map dataParam = (Map) inputParam.get("DATA");
        //返回值
        Map returnMap = new HashMap();

        if (!CONSTANTS.RULE.equals(operate)) {
            Object checkResult = operateCheck(script, dataParam);
            if (CONSTANTS.NULL.equals(operate) && checkResult != null) {
                //接受js返回值
                returnMap.putAll((Map) checkResult);
            }

        }
        switch (operate) {
            case CONSTANTS.ADD:
//                vertifyField(tableName, dataParam);
                returnMap = jdbcDao.add(tableName, dataParam);
                if("SYS_DEPT".equals(tableName)){
                    dataParam = new HashMap();
                    dataParam.put("DEPT_ID",returnMap.get("ID"));
                    dataParam.put("TENANT_ID",returnMap.get("TENANT_ID"));
                    dataParam.put("USER_ID",returnMap.get("CREATE_BY"));
                    dataParam.put("CREATE_BY",returnMap.get("CREATE_BY"));
                    dataParam.put("CREATE_TIME",new Date());
//                    jdbcDao.add("SYS_USER_DEPT_REL", dataParam);
                }
                break;
            case CONSTANTS.UPDATE:
//                vertifyField(tableName, dataParam);
                returnMap = jdbcDao.update(tableName, dataParam);
                break;
            case CONSTANTS.DEL: //假删
                jdbcDao.del(tableName, dataParam);
                returnMap.putAll(dataParam);
                break;
            case CONSTANTS.REMOVE: //真删
                jdbcDao.remove(tableName, dataParam);
                returnMap.putAll(dataParam);
                break;
            case CONSTANTS.RULE:
                Map params = new HashMap();
                params.put("db", jdbcDao);
                params.put("prm", dataParam);
                returnMap.put("RULE", scriptCmp.excute(script, params));
                break;
            default:
                break;

        }
        return returnMap;
    }


    public void vertifyField(String tableName, Map dataParam) {
        //针对 CODE NAME 进行判断
        Map<String, String> fields = ValidatePrm.getInstance().getAdminVerifyParam(tableName);
        fields.forEach((key, value) -> {
            {
                if (dataParam.containsKey(key)) {
                    Map result = jdbcDao.loadRowByField(tableName, key, dataParam.get(key));
                    if (result != null && !result.get(CONSTANTS.C_PK).equals(dataParam.get(CONSTANTS.C_PK))) {
                        throw new BusinessException(value + "已存在");
                    }
                }

            }
        });
    }


    /**
     * 操作前检查
     *  @param script
     * @param dataParam
     */
    private Object operateCheck(String script, Map dataParam) {
        if (script == null || script == "") {
            return null;
        }
        Map params = new HashMap();
        params.put("db", jdbcDao);
        params.put("prm", dataParam);
        return scriptCmp.excute(script, params);

    }


    /**
     * 根据ID获取记录
     *
     * @param InputParam clz、id
     * @return
     */
    public Map getById(Map InputParam) {
        String tableName = (String) InputParam.get("CLZ");
        String code = (String) InputParam.get("CODE");
        if (StringUtils.isNotEmpty(tableName)) {
            String id = (String) ((Map<String, Object>) InputParam.get("DATA")).get("ID");
            Assert.notNull(id, "id must not null");
            return jdbcDao.loadRT(tableName, id);
        } else {
            return jdbcDao.loadRowByCode(code, (Map) InputParam.get("DATA"));
        }
    }

    public List queryTree(Map inputParam) {
        List list = this.queryList(inputParam,"");
        return BeanUtil.list2Tree(list);
    }

    //上传图片
    public String upload(MultipartFile multipartFile)  {
        String id = UUID.randomUUID().toString();
        String filepath = multipartFile.getOriginalFilename();
        String ext = filepath.substring(filepath.lastIndexOf("."));
        Date curTime = new Date();
        SimpleDateFormat folder = new SimpleDateFormat("yyyyMMdd");
        try {
            AliOSSUtil.uploadObject2OSS(AliOSSUtil.getOSSClient(),multipartFile.getInputStream(),id + ext,AliOSSUtil.BACKET_NAME,AliOSSUtil.FOLDER+folder.format(curTime)+"/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AliOSSUtil.IMAGE_URL +AliOSSUtil.FOLDER+folder.format(curTime)+"/" +id + ext;
    }
}
