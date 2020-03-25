/**
 * describe: 短信发送工具类
 * @author lxd
 * @date 2018/10/18
 */
package org.chwin.firefighting.apiserver.sms;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.util.HttpclientUtilsV2;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.chwin.firefighting.apiserver.sms.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MesUtils {
    private static final Logger logger = LoggerFactory.getLogger(MesUtils.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    private static String userId = "JJ1177";
    private static String pwd = "102502";
    private static String url = "http://114.67.58.227:8901/sms/v2/std/";
    private static String tbaleName = "SMS_SEND_LOG";
    @Autowired
    private JdbcDao jdbcDao;
    /**
     * @description 单条发送接口
     * @JdbcDao dao实例 （js调用必传,）
     * @param content
     *         短信内容
     * @param serialNo 流水号
     * @param mobile 手机号(群发手机号码用英文逗号隔开)
     * @param method 发送方法（单条，群发...）
     * @return 0:成功 非0:返回错误代码
     */
    public  String sendMes(JdbcDao db,String content, String serialNo, String mobile, String method) throws Exception {
        // 对密码进行加密
        String timestamp = sdf.format(Calendar.getInstance().getTime());
        String encryptPwd = encryptPwd(userId, pwd, timestamp);
        Message message = new Message();
        message.setTimestamp(timestamp);
        message.setUserid(userId.toUpperCase());
        message.setPwd(encryptPwd);
        message.setMobile(mobile);
        message.setCustid(serialNo);
        logger.info("发送短信内容："+ JSONParser.obj2Json(message));
        message.setContent(URLEncoder.encode(content, "GBK"));
        //保存日志
        Map entityMap = saveMesLog(db,content,serialNo,"");
        String result = HttpclientUtilsV2.postJson(url + method, JSONParser.obj2Json(message));
        logger.info("短信返回结果result:"+result);
        updateMesLog(db,result,entityMap);
        return result;
    }


    /**
     * @description 对密码进行加密
     * @param userid
     *        用户账号
     * @param pwd
     *        用户原始密码
     * @param timestamp
     *        时间戳
     * @return 加密后的密码
     */
    public static String encryptPwd(String userid, String pwd, String timestamp) throws Exception {
        // 加密后的字符串
        String encryptPwd = null;

        String passwordStr = userid.toUpperCase() + "00000000" + pwd + timestamp;
        // 对密码进行加密
        encryptPwd = getMD5Str(passwordStr);
        // 返回加密字符串
        return encryptPwd;
    }

    /**
     *
     *
     * @description MD5加密方法
     * @param str
     *        需要加密的字符串
     * @return 返回加密后的字符串
     */
    private static String getMD5Str(String str) throws Exception {
        MessageDigest messageDigest = null;
        // 加密前的准备
        messageDigest = MessageDigest.getInstance("MD5");

        messageDigest.reset();

        messageDigest.update(str.getBytes("UTF-8"));

        byte[] byteArray = messageDigest.digest();

        // 加密后的字符串
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }

    /**
     * @description 保存发送短信记录
     * @param content
     *        短信内容
     * @param serialNo
     *        业务流水号
     * @param result
     *        发送短信返回结果
     * @return 保存日志记录对象
     */
    public Map saveMesLog(JdbcDao db,String content, String serialNo, String result)throws Exception{
        logger.info("保存发送日志"+content);
        if(db!=null){
            jdbcDao = db;
        }
        Map paramMap = new HashMap();
        paramMap.put("content",content);
        paramMap.put("serial_no",serialNo);
        paramMap.put("result",result);
        return jdbcDao.add(tbaleName,paramMap);
    }


    /**
     * @description 更新短信记录返回结果
     * @param result
     *        发送短信返回结果
     * @return 保存日志记录对象
     */
    public Map updateMesLog(JdbcDao db, String result,Map entityMap)throws Exception{
        if(entityMap!=null){
            logger.info("更新发送日志id:"+entityMap.get("id")+"更新时间："+new Date());
            if(db!=null){
                jdbcDao = db;
            }
            Map paramMap = new HashMap();
            paramMap.put("ID",entityMap.get("ID"));
            paramMap.put("result",result);
            return jdbcDao.update(tbaleName,paramMap);
        }
        return  null;
    }
}
