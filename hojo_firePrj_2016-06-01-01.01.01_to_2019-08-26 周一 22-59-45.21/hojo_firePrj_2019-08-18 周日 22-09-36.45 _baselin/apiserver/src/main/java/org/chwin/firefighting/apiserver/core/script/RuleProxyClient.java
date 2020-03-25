package org.chwin.firefighting.apiserver.core.script;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.FileUtil;

/**
 * 总体说明
 * <p>具体说明</p>
 *
 * @author cailm
 * @version $Id: RuleProxyClient,v 0.1 2017/12/20 18:47 Exp $$
 */
public class RuleProxyClient {
    public String readJS(String fn){
        if (SpringUtil.isDebug() || true) {
            String local = StringUtils.replace(fn, ".", "/");
            String path = SpringUtil.getProp("spring.application.rule_path") + "/scripts/" + local + ".js";
            return FileUtil.readTxt(path);
        } else {
            return "";
        }
    }

    public String readTemplate(String template){
        String local = StringUtils.replace(template, ".", "/");
        String path = SpringUtil.getProp("spring.application.rule_path") + "/config/" + local + ".vm";
        return FileUtil.readTxt(path);
    }
}
