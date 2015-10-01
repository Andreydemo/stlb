package com.demosoft.stlb.controller;

import com.demosoft.stlb.bean.Configs;
import com.demosoft.stlb.bean.SessionConnection;
import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Controller
@Scope("request")
public class MainComtroller {

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    @Autowired
    private SessionConnection sessionConnection;

    @Autowired
    Configs configs;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    @ResponseBody
    private String processGetRequest(HttpSession session, HttpServletRequest request) throws ResourceAccessException {
        checkSessionConection(session);
        String response = loadBalancerHelper.get(request, sessionConnection).getBody();
        return generateResponseHtml(request, response);
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    @ResponseBody
    private String processPostRequest(HttpSession session, HttpServletRequest request) throws ResourceAccessException {
        checkSessionConection(session);
        String response = loadBalancerHelper.post(request, sessionConnection).getBody();
        return generateResponseHtml(request, response);
    }

    private void checkSessionConection(HttpSession session) {
        sessionConnection.setjSessionId(session.getId());
        if (sessionConnection.getNode() == null || !sessionConnection.getNode().isAvailable()) {
            sessionConnection.setNode(loadBalancerHelper.getAvailibleNode());
            log.info("Node {} was given to {}", sessionConnection.getNode().getUrl(), sessionConnection.getjSessionId());
            log.info("Node {} has  {} connections {}", sessionConnection.getNode().getUrl(), sessionConnection.getNode().getConnectionCount(), sessionConnection.getNode().getConnections());
        }
    }

    private String generateResponseHtml(HttpServletRequest request, String response) {
        if (configs.isDebugMode()) {
            return "<b>" + request.getRequestURL() + "?" + request.getQueryString() + "</b>" +
                    "<br>" +
                    "Node: " + sessionConnection.getNode().getUrl() +
                    "<br>" +
                    "Response from node: " + response;
        }
        return response;
    }


}
