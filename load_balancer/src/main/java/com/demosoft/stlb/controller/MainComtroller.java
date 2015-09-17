package com.demosoft.stlb.controller;

import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@RestController
public class MainComtroller {

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    @RequestMapping("/*")
    private String processRequest(HttpSession session, HttpServletRequest request){
        System.out.println("try  connect to http://localhost:8081 session id " +  session.getId());
        String response = loadBalancerHelper.get("http://localhost:8081");
        return"<b>" +  request.getRequestURL().toString() + "?" + request.getQueryString() + "</b>" +
                "<br>" +
                "Response from node: " + response;
    }
}
