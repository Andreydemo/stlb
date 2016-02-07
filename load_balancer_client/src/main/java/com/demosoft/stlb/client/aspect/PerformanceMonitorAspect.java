package com.demosoft.stlb.client.aspect;

import com.demosoft.stlb.client.bean.SessionBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
@Aspect
@Component
public class PerformanceMonitorAspect {

    @Autowired
    private SessionBean sessionBean;

    @Around("@annotation( com.demosoft.stlb.client.annotation.PerformanceMonitor)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        sessionBean.addActiveTime(time);
        if(sessionBean.getSessionId() == null) {
           sessionBean.accomulateSessionId();
        }
        return retVal;
    }
}
