package org.chwin.firefighting.apiserver.fire.controller;

import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.fire.service.TreeService;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 在登录权限下的树列表
 */
@RestController
@RequestMapping("tree")
public class TreeController extends BaseSupport {

    @Resource
    private TreeService treeService;


    @RequestMapping("queryList")
    public String queryList(HttpServletRequest request) {
        String ssid = request.getParameter("ssid");
        return output(treeService.queryList(getInputParam(),ssid));
    }


}
