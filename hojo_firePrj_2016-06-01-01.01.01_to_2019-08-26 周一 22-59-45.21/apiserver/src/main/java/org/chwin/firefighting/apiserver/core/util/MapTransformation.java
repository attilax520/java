package org.chwin.firefighting.apiserver.core.util;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.atan2;
import static java.lang.StrictMath.*;

@Component
public class MapTransformation {
    @Autowired
    private JdbcDao jdbcDao;

//    public JdbcDao getJdbcDao(){
//        return getJdbcDao();
//    }

    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 对double类型数据保留小数点后多少位
     * 高德地图转码返回的就是 小数点后6位，为了统一封装一下
     *
     * @param digit 位数
     * @param in    输入
     * @return 保留小数位后的数
     */
    static double dataDigit(int digit, double in) {
        return new BigDecimal(in).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    public void mapCoordConverByID(String FC_ID) throws SQLException, ClassNotFoundException {
        String URL = "jdbc:mysql://192.168.2.108:3306/hy_test?useUnicode=true&amp;characterEncoding=utf-8";
        String USER = "hy";
        String PASSWORD = "123456";
        //2.获得数据库链接
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<Map> list = new ArrayList<Map>();
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            st = conn.createStatement();
            rs = st.executeQuery("select ID,LONGITUDE,LATITUDE from IOT_DEVICE WHERE FC_ID='"+FC_ID+"'");

            //4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                Map map = new HashMap(1);
                double lo = rs.getDouble("LONGITUDE");
                double la = rs.getDouble("LATITUDE");
                String id = rs.getString("ID");
                Map s = bd_encrypt(lo, la);
                map.put("LONGITUDE",s.get("LONGITUDE"));
                map.put("LATITUDE",s.get("LATITUDE"));
                map.put("ID",id);
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {//轻量级，创建和销毁rs所需要的时间和资源较小
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {//轻量级，创建和销毁rs所需要的时间和资源较小
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            st = conn.createStatement();
            for(int i=0;i<list.size();i++){
                if(list.size()>0&null!=list){
                    Map map = list.get(i);
                    System.out.println("id："+map);
                    String sql = "update IOT_DEVICE SET LONGITUDE='" + map.get("LONGITUDE") + "',LATITUDE='" +
                            map.get("LATITUDE") + "' where ID='" + map.get("ID") + "' ";
                    st.executeUpdate(sql);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {//轻量级，创建和销毁rs所需要的时间和资源较小
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {//轻量级，创建和销毁rs所需要的时间和资源较小
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {//重量级，创建和销毁rs所需要的时间和资源较小
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将火星坐标转变成百度坐标
     */
    public static Map bd_encrypt (double longitude,double latitude){
        double x = longitude, y = latitude;
        double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
        Map map = new HashMap<String, Double>(1);
        map.put("LONGITUDE", (dataDigit(6, z * cos(theta) + 0.0065)));
        map.put("LATITUDE", (dataDigit(6, z * sin(theta) + 0.006)));
        return map;
    }

    /**
     * 将百度坐标转变成火星坐标
     */
    public static Map bd_decrypt ( double longitude, double latitude){
        double x = longitude - 0.0065, y = latitude - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
        Map map = new HashMap<String, Double>(1);
        map.put("LONGITUDE", ((dataDigit(6, z * cos(theta)))));
        map.put("LATITUDE", ((dataDigit(6, z * sin(theta)))));
        return map;
    }
}