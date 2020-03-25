package org.chwin.firefighting.apiserver.core.service;

import org.chwin.firefighting.apiserver.core.script.JScript;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liming on 2017/10/10.
 */
@Component
public class ScriptComponent {
    public Object excute(String fun,Map param){
        Object result=JScript.getInstance().execute(fun,param);
        return result;
    }
}
