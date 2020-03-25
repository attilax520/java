//package org.chwin.firefighting.apiserver.MQTT;
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.ons.model.v20190214.OnsMessagePageQueryByTopicRequest;
//import com.aliyuncs.ons.model.v20190214.OnsMessagePageQueryByTopicResponse;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Base64;
//import java.util.List;
//
//public class MQTTQueryMsg {
//    public static void main(String[] args) {
//        /**
//         *Open API 的接入点，设置为目标 Region
//         */
//        String regionId = "mq-internet-access";
//        /**
//         *鉴权使用的 AccessKeyId，由阿里云管理控制台获取
//         */
//        String accessKey = "LTAIVAESH2PQJR4T";
//        /**
//         *鉴权使用的 AccessKeySecret，由阿里云管理控制台获取
//         */
//        String secretKey = "DJwyqzLSzqBoP4cbXvHcZhwJves4hq";
//        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, secretKey);
//        IAcsClient iAcsClient = new DefaultAcsClient(profile);
//        OnsMessagePageQueryByTopicRequest request = new OnsMessagePageQueryByTopicRequest();
//        request.setPreventCache(System.currentTimeMillis());
//        request.setInstanceId("MQ_INST_1309862045498958_BbJRGyc4");
//        request.setTopic("T_Hidden_Warn");
//        request.setBeginTime(System.currentTimeMillis() - 24 * 3600 * 1000 * 10);
//        request.setEndTime(System.currentTimeMillis());
//        request.setCurrentPage(1);
//        request.setPageSize(100);
//
//        try {
//            OnsMessagePageQueryByTopicResponse response = iAcsClient.getAcsResponse(request);
//            OnsMessagePageQueryByTopicResponse.MsgFoundDo msgFoundDo = response.getMsgFoundDo();
//            List<OnsMessagePageQueryByTopicResponse.MsgFoundDo.OnsRestMessageDo> msgFoundList = msgFoundDo.getMsgFoundList();
//
//
//            for (OnsMessagePageQueryByTopicResponse.MsgFoundDo.OnsRestMessageDo msg : msgFoundList
//            ) {
//                String body = msg.getBody();
//                if(null != body){
//                    Base64.Decoder decoder = Base64.getDecoder();
//
//                    try {
//                        String gbk = new String(decoder.decode(body.getBytes()), "UTF-8");
//                        System.out.println(gbk);
//                    } catch (UnsupportedEncodingException e) {
//                    }
//                }
//            }
//
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }
//}