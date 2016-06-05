package com.demosoft.stlb.loadbalancer.controller;

import com.demosoft.stlb.loadbalancer.bean.Configs;
import com.demosoft.stlb.loadbalancer.bean.SessionConnection;
import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Controller
@Scope("session")
public class MainController {

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    @Autowired
    private SessionConnection sessionConnection;

    @Autowired
    Configs configs;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping( method = RequestMethod.GET)
    @ResponseBody
    private byte[] processGetRequest(HttpSession session, HttpServletRequest request) throws ResourceAccessException, InterruptedException {
        checkSessionConection(session);
        request.getServletContext().getServletRegistrations();
        log.debug("GET call for {} node for path {}",sessionConnection.getNode().getUrl(), request.getRequestURI());
        while (sessionConnection.isLocked()){
            Thread.sleep(100);
        }
        /*String response = loadBalancerHelper.get(request, sessionConnection).getBody();
        sessionConnection.updateActivity();
        return generateResponseHtml(request, response).getBytes(StandardCharsets.UTF_8);*/
        byte[] responeBody = loadBalancerHelper.getBytes(request, sessionConnection).getBody();
        sessionConnection.updateActivity();
        return  generateResponseHtml(request, responeBody);
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    @ResponseBody
    private String processPostRequest(HttpSession session, HttpServletRequest request, HttpServletResponse httpServletResponse) throws ResourceAccessException, InterruptedException {
        checkSessionConection(session);
        while (sessionConnection.isLocked()){
            Thread.sleep(100);
        }
        ResponseEntity<String> responseEntity = loadBalancerHelper.post(request, sessionConnection);
        String response = responseEntity.getBody();
        loadBalancerHelper. compileHttpPostHeader(request,httpServletResponse, responseEntity.getHeaders());
        httpServletResponse.setStatus(responseEntity.getStatusCode().value());
        sessionConnection.updateActivity();
        return response;
    }



    private void checkSessionConection(HttpSession session) {
        sessionConnection.setjSessionId(session.getId());
        if (sessionConnection.getNode() == null || !sessionConnection.getNode().isCanBeUsed()) {
            sessionConnection.setNode(loadBalancerHelper.getAvailibleNode());
            log.debug("Node {} was given to {}", sessionConnection.getNode().getUrl(), sessionConnection.getjSessionId());
            log.debug("Node {} has  {} connections {}", sessionConnection.getNode().getUrl(), sessionConnection.getNode().getConnectionCount(), sessionConnection.getNode().getConnections());
        }
    }

    private byte[] generateResponseHtml(HttpServletRequest request, byte[] response) {
        if (configs.isDebugMode()) {
            byte[] debugHeader =
                    ("<link rel=\"stylesheet\" type=\"text/css\" href=\"/stlb/bower_components/normalize-css/normalize.css\"/>\n" +
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
                    "</div>").getBytes();

            byte[] result = new byte[debugHeader.length + response.length];
            System.arraycopy(debugHeader,0,result,0,debugHeader.length);
            System.arraycopy(response,0,result,debugHeader.length,response.length);
            return result;
        }

        return response;
    }


}
