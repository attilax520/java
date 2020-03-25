package org.chwin.firefighting.apiserver.core.script;


import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.exception.ExceptionEncoder;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.chwin.firefighting.apiserver.core.util.VtplUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by liming on 2017/9/15.
 */
public class JScript {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static JScript instance = null;
    private ScriptEngine engine;
    private Map<String, CompiledScript> cmpMap = Collections.synchronizedMap(new HashMap());
    private Map<String, Map> importMap = Collections.synchronizedMap(new HashMap());

    public static synchronized JScript getInstance() {
        if (instance == null)
            instance = new JScript();
        return instance;
    }

    public JScript() {
        ScriptEngineManager sem = new ScriptEngineManager();
        engine = sem.getEngineByName("Nashorn");
    }

    public synchronized String getRuleContent(String fn) {
        return new RuleProxyClient().readJS(fn);
    }

    public void pushJS(String fn,String sub,String script){
        if(SpringUtil.isDebug()) return;
        Map importData=importMap.get(fn);
        if(importData==null)importData=new HashMap();
        importData.put(sub,script);
        importMap.put(fn,importData);
        cmpMap.remove(fn);
    }

    public String getSelfCall(String fn,String script_content){
        String[] fns=StringUtils.split(fn,".");
        StringBuffer fcall=new StringBuffer(fns[fns.length-1]);

        String content=script_content.replaceAll(" ","");
        String fun="function"+fcall.toString()+"(";
        int index=StringUtils.lastIndexOf(content,fun);
        if(index<0) throw new BusinessException("MAIN_FUNCTION_NOT_FOUNDED",fn);

        index+=fun.length()-1;
        int end=StringUtils.indexOf(content,")",index)+1;
        String params=StringUtils.substring(content,index,end);

        fcall.append(params).append(";");
        if(StringUtils.indexOf(script_content,fcall.toString())>0)
            return "";
        return fcall.toString();
    }

    public synchronized void putCmp(String fn,CompiledScript cmpScript) {
        cmpMap.put(fn, cmpScript);
    }

    public synchronized CompiledScript getCmp(String fn) {
        return cmpMap.get(fn);
    }

    public Object execute(String fn, Map params) {
        try {
            CompiledScript cmpScript = getCmp(fn);
            if (cmpScript == null ) {
                StringBuffer script = new StringBuffer();
                String script_content=getRuleContent(fn);
                Map parm=new HashMap(){{
                    put("fn",fn);
                    put("imps",importMap.get(fn));
                    put("script",script_content);
                    put("selfcall",getSelfCall(fn,script_content));
                }};
                script.append( VtplUtil.parseContent(getRuleContent("pub.include"),parm) );
                logger.debug("SCRIPT:" + script);
                cmpScript = ((Compilable) engine).compile(script.toString());

                if (!SpringUtil.isDebug()) putCmp(fn,cmpScript);
            }
            ScriptContext context = new SimpleScriptContext();
            Bindings bindings = new SimpleBindings();//engine.createBindings();

            bindings.putAll(params);
            context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            Object obj = cmpScript.eval(context);
            bindings.clear();
            logger.info("result:" + obj);
            return obj;
        } catch (Exception e) {
            logger.error("script exception");
            logger.error("exception fun:"+fn);
            logger.error("exception param:"+ JSONParser.obj2Json(params.get("prm")));
            logger.error("exception:", e);
            String msg = e.getMessage();
            SQLException se = ExceptionEncoder.findTypeException(e, SQLException.class);
            if (se != null) {
                msg = ExceptionEncoder.msg(se);
                throw new BusinessException("SQL异常：" + msg);
            } else {
                BusinessException be = ExceptionEncoder.findTypeException(e, BusinessException.class);
                if (be != null)
                    throw be;
                else {
                    int start = StringUtils.indexOf(msg, "【");
                    int end = StringUtils.lastIndexOf(msg, "】");
                    if (start > -1 && end > 0) {
                        throw new BusinessException(StringUtils.substring(msg, start + 1, end));
                    } else {
                        throw new BusinessException(msg);
                    }
                }
            }
        }
    }
}
