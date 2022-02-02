package com.Lukas.demo.controller;

import com.Lukas.demo.model.Greeting;
import com.Lukas.demo.model.GreetingResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController
{
    @MessageMapping("/greeting")
    @SendTo("/topic/greeting")
    public GreetingResponse getUser(Greeting greet) {

        return new GreetingResponse("Hi " + greet.getName());
    }
}
