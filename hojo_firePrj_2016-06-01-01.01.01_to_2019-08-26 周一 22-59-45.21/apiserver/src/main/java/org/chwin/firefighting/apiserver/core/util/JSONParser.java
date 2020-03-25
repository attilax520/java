package org.chwin.firefighting.apiserver.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * Created by liming on 2017/9/15.
 */
public class JSONParser {
    private static ObjectMapper obm = new ObjectMapper();
    static {
        obm.setSerializationInclusion(JsonInclude.Include.ALWAYS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
    }

    public static List json2List(String JSON){

        List list=null;
        try {
            list = obm.readValue(JSON,List.class);
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    public static Map xml2Map(String xml){
        try{
            JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
            jacksonXmlModule.setDefaultUseWrapper(false);

            XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
            return xmlMapper.readValue(xml,Map.class);
//            JsonNode node = xmlMapper.readTree(xml.getBytes());
//            ObjectMapper jsonMapper = new ObjectMapper();
//            String json = jsonMapper.writeValueAsString(node);
//            return JSONParser.json2Map(json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON数据格式转成Map对象
     * @param json
     * @return
     */
    public static Map json2Map(String json){
//        ObjectMapper obm=new ObjectMapper();
        Map list=null;
        try {
            // json 为空返回null
            if(StringUtils.isBlank(json)){
                return list;
            }
            list = obm.readValue(json,Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static Object json2Obj(String json){
        if(StringUtils.isBlank(json)){
            return null;
        }
        try {
            Object obj = obm.readValue(json,Object.class);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String obj2Json(Object obj){
//        ObjectMapper obm = new ObjectMapper();
        try{
            DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            obm.setDateFormat(dateFormat);
            return obm.writeValueAsString(obj);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSON数据格式转成指定类型
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T from2JSON(String json, Class<T> clazz){
        try {
            return obm.readValue(json, clazz);
        }catch (JsonParseException e){
            e.printStackTrace();
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转成JSON数据格式转成List类型
     * @param json  JSON数据
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> Json2List(String json, Class<T> tClass){
        JavaType javaType = obm.getTypeFactory().constructParametricType(List.class, tClass);
        try {
            return obm.readValue(json, javaType);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String obj2Json(Map map){
        String jsonstr = "";
        try {
            DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            obm.setDateFormat(dateFormat);
            jsonstr = obm.writeValueAsString(map);
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonstr;
    }

    /**
     * 将JSON字符串数据转化成可以读取节点对象，通过key的形式获取value
     * @param json
     * @return
     */
    public static JsonNode json2Node(String json){
        JsonNode root = null;
        try {
            root = obm.readTree(json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return root;
    }

    /*public static void main(String[] args) throws IOException {
        String s = "{\"page\":{\"total\":1,\"page\":0,\"size\":10},\"data\":[{\"deviceSerial\":\"C20994797\",\"deviceName\":\"华赢001展厅\",\"deviceType\":\"CS-C6C-3B1WFR\",\"status\":1,\"defence\":0,\"deviceVersion\":\"V5.2.4 build 180129\"}],\"code\":\"200\",\"msg\":\"操作成功!\"}";
        JsonNode root = obm.readTree(s);
        JsonNode dataNode = root.get("data");
        System.out.println("deviceSerial="+dataNode.get(0).get("deviceSerial"));;

    }*/
}
