package org.chwin.firefighting.apiserver.fire.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Service
public class CameraInsertService implements Serializable {
    @Autowired
    private JdbcDao jdbcDao;

    public void insertCameraVideo(String requestJson){

        JsonNode root = JSONParser.json2Node(requestJson);
        JsonNode datalist = root.get("data");
        for(JsonNode node : datalist){
            System.out.println("deviceSerial="+node.get("deviceSerial"));
            System.out.println("deviceName="+node.get("deviceName"));
            Map parammap = new HashMap();
            parammap.put("DEVICE_SERIAL", node.get("deviceSerial").asText());
            parammap.put("DEVICE_NAME", node.get("deviceName").asText());
            parammap.put("DEVICE_FACTORY","杭州萤石云网络科技有限公司");
            parammap.put("COMMUNICATION_PROTOCOL", "HTTP协议");
            parammap.put("DEVICE_TYPE", node.get("deviceType").asText());
            parammap.put("DEVICE_STATUS", node.get("status").asText());  //是否在线
            parammap.put("DEFENCE", node.get("defence").asText());
            parammap.put("DEVICE_VERSION", node.get("deviceVersion").asText());

            jdbcDao.add("VIDEO_MANAGE", parammap);
        }
    }
}
