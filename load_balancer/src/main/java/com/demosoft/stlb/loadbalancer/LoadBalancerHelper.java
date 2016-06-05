package com.demosoft.stlb.loadbalancer;

import com.demosoft.stlb.loadbalancer.bean.Node;
import com.demosoft.stlb.loadbalancer.bean.NodeConfigsConteiner;
import com.demosoft.stlb.loadbalancer.bean.SessionConnection;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.LOCATION;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class LoadBalancerHelper {

    public static final String JSESSIONID = "JSESSIONID";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";

    private final Logger log = LoggerFactory.getLogger(this.getClass());


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
        processJSessionIdAfterRequest(response, connection);
        return response;
    }

    public ResponseEntity<byte[]> getBytes(HttpServletRequest httpRequest, SessionConnection connection) throws ResourceAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.TEXT_HTML);
        putCorrectSessionIdToHeadrs(headers, connection);
        HttpEntity<byte[]> entity = new HttpEntity<>("parameters".getBytes(), headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(connection.getNode().getUrl() + httpRequest.getRequestURI(), HttpMethod.GET, entity, byte[].class);
        processJSessionIdAfterRequest(response, connection);
        return response;
    }

    public ResponseEntity<String> post(HttpServletRequest httpRequest, SessionConnection connection) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        //headers.setContentType(MediaType.TEXT_HTML);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        preHeadersCompilation(httpRequest,headers);
        putCorrectSessionIdToHeadrs(headers, connection);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.setAll(compilePostParamMap(httpRequest.getParameterMap()));
        //TODO checkboxes ignored
        body.add("field", "value");

        HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(connection.getNode().getUrl() + httpRequest.getRequestURI(), HttpMethod.POST, entity, String.class);
        processJSessionIdAfterRequest(response, connection);
        log.info(response.getHeaders().toString());
        log.info(response.getBody());
        return response;
    }

    private void preHeadersCompilation(HttpServletRequest httpReques, HttpHeaders httpHeaders) {
        Enumeration<String> headerNames = httpReques.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues =  httpReques.getHeaders(headerName);
            boolean firtsValue = true;
            while (headerValues.hasMoreElements()) {
                String headerValue = headerValues.nextElement();
                if (firtsValue) {
                    httpHeaders.set(headerName, headerValue);
                } else {
                    httpHeaders.add(headerName, headerValue);
                }
            }
        }
    }

    private Map<String, String> compilePostParamMap(Map<String, String[]> reqestParamMap) {
        Map<String, String> compiledReqestParamMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : reqestParamMap.entrySet()) {
            compiledReqestParamMap.put(entry.getKey(), entry.getValue()[0]);
        }
        return compiledReqestParamMap;
    }

    public ResponseEntity<String> get(String url) throws ResourceAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }

    private void processJSessionIdAfterRequest(ResponseEntity response, SessionConnection connection) {
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

    public List<Node> getNodesInCriticalState(){
        return nodeConfigsConteiner.getAvailbleNodes().stream().filter(node -> node.isInCriticalState()).collect(Collectors.toList());
    }
    public List<Node> getNodeInNormalState(){
        return nodeConfigsConteiner.getAvailbleNodes().stream().filter(node -> !node.isInCriticalState()).collect(Collectors.toList());
    }

    public void compileHttpPostHeader(HttpServletRequest request, HttpServletResponse httpServletResponse, HttpHeaders httpHeaders) {
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {

            switch (entry.getKey()) {
                case COOKIE:
                    //TODO cookies will be ignored
                    break;
                case SET_COOKIE:
                    break;
                case LOCATION:
                    for (String value : entry.getValue()) {
                        httpServletResponse.addHeader(entry.getKey(), fixLocationHeader(value, request));
                    }
                    break;
                default:
                    for (String value : entry.getValue()) {
                        httpServletResponse.addHeader(entry.getKey(), value);
                    }
            }

        }

    }

    private String fixLocationHeader(String value, HttpServletRequest request) {
        try {
            URI location = new URI(value);
            URI requestUrl = new URI(request.getRequestURL().toString());
            String result = value.replace(location.getHost(), requestUrl.getHost());
            if (location.getPort() != -1) {
                //TODO port problems
                result = result.replace(String.valueOf(location.getPort()), String.valueOf(requestUrl.getPort()));
            }
            return result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String originalUrl = value;
        String host = originalUrl.substring(originalUrl.indexOf("//") + 2);
        host = host.substring(0, host.indexOf("/"));
        originalUrl = originalUrl.replace(host, "localhost");
        return "";
    }

    public void reassignSessionForNode(SessionConnection sessionConnection, Node node){
        if(node == null) {
            return;
        }
        sessionConnection.setLocked(true);
        try {
            String sessionDataBody = getSessionData(sessionConnection);
            Node oldNode = sessionConnection.getNode();
            oldNode.removeSessionConnection(sessionConnection);
            sessionConnection.setNode(node);
            node.addConnection(sessionConnection);
            getNewSessionConnectionForNode(sessionConnection, node);
            setSessionData(sessionConnection, sessionDataBody);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            sessionConnection.setLocked(false);
        }
    }

    public void getNewSessionConnectionForNode(SessionConnection sessionConnection,Node node){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(node.getUrl() , HttpMethod.GET, entity, String.class);
        processJSessionIdAfterRequest(response, sessionConnection);
    }

    public String getSessionData(SessionConnection sessionConnection){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML,MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.TEXT_HTML);
        putCorrectSessionIdToHeadrs(headers,sessionConnection);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(sessionConnection.getNode().getUrl()+"/admin/stlb/getSessionBean" , HttpMethod.GET, entity, String.class);
        return  response.getBody();
    }

    public void setSessionData(SessionConnection sessionConnection,String body){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        putCorrectSessionIdToHeadrs(headers,sessionConnection);
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(sessionConnection.getNode().getUrl()+"/admin/stlb/setSessionBean" , HttpMethod.POST, entity, String.class);
        sessionConnection.setDataBroked(Boolean.parseBoolean(response.getBody()));
    }




}
