package org.chwin.firefighting.apiserver.net;

import javax.servlet.http.Cookie;

public class RequestImpAdv  extends  requestImp {
    /**
     attilax    2016年4月19日  下午5:10:44
     * @param ck
     */
    public void addCookie(Cookie ck) {
        cookies_li.add(ck);

    }


}
