package com.demosoft.stlb.controller;

import com.demosoft.stlb.bean.Configs;
import com.demosoft.stlb.bean.SessionConnection;
import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
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
    private SessionConnection sessionConnection;

    @Autowired
    Configs configs;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping( method = RequestMethod.GET)
    @ResponseBody
    private String processGetRequest(HttpSession session, HttpServletRequest request) throws ResourceAccessException {
        checkSessionConection(session);
        request.getServletContext().getServletRegistrations();
        log.info("GET call for {} node for path {}",sessionConnection.getNode().getUrl(), request.getRequestURI());
        String response = loadBalancerHelper.get(request, sessionConnection).getBody();
        sessionConnection.updateActivity();
        return generateResponseHtml(request, response);
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    @ResponseBody
    private String processPostRequest(HttpSession session, HttpServletRequest request, HttpServletResponse httpServletResponse) throws ResourceAccessException {
        checkSessionConection(session);
        ResponseEntity<String> responseEntity = loadBalancerHelper.post(request, sessionConnection);
        String response = responseEntity.getBody();
        loadBalancerHelper. compileHttpPostHeader(request,httpServletResponse, responseEntity.getHeaders());
        httpServletResponse.setStatus(responseEntity.getStatusCode().value());
        sessionConnection.updateActivity();
        System.out.println(httpServletResponse.getHeader("Set-Cookie"));
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
            return "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stlb/bower_components/normalize-css/normalize.css\"/>\n" +
                    "        <link rel=\"stylesheet\" type=\"text/css\" href=\"/stlb/bower_components/semantic/dist/semantic.css\"/>" +
                    "<div class=\"ui message\">\n" +
                    "<div class=\"header\">" +
                    "Debug Info" +
                    "</div>" +
                    " <ul class=\"list\">" +
                    "<li>" + request.getRequestURL() + "?" + request.getQueryString() + "</li>" +
                    "<li>Node: " + sessionConnection.getNode().getUrl() + "</li>" +
                    "<li>Full request url" + sessionConnection.getNode().getUrl() + request.getRequestURI() + "</li>" +
                    "</ul>\n" +
                    "</div>" + response;
        }
        return response;
    }


}
