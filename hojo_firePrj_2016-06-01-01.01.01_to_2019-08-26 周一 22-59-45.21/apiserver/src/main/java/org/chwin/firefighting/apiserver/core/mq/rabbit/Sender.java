package org.chwin.firefighting.apiserver.core.mq.rabbit;

import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by liming on 2017/9/18.
 */
@Component
public class Sender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getQueue() {
        return SpringUtil.getProp("spring.rabbitmq.queue");
    }

//    @Autowired
//    private AmqpTemplate rabbitTemplate;

    public void sendMsg(String msg){
        logger.debug(msg);
 //       this.rabbitTemplate.convertAndSend(getQueue(),msg);
    }

}
