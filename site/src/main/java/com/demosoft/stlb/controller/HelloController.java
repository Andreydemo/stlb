package com.demosoft.stlb.controller;

import com.demosoft.stlb.annotation.PerformanceMonitor;
import com.demosoft.stlb.bean.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HelloController {


    @Autowired
    SessionBean sessionBean;

    @PerformanceMonitor
    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        model.addAttribute("sessionId", session.getId());
        System.out.println("sessionBean" + sessionBean);
        return "index";
    }

    @PerformanceMonitor
    @RequestMapping("/perf")
    public String perfIndex(HttpSession session, Model model) {
        System.out.println("perf");
        model.addAttribute("sessionId", session.getId());
        return "index";
    }

    @PerformanceMonitor
    @RequestMapping("/page{number}")
    public String page(@PathVariable String number, Model model) {
        model.addAttribute("number", number);
        return "page";
    }

}