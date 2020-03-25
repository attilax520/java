package org.chwin.firefighting.apiserver.fire.controller;

import net.sf.json.JSONObject;
import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.core.service.PublicService;
import org.chwin.firefighting.apiserver.fire.enums.EnumErrorMsg;
import org.chwin.firefighting.apiserver.fire.service.FileService;
import org.chwin.firefighting.apiserver.fire.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
*@author aniu
*@Description 附件上传 下载
*@Date 10:55 2018/8/2
*@Param 
*@return 
**/
@ControllerAdvice
@RestController
@CrossOrigin//springMVC解决跨域问题
@RequestMapping("file")
public class FileController extends BaseSupport {

    @Resource
    private FileService fileService;
    
/**
*@author aniu
*@Description 上传文件
*@Date 10:56 2018/8/2
*@Param []
*@return java.lang.String
**/
    @RequestMapping("uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map params = new HashMap<>();
        String filename = request.getParameter("filename");
        String architecture_id = request.getParameter("architecture_id");
        String code_value = request.getParameter("code_value");
//        String ssid = request.getParameter("ssid");
        params.put("architecture_id",architecture_id);
        params.put("unit_id", request.getParameter("UNIT_ID"));
        params.put("code_value",code_value);
        params.put("filename",filename);
        params.put("req",request);
        params.put("res",response);
        return fileService.fileupload(file,params);

    }
    /**
    *@author aniu
    *@Description 下载
    *@Date 10:42 2018/8/3
    *@Param [request]
    *@return java.lang.String
    **/
    @RequestMapping("downloadfile")
    public void downloadFile(HttpServletRequest request,HttpServletResponse res) {
        Map params = new HashMap<>();
        String attac_id = request.getParameter("attac_id");
        params.put("attac_id",attac_id);
        params.put("unit_id", request.getParameter("UNIT_ID"));
        fileService.filedownload(params,res);
//        return output();
    }
    /**
    *@author aniu
    *@Description 文件原始名 重命名
    *@Date 14:54 2018/8/2
    *@Param [request]
    *@return java.lang.String
    **/
    @RequestMapping("rename")
    public String rename( HttpServletRequest request) {
        Map params = new HashMap<>();
        String attac_id = request.getParameter("attac_id");
        String rename = request.getParameter("rename");
        params.put("attac_id",attac_id);
        params.put("rename",rename);

        return output(fileService.rename(params));
    }
    /**
    *@author aniu
    *@Description 删除附件
    *@Date 15:20 2018/8/2
    *@Param [request]
    *@return java.lang.String
    **/
    @RequestMapping("delfile")
    public String delAttac( HttpServletRequest request) {
        Map params = new HashMap<>();
//        String ssid = request.getParameter("ssid");
        String attac_id = request.getParameter("attac_id");
//        params.put("ssid",ssid);
        params.put("ID",attac_id);

        return output(fileService.delfile(params));
    }
    /**
    *@author aniu
    *@Description 获取文件列表
    *@Date 14:39 2018/8/3
    *@Param [request]
    *@return java.lang.String
    **/
    @RequestMapping("getlist")
    public String attacList( HttpServletRequest request) {
        Map params = new HashMap<>();
//        String ssid = request.getParameter("ssid");
        String code_value = request.getParameter("code_value");
//        params.put("ssid",ssid);
        params.put("code_value",code_value);
        params.put("unit_id",request.getParameter("UNIT_ID"));
        return output(fileService.getfiles(params));
    }
    /**
    *@author aniu
    *@Description 根据搜索框 文件名 检索
    *@Date 15:48 2018/8/15
    *@Param [request]
    *@return java.lang.String
    **/
    @RequestMapping("getfilesbyname")
    public String getfilesbyname( HttpServletRequest request) throws Exception{
        Map params = new HashMap<>();
//        String ssid = request.getParameter("ssid");
        String code_value = request.getParameter("code_value");
        String file_name = request.getParameter("file_name");
//        params.put("ssid",ssid);
        params.put("code_value",code_value);
        params.put("file_name",file_name);
        params.put("unit_id", request.getParameter("UNIT_ID"));
        return output(fileService.getfilesbyname(params));
    }

    /**---------------------------                  重构平台后,预案图纸等接口                    ---------------------------**/

    /**
     *  new文件上传接口
     * @param request
     * @param file
     * @return
     */
    @RequestMapping("uploadAttachment")
    public String uploadAttachment(HttpServletRequest request , @RequestParam("file") MultipartFile file){
        Map param = new HashMap();
        param.put("ssid",request.getParameter("ssid"));
        param.put("code_value", request.getParameter("code_value"));
        param.put("file_name",request.getParameter("file_name"));
        return fileService.uploadAttachment(file,param);
    }

    /**
     * 获取文件列表
     * @param request
     * @return
     */
    @RequestMapping("getAttachmentList")
    public String getAttachmentList(HttpServletRequest request){
        Map param = new HashMap();
        param.put("code_value",request.getParameter("code_value"));
        param.put("file_name",request.getParameter("file_name"));
        param.put("start",request.getParameter("start"));
        param.put("limit",request.getParameter("limit"));
        String _IN_UNIT_ID = request.getParameter("UNIT_ID");
        System.out.println("_IN_UNIT_ID: " +_IN_UNIT_ID);
        if(null != _IN_UNIT_ID && !_IN_UNIT_ID.equals("")){   //如果从地图首页进来的单位
            param.put("UNIT_ID", _IN_UNIT_ID);
        }else{
            param.put("ssid",request.getParameter("ssid"));
        }
        return output(fileService.getfileList(param));
    }

    /**
     * 重命名
     * @param data
     * @return
     */
    @RequestMapping("renameAttachment")
    public String renameAttachment(@RequestBody String data){
        JSONObject json = JSONObject.fromObject(data);
        return output(fileService.rename(json));
    }

    /**
     * 删除
     * @param data
     * @return
     */
    @RequestMapping("deleteAttachment")
    public String deleteAttachment(@RequestBody String data){
        JSONObject json = JSONObject.fromObject(data);
        return fileService.deleteAttachment(json);
    }

    /**
     * 下载
     * @param request
     * @param res
     */
    @RequestMapping("downloadAttachment")
    public void downloadAttachment(HttpServletRequest request,HttpServletResponse res) {
        Map<String,String> map = new HashMap();
        map.put("atta_path",request.getParameter("atta_path"));
        map.put("atta_name",request.getParameter("atta_name"));
        map.put("atta_size",request.getParameter("atta_size"));
        fileService.downloadAttachment(map,res);
    }

    /**
     *  技术服务-消防单位-文件上传
     */
    @RequestMapping("uploadFireFiles")
    public String uploadFireFiles(HttpServletRequest request , @RequestParam("file") MultipartFile file){
        Map param = new HashMap();
        param.put("type",request.getParameter("type"));
        param.put("id",request.getParameter("id"));
        param.put("fileType",request.getParameter("fileType"));
        return fileService.uploadFireFiles(file,param);
    }

    /**
     *  消防安全培训/消防疏散演练-文件上传
     */
    @RequestMapping("uploadTrainDrillFiles")
    public String uploadTrainDrillFiles(HttpServletRequest request , @RequestParam("file") MultipartFile file){
        Map param = new HashMap();
        param.put("ssid",request.getParameter("ssid"));
        param.put("train_drill_id",request.getParameter("train_drill_id"));
        param.put("code_value", request.getParameter("code_value"));
        param.put("file_name",request.getParameter("file_name"));
        return fileService.uploadTrainDrillFiles(file,param);
    }
    /**
     * 消防安全培训/消防疏散演练-删除
     * @param data
     * @return
     */
    @RequestMapping("deleteTrainDrillFile")
    public String deleteTrainDrillFile(@RequestBody String data){
        JSONObject json = JSONObject.fromObject(data);
        return fileService.deleteTrainDrillFile(json);
    }
}
