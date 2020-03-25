package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.fire.service.UnitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("unitInfo")
public class UnitInfoController extends BaseSupport {

    @Autowired
    private UnitInfoService unitInfoService;

    @RequestMapping("/excel")
    public String add(@RequestParam("file")MultipartFile file){
        unitInfoService.addCustomerInfo(file);
        return "success";
    }


    @Override
    protected String output(Object detail) {
        RspBody result = new RspBody(detail);
        return result.toJson();
    }

    /**
     * 根据SSID查询单位
     * @param request
     * @return
     */
    @RequestMapping("qryUnitBySSID")
    public String qryUnitBySSID(HttpServletRequest request){

        String ssid = request.getParameter("ssid").toString();
        String unit_id = request.getParameter("UNIT_ID");
        return output(unitInfoService.qryUnitBySSID(ssid,unit_id));
    }

}
