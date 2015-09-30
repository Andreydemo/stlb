package com.demosoft.stlb.controller;

import com.demosoft.stlb.bean.SessionConnection;
import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Controller
@Scope("request")
public class MainComtroller {

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    @Autowired
    SessionConnection sessionConnection;

    @RequestMapping(value = "/stlb", method = RequestMethod.GET)
    private String balancerAdmin(Model m) {
        System.out.println("admin Congigs11");
        return "stlbAdmin";
    }

  /*  @RequestMapping(value = "*//**", method = RequestMethod.GET)
    @ResponseBody
    private String processRequest(HttpSession session, HttpServletRequest request) {
        System.out.println("try  connect to http://localhost:8081 session id " + session.getId());
        System.out.println(sessionConnection);
        String response = loadBalancerHelper.get("http://localhost:8081");
        return "<b>" + request.getRequestURL().toString() + "?" + request.getQueryString() + "</b>" +
                "<br>" +
                "Response from node: " + response;
    }*/
}
