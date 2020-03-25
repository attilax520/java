package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.fire.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("index")
public class IndexController extends BaseSupport {

    @Autowired
    private IndexService indexService;

    @RequestMapping(path="/{url}")
    public String index(HttpServletRequest request,@PathVariable("url") String url) {
        Map param = new HashMap();
        param.put("url",url);
        param.put("req",request);
        return this.output(indexService.doProcess(param));
    }

    @Override
    protected String output(Object detail) {
        RspBody result = new RspBody(detail);
        return result.toJson();
    }

    //扫描巡检任务的完成情况
    @Scheduled(cron = "0 0 1 * * ?")//凌晨1点执行
    public void timeTask(){
        indexService.routingTask();
        System.out.println("巡检定时任务执行了.....");
    }

    @RequestMapping("/test")
    public void test(){
        indexService.routingTask();
        System.out.println("巡检定时任务执行了.....");
    }

}
