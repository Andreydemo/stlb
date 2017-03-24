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
    public static final String NODE_ANALISE_INTERVAL = "nodeAnaliseInterval";
    public static final String DEEP_STATISTIC = "deepStatistic";

    Map<String, Object> configMap = new HashMap<>();

    public Configs() {
        configMap.put(DEBUG_MODE, Boolean.FALSE);
        configMap.put(DEEP_STATISTIC, Boolean.FALSE);
        configMap.put(NODE_ANALISE_INTERVAL, new Integer(10000));
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

    public Integer getNodeAnaliseInterval() {
        return (Integer) configMap.get(NODE_ANALISE_INTERVAL);
    }

    public Boolean isDeepStatistic() {
        return (Boolean) configMap.get(DEEP_STATISTIC);
    }

    public void setDeepStatistic(Boolean debugMode) {
        configMap.put(DEEP_STATISTIC, debugMode);
    }

}
