package org.chwin.firefighting.apiserver.test;

import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.iotx.api.client.IoTApiClientBuilderParams;
import com.aliyun.iotx.api.client.IoTApiRequest;
import com.aliyun.iotx.api.client.SyncApiClient;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.chwin.firefighting.apiserver.core.util.HttpclientUtilsV2;
import org.chwin.firefighting.apiserver.data.Processor;
import org.chwin.firefighting.apiserver.data.SpringUtil;
import org.chwin.firefighting.apiserver.data.mybatisdemo;
import org.chwin.firefighting.apiserver.dsl.velocityUtil;
import org.chwin.firefighting.apiserver.net.RequestImpAdv;
import org.chwin.firefighting.apiserver.warning.WarningManager;
import org.chwin.firefighting.apiserver.workorder.WorkOrderCls;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class T {

//    @Test
//    public void manAddYinhwe() throws Exception {
//        String url = "http://localhost:8088/startProcess?process_name=%E7%AE%A1%E5%A7%94%E4%BC%9A%E6%B4%BE%E5%8D%95%E7%BB%99%E7%89%A9%E4%B8%9A%E6%B5%81%E7%A8%8B&id=2&property0=aaa%E7%89%A9%E4%B8%9A%E5%85%AC%E5%8F%B8";
//        //  url="http://localhost:8088/startProcess?process_name=";
//        String r = HttpclientUtilsV2.get(url);
//        JSONObject jo = JSON.parseObject(r);
//        assertTrue(jo.get("errcode") == null);
//
//
//    }

    @Test
    public void mqInsert() throws Exception {
        String mqMsg = FileUtils.readFileToString(new File("D:\\00wkspc\\v4master\\fire\\apiserver\\src\\main\\java\\org\\chwin\\firefighting\\apiserver\\warning\\iota2.json"));

        new WarningManager().mqHandlerNosec(mqMsg);
        //  assertTrue(  jo.get("errcode")==null );--


    }

    @Test
    public void manAddInhwe() throws Exception {

        RequestImpAdv ri = new RequestImpAdv();
        ri.setParam("dbg_normallogin", "normal");
        //   ri.setParam("process_name", "上报隐患流程");


        Processor pcr = new Processor();
        pcr.req = ri;
        String rzt = pcr.startProcess("上报隐患流程");
        try {  //if ret jo  ,cant be err
            System.out.println(rzt);
            JSONObject jo = JSONObject.parseObject(rzt);
            assertTrue(jo.get("errcode") == null);

        } catch (Exception e) {
            //if not jo  then must be ok
        }


    }

    @Test
    public void sendTaskfinisih() throws Exception {

        String tmp = FileUtils.readFileToString(new File(mybatisdemo.class.getResource("/taskfinishTmplt.json").getFile()));
        VelocityContext context = new VelocityContext();
//添加数据
        context.put("taskid", "11");
        context.put("data", "11");
        String txt = velocityUtil.getTmpltCalcRzt(tmp, context);
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

    @Test
    public void wanrQueryAdm() throws Exception {

        RequestImpAdv ri = new RequestImpAdv();

        ri.setParam("dbg_adminlogin", "xxx");


        Processor pcr = new Processor();
        pcr.req = ri;
        String rzt = pcr.startProcess("warning_query");
        try {  //if ret jo  ,cant be err
            System.out.println(rzt);
            JSONObject jo = JSONObject.parseObject(rzt);

            assertTrue(jo.get("errcode") == null);

        } catch (Exception e) {
            //if not jo  then must be ok
        }


    }

    @Test
    public void wanrQueryPmcAdm() throws Exception {

        RequestImpAdv ri = new RequestImpAdv();

        ri.setParam("dbg_pmclogin", "xxx");


        Processor pcr = new Processor();
        pcr.req = ri;
        String rzt = pcr.startProcess("warning_query");
        try {  //if ret jo  ,cant be err
            JSONObject jo = JSONObject.parseObject(rzt);
            assertTrue(jo.get("errcode") == null);

        } catch (Exception e) {
            //if not jo  then must be ok
        }


    }

    @Test
    public void wanrQueryMainAdm() throws Exception {

        RequestImpAdv ri = new RequestImpAdv();

        ri.setParam("dbg_mainlogin", "xxx");


        Processor pcr = new Processor();
        pcr.req = ri;
        String rzt = pcr.startProcess("warning_query");
        try {  //if ret jo  ,cant be err
            JSONObject jo = JSONObject.parseObject(rzt);
            assertTrue(jo.get("errcode") == null);

        } catch (Exception e) {
            //if not jo  then must be ok
        }


    }

    @Test
    public void 测试维保或物业普通人员接单查询() throws Exception {

        RequestImpAdv ri = new RequestImpAdv();

        ri.setParam("dbg_normallogin", "xxx");


        Processor pcr = new Processor();
        pcr.req = ri;
        String rzt = pcr.startProcess("mainCmpNmlQry");
        try {  //if ret jo  ,cant be err
            JSONObject jo = JSONObject.parseObject(rzt);
            assertTrue(jo.get("errcode") == null);

        } catch (Exception e) {
            //if not jo  then must be ok
        }


    }


    @Test
    public void 确认完成() throws Exception {

        RequestImpAdv ri = new RequestImpAdv();
        ri.setParam("dbg_pmclogin", "xxx");
        ri.setParam("tkid", "tokid");
        WorkOrderCls pcr = new WorkOrderCls();
        pcr.req = ri;
        String rzt = pcr.confirmFinsh( );
        try {  //if ret jo  ,cant be err
            JSONObject jo = JSONObject.parseObject(rzt);
            assertTrue(jo.get("errcode") == null);

        } catch (Exception e) {
            //if not jo  then must be ok
        }


    }


}
