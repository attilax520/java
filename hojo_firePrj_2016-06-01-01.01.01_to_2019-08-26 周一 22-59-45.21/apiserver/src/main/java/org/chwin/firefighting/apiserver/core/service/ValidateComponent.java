package org.chwin.firefighting.apiserver.core.service;

import org.chwin.firefighting.apiserver.core.util.ValidatePrm;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 参数校验
 *
 * @author wangjunma
 * @version $Id: ValidateComponent,v 0.1 2017/12/23 16:41 Exp $$
 */
@Component
public class ValidateComponent {
    /**
     * 参数校验
     * @param param 参数校验
     * @param method 请求方法(服务名称.方法名称) exp: parking.arrive
     * @return
     */
    public boolean validate(Map param,String method){
        return ValidatePrm.getInstance().verifyParam(param,method);
    }
}
