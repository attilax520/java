package org.chwin.firefighting.apiserver.core.controller;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.chwin.firefighting.apiserver.core.service.SystemService;
import org.chwin.firefighting.apiserver.core.util.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 总体说明
 * <p>具体说明</p>
 *
 * @author cailm
 * @version $Id: org.chwin.firefighting.apiserver.core.controller.SystemController,v 0.1 2018/6/23 5:54 Exp $$
 */
@RestController
@RequestMapping("/sys")
public class SystemController extends BaseSupport{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CacheService cache;
    @Autowired
    private SystemService service;

    @RequestMapping("/userinfo")
    public String userInfo(HttpServletRequest request){
        Map map=this.getInputJSON(request);
        return output(service.getUserInfo(map));
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value = "uname", required = false) String uname,
                        @RequestParam(value = "pwd", required = false) String pwd){
        String un = Encryption.des_decrypt(uname,"12345678910");
        String pw = Encryption.des_decrypt(pwd,(un + "12345678910").substring(0,11));
        if(StringUtils.isBlank(un) || StringUtils.isBlank(pw)) throw new BusinessException("请输入用户名密码！");
        Map map = new HashMap(){{
            put("uname",un);
            put("pwd",pw);
        }};
        Map online= service.doLogin(map);
        if(online == null) throw new BusinessException("用户名密码错误");
        System.out.println("用户: " + online.get("USER_NAME") + "登录了！");
        return output(online);
    }

    @RequestMapping("/loginSmokePro")
    @ResponseBody
    public String loginSmokePro(@RequestParam(value = "uname", required = false) String uname,
                                @RequestParam(value = "pwd", required = false) String pwd,
                                HttpServletRequest request){
        Map mp =new HashMap();
        mp.put("uname", uname);
        mp.put("pwd", pwd);
        Map online = service.doLogin(mp);
        if(online==null)
            throw  new BusinessException("用户名密码错误");
        return output(online);
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        Map map=this.getInputJSON(request);
        service.doLogOut(map);
        return output("");
    }

}
