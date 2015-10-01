package com.demosoft.stlb.loadbalancer;

import com.demosoft.stlb.bean.Node;
import com.demosoft.stlb.bean.NodeConfigsConteiner;
import com.demosoft.stlb.bean.SessionConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class LoadBalancerHelper {

    public static final String JSESSIONID = "JSESSIONID";
    public static final String SET_COOKIE = "Set-cookie";
    public static final String COOKIE = "Cookie";


    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;

    private RestTemplate restTemplate = new RestTemplate();

    private List<Node> usedNodes = new ArrayList<>();


    public Object post(String url, Object request) {
        return restTemplate.<Object>postForEntity(url, request, Object.class);
    }

    public ResponseEntity<String> get(HttpServletRequest httpRequest, SessionConnection connection) throws ResourceAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.TEXT_HTML);
        putCorrectSessionIdToHeadrs(headers, connection);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(connection.getNode().getUrl() + httpRequest.getRequestURI(), HttpMethod.GET, entity, String.class);
        System.out.println(response.getHeaders().get("Set-cookie"));
        processJSessionIdAfterRequest(response,connection);
        return response;
    }

    public ResponseEntity<String> get(String url) throws ResourceAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }

    private void processJSessionIdAfterRequest(ResponseEntity<String> response, SessionConnection connection) {
        List<String> newHeaders = response.getHeaders().get(SET_COOKIE);
        boolean isJSessionFound = false;
        String jSessionId = null;
        if (newHeaders == null || newHeaders.size() == 0) {
            return;
        }
        for (String header : newHeaders) {
            if (!isJSessionFound) {
                String[] cookies = header.split(";");
                for (String cookie : cookies) {
                    if (cookie.contains(JSESSIONID)) {
                        jSessionId = cookie.substring(cookie.indexOf("=") + 1).trim();
                        isJSessionFound = true;
                    }
                }
            }
        }
        if (connection.getNodeJSessionId() == null) {
            connection.setNodeJSessionId(jSessionId);
        } else if (!connection.getNodeJSessionId().equalsIgnoreCase(jSessionId)) {
            connection.setNodeJSessionId(jSessionId);
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString("JSESSIONID=05B8388D40FB2A26BDE9A023282D22CC; Path=/; HttpOnly".split(";")));
    }

    private void putCorrectSessionIdToHeadrs(HttpHeaders headers, SessionConnection connection) {
        if (connection.getNodeJSessionId() == null) {
            return;
        }
        headers.set(COOKIE, JSESSIONID + "=" + connection.getNodeJSessionId());
    }

    public Node getAvailibleNode() {
        List<Node> nodes = nodeConfigsConteiner.getAvailbleNodes();
        if (nodes.size() == 0) {
            return null;
        }
        nodes.removeAll(usedNodes);
        if (nodes.size() == 0) {
            nodes = usedNodes;
            usedNodes = new ArrayList<>();
        }
        usedNodes.add(nodes.get(0));
        return nodes.get(0);
    }

}
