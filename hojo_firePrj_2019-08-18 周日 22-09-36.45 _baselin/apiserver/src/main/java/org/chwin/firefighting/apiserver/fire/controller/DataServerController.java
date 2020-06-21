package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.fire.service.DataServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加APP接口
 */
@RestController
@CrossOrigin
@RequestMapping("/dataServer")
public class DataServerController extends BaseSupport {

    @Autowired
    private DataServerService dataServerService;

    @PostMapping("/addApp")
    public String addApp(){
        return output(dataServerService.addApp(getInputParam()));
    }

    @GetMapping("/getAppList")
    public String getAppList(HttpServletRequest request){
        Map params = new HashMap();
        String ssid = request.getParameter("ssid");
        params.put("SSID", ssid);
        return output(dataServerService.getAppList(params));
    }

    /*@PostMapping("/v4/addApp")
    public String addAppV4(HttpServletRequest request){
        return output(dataServerService.addAppV4(getInputParam()));
    }*/

    /*@GetMapping("/v4/getAppList")
    public String getAppListV4(HttpServletRequest request){
        Map params = new HashMap();
        String ssid = request.getParameter("ssid");
        params.put("SSID", ssid);
        System.out.println(request.getParameter("ssid"));
        return output(dataServerService.getAppListV4(params));
    }*/
}
