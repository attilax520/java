package org.chwin.firefighting.apiserver.fire.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.PublicService;
import org.chwin.firefighting.apiserver.core.util.AliOSSUtil;
import org.chwin.firefighting.apiserver.core.util.FileUploadUtil;
import org.chwin.firefighting.apiserver.fire.enums.EnumErrorMsg;
import org.chwin.firefighting.apiserver.fire.enums.EnumFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aniu
 * @Description
 * @Date 10:54 2018/8/2
 * @Param
 * @return
 **/
@Service
public class FileService {

    @Resource
    private JdbcDao jdbcDao;
//    @Value("${spring.http.multipart.maxFileSize}")
//    private String maxFileSize;

    /**
     * @return java.util.Map
     * @author aniu
     * @Description 附件上传
     * @Date 15:19 2018/8/2
     * @Param [file, params]
     **/
    public String fileupload(MultipartFile file, Map params) throws Exception{

        //附件唯一名称
        String contentType = file.getContentType();
        System.out.println("contentType: "+contentType);
        String fileName = params.get("filename").toString();
//        String fileName = file.getOriginalFilename();
//        fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
        long size = file.getSize();
        String extension = getExtension(fileName);
        String newName = Clock.systemUTC().millis() + "." + extension;
        if (null == EnumFileType.find(contentType)) {//文件类型不支持
            RspBody result = new RspBody(EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getCode(), EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getMsg(), null);
            return result.toJson();
        }
//        Map param = new HashMap<>(1);
//        param.put("ID", params.get("unit_id"));
//        Map unit = jdbcDao.loadRowByCode("dtset_unit_info", param);
//        String dept_id = unit.get("DEPT_ID").toString();
        //附件保存

        String path = null;//返回附表保存完毕后的完整路径
        try {
            path = FileUploadUtil.uploadMultipartFile(file, newName);
        } catch (Exception e) {//上传失败
            e.printStackTrace();
            RspBody result = new RspBody(EnumErrorMsg.UPLOAD_FAIL.getCode(), EnumErrorMsg.UPLOAD_FAIL.getMsg(), null);
            return result.toJson();
        }
        //保存单位关联附件表记录
        Map attachment_info = new HashMap<>();
        attachment_info.put("ATTA_PATH", path);
        attachment_info.put("ATTA_NAME", getFileName(fileName));
        System.out.println(contentType);
        attachment_info.put("ATTA_TYPE", EnumFileType.find(contentType).getFlag());
        attachment_info.put("ATTA_SIZE", size);
        attachment_info.put("UPLOAD_TYPE", extension);
        attachment_info.put("FILE_TYPE",params.get("code_value")); //文件类型
        attachment_info.put("UNIT_ID", params.get("unit_id"));

        Map attac_map = jdbcDao.add("ATTACHMENT_INFO", attachment_info);
        String attac_id = attac_map.get("ID").toString();

        String code_value = params.get("code_value").toString();
//        String architecture_id = params.get("architecture_id").toString();
        Map unit_attac = new HashMap<>();
//        unit_attac.put("DEPT_ID", dept_id);
//        unit_attac.put("ARCHITECTURE_ID", architecture_id);
        unit_attac.put("ATTAC_BUSINESS_TYPE", code_value);
        unit_attac.put("ATTAC_ID", attac_id);
        unit_attac.put("UNIT_ID", params.get("unit_id"));
        jdbcDao.add("UNIT_ATTAC", unit_attac);
        //上传成功
        RspBody result = new RspBody(EnumErrorMsg.UPLOAD_SUCCESS.getCode(), EnumErrorMsg.UPLOAD_SUCCESS.getMsg(), null);
        return result.toJson();
    }

    /**
     * @return java.util.Map
     * @author aniu
     * @Description 下载
     * @Date 10:49 2018/8/3
     * @Param [params, res]
     **/
    public void filedownload(Map params, HttpServletResponse res) {
        //根据文件id 获取物理路径
        String attac_id = params.get("attac_id").toString();
        Map attacParam = new HashMap<>();
        attacParam.put("ID", attac_id);
        Map attac = jdbcDao.loadRowByCode("fire_web_attac_by_id", attacParam);
        String atta_path = null;
        String fileName = null;
        String upload_type = null;
        String atta_size = null;
        if (null != attac) {
            atta_path = attac.get("ATTA_PATH").toString();
            fileName = attac.get("ATTA_NAME").toString();
            upload_type = attac.get("UPLOAD_TYPE").toString();
            atta_size = attac.get("ATTA_SIZE").toString();
        }
        res.setHeader("content-type", "application/octet-stream");
        res.setHeader("Content-Length", String.valueOf(atta_size));
        res.setContentType("application/octet-stream");
        try {
            fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + "." + upload_type);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(atta_path)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @return java.util.Map
     * @author aniu
     * @Description 为附件原始名 重命名
     * @Date 15:19 2018/8/2
     * @Param [params]
     **/
    public Map rename(Map params) {
        String attac_id = params.get("attac_id").toString();
        String rename = params.get("rename").toString();
        Map updateMap = new HashMap<>();
        updateMap.put("ID", attac_id);
        updateMap.put("ATTA_NAME", rename);
        try {
            jdbcDao.update("ATTACHMENT_INFO", updateMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EnumErrorMsg.RENAME_FAIL.getMsg(), EnumErrorMsg.RENAME_FAIL.getCode());
        }
        throw new BusinessException(EnumErrorMsg.RENAME_SUCCESS.getMsg(), EnumErrorMsg.RENAME_SUCCESS.getCode());
    }

    /**
     * @return java.util.Map
     * @author aniu
     * @Description 删除附件
     * @Date 15:19 2018/8/2
     * @Param [params]
     **/
    public Map delfile(Map params) {
//        String attac_id = params.get("attac_id").toString();
//
//         Map a = new HashMap();
//         a.put("ID", attac_id);
//         jdbcDao.del("ATTACHMENT_INFO", params);

//        String ssid = params.get("ssid").toString();
//        Map param = new HashMap<>(1);
//        param.put("ID", ssid);
//        Map unit = jdbcDao.loadRowByCode("fire_web_ssid_unitid", param);
//        String dept_id = unit.get("DEPT_ID").toString();
        //根据单位id 和 附件id 查询关联表中 唯一记录
//        Map selMap = new HashMap<>(2);
////        selMap.put("DEPT_ID", dept_id);
//        selMap.put("ATTAC_ID", attac_id);
//        Map map = jdbcDao.loadRowByCode("fire_web_unit_attac", selMap);
//        //根据得到的记录id 删除该条记录
//        String id = map.get("ID").toString();
//        Map delUnitAttac = new HashMap<>(1);
//        delUnitAttac.put("ID", id);
//        try {
//            jdbcDao.del("UNIT_ATTAC", delUnitAttac);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BusinessException(EnumErrorMsg.DELETE_FILE_FAIL.getMsg(), EnumErrorMsg.DELETE_FILE_FAIL.getCode());
//        }
//        //删除附件表中对应附件
//        Map delMap = new HashMap<>(1);
//        delMap.put("ID", attac_id);
        try {
            jdbcDao.remove("ATTACHMENT_INFO", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EnumErrorMsg.DELETE_FILE_FAIL.getMsg(), EnumErrorMsg.DELETE_FILE_FAIL.getCode());
        }
        throw new BusinessException(EnumErrorMsg.DELETE_FILE_SUCCESS.getMsg(), EnumErrorMsg.DELETE_FILE_SUCCESS.getCode());
    }

    /**
     * @return java.util.List
     * @author aniu
     * @Description 获取附件列表
     * @Date 16:00 2018/8/2
     * @Param [params]
     **/
    public List getfiles(Map params) {
        String code_value = params.get("code_value").toString();
        Map param = new HashMap<>(1);
        param.put("ID", params.get("unit_id"));
        Map unit = jdbcDao.loadRowByCode("dtset_unit_info", param);
        String dept_id = unit.get("DEPT_ID").toString();
        //根据单位id 以及 附件业务类型 获取多条记录
        Map map = new HashMap<>();
//        map.put("DEPT_ID", dept_id);
        map.put("FILE_TYPE", code_value);
        map.put("UNIT_ID",params.get("unit_id"));
        List list = jdbcDao.loadListByCode("fire_web_attac_info", map);
        return list;
    }
    /**
    *@author aniu
    *@Description 根据搜索框 输入 文件名 检索文件
    *@Date 15:47 2018/8/15
    *@Param [params]
    *@return java.util.List
    **/
    public List getfilesbyname(Map params) {
        String unit_id = params.get("unit_id").toString();
        String code_value = params.get("code_value").toString();
        String file_name = params.get("file_name").toString();
        Map param = new HashMap<>(1);
        param.put("ID", unit_id);
        Map unit = jdbcDao.loadRowByCode("dtset_unit_info", param);
        String dept_id = unit.get("DEPT_ID").toString();
        //根据单位id 以及 附件业务类型 获取多条记录
        Map map = new HashMap<>();
//        map.put("DEPT_ID", dept_id);
        map.put("ATTA_NAME", file_name);
        map.put("FILE_TYPE", code_value);
        map.put("UNIT_ID", unit_id);
        List list = jdbcDao.loadListByCode("fire_web_attac_info", map);
        return list;
    }

    //通过文件名获取拓展名
    private String getExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot < 0) {
            return "";//没有拓展名
        }
        String extension = fileName.substring(lastIndexOfDot + 1);
        return extension;
    }

    //获取无扩展名文件名
    private String getFileName(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot < 0) {
            return "";//没有拓展名
        }
        String name = fileName.substring(0, lastIndexOfDot);
        return name;
    }

    /**---------------------------                  重构平台后,预案图纸等接口                    ---------------------------**/
    @Autowired
    private PublicService publicService;
    public String uploadAttachment(MultipartFile file , Map param){
        //根据ssid查到单位id
        List<Map> units = jdbcDao.loadListByCode("qry_unit_by_ssid",param);
        if ( units.size() != 1 ){
            throw new BusinessException("查询到多个单位,请登录单位账号!");
        }
        if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
            throw new BusinessException("上传文件数据异常");
        }
        String contentType = file.getContentType();
        System.out.println(contentType);
        if (!contentType.contains("")) {
            throw new BusinessException("上传文件格式异常！");
        }else{
            if (null == EnumFileType.find(contentType)) {//文件类型不支持
                RspBody result = new RspBody(EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getCode(), EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getMsg(), null);
                return result.toJson();
            }
        }
        //调用实际上传接口->OSS
        String path = publicService.upload(file);

        //保存单位关联附件表记录
        Map attachment_info = new HashMap<>();
        attachment_info.put("ATTA_PATH", path);
        attachment_info.put("ATTA_NAME", param.get("file_name"));
        attachment_info.put("ATTA_TYPE", EnumFileType.find(contentType).getFlag());
        attachment_info.put("ATTA_SIZE", file.getSize());
        attachment_info.put("UPLOAD_TYPE", getExtension(file.getOriginalFilename()));
        attachment_info.put("FILE_TYPE",param.get("code_value")); //文件类型
        attachment_info.put("UNIT_ID", units.get(0).get("ID"));

        Map attac_map = jdbcDao.add("ATTACHMENT_INFO", attachment_info);
        String attac_id = attac_map.get("ID").toString();

        String code_value = param.get("code_value").toString();
        Map unit_attac = new HashMap<>();
        unit_attac.put("ATTAC_BUSINESS_TYPE", code_value);
        unit_attac.put("ATTAC_ID", attac_id);
        unit_attac.put("UNIT_ID", units.get(0).get("ID"));
        jdbcDao.add("UNIT_ATTAC", unit_attac);

        //上传成功
        RspBody result = new RspBody(EnumErrorMsg.UPLOAD_SUCCESS.getCode(), EnumErrorMsg.UPLOAD_SUCCESS.getMsg(), null);
        return result.toJson();
    }

    public Map getfileList(Map param) {
        List<Map> units = jdbcDao.loadListByCode("qry_unit_by_ssid",param);
        if (units.size() > 1 ){
            throw new BusinessException("查询到多个单位,请登录单位账号!");
        }
        String code_value = param.get("code_value").toString();
        String file_name = "";
        if (param.get("file_name") != null && !"".equals(param.get("file_name"))){
            file_name = param.get("file_name").toString();
        }
        //根据单位id 以及 附件业务类型 获取多条记录
        Map map = new HashMap<>();
        if (!"".equals(file_name)) {
            map.put("ATTA_NAME", "%"+file_name+"%");
        }
        map.put("FILE_TYPE", code_value);
        map.put("UNIT_ID", units.get(0).get("ID"));
        Map pg = new HashMap();
        pg.put("start",Integer.valueOf(param.get("start").toString()));
        pg.put("limit",Integer.valueOf(param.get("limit").toString()));
        pg.put("tcount",1);
        return jdbcDao.loadPage("fire_web_attac_info", map,pg);
    }

    public String deleteAttachment(Map json){
        try {
            //删除数据库记录
            jdbcDao.remove("ATTACHMENT_INFO", json);
            String atta_path = json.get("ATTA_PATH").toString();
            String ossFileName = atta_path.substring(atta_path.lastIndexOf("/")+1);
            String folder= atta_path;
            for(int i = 0; i < 3; i++){
                folder = folder.substring(folder.indexOf("/")+1 );
            }
            folder = folder.substring(0,folder.lastIndexOf("/")+1);
            //删除文件服务器的文件
            AliOSSUtil.deleteFile(AliOSSUtil.getOSSClient(),AliOSSUtil.BACKET_NAME,folder,ossFileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EnumErrorMsg.DELETE_FILE_FAIL.getMsg(), EnumErrorMsg.DELETE_FILE_FAIL.getCode());
        }
        RspBody result = new RspBody(EnumErrorMsg.DELETE_FILE_SUCCESS.getCode(), EnumErrorMsg.DELETE_FILE_SUCCESS.getMsg(), null);
        return result.toJson();
    }

    public void downloadAttachment(Map<String,String> map , HttpServletResponse res) {
        String objectName = map.get("atta_path");
        for (int i = 0; i < 3; i++) {
            objectName = objectName.substring(objectName.indexOf("/") + 1);
        }
        String fileName = map.get("atta_name");
        try {
            fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        res.setHeader("Content-Length", map.get("atta_size"));
        res.setContentType("application/octet-stream;charset=UTF-8");
        res.setHeader("Content-Disposition", "attachment;filename=" +fileName);

        OSSClient ossClient = AliOSSUtil.getOSSClient();
        BufferedInputStream bin = null;
        try {
            OSSObject ossObject = ossClient.getObject(AliOSSUtil.BACKET_NAME, objectName);
            bin = new BufferedInputStream(ossObject.getObjectContent());
            BufferedOutputStream out=new BufferedOutputStream(res.getOutputStream());
            byte[] car = new byte[1024];
            int L = 0;
            while ((L = bin.read(car)) != -1) {
                out.write(car, 0, L);
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        }catch (IOException e) {
                e.printStackTrace();
        }finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 关闭OSSClient。
            ossClient.shutdown();
        }

    }


    public String uploadFireFiles(MultipartFile file , Map param){
        if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
            throw new BusinessException("上传文件数据异常");
        }
        String contentType = file.getContentType();
        if ( !contentType.contains("") ) {
            throw new BusinessException("上传文件格式异常！");
        }
        if(null == EnumFileType.find(contentType)) {//文件类型不支持
            RspBody result = new RspBody(EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getCode(), EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getMsg(), null);
            return result.toJson();
        }
        //调用实际上传接口->OSS
        String path = publicService.upload(file);

        String NAME = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
        String FILE_TYPE = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1,file.getOriginalFilename().length());
        String SIZE = file.getSize()/1024L<1 ? String.format("%.2f", file.getSize() /1024d) + "KB" : file.getSize()/1024L + "KB";
        String UNIT_MAINTENANCE_ID = (String) param.get("id");
        String TYPE = (String) param.get("type");
        String UPLOAD_TYPE = (String) param.get("fileType");
        param.clear();
        param.put("UNIT_MAINTENANCE_ID",UNIT_MAINTENANCE_ID);
        param.put("NAME",NAME);
        param.put("FILE_TYPE",FILE_TYPE);
        param.put("SIZE",SIZE);
        param.put("PATH",path);
        param.put("TYPE",TYPE);//1.维修记录、2.保养记录、3.检测报告、4.整改建议、5.工程联系单、6.安全评估报告、7.其他
        param.put("UPLOAD_TYPE",UPLOAD_TYPE);//上传的文件类型（1文件，2资质证书）
        jdbcDao.add("UNIT_MAINTENANCE_FILE",param);
        //上传成功
        RspBody result = new RspBody(EnumErrorMsg.UPLOAD_SUCCESS.getCode(), EnumErrorMsg.UPLOAD_SUCCESS.getMsg(), path);
        return result.toJson();
    }

    public String uploadTrainDrillFiles(MultipartFile file , Map param){
        //根据ssid查到单位id
        List<Map> units = jdbcDao.loadListByCode("qry_unit_by_ssid",param);
        if ( units.size() != 1 ){
            throw new BusinessException("查询到多个单位,请登录单位账号!");
        }
        if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
            throw new BusinessException("上传文件数据异常");
        }
        String contentType = file.getContentType();
        System.out.println(contentType);
        if (!contentType.contains("")) {
            throw new BusinessException("上传文件格式异常！");
        }else{
            if (null == EnumFileType.find(contentType)) {//文件类型不支持
                RspBody result = new RspBody(EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getCode(), EnumErrorMsg.FILE_TYPE_NOT_SUPPORT.getMsg(), null);
                return result.toJson();
            }
        }
        //调用实际上传接口->OSS
        String path = publicService.upload(file);

        //保存单位关联附件表记录
        Map attachment_info = new HashMap<>();
        attachment_info.put("ATTA_PATH", path);
        attachment_info.put("ATTA_NAME", param.get("file_name"));
        attachment_info.put("ATTA_TYPE", EnumFileType.find(contentType).getFlag());
        attachment_info.put("ATTA_SIZE", file.getSize());
        attachment_info.put("UPLOAD_TYPE", getExtension(file.getOriginalFilename()));
        attachment_info.put("FILE_TYPE",param.get("code_value")); //文件类型
        attachment_info.put("UNIT_ID", units.get(0).get("ID"));

        Map attac_map = jdbcDao.add("ATTACHMENT_INFO", attachment_info);
        String attac_id = attac_map.get("ID").toString();

        //插入文件-消防安全培训/疏散演练中间表
        Map unit_attac = new HashMap<>();
        unit_attac.put("attac_id", attac_id);
        unit_attac.put("train_drill_id", param.get("train_drill_id"));
        jdbcDao.add("TRAIN_DRILL_ATTAC_REL", unit_attac);

        //上传成功
        RspBody result = new RspBody(EnumErrorMsg.UPLOAD_SUCCESS.getCode(), EnumErrorMsg.UPLOAD_SUCCESS.getMsg(), null);
        return result.toJson();
    }

    public String  deleteTrainDrillFile(Map json){
        deleteAttachment(json);
        Map param = new HashMap();
        param.put("attac_id",json.get("ID"));
        jdbcDao.remove("TRAIN_DRILL_ATTAC_REL",param,"attac_id");
        //删除成功
        RspBody result = new RspBody(EnumErrorMsg.DELETE_FILE_FAIL.getCode(), EnumErrorMsg.DELETE_FILE_FAIL.getMsg(), null);
        return result.toJson();
    }
}
