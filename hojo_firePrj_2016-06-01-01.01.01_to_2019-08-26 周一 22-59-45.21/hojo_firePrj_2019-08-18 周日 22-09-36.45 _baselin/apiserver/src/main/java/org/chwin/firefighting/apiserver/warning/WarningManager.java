package org.chwin.firefighting.apiserver.warning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.chwin.firefighting.apiserver.MQTT.MQTTRecvMsg;
import org.chwin.firefighting.apiserver.core.util.DateUtil;
import org.chwin.firefighting.apiserver.data.MybatisUtil;
import org.chwin.firefighting.apiserver.data.Processor;
import org.chwin.firefighting.apiserver.data.mybatisdemo;
import org.chwin.firefighting.apiserver.dsl.velocityUtil;
import org.chwin.firefighting.apiserver.net.HttpclientUtilsV3;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WarningManager {
    public static void main(String[] args) throws Exception {
        start();


    }

    private static void start() {
        String queruoName = "T_Fire_Alarm";
        java.util.function.Consumer csmr = new java.util.function.Consumer() {
            @Override
            public void accept(Object msgtxt) {
                System.out.println(msgtxt);
                try {
                    //   DateUtil.
                    FileUtils.writeStringToFile(new File("d:\\0mq\\" + DateUtil.getDateStr4file() + ".txt"), "utf8");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //    String mqMsg= FileUtils.readFileToString(new File("D:\\00wkspc\\v4master\\fire\\apiserver\\src\\main\\java\\org\\chwin\\firefighting\\apiserver\\warning\\iot.json"));
                new WarningManager().mqHandler(msgtxt.toString());

            }
        };

        MQTTRecvMsg.ini(queruoName, csmr);
    }

    public void mqHandler(String mqmsg) {


        try {
            mqHandlerNosec(mqmsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mqHandlerNosec(String mqmsg) throws Exception {

        JSONObject jo = JSONObject.parseObject(mqmsg);
        JSONObject DvcPartObj = getDvcPart(jo);
        new WarningManager().mqInsertProcess(jo, DvcPartObj);

    }

    public void mqInsertProcess(JSONObject jo, JSONObject dvcPartObj) throws Exception {

        //auth
        //mq  get json
        //   String mqMsg= FileUtils.readFileToString(new File("D:\\00wkspc\\v4master\\fire\\apiserver\\src\\main\\java\\org\\chwin\\firefighting\\apiserver\\warning\\iot.json"));
        //  JSONObject jo=JSONObject.parseObject(mqMsg);
        Map m = Maps.newLinkedHashMap();
        m.put("iotmsg", jo);
        m.put("oi", "");
        m.put("dvc_park", dvcPartObj);

        Object rzt = MybatisUtil.selectList("mqInsertProcess", m);
        System.out.println(JSON.toJSONString(rzt));
    }

    public JSONObject getDvcPart(JSONObject jo) throws Exception {


        String url2 = get_url();

        VelocityContext context = new VelocityContext();
//添加数据
        context.put("productKey", jo.getJSONObject("deviceInfo").getString("productKey"));
        context.put("deviceName", jo.getJSONObject("deviceInfo").getString("deviceName"));
        String url3 = velocityUtil.getTmpltCalcRzt(url2, context);
        String txt = HttpclientUtilsV3.get(url3);
        System.out.println(txt);
        return JSONObject.parseObject(txt);
    }

    public static String get_url() throws Exception {
       if (new File("d:\\localhost").exists()) {
            org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
            Object mObject = yaml.load(mybatisdemo.class.getResourceAsStream("/cfg.yml"));
            Map m = (Map) mObject;
            return m.get("selectParkFromIotdvc4queryUrl").toString();

        }

        if(Processor.HostAppPrefix!=null)
            return Processor.HostAppPrefix+"/device/get?productKey=${productKey}&deviceName=${deviceName}";
       else {
            //remote svr mode
            String sql = "select * from data_dictionary where key='parkQueryUrl' ";
            List<Map> li = MybatisUtil.executeSql(sql);
            return li.get(0).get("text").toString();
        }
    }

}
