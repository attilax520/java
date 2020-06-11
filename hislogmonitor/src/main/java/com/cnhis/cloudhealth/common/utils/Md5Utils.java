package com.cnhis.cloudhealth.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;




/*
 * MD5 算法
 */
/**
 * @ClassName: Md5Utils
 * @Description: 加密
 * @author yangzhengxin(Jason)
 * @date 2014年5月1日 上午10:48:40
 */
public class Md5Utils {

    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public Md5Utils() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
    	System.out.println(encryptionMd5("123456"));
    }

    /**
     * 加密
     * 
     * @Title: encryptionMd5
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param str
     * @return
     * @return String 返回类型
     * @throws
     */
	public static String encryptionMd5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return Base64.encode(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证密码
     * 
     * @Title: validateMd5
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param str
     * @param md5str
     * @return
     * @return String 返回类型
     * @throws
     */
	public static boolean validateMd5(String str, String md5str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return Base64.encode(messageDigest.digest()).toString().equals(md5str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}