package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by liming on 2017/9/18.
 */
public class DataPreset {
    private static DataPreset instance=null;

    public static synchronized DataPreset getInstance(){
        if(instance==null)
            instance = new DataPreset();

        return instance;
    }

    public Map insertPre(Map entity){
        try{
            Object create_time=entity.get(CONSTANTS.C_CRT_TIME);
            if(create_time==null){
                entity.put(CONSTANTS.C_CRT_TIME,new Date());
            }
            if(create_time instanceof Integer){
                entity.put(CONSTANTS.C_CRT_TIME,new Date().getTime());
            }

            String ip=(String)ThreadVariable.getInstance().get(CONSTANTS.REQ_IP);
            entity.put(CONSTANTS.C_CRT_IP,ip);

            Map uinfo=(Map)ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
            if(uinfo!=null){
                String id=(String)uinfo.get("ID");
                entity.put(CONSTANTS.C_CRT_ID,id);

                if(!"3".equals(uinfo.get("IS_SUPER_ADMIN"))){//华嬴管理员，跳过这个操作
                    String TENANT_ID=(String)uinfo.get(CONSTANTS.TENANT_ID);
                    entity.put(CONSTANTS.TENANT_ID,TENANT_ID);
                }
            }

            String idx=(String)entity.get(CONSTANTS.C_PK);
            if(StringUtils.isBlank(idx))
                entity.put(CONSTANTS.C_PK,crtKey());



        }catch(Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    public Map updatePre(Map entity){
        try{
            Object update_time=entity.get(CONSTANTS.C_UPT_TIME);
            if(update_time==null)
                entity.put(CONSTANTS.C_UPT_TIME,new Date());

            String ip=(String)ThreadVariable.getInstance().get(CONSTANTS.REQ_IP);
            entity.put(CONSTANTS.C_UPT_IP,ip);

            Map uinfo=(Map)ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
            if(uinfo!=null){
                String id=(String)uinfo.get("ID");
                String userId = entity.get("ID")+"";
                String TENANT_ID = "";
                if(StringUtils.isNotBlank(userId)&&!id.equals(userId) && entity.get(CONSTANTS.TENANT_ID) != null){
                    TENANT_ID=(String)entity.get(CONSTANTS.TENANT_ID);
                }else{
                    entity.put(CONSTANTS.C_UPT_ID,id);
                    TENANT_ID=(String)uinfo.get(CONSTANTS.TENANT_ID);
                }
                //更新数据时，如果是总管理员，不修改租户信息
                if(uinfo!=null){
                    if(!uinfo.get("IS_SUPER_ADMIN").equals("3")){
                        entity.put(CONSTANTS.TENANT_ID,TENANT_ID);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    public String crtKey(){
        return UUID.randomUUID().toString();
    }
}

