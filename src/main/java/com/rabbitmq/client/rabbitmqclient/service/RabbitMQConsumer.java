package com.rabbitmq.client.rabbitmqclient.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class RabbitMQConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = {"${app.rabbitmq.queue01}","${app.rabbitmq.queue02}","${app.rabbitmq.queue03}"})
    public void receivedMessage(String json) {
        logger.debug("Received Message From RabbitMQ: {}", json);

        final JSONObject jObject = new JSONObject(json);
        Object obj, subObj;
        JSONObject subJsonObj;
        for (String key : jObject.keySet()) {
            obj = jObject.get(key);
            logger.debug("{} : {} [{}]", key, obj, obj.getClass().getName());
            if(obj instanceof JSONObject) {
                subJsonObj = (JSONObject) obj;
                for(String subKeu:subJsonObj.keySet()) {
                    subObj = subJsonObj.get(subKeu);
                    logger.debug("==> {} : {} [{}]", subKeu, subObj, subObj.getClass().getName());
                }
            }
        }
        logger.debug("==End==%n");
    }

}