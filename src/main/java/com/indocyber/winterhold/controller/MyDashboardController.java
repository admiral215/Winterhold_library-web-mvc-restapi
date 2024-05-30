package com.indocyber.winterhold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class MyDashboardController {
    @GetMapping("")
    public ModelAndView dashboard(){
        var mv = new ModelAndView("dashboard");
        return mv;
    }
}
