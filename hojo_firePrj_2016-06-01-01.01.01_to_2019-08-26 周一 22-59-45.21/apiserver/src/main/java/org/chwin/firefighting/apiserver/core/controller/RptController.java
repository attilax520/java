package org.chwin.firefighting.apiserver.core.controller;

import org.chwin.firefighting.apiserver.core.service.RptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 总体说明
 * <p>具体说明</p>
 *
 * @author cailm
 * @version $Id: org.chwin.iot.apiserver.core.controller.RptDesign,v 0.1 2017/12/20 18:01 Exp $$
 */
@RestController
@RequestMapping("/rpt")
public class RptController extends BaseSupport{
    @Autowired
    private RptService service;

    @RequestMapping("/cfg/{id}/{type}")
    @ResponseBody
    public String openCfg(@PathVariable String id, @PathVariable String type){
        Map param=this.getInputParam();
        return output(service.openCfg(id,type));
    }

    @RequestMapping("/pvw/dataset")
    @ResponseBody
    public String pvwDataset(){
        return output(service.pvwDataset(this.getInputParam()));
    }
}
