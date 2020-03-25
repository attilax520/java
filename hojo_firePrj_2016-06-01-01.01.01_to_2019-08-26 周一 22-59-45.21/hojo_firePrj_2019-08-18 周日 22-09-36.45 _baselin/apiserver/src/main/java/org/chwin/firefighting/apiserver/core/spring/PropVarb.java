package org.chwin.firefighting.apiserver.core.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by liming on 2017/9/18.
 */

@Component("propvarb")
public class PropVarb {

    @Autowired
    private Environment env;

    public int id=1;
    public String getProp(String s) {
        return env.getProperty(s);
    }
}
