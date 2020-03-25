package org.chwin.firefighting.apiserver.core.controller;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.chwin.firefighting.apiserver.core.service.LogService;
import org.chwin.firefighting.apiserver.core.service.SystemService;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.core.util.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志系统
 */
@RestController
@RequestMapping("/log")
public class LogSystemController extends BaseSupport{

    @Autowired
    private LogService logService;

    @RequestMapping(path="/{url}")
    public String query(HttpServletRequest request,@PathVariable("url") String url) {
        Map param = new HashMap();
        param.put("url",url);
        param.put("req",request);
        return this.output(logService.doProcess(param));
    }



}
