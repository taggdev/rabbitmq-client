# rabbitmq-client
rabbitmq-client

application.yml
```text
app:
  rabbitmq:
    queue: QUEUE_TEST

spring:
  rabbitmq:
    addresses: <ip1>:5672,<ip2>:5672
    username: <username>
    password: <password>

server:
  port: 18082

```

RabbitListener
```java
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
```

RabbitmqClientApplication
```java
package com.rabbitmq.client.rabbitmqclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqClientApplication.class, args);
	}

}

```