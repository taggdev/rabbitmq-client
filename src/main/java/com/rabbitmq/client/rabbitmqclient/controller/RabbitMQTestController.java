package com.rabbitmq.client.rabbitmqclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.rabbitmqclient.constant.Constant;
import com.rabbitmq.client.rabbitmqclient.service.RabbitSender;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class RabbitMQTestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    private final RabbitMQSender sender;

    private final RabbitSender sender;

    public RabbitMQTestController(RabbitSender sender) {
        this.sender = sender;
    }

    @RequestMapping(value = Constant.REQ_MAP_RB_MQ_TEST, method = RequestMethod.POST)
    @ResponseBody
    public String producer(@RequestBody HashMap<String, Object> hashMap) {

        final ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(hashMap);
        } catch (JsonProcessingException e) {
            logger.debug("Cannot convert to json!!");
        }

        String channel = (String) hashMap.get(Constant.APP_CHANNEL);
        if(StringUtils.isEmpty(channel)) {
            channel = "SBMTEST";
        }
        sender.send(channel, jsonInString);

        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }
}
