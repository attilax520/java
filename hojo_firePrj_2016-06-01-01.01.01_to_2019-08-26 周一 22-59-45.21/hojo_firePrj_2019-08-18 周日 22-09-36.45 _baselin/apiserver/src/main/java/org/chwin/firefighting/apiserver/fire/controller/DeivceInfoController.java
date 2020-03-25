package org.chwin.firefighting.apiserver.fire.controller;

import net.sf.json.JSONObject;
import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.service.PublicService;
import org.chwin.firefighting.apiserver.fire.service.DeviceInfoService;
import org.chwin.firefighting.apiserver.fire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/device")
public class DeivceInfoController extends BaseSupport{

    @Autowired
    private DeviceInfoService deviceInfoService;

    /**
     * 新增
     *
     * @return List
     */
    @RequestMapping("/save/{url}")
    @ResponseBody
    public String save(HttpServletRequest request, @PathVariable String url) {

        return output(deviceInfoService.saveDeviceInfo(getInputData(), url));
    }

    /**
     * 查岗时间
     * @param
     * @return
     */
    @RequestMapping("/hardwareDevicesPosition")
    @ResponseBody
    public String hardwareDevicesPosition(){
        return output(deviceInfoService.hardwareDevices_position(getInputParam()));
    }

    /**
     * 巡检
     * @param
     * @return
     */
    @RequestMapping("/hardwareDevicesRouting")
    @ResponseBody
    public String hardwareDevicesRouting(){
        return output(deviceInfoService.hardwareDevices_routing(getInputParam()));
    }
}
