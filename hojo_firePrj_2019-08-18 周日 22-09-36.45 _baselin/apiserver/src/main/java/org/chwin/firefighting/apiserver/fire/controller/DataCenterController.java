package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.fire.service.DataCenterService;
import org.chwin.firefighting.apiserver.fire.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("dataCenter")
public class DataCenterController extends BaseSupport {

    @Autowired
    private DataCenterService dataCenterService;

    @RequestMapping(path="/{url}")
    public String dataCenter(HttpServletRequest request,@PathVariable("url") String url) {
        Map param = new HashMap();
        param.put("url",url);
        param.put("req",request);
        return this.output(dataCenterService.doProcess(param));
    }

    @Override
    protected String output(Object detail) {
        RspBody result = new RspBody(detail);
        return result.toJson();
    }

    @RequestMapping(path="/unitScore")
    public void unitScore() {
        dataCenterService.unitScore();
    }

}
