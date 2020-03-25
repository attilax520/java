package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.service.PublicService;
import org.chwin.firefighting.apiserver.fire.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色管理
 *
 * @author fll
 * @data 2017/10/30
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseSupport {


    @Resource
    private RoleService roleService;

    @Resource
    private PublicService publicService;

    /**
     * 新增
     *
     * @return LIST
     */
    @RequestMapping("save")
    public String save() {
        //参数判断 是否重复
        //publicService.vertifyField(RoleService.BASE_TABLE_NAME, getInputData());
        Map result = new HashMap(1);
        if ("ADD".equals(getInputCode())) {
            result = roleService.add(getInputData());
        } else if ("UPDATE".equals(getInputCode())) {
            result = roleService.update(getInputData());
        }
        return output(result);
    }


    /**
     * 删除
     * @return NULL
     */
    @RequestMapping("del")
    public String del() {
        roleService.del(getInputData());
        return output(getInputData());
    }


    /**
     * 根据ID 获取
     * @return Map
     */
    @RequestMapping("queryById")
    public String queryById() {
        return output(roleService.queryById(getInputData()));

    }


}
