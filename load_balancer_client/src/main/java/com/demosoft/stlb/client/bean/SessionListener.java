package com.demosoft.stlb.client.bean;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Andrii Korkoshko on 07.02.2016.
 */
@Component
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        SessionBean toRemove = null;
        for (SessionBean sessionBean : SessionBean.sessionBeanRegister){
            if(sessionBean.getHttpSession().getId().equals(httpSessionEvent.getSession().getId())){
                toRemove = sessionBean;
            }
        }
        SessionBean.sessionBeanRegister.remove(toRemove);
    }
}
