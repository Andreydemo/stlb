package com.demosoft.stlb.aspect;

import com.demosoft.stlb.bean.SessionBean;
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

    @Around("@annotation( com.demosoft.stlb.annotation.PerformanceMonitor)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        sessionBean.addActiveTime(time);
        return retVal;
    }
}
