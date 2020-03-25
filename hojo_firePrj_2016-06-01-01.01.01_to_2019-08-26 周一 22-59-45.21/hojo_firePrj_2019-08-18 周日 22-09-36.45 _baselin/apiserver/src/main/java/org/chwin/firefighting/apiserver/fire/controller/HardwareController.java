package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.fire.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/hardware")
public class HardwareController extends BaseSupport {

    @Autowired
    private HardwareService hardwareDevices;

    /**
     * 下发命令,统一接口
     * @param
     * @return
     */
    /*@PostMapping("/hardwareDevices")
    @ResponseBody
    public String hardwareDevices(){
        return output(hardwareDevices.hardwareDevices(getInputParam()));
    }*/

    /**
     * 下发查岗指令:异步操作
     * @param
     * @return
     */
    @PostMapping("/hardwareDevicesInspection")
    public String hardwareDevicesInspection(){
        return output(hardwareDevices.hardwareDevicesInspection(getInputParam()));
    }
    /**
     * 接收查岗时间
     * @param
     * @return
     */
    @PostMapping("/hardwareDevicesInspectionTime/{app_id}/{device_code}")
    public ResponseEntity<?> hardwareDevicesInspectionTime(@PathVariable String app_id,@PathVariable String device_code){
        Map<String,String> data = new HashMap<>();
        data.put("app_id",app_id);
        data.put("device_code",device_code);
        return hardwareDevices.hardwareDevicesInspectionTime(data);
    }

    /**
     * 下发查岗指令:同步操作
     * @param
     * @return
     */
    @PostMapping("/hardwareSyncInspection")
    public String hardwareSyncInspection(){
        return output(hardwareDevices.hardwareSyncInspection(getInputParam()));
    }

    /**
     * 下达巡检指令
     * @param
     * @return
     */
    @PostMapping("/hardwareDevicesRouting")
    @ResponseBody
    public String hardwareDevicesRouting(){
        return output(hardwareDevices.hardwareDevicesRouting(getInputParam()));
    }
}
