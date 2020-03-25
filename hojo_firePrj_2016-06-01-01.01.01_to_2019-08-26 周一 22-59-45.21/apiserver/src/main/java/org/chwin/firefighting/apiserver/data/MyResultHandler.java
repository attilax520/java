package org.chwin.firefighting.apiserver.data;

import com.alibaba.fastjson.JSON;
import ognl.OgnlException;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyResultHandler implements ResultHandler {

    public static void main(String[] args) throws IOException, OgnlException {
        SqlSessionFactory sqlSessionFactory = MybatisUtil. getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
    List<List<Map>>  li=   session.selectList("retMltRzt",null);
       // session.select("retMltRzt",null,new MyResultHandler());
        System.out.println(JSON.toJSONString(li));
    }

        private final Map<String, String> mappedResults = new HashMap<String, String>();


        @Override
        public void handleResult(ResultContext resultContext) {
            Object map =  resultContext.getResultObject();

            System.out.println(JSON.toJSONString(map));
//            if (map.get("total") instanceof Double) {
//                System.out.println("total Long ");
//                String total = String.valueOf((Double) map.get("total")).split("\\.")[0];
//                if (total.equals("0")) {
//                    mappedResults.put("total", "0");
//                }else {
//                    float yuan = Float.valueOf(total) / 1000;
//                    mappedResults.put("total", String.valueOf(yuan));
//                }
//            }

        }


    }

