package org.chwin.firefighting.apiserver.core.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by liming on 2017/9/18.
 */
public class VtplUtil {
    private final static Logger logger = LoggerFactory.getLogger(VtplUtil.class);
    public static String parse(String tpt,Map params){
        Properties p = new Properties();
        p.put("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.setProperty(Velocity.ENCODING_DEFAULT, CONSTANTS.UTF8);
        p.setProperty(Velocity.INPUT_ENCODING, CONSTANTS.UTF8);
        p.setProperty(Velocity.OUTPUT_ENCODING, CONSTANTS.UTF8);
        Velocity.init(p);
        VelocityContext context = new VelocityContext();

        StringWriter w = new StringWriter();
        if(params==null) params=new HashMap();
        Set ks = params.keySet();
        for(Object key:ks)
            context.put((String)key, params.get(key));

        Velocity.mergeTemplate("vm/template/"+tpt, CONSTANTS.UTF8, context, w);
        StringBuffer buffer = w.getBuffer();
        logger.debug(buffer.toString());
        return buffer.toString();
    }

    public static String parseContent(String content,Map param){
        VelocityEngine ve = new VelocityEngine(); ve.init();
        VelocityContext context = new VelocityContext();
        param.forEach((k,v)->{
            context.put((String)k,v);
        });
        StringWriter writer = new StringWriter();
        ve.evaluate(context, writer, "", content);
        return writer.toString();
    }
}

