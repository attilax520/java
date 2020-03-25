package org.chwin.firefighting.apiserver.core.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.chwin.firefighting.apiserver.core.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liming on 2017/9/15.
 */
@RestController
@RequestMapping("/step")
public class StepController extends BaseSupport{
    @Autowired
    private StepService service;

    @RequestMapping("/qry")
    @ResponseBody
    public String qry(){
        return output(service.qry(getInputParam()));
    }

    @RequestMapping("/pageq/{code}")
    @ResponseBody
    public String qryp(@PathVariable String  code){
        return output(service.qryp(code,getInputParam()));
    }

    @RequestMapping("/dui")
    @ResponseBody
    public String dui(){
        return output(service.dui(getInputParam()));
    }
}
