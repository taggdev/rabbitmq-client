package com.rabbitmq.client.rabbitmqclient.service;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitSender {

//    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate rabbitTemplate;

    private final RabbitAdmin rabbitAdmin;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    public RabbitSender(RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
    }

    public void send(final String channelKey, final String json) {
        final Queue queue = new Queue(channelKey);
        rabbitAdmin.declareQueue(queue);

        final DirectExchange directExchange = new DirectExchange(exchange);
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(channelKey));

        this.rabbitTemplate.convertAndSend(exchange, channelKey, json);
    }

}
