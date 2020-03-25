package org.chwin.firefighting.apiserver.fire.listener;

import FireCloudLib.*;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 火警报告监听器，初始化的时候启动程序
 * @zhouzuorong 2018/6/6
 */

//@WebListener
public class FireCheckListener  implements ServletContextListener{

    private String DEFAULT_CONFIG_PATH = "static/license/FireClientlic";
    @Autowired
    private JdbcDao jdbcDao;
//    private Resource resource = new DefaultResourceLoader().getResource("classpath:"+ DEFAULT_CONFIG_PATH);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("IndexListener2 contextDestroyed method");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
//        try {
//            //获取指定文件，打成jar包的方式必须用流读取
//            InputStream is = Resource.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_PATH);
////            BufferedReader bf = new BufferedReader(new InputStreamReader(is, CONSTANTS.UTF8));
//            BufferedInputStream bis = new BufferedInputStream(is);
//            //获取到文件内容生成临时文件
//            File tempFile = new File("FireClientlic");
//            FileOutputStream fos = new FileOutputStream(tempFile);
//            byte[] buf = new byte[1024];
//            int size = 0;
//            while ((size = bis.read(buf)) != -1){
//                fos.write(buf, 0, size);
//            }
////            String filePath = resource.getFile().getPath();
//            fos.close();
//            bis.close();
//            //传临时文件路劲
//            FireCloudClient fireClient = new FireCloudClient(tempFile.getPath(),"abcdef.012345678");
//            List<Device> deviceList = fireClient.DeviceList;
//            for (Device device : deviceList){
//                fireClient.CheckFire(device);
//                fireClient.SetCallBack(new FireEventInterface() {
//                    @Override
//                    public void FireEvent(Object o, Event event) {
//                        Device d = (Device) o;
//                        Map devMap = new HashMap();
//                        String firedata = "{\"DevStatus\":\"" + event.DevStatus+"\",\"DevType\":\"" + event.DevType+"\"," +
//                                "\"Did\":\"" + event.Did + "\",\"mid\":\"" + event.Mid+"\"," +
//                                "\"Lid:\"" + event.Lid + "\",\"Note\":\"" + event.Note + "\",\"Time\":\"" + event.Time + "\"}";
////                        System.out.println(firedata);
//                        devMap.put("BUSINESS_DATA", firedata);
//                        devMap.put("IMEI", d.DeviceCode);
//                        devMap.put("DEVICE_ID", d.ProjectId);
//                        Map devdata = jdbcDao.add("IOT_DEVICE_BUSINESS", devMap);
//
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

//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

}
