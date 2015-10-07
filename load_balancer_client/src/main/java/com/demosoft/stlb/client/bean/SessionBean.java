package com.demosoft.stlb.client.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean {


    private long activeTime;
    private Date startCounting;
    private Date endCounting;

    @Autowired
    private HttpSession httpSession;

    @PostConstruct
    public void init() {
        System.out.println("session id: " + httpSession.getId());
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void addActiveTime(Long time) {
        if (startCounting == null) {
            startCounting = new Date();
        }
        activeTime += time;

    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
}
