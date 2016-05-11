package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.client.CriticalSessionSender;
import com.demosoft.stlb.client.CriticalSessionReceiver;
import com.demosoft.stlb.client.bean.MyJsonMapper;
import com.demosoft.stlb.client.bean.SessionTransportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by andrii_korkoshko on 06.05.16.
 */
@Controller
@RequestMapping("/admin/stlb")
@Scope("request")
public class STLBClientAdminController {


    @Autowired
    private CriticalSessionSender receiveCriticalSession;

    @Autowired
    private CriticalSessionReceiver sendCriticalSession;

    @Autowired
    private SessionTransportBean.SessionTransportBeanFactory transportBeanWrapperFactory;


    @Autowired
    @Qualifier("jacksonObjectMapper")
    private MyJsonMapper jsonMapper;

    @PostConstruct
    public void init(){

    }


    @RequestMapping(value = "/getSessionBean")
    public @ResponseBody SessionTransportBean getSessionTransportObject(HttpSession session){
        SessionTransportBean sessionTransportBean = receiveCriticalSession.send();
        session.invalidate();
        return sessionTransportBean;
    }

    @RequestMapping(value = "/setSessionBean", method = RequestMethod.POST)
    public @ResponseBody String  setSessionTransportObject(@RequestBody String sessionTransportBeanBody) throws IOException {
        System.out.println(sessionTransportBeanBody);
        SessionTransportBean sessionTransportBean =  transportBeanWrapperFactory.get(sessionTransportBeanBody);
        Boolean result =  sendCriticalSession.receive(sessionTransportBean);
        return result.toString();
    }

}
