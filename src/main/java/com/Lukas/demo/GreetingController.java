package com.Lukas.demo;

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
