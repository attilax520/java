package org.chwin.firefighting.apiserver.MQTT;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import org.apache.commons.io.FileUtils;
import org.chwin.firefighting.apiserver.core.util.DateUtil;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
//import java.util.function.Consumer;

public class MQTTRecvMsg {
    public static void main(String[] args) {
        String queruoName_topic = "T_Fire_Alarm";
        java.util.function.Consumer csmr=new java.util.function.Consumer() {
            @Override
            public void accept(Object msgtxt) {
                System.out.println(msgtxt);
                try {
                 //   DateUtil.
                    FileUtils.writeStringToFile(new File("d:\\0mq\\"+DateUtil.getDateStr4file()+".txt"),msgtxt.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ini(queruoName_topic, csmr);
    }

    public static void ini(String queruoName, java.util.function.Consumer csmr) {
        Properties properties = new Properties();
        // 您在控制台创建的 Group ID  jeig recv send byyo
        properties.put(PropertyKeyConst.GROUP_ID, "GID_TCP_FIRE_CON1");
        // 鉴权用 AccessKeyId，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, "LTAIVAESH2PQJR4T");
        // 鉴权用 AccessKeySecret，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "DJwyqzLSzqBoP4cbXvHcZhwJves4hq");
        // 设置 TCP 接入域名，进入控制台的实例管理页面，在页面上方选择实例后，在实例信息中的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR,"http://onsaddr.mq-internet-access.mq-internet.aliyuncs.com:80");
        Consumer consumer = ONSFactory.createConsumer(properties);

        //topic   tag ??
        consumer.subscribe(queruoName, "*", new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext context) {
              //  System.out.println("Receive: " + new String(message.getBody()));
                csmr.accept(new String(message.getBody()));
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}