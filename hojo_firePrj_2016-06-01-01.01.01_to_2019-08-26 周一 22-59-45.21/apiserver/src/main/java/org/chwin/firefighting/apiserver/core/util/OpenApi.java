package org.chwin.firefighting.apiserver.core.util;

/*import com.hy.apiclient.client.ApiClient;
import com.hy.apiclient.client.ClientInfo;*/

import java.util.Map;

public class OpenApi {

    private static String OPENPLATFORM_IP = "ip";
    private static String OPENPLATFORM_PORT = "port";
    private static String OPENPLATFORM_APPID = "app_id";
    private static String OPENPLATFORM_SECRET = "app_secret";
    private static String OPENPLATFORM_TENANT_ID = "tenant_id";

    /*private static ApiClient apiClient = null;

    public static synchronized ApiClient getInstance(Map<String,Object> param) {
        if (apiClient == null) {
            apiClient = new ApiClient();
            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setOpenPlatFormIp((String) param.get(OPENPLATFORM_IP));
            clientInfo.setOpenPlatFormPort((Integer) param.get(OPENPLATFORM_PORT));
            clientInfo.setAppId((String) param.get(OPENPLATFORM_APPID));
            clientInfo.setSecret((String) param.get(OPENPLATFORM_SECRET));
            clientInfo.setTenantId((String) param.get(OPENPLATFORM_TENANT_ID));
            apiClient.setClientInfo(clientInfo);
        }
        return apiClient;
    }*/
}
