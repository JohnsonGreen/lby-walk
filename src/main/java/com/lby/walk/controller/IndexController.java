package com.lby.walk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @GetMapping(value = {"/", "/index",""})
    public String index(ModelAndView modelAndView) {
        return "/index";
    }
}
