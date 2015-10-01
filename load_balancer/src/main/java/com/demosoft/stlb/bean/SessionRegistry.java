package com.demosoft.stlb.bean;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrii_Korkoshko on 01.10.2015.
 */
@Component
public class SessionRegistry {

    private Map<String,SessionConnection> sessions = new HashMap<>(10);

    public SessionConnection put(String key, SessionConnection value) {
        return sessions.put(key, value);
    }

    public SessionConnection get(Object key) {
        return sessions.get(key);
    }

    public SessionConnection remove(Object key) {
        return sessions.remove(key);
    }
}
