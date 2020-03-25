package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.fire.service.DeviceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("smokePlat")
public class SmokePlatController extends BaseSupport {

    @Autowired
    private DeviceConfigService deviceConfigService;

    @RequestMapping(path="/{url}")
    public String smokePlat(HttpServletRequest request,@PathVariable("url") String url) {
        Map param = new HashMap();
        param.put("url",url);
        Map loginInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if(loginInfo!=null){
            request.setAttribute("TENANT_ID",loginInfo.get("TENANT_ID"));
        }
        param.put("req",request);
        return this.output(deviceConfigService.doProcess(param));
    }

    @RequestMapping("/excel")
    public String add(@RequestParam("file")MultipartFile file,HttpServletRequest request){
        String table = request.getParameter("table");
        deviceConfigService.addResidentInfo(file,table);
        return "success";
    }

    //初始化日志配置信息接口
    @RequestMapping("/initData")
    public String initData(HttpServletRequest request){
        String table = request.getParameter("name");
        deviceConfigService.initData(table);
        return "success";
    }

    @Override
    protected String output(Object detail) {
        RspBody result = new RspBody(detail);
        return result.toJson();
    }

}
