package com.demosoft.stlb.loadbalancer.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Configuration
@ImportResource({"appConfigs.xml"})
public class Configs {


    public static final String DEBUG_MODE = "debugMode";
    public static final String NODE_ANAKLISE_INTERVAL = "nodeAnaliseInterval";

    Map<String, Object> configMap = new HashMap<>();

    public Configs() {
        configMap.put(DEBUG_MODE, Boolean.FALSE);
        configMap.put(NODE_ANAKLISE_INTERVAL, new Integer(10000));
    }

    public Boolean isDebugMode() {
        return (Boolean) configMap.get(DEBUG_MODE);
    }

    public void setDebugMode(Boolean debugMode) {
        configMap.put(DEBUG_MODE, debugMode);
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public Integer getNodeAnaliseInterval(){
        return (Integer) configMap.get(NODE_ANAKLISE_INTERVAL);
    }

}
