package com.rabbitmq.client.rabbitmqclient.controller;

import com.rabbitmq.client.rabbitmqclient.constant.Constant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Controller
@ConditionalOnWebApplication
public class SwaggerController {

    @RequestMapping("/")
    public ModelAndView root() {
        return new ModelAndView(Constant.REDIRECT_SWAGGER_UI);
    }
}
