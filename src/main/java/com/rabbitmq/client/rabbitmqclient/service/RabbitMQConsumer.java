package com.rabbitmq.client.rabbitmqclient.service;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receivedMessage(String json) {
        System.out.printf("Received Message From RabbitMQ: %s%n%n", json);

        final JSONObject jObject = new JSONObject(json);
        for (String key : jObject.keySet()) {
            System.out.printf("%s : %s%n", key, jObject.getString(key));
        }
    }

}