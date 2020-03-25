package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by liming on 2017/9/18.
 */
public class Encryption {
    private static String encrypt(String source,String mode) {
        String result = "";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(mode);
            byte[]  str=messageDigest.digest(source .getBytes(CONSTANTS.UTF8));
            return Base64.encodeBase64String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sha1(String content){
       return encrypt(content,CONSTANTS.SECURITY_SHA1);
    }

    public static String encryptPWD(String password){
        return md5(password);
    }

    public static String md5(String content) {
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(CONSTANTS.SECURITY_MD5);
            messageDigest.reset();
            messageDigest.update(content.getBytes(CONSTANTS.UTF8));
            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            String mdstr = md5StrBuff.toString();
            return Base64.encodeBase64String(mdstr.getBytes());
        }catch(Exception e){

        }
        return null;
        // return encrypt(content,CONSTANTS.SECURITY_MD5);
    }

    public static String des_encrypt(String data, String key) {
        try {
            if (StringUtils.isBlank(key)) key = CONSTANTS._DES_PWD;
            byte[] bt = encrypt(data.getBytes(CONSTANTS.UTF8), key.getBytes(CONSTANTS.UTF8));
            String strs = Base64.encodeBase64String(bt);
            return strs;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String des_encrypt(String data){
        return des_encrypt(data,null);
    }

    public static String des_decrypt(String data){
        return des_decrypt(data,null);
    }

    public static String des_decrypt(String data, String key) {
        try{
            if (StringUtils.isBlank(data))return null;
            if(StringUtils.isBlank(key))key=CONSTANTS._DES_PWD;
            byte[] buf = Base64.decodeBase64(data);
            byte[] bt = decrypt(buf,key.getBytes(CONSTANTS.UTF8));
            return new String(bt);
        }catch(Exception e){
            return null;
        }
    }

    public static String aes_encrypt(String content, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String aes_decrypt(String content, String key) {
        if (content.length() < 1)
            return null;
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CONSTANTS.SECURITY_DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(CONSTANTS.SECURITY_DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CONSTANTS.SECURITY_DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(CONSTANTS.SECURITY_DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
}
