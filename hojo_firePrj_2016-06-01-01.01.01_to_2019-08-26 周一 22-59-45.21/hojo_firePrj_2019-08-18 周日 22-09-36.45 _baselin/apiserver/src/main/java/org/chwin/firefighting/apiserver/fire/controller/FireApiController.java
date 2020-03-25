package org.chwin.firefighting.apiserver.fire.controller;

import FireCloudLib.FireCloudClient;
import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.fire.service.FireServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController

@CrossOrigin//springMVC解决跨域问题
@RequestMapping("/iot")
public class FireApiController extends BaseSupport{

//    @Autowired
//    private FireServerService fireServerService;
//
//    @RequestMapping("/getFireInfo")
//    public String getFireInfo(HttpServletRequest request){
//
//        Map param = new HashMap();
//        //工程号：00000011/00000005
//        //imei:868575028596718/868575028582197
//        param.put("appid", request.getHeader("App-Id"));
//        param.put("authorization",request.getHeader("Authorization"));
//
//        return this.output(fireServerService.getFireInfo(param));
//    }
    @Autowired
    private FireServerService fireServerService;
    /**
     *@author aniu
     *@Description 处理所有请求
     *@Date 11:38 2018/6/2
     *@Param [request, url]
     *@return java.lang.String
     **/
    @RequestMapping("/{url}")
    public String mobileApi(HttpServletRequest request,@PathVariable("url") String url) {
        Map param = new HashMap();
        param.put("url",url);
        param.put("data",getInputJSON(request));
        param.put("req",request);
        return this.output(fireServerService.doProcess(param));
    }
}


