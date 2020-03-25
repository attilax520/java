package org.chwin.firefighting.apiserver.fire.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.util.HttpUtil;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.chwin.firefighting.apiserver.fire.service.CameraInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/camera")
public class CameraInsertController extends BaseSupport {

    //设备序列号：C20994797 ， 设备类型： CS-C6C-3B1WFR
    private static String query = "appKey=28974d61059f49ab905e783f75fae052&appSecret=dd9ffff23df1ded371638315bc7aad4f";
    private String TokenValue = "at.8ibi93549yyuwn9f4mrfy7oq4790k5oy-5mla6h9vca-0bifo1j-0bvtwzyxw";
    private  HttpUtil httpUtil = new HttpUtil();

    @Autowired
    private CameraInsertService cameraService;

    /**
     * 查询所有列表信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/devicelist")
    public String getDeviceList()throws Exception{
        String result = httpUtil.load("https://open.ys7.com/api/lapp/device/list", "accessToken="+TokenValue);
        cameraService.insertCameraVideo(result);
        return this.output(result);
    }

    /**
     * 获取视频地址
     * @return
     * @throws Exception
     */
    @RequestMapping("/videolist")
    public String getVideoList() throws Exception {
        String result = httpUtil.load(" https://open.ys7.com/api/lapp/video/by/time",
                "accessToken="+TokenValue+"&deviceSerial=C20994797");
        return this.output(result);

    }

    @RequestMapping("/video")
    public String getVideoByTime()  throws Exception {
        String result = httpUtil.load("https://open.ys7.com/api/lapp/video/by/time",
                "accessToken="+TokenValue+"&deviceSerial=C20994797&channelNo=1");
        return this.output(result);
    }

    /**
     * 开通直播功能
     * @return
     * @throws Exception
     */
    @RequestMapping("/videoopen")
    public String setVideoOpen()throws Exception{

        String result = httpUtil.load(" https://open.ys7.com/api/lapp/video/by/time",
                "accessToken="+TokenValue+"&deviceSerial=C20994797");
        return this.output(result);
    }

    /**
     * 获得登录萤石云的token值
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public String getToken() throws MalformedURLException,ProtocolException,IOException {

       //TOKEN值： at.cy50dj54ako758zn0umb6fe2deq7hdjm-32fftrkm72-13wmoei-vz35toipq
        URL restURL = new URL("https://open.ys7.com/api/lapp/token/get");
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        //请求方式
        conn.setRequestMethod("POST");
        //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
        conn.setDoOutput(true);
        //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        conn.setAllowUserInteraction(false);

        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);

        ps.close();

        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line,resultStr="";

        while(null != (line=bReader.readLine()))
        {
            resultStr +=line;
        }
        System.out.println("3412412---"+resultStr);
        bReader.close();

        return "";
    }

    public static void main(String [] str){
        /*CameraInsertController controller = new CameraInsertController();
        try{
            controller.getToken();
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }
}

