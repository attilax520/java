package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.service.PublicService;
import org.chwin.firefighting.apiserver.fire.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 *
 * @author fll
 * @data 2017/10/27
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseSupport {

    @Resource
    private UserService userService;

    @Resource
    private PublicService publicService;



    /**
     * 新增
     *
     * @return List
     */
    @RequestMapping("save")
    public String save() {
        //参数判断 是否重复
//        publicService.vertifyField(UserService.BASE_TABLE_NAME, getInputData());
        Map result = new HashMap(1);
        if ("ADD".equals(getInputCode())) {
            result = userService.add(getInputData());
        } else if ("UPDATE".equals(getInputCode())) {
            result = userService.update(getInputData());
        }
        return output(result);
    }
    //独立式烟感账号新增
    @RequestMapping("saveUser")
    public String saveUser() {
        Map result = new HashMap(1);
        if ("ADD".equals(getInputCode())) {
            result = userService.addUser(getInputData());
        } else if ("UPDATE".equals(getInputCode())) {
            result = userService.updateUser(getInputData());
        }
        return output(result);
    }

    /**
     * 删除
     * @return null
     */
    @RequestMapping("del")
    public String del() {
        userService.del(getInputData());
        return output(getInputData());
    }

    /**
     * 根据ID获取记录
     * @return Map
     */
    @RequestMapping("queryById")
    public String queryById() {
        return output(userService.queryById(getInputData()));

    }


    /**
     * 重置密码
     *
     * @return Map ID
     */
    @RequestMapping("resetPassword")
    public String resetPassword() {
        return output(userService.resetPassword(getInputData()));
    }


    /**
     * 修改密码
     *
     * @return Map ID
     */
    @RequestMapping("modifyPassword")
    public String modifyPassword() {
        userService.modifyPassword(getInputData());
        return output();
    }

    /**
     * 给设备配置相应租户
     * @return
     */
    @RequestMapping("deviceTenantSet")
    public String modifyDeviceTenant(){
        userService.modifyDeviceTenant(getInputData());
        return output();
    }


    @RequestMapping("openMessageByUser")
    public String openMessageByUser(HttpServletRequest request){
        Map messageMap = new HashMap();
        String user_id = request.getParameter("ID");
        String user_telphone = request.getParameter("TELPHONE");
        messageMap.put("USER_ID", user_id);
        messageMap.put("USER_TELPHONE", user_telphone);
        userService.openMessageByUser(messageMap);
        return output();
    }

}
