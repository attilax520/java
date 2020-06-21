package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.fire.service.TroutingAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/trounting")
public class TroutingAllocationController extends BaseSupport {

    @Autowired
    private TroutingAllocationService trountService;

    @RequestMapping("/selectTrount/{url}")
    @ResponseBody
    public String getAllocation(HttpServletRequest request, @PathVariable String url) {
        return output(trountService.getRountingEvent(getInputData(), url));
    }

    @RequestMapping("/getSafePerson")
    public String getSafePerson() {
        Map map = new HashMap();
        return output(trountService.getSafePerson(map));
    }

    /**
     * 对巡检任务分配的信息保存
     * @param request
     * @param url
     * @return
     */
    @RequestMapping("/save/{url}")
    @ResponseBody
    public String save(HttpServletRequest request, @PathVariable String url) {

        return output(trountService.saveTrounInfo(getInputData(), url));
    }

}
