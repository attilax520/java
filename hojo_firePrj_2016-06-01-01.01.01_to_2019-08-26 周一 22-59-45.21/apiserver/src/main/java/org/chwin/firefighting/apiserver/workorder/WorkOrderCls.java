package org.chwin.firefighting.apiserver.workorder;

import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.iotx.api.client.IoTApiClientBuilderParams;
import com.aliyun.iotx.api.client.IoTApiRequest;
import com.aliyun.iotx.api.client.SyncApiClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ognl.OgnlException;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.chwin.firefighting.apiserver.data.MybatisUtil;
import org.chwin.firefighting.apiserver.data.SpringUtil;
import org.chwin.firefighting.apiserver.data.mybatisdemo;
import org.chwin.firefighting.apiserver.dsl.velocityUtil;
import org.chwin.firefighting.apiserver.net.RequestUtil;
import org.chwin.firefighting.apiserver.net.ResponseUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Api("工单系列业务流程操作api")
@Controller
@Component
public class WorkOrderCls {

    @Autowired
    public HttpServletRequest req;
    @Autowired
    public HttpServletResponse res;
    @ApiOperation(value = "确认完成", notes = "说明note")
    @RequestMapping(value = "/confirmFinsh")
    @ResponseBody
    public String confirmFinsh( ) throws Exception {
        ResponseUtil.setAccessControlAllowOrigin(res);//cookie cross domain
        Map paramMap=processReqMap(req);
        List<Map> pageInfo = MybatisUtil.selectList("确认派单完成流程", paramMap);


        sendTaskfinisih(pageInfo.get(0));
        return (JSON.toJSONString(pageInfo, true));
    }

    private Map processReqMap(HttpServletRequest req) throws Exception, OgnlException {
        Map paramMap = RequestUtil.getMap(req);
        //add login user info
        List<Map> umapList = MybatisUtil.selectList("listRecentlyLoginUsrMood", paramMap);
        if (umapList.size() == 0)
            throw new RuntimeException("notlogin");
        paramMap.put("loginUserInfo", umapList.get(0));
        return paramMap;
    }

    // @Test
    public void sendTaskfinisih(Map prm) throws Exception {

        String tmp = FileUtils.readFileToString(new File(mybatisdemo.class.getResource("/taskfinishTmplt.json").getFile()));
        VelocityContext context = new VelocityContext();
//添加数据
        context.put("taskid", prm.get("taskid").toString());
        context.put("data", prm.get("data"));
        String txt = velocityUtil.getTmpltCalcRzt(tmp, context);
        System.out.println(txt);
        JSONObject jo = JSONObject.parseObject(txt);
        JSONObject jo_poaram = jo.getJSONObject("params");


        IoTApiClientBuilderParams ioTApiClientBuilderParams = new IoTApiClientBuilderParams();
        ioTApiClientBuilderParams.setAppKey(SpringUtil.getCfgProperty("aliapi.app_key"));
        ioTApiClientBuilderParams.setAppSecret(SpringUtil.getCfgProperty("aliapi.app_secret"));
        SyncApiClient SyncApiClient1 = new SyncApiClient(ioTApiClientBuilderParams);

        IoTApiRequest request = new IoTApiRequest();
//设置协议版本号
        request.setVersion(SpringUtil.getCfgProperty("aliapi.request.version"));
        String uuid = UUID.randomUUID().toString();
        String id = uuid.replace("-", "");
//设置请求ID
        request.setId(id);
//设置API版本号
        request.setApiVer(SpringUtil.getCfgProperty("aliapi.request.api_version"));
        Map<String, Object> m = (Map) jo_poaram;
        //  JSONObject.
        //    BeanUtils.copyProperties(m,jo_poaram);
        String ms = JSON.toJSONString(m);
        request.setParams(m);
        ApiResponse res = SyncApiClient1.postBody("api.citylink.hzcloudtown.com", "/user/task/handle", request);
        // System.out.println(res.get);
        System.out.println(res.getBodyStr());
        //  String rzt=   HttpclientUtilsV2.post("http://api.citylink.hzcloudtown.com/user/task/handle",txt);
        System.out.println(JSON.toJSONString(res));


    }

}
