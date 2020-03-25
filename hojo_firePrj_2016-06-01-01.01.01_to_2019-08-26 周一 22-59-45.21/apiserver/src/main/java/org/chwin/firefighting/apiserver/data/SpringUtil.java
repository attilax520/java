package org.chwin.firefighting.apiserver.data;

import com.google.common.collect.Maps;
import ognl.Ognl;
import ognl.OgnlException;

import java.util.Map;

public class SpringUtil {

    public static String getCfgFile()
    {
        try{
            String profiles_active=  get_profiles_active();
            return "/application-"+profiles_active+".yml";
        }catch( OgnlException e)
        {
            return "/application.yml";
        }
    }


    public static String get_profiles_active() throws OgnlException {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        Object mObject=yaml.load(mybatisdemo.class.getResourceAsStream("/application.yml"));
        Object expression = Ognl.parseExpression("spring.profiles.active");
        Object url = Ognl.getValue(expression, mObject);
        return url.toString();
    }
@Deprecated
    public static Map getDbcfg() throws  Exception
    {
        String cfgTag=get_profiles_active();
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        Object mObject=yaml.load(mybatisdemo.class.getResourceAsStream("/application-"+cfgTag+".yml"));
        Object expression = Ognl.parseExpression("spring.datasource.url");
        Object url = Ognl.getValue(expression, mObject);
        Object usr = Ognl.getValue(Ognl.parseExpression("spring.datasource.username"), mObject);
        Object pwd = Ognl.getValue(Ognl.parseExpression("spring.datasource.password"), mObject);
        if(pwd==null)pwd="";
        Map m= Maps.newLinkedHashMap();
        m.put("url",url);
        m.put("user",usr.toString());
        m.put("pwd",pwd.toString());
        return m;
    }

    public static String getCfgProperty(String k) throws Exception {
        try {
            org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
            Object mObject=yaml.load(mybatisdemo.class.getResourceAsStream(getCfgFile()));

            String usr =   Ognl.getValue(Ognl.parseExpression(k), mObject).toString();
            return usr;
        }catch (Exception e){
            System.out.println("k:"+k);
            throw e;
        }


    }
}
