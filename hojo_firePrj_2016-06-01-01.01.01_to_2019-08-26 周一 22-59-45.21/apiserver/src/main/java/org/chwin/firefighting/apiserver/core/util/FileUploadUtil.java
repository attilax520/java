package org.chwin.firefighting.apiserver.core.util;

import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * 文件上传工具类
 * <p>{@code upload_method} 文件的上次存储位置：文件系统、远程文件系统、OSS等等；
 * <p>{@code file_path} 文件存储路径；
 * <p>配置在 application.yml
 * @Author GJW
 * @since 2017/10/30.
 */
public class FileUploadUtil {

    static LogUtil logger = new LogUtil(FileUploadUtil.class);

    private static String upload_method = SpringUtil.getProp("file.uploadMethod");
    private static String file_path = SpringUtil.getProp("file.uploadPath");

    private final static String METHOD_FILE_SYSTEM = "FILE_SYSTEM";
    private final static String METHOD_OSS = "OSS";
    private final static String METHOD_REMOTE_FILE_SYSTEM = "FTP";

    public static String uploadMultipartFile(MultipartFile f, String name){

        Assert.notNull(f,"file must not be null");
        try{
            return generateFile(f.getBytes(),name);
        }catch (Exception e){
            logger.error("uploadMultipartFile error");
            throw new BusinessException("uploadMultipartFile error" + ExceptionUtil.getStackTraceAsString(e));
        }
    }

    public static String uploadFile(File f,String fileName){
        try {
            byte[] fileContent = FileCopyUtils.copyToByteArray(f);
            return upload2FileSystem(fileContent,fileName);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            return null;
        }
    }

    private static String generateFile(byte[] fileContent, String fileName){
        switch (upload_method){
            case METHOD_FILE_SYSTEM:
                return upload2FileSystem(fileContent,fileName);
            case METHOD_OSS:
            case METHOD_REMOTE_FILE_SYSTEM:
            default:
                return null;
        }

    }

    private static String upload2FileSystem(byte[] fileContent, String fileName) {

        try {
            String path = file_path + getSaveFilePath();
            checkFolder(path);
            File file = new File(path + fileName);
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(file));
            out.write(fileContent);
            out.flush();
            out.close();
            return file.getPath();
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            return null;
        }
    }

    private static void checkFolder(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 根据编号得到多媒体文件存放路径
     *
     * @return
     */
    public static String getSaveFilePath() {
        String path = "";
        String dateStr = "";
        try {
            dateStr = DateUtil.format(new Date(), DateUtil.pattern8);
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(4, 6);
            String day = dateStr.substring(6, 8);

            if (month != null && month.length() < 2) {
                month = "0" + month;
            }
            if (day != null && day.length() < 2) {
                day = "0" + day;
            }
            path = File.separator + year + File.separator + month + File.separator + day
                    + File.separator;
        } catch (Exception e) {
        }
        return path;
    }
}
