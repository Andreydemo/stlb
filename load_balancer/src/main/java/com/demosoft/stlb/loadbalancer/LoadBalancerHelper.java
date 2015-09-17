package com.demosoft.stlb.loadbalancer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class LoadBalancerHelper {

    private RestTemplate restTemplate = new RestTemplate();


    public Object post(String url, Object request){
        return restTemplate.<Object>postForEntity(url,request,Object.class);
    }

    public String get(String url){
        ResponseEntity<String> response = restTemplate.<String>getForEntity(url,String.class);
       return response.getBody();
    }

}
