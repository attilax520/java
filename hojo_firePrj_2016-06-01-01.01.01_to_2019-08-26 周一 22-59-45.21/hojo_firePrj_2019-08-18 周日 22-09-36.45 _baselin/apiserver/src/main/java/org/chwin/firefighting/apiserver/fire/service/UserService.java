package org.chwin.firefighting.apiserver.fire.service;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.util.Encryption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fll
 * @data 2017/10/27
 */
@Service
public class UserService {

    @Resource
    private JdbcDao jdbcDao;

    public static final String BASE_TABLE_NAME = "SYS_USER";

    /**
     * 新增记录
     *
     * @param inputData user
     * @return Map
     */
    @Transactional(rollbackFor = Exception.class)
    public Map add(Map inputData) {

        inputData.putAll(createUserPassword(null));

//        //通过在线用户COOKIE_ID获得在线用户ID
//        Map param = new HashMap(){{
//            put("SID", inputData.get(CONSTANTS.COOKIE_ID));
//        }};
//        Map onlineUser = jdbcDao.loadRowByCode("sys_online_user_info", param);
//        //获取在线用户信息
//        inputData.put("TENANT_ID", onlineUser.get("TENANT_ID"));  //添加租户ID信息

        Map user = jdbcDao.add(BASE_TABLE_NAME, inputData);
        String userID = (String) user.get("ID");
        //创建角色关联记录
        createRel(userID, (List) inputData.get("ROLE_IDS"), "ROLE_ID", "SYS_USER_ROLE_REL", user);
        //创建机构关联记录
        createRel(userID, (List) inputData.get("DEPT_IDS"), "DEPT_ID", "SYS_USER_DEPT_REL", user);

        return user;
    }

    //独立式烟感账号新增
    @Transactional(rollbackFor = Exception.class)
    public Map addUser(Map inputData) {
        inputData.putAll(createUserPassword(null));
        Map user = jdbcDao.add(BASE_TABLE_NAME, inputData);
        String userID = (String) user.get("ID");
        //创建用户和单位的关联记录
        createRel(userID, (List) inputData.get("UNIT_IDS"), "UNIT_ID", "SYS_USER_UNIT_REL", user);
        //创建机构关联记录
        //createRel(userID, (List) inputData.get("DEPT_IDS"), "DEPT_ID", "SYS_USER_DEPT_REL", user);
        return user;
    }

    /**
     * 生成用户密码
     */
    private Map createUserPassword(String password) {

        //密码 salt
        /*if (StringUtils.isEmpty(password)) {
            password = generatePassword();
        }*/
        Map<String, Object> info = new HashMap<>(2);
        password = "111111";//统一默认密码为111111
        info.put("PLAIN_PASSWORD", password);
        System.out.println("密码！！！！！！" + password);
        String salt = UUID.randomUUID().toString().substring(0, 4);
        password = Encryption.md5(password + salt);
        info.put("PASSWORD", password);
        info.put("SALT", salt);
        return info;

    }
    /**
     * 修改用户密码
     */
    private Map modifyUserPassword(String password) {

        //密码 salt
        /*if (StringUtils.isEmpty(password)) {
            password = generatePassword();
        }*/
        Map<String, Object> info = new HashMap<>(2);
        info.put("PLAIN_PASSWORD", password);
        System.out.println("密码！！！！！！" + password);
        String salt = UUID.randomUUID().toString().substring(0, 4);
        password = Encryption.md5(password + salt);
        info.put("PASSWORD", password);
        info.put("SALT", salt);
        return info;

    }
    /**
     * 随机生成密码
     * 规则：随机大写英文+随机小写英文+随机6位数字，
     *
     * @return String
     */
    private String generatePassword() {
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 2; ++i) {
            int temp = r.nextInt(52);
            char x = (char) (temp < 26 ? temp + 97 : (temp % 26) + 65);

            if (i == 0) {
                code.append(String.valueOf(x).toUpperCase());
            } else {
                code.append(String.valueOf(x).toLowerCase());
            }
        }
        int radomInt = r.nextInt(899999) + 100000;
        return code.append(radomInt).toString();

    }

    /**
     * 创建关联记录
     *
     * @param userID    用户ID
     * @param relIds    相关记录ID
     * @param paramName 参数名字
     * @param tableName 表明
     * @param user 用户信息
     */
    private void createRel(String userID, List<String> relIds, String paramName, String tableName, Map<String, Object> user) {
        if (relIds == null || relIds.isEmpty()) {
            return;
        }
        List<Map<String, Object>> userPermission = new ArrayList<>();
        for (String id : relIds) {
            Map<String, Object> param = new HashMap<>(2);
            param.put("USER_ID", userID);
            param.put(paramName, id);
            userPermission.add(param);
        }
        jdbcDao.addBatch(tableName, userPermission);
        user.put(paramName + "S", relIds);
    }


    /**
     * 修改记录
     *
     * @param inputData user
     */
    @Transactional(rollbackFor = Exception.class)
    public Map update(Map inputData) {
        if("admin".equals(inputData.get("ACCOUNT"))){
            inputData.put("IS_SUPER_ADMIN","3");
        }
        Map user = jdbcDao.update(BASE_TABLE_NAME, inputData);
        //删除关联
        inputData.put("USER_ID", inputData.get("ID"));
        jdbcDao.excuteByCode("user_remove_role_rel", inputData);
        jdbcDao.excuteByCode("user_remove_dept_rel", inputData);

        //增加关联
        String userID = (String) inputData.get("ID");
        //创建角色关联记录
        createRel(userID, (List) inputData.get("ROLE_IDS"), "ROLE_ID", "SYS_USER_ROLE_REL", user);
        //创建机构关联记录
        createRel(userID, (List) inputData.get("DEPT_IDS"), "DEPT_ID", "SYS_USER_DEPT_REL", user);

        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map updateUser(Map inputData) {
        Map user = jdbcDao.update(BASE_TABLE_NAME, inputData);
        //删除关联
        inputData.put("USER_ID", inputData.get("ID"));
        jdbcDao.excuteByCode("user_remove_unit_rel", inputData);

        //增加关联
        String userID = (String) inputData.get("ID");
        //创建角色单位关联记录
        createRel(userID, (List) inputData.get("UNIT_IDS"), "UNIT_ID", "SYS_USER_UNIT_REL", user);


        return user;
    }


    /**
     * 删除记录
     *
     * @param inputData ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void del(Map inputData) {

        jdbcDao.del(BASE_TABLE_NAME, inputData);
        //删除关联记录
        inputData.put("USER_ID", inputData.get("ID"));
        jdbcDao.excuteByCode("user_remove_role_rel", inputData);
        jdbcDao.excuteByCode("user_remove_dept_rel", inputData);
        jdbcDao.excuteByCode("user_remove_unit_rel", inputData);
    }


    /**
     * 根据Id 获记录
     *
     * @param inputData ID
     * @return Map
     */
    public Map queryById(Map inputData) {
        String userId = (String) inputData.get("ID");
        Map user = jdbcDao.loadRT(BASE_TABLE_NAME, userId);
        //获取关联记录
        inputData.put("USER_ID", userId);
        user.put("ROLE_IDS", jdbcDao.loadListByCode("user_query_role_id", inputData));
        user.put("DEPT_IDS", jdbcDao.loadListByCode("user_query_dept_id", inputData));
        return user;

    }


    /**
     * 重置密码
     *
     * @param inputData ID
     * @return Map
     */
    @Transactional(rollbackFor = Exception.class)
    public Map resetPassword(Map inputData) {
        inputData.putAll(createUserPassword(null));
        Map user = jdbcDao.update(BASE_TABLE_NAME, inputData);
        //TODO 发送短信
        user.put("PLAIN_PASSWORD", inputData.get("PLAIN_PASSWORD"));
        return user;

    }

    public void modifyPassword(Map inputData) {
        Map user = jdbcDao.loadRT(BASE_TABLE_NAME, (String) inputData.get("ID"));
        if (user.get("PASSWORD").equals(Encryption.md5((String) inputData.get("OLD_PWD") + user.get("SALT")))) {
            inputData.putAll(modifyUserPassword((String) inputData.get("NEW_PWD")));
            jdbcDao.update(BASE_TABLE_NAME, inputData);
        } else {
            throw new BusinessException("旧密码错误");
        }
    }

    public void modifyDeviceTenant(Map inputData){
        List list = (ArrayList)inputData.get("PID");
        if (null != list && list.size() != 0) {
            for (int i = 0; i < list.size(); i ++){
                String pid = (String)list.get(i);
                Map upmap = new HashMap();
                upmap.put("ID", pid);
                jdbcDao.update("IOT_DEVICE", upmap);
            }
        }else{
            throw new BusinessException("没有选择设备");
        }
    }

    public String openMessageByUser(Map params){
        return null;

    }
}
