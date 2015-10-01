package com.demosoft.stlb.bean.listener;

import com.demosoft.stlb.bean.SessionConnection;
import com.demosoft.stlb.bean.SessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class SessionCreationListener implements HttpSessionListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.info("Session create with id" + httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        String jSessionId = httpSessionEvent.getSession().getId();
        log.info("Session destroyed with id" + jSessionId);
        SessionConnection conncetion = sessionRegistry.get(jSessionId);
        if (conncetion != null) {
            conncetion.setExpired(true);
            sessionRegistry.remove(jSessionId);
        } else {
            log.info("Connection with Session if {} is not exist in SessionRegistry ", jSessionId);
        }
    }


}
