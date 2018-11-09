package com.elecredit.op.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Doaim on 2017/8/12.
 */
@Component
public class MessageQueueService {
    private final Logger logger = LogManager.getLogger(MessageQueueService.class);
    @Autowired
    @Qualifier("amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Value("${rabbit.op.exchange}")
    private String TOPIC_EXCHANGE;

    /**
     * 发送主题消息
     * @param routingKey
     * @param msg
     * @throws AmqpException
     */
    public void sendTopic(String routingKey,Object msg) {
        try{
            amqpTemplate.convertAndSend(TOPIC_EXCHANGE,routingKey, msg);
        }catch (AmqpException e){
            logger.error("消息发送异常，Exchange:{},RoutingKey:{},消息:{}",TOPIC_EXCHANGE,routingKey, JSONObject.toJSONString(msg),e);
        }
    }
}
