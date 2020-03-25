package org.chwin.firefighting.apiserver.core.controller;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.PublicService;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 公用Controller
 * Created by fll on 2017/17.
 */
@RestController
@RequestMapping("/public")
public class PublicController extends BaseSupport {

    @Autowired
    private PublicService publicService;

    /**
     * 日志记录
     */
    private LogUtil logger = new LogUtil(this.getClass());
    /**
     * 根据条件查询结果（不分页）
     * CODE ->sqlName DATA ->查询参数
     *
     * @return List
     */
    @RequestMapping("queryList")
    public String queryList(HttpServletRequest request) {
        String ssid = request.getParameter("ssid");
        return output(publicService.queryList(getInputParam(),ssid));
    }

    /**
     * 根据条件查询结果（分页）
     *
     * @return Map
     */
    @RequestMapping("/queryPage/{sqlName}")
    public String queryPage(@PathVariable String sqlName) {
        return output(publicService.queryPage(sqlName, getInputParam()));
    }


    /**
     * 基础操作（增删改）
     *
     * @return Map
     */
    @RequestMapping("/execute")
    public String execute() {
        return output(publicService.execute(getInputParam()));
    }


    /**
     * 根据ID获取记录
     *
     * @return Map
     */
    @RequestMapping("/queryById")
    public String queryById() {
        return output(publicService.getById(getInputParam()));
    }

    /**
     * 根据条件查询结果（成树）
     * CODE ->sqlName DATA ->查询参数
     *
     * @return List
     */
    @RequestMapping("/queryTree")
    public String queryTree() {
        return output(publicService.queryTree(getInputParam()));
    }

    /**
     * 上传图片到oss服务器
     * @param multipartFile
     * @return
     */
    @RequestMapping("/uploadFileOss")
    public String upload(@RequestParam("file") MultipartFile multipartFile)  {
        logger.info("开始上传文件...........");
        String  filePath = "";
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BusinessException("上传文件数据异常");
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            throw new BusinessException("上传文件格式异常！");
        }
        Map<String,Object> map = new HashMap<>();
        filePath = publicService.upload(multipartFile);
        map.put("url",filePath);
        return JSONParser.obj2Json(map);
    }

    /**
     * 基础操作（增删改）
     * 把url中的参数也放进去
     * @return Map
     */
    @RequestMapping("/executeWithReq")
    public String executeWithReq(HttpServletRequest request) {
        Map param = getInputParam();
        ((Map) param.get("DATA")).put("req",request);
        return output(publicService.execute(param));
    }
}
