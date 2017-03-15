package com.demosoft.stlb.loadbalancer.bean.listener;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.ApplicationServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Field;

/**
 * Created by Andrii_Korkoshko on 02.10.2015.
 */
@Component
public class ContextListener extends ContextLoaderListener implements ServletContextListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ServletContext servletContext;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        chageJaspeMapping();
        servletContext.getServletRegistrations().get("jsp").addMapping("*.lbsp");
        log.info("Jasper Mapping {}", servletContext.getServletRegistrations().get("jsp").getMappings());

    }

    private void chageJaspeMapping() {
        ApplicationServletRegistration servletRegistration = ((ApplicationServletRegistration) servletContext.getServletRegistrations().get("jsp"));
        Field context = null;
        try {
            context = servletRegistration.getClass().getDeclaredField("context");
            context.setAccessible(true);
            Context context1 = (Context) context.get(servletRegistration);
            ((Wrapper) context1.findChild("jsp")).removeMapping("*.jsp");
            ((Wrapper) context1.findChild("jsp")).removeMapping("*.jspx");
            context1.removeServletMapping("*.jsp");
            context1.removeServletMapping("*.jspx");
            ((Wrapper) context1.findChild("jsp")).addMapping("*.lbsp");
            System.out.println("removed");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
