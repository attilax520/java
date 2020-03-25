package org.chwin.firefighting.apiserver.fire.service;


import FireCloudLib.*;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FireServerService {
//
//    @Autowired
//    private JdbcDao jdbcDao;
//
//    public String getFireInfo(Map param){
//        StringBuffer strbuff = new StringBuffer();
//        try{
//            FireCloudClient fireClient = new FireCloudClient("F:\\FireClientlic","abcdef.012345678");
//            List<Device> devlist = fireClient.DeviceList;
//            for (Device device : devlist){
//                fireClient.CheckFire(device);
//                fireClient.SetCallBack(new FireEventInterface() {
//                    @Override
//                    public void FireEvent(Object o, Event e) {
//                        Device d = (Device) o;
//
//                        Map devMap = new HashMap();
//                        String firedata = "{\"DevStatus\":\"" + e.DevStatus+"\",\"DevType\":\"" + e.DevType+"\"," +
//                                "\"Did\":\"" + e.Did + "\",\"mid\":\"" + e.Mid+"\"," +
//                                "\"Lid:\"" + e.Lid + "\",\"Note\":\"" + e.Note + "\",\"Time\":\"" + e.Time + "\"}";
//                        System.out.println(firedata);
//                        devMap.put("BUSINESS_DATA", firedata);
//                        devMap.put("DEVICE_ID", d.ProjectId);
//                        devMap.put("IMEI", d.DeviceCode);
//                        Map devdata = jdbcDao.add("IOT_DEVICE_BUSINESS", devMap);
//                        strbuff.append(firedata +",");
//                    }
//
//                    @Override
//                    public void FaultEvent(Object o, Event event) {
//
//                    }
//
//                    @Override
//                    public void LinkEvent(Object o, Event event) {
//
//                    }
//
//                    @Override
//                    public void ShieldEvent(Object o, Event event) {
//
//                    }
//
//                    @Override
//                    public void DeviceEvent(Object o, Event event) {
//
//                    }
//
//                    @Override
//                    public void StatusEvent(Object o, FireStatus fireStatus) {
//
//                    }
//
//                    @Override
//                    public void ConnectionClosedEvent() {
//
//                    }
//                });
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return strbuff.toString();
//    }
@Autowired
private ScriptComponent scriptCmp;
    @Autowired
    private JdbcDao jdbcDao;

    public Object doProcess(Map param){
        String url=(String)param.get("url");
        Map params = new HashMap();
        params.put("db", jdbcDao);
        params.put("prm", param);
        Object rtmap = scriptCmp.excute(getRuleByUrl(url), params);
        return rtmap;
    }
    private String getRuleByUrl(String url){
        return "pub.rpt.iot."+url;
    }
    /**
    *@author aniu
    *@Description 获取某段时间内每天日期
    *@Date 14:47 2018/8/8
    *@Param [begintTime, endTime] 开始时间，结束时间
    *@return java.util.List<java.lang.String>
    **/
    public static List<String> findDaysStr(String begintTime, String endTime) {
//JAVA获取某段时间内每天的日期（String类型，格式为："2018-06-16"）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(begintTime);
            dEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //存放每一天日期String对象的daysStrList
        List<String> daysStrList = new ArrayList<String>();
        //放入开始的那一天日期String
        daysStrList.add(sdf.format(dBegin));

        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);

        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);

        // 判断循环此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，给定的日历字段增加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String dayStr = sdf.format(calBegin.getTime());
            daysStrList.add(dayStr);
        }

        //打印测试数据
        //System.out.println("#####################："+daysStrList);

        return daysStrList;
    }
    /**
    *@author aniu
    *@Description 时间字符串转日期
    *@Date 14:46 2018/8/8
    *@Param [time, sdf] 要转换时间字符串，转换格式
    *@return java.util.Date
    **/
    public static Date strToDate(String time,String sdf) {
//        SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2=new SimpleDateFormat(sdf);
        Date date = new Date();
        try {
            date = format2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getNowDate(String sdf) {
        Date date = new Date();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        SimpleDateFormat dateFormat= new SimpleDateFormat(sdf);
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        Date date = strToDate("2018-07-25"+" 23:59:59","yyyy-MM-dd hh:mm:ss");
        System.out.println("DATE:"+date);
        System.out.println("NOWDATE:"+getNowDate("yyyy-MM-dd hh:mm:ss"));
    }

}
