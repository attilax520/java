package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.fire.service.ceshiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ceshicontroller {

   @Autowired
   private ceshiService service;

    @RequestMapping(value = "/ceshi")
    public String getceshi(ModelMap modelMap){
      List list=service.getRouting();
      modelMap.addAttribute("ROUNTING",list);
      return "/ceshi";
    }

    @RequestMapping(value = "/rountEvent")
    @ResponseBody
    public List getDeviceEvent(@RequestParam("rountId") String rountId){
        System.out.println("ID:"+rountId);
        List list=service.getRoutingEvent(rountId);
        System.out.println("list:"+list);
      return list;
    }

    @RequestMapping(value = "/deviceByEventId")
    @ResponseBody
    public List getDeviceByEventId(@RequestParam("evenId") String eventId){
        return service.getDevice(eventId);
    }

    @RequestMapping(value = "/getRoutingjs/{url}")
    @ResponseBody
    public Object getRoutingjs(@PathVariable String url){
        Map param=new HashMap();
        param.put("url",url);
       return service.getRoutingJs(param);
    }

    @RequestMapping(value = "/getRoutingEventjs")
    @ResponseBody
    public Object getRoutingEventjs(@RequestParam("rountId")String rountId){
        Map param=new HashMap();
        param.put("rountId",rountId);
        param.put("url","device_routing_eventId");
        return service.getRoutingJs(param);
    }

    @RequestMapping(value = "/deviceByEventIdjs")
    @ResponseBody
    public Object getDeviceByEventIdjs(@RequestParam("eventId") String eventId){
        Map param=new HashMap();
        param.put("eventId",eventId);
        param.put("url","device_routing_eventId_detail");
        return service.getDevicejs(param);
    }
}
