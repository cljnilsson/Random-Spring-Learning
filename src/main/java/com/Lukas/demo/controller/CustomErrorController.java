package com.Lukas.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController
{
    @GetMapping("/error")
    public  ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.valueOf(status.toString());

        Map<String, Object> data = new HashMap<>();

        data.put("code", statusCode);

        return new ModelAndView("error", data);
    }
}
