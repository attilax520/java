package org.chwin.firefighting.apiserver.fire.listener;

import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.fire.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketPushHandler extends BaseSupport implements WebSocketHandler{
    private static Map<String,Boolean> timers = new ConcurrentHashMap<>();//每个连接的线程控制器

    @Autowired
    private WebSocketService wsService;

    // 用户进入系统监听
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id = session.getId();
        System.out.println(id + "成功进入了系统。。。");
        wsService.initRedisData(id);
        timers.put(id,true);//线程开启
        System.out.println("目前线程数：" + timers.size() + "个！");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String id = session.getId();
        System.out.println(id + "处理发送消息!");
        Map loginInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        new Thread(new Runnable(){
            @Override
            public void run() {
                boolean isFirst = true;
                ThreadVariable.getInstance().put(CONSTANTS.REQ_UKEY,loginInfo);
                while(timers.containsKey(id)&&timers.get(id)){
                    if (isFirst){
                        Map<String,Object> map = wsService.getAll(id);
                        if ( map != null) {
                            TextMessage tm = new TextMessage(output(map));
                            try{
                                if(session.isOpen()) {
                                    if(tm.getPayloadLength()>73) {
                                        session.sendMessage(tm);
                                        isFirst = false;
                                    }
                                }
                            }catch (Exception e){
                                System.out.println("发送消息异常!");
                                try {
                                    sessionClear(session);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    } else {
                        if(wsService.updateRedis(id)){
                            isFirst = true;
                        }
                    }
                }
            }
        }).start();
    }

    // 后台错误信息处理方法
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("进入错误处理");
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(new RspBody(110,"MSG").output()));
            sessionClear(session);
        }
    }

    // 用户退出后的处理，不如退出之后，要将用户信息从websocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessionClear(session);
        System.out.println("安全退出了系统,并移除对应定时器,清除对应缓存!");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

   /**
   *@author aniu
   *@Description 关闭session  并且 删除对应session 的 定时器
   *@Date 9:45 2018/7/11
   *@Param []
   *@return void
   **/
   private  void sessionClear(WebSocketSession session) throws Exception{
       System.out.println("切换中。。。。。。");
       timers.put(session.getId(),false);//停掉对应的线程
       timers.remove(session.getId());
       if (session.isOpen()) {
           session.close();
       }
   }


    @Override
    protected String output(Object detail) {
        RspBody result = new RspBody(1, "OK", detail);
        return result.toJson();
    }
}
