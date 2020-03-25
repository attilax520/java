package org.chwin.firefighting.apiserver.warning;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.chwin.firefighting.apiserver.net.HttpclientUtilsV3;

import java.io.File;

public class warningTest {

    public static void main(String[] args) throws Exception {
        //test
        String mqMsg= FileUtils.readFileToString(new File("D:\\00wkspc\\v4master\\fire\\apiserver\\src\\main\\java\\org\\chwin\\firefighting\\apiserver\\warning\\iota2.json"));

       new WarningManager().mqHandler(mqMsg);
    //    System.out.println(HttpclientUtilsV3.get("http://www.baidu.com"));

    }
}
