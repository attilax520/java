package com.cnhis.cloudhealth.module.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.cnhis.cloudhealth.common.utils.DateUtils;
import com.cnhis.cloudhealth.common.utils.StringUtils;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;

public class AESUtil {
	
    private static final int ZERO = 0;  
    private static final int ONE = 1;  
	
	
	public static void main(String[] args) {
		try {
            //加密
//            encryptfile("e:\\decriptfile\\zbrjyjkkfk20180226171957license.xml");
            //解密
            decriptfile("e:\\licensefile\\坐标软件云健康演示库[001170][2018022715]-NQlx5Do3moxV8Bv1yLpWGwpp.License");
			
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }  
	

    /** 
     * 文件处理方法 
     * code为加密或者解密的判断条件 
     * key 加密密钥 
     */  
    public static String doFile(int code, File file, String key,SysCorporation sysCorporation) throws Exception{  
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));  
        byte[] bytIn = new byte[(int) file.length()];  
        bis.read(bytIn);  
        bis.close();  
        // AES加密 
        SecretKey skey = getKey(key);
        byte[] raw = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        Cipher cipher = Cipher.getInstance("AES");
        if(0 == code){  
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            // 写文件  
            byte[] bytOut = cipher.doFinal(bytIn);
            //获取文件全路径
            String path = file.getPath();
            //获取文件的绝对路径
            String absolutePath = path.substring(0,path.lastIndexOf("\\"));
            //获取文件的后缀
            String suffix = path.substring(path.lastIndexOf("."));
            
            String acceDate = DateUtils.formatDate(sysCorporation.getAccedate(),"yyyyMMddHH"); //有效期至
            
            String newFileName = sysCorporation.getName()+"[有效期"+acceDate+"][生成日期"+DateUtils.getDate("yyyyMMddHH")+"]-"+key;
            
            //新xml文件的路径
            String newXMLPath = absolutePath+"/"+newFileName+suffix;
            //创建新xml文件
            File newFile = new File(newXMLPath);
            IOUtils.write(bytOut, new FileOutputStream(newFile));
            file.delete();
            return newFileName;
        }else if(1 == code){  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 写文件  
            byte[] bytOut = cipher.doFinal(bytIn);
            String result = IOUtils.toString(bytOut,"UTF-8");
            System.out.println(result);
            return result;
        }
        return key;
    }
      
    //文件加密  
    public static String encryptfile(String derectory,SysCorporation sysCorporation) throws Exception {
    	KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        SecretKey skey = kgen.generateKey();  
        byte[] key = skey.getEncoded(); 
        String base64Key = Base64.encodeBase64String(key);
        base64Key = StringUtils.toFileName(base64Key);
        File file = new File(derectory);
        String fileName = doFile(ZERO,file,base64Key,sysCorporation);
        return fileName;
    }
      
    //文件解密  
    public static void decriptfile(String derectory) throws Exception{
        File file = new File(derectory);
        String fileName = "坐标软件云健康演示库[001170][2018022715]-NQlx5Do3moxV8Bv1yLpWGwpp.License";
        fileName = fileName.substring(fileName.lastIndexOf("-")+1,fileName.length());
        String key = fileName.substring(0,fileName.lastIndexOf("."));
        
        doFile(ONE,file,key,null);
    } 
    
    public static SecretKey getKey(String strKey) {
        try {    
           KeyGenerator generator = KeyGenerator.getInstance("AES");
           SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
           secureRandom.setSeed(strKey.getBytes());
           generator.init(128,secureRandom);
           return generator.generateKey();
       }  catch (Exception e) {
            throw new RuntimeException("初始化密钥出现异常 ");
       }
   }
   

}
