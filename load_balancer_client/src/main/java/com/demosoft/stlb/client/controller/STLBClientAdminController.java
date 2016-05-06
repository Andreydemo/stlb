package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.client.ReceiveCriticalSession;
import com.demosoft.stlb.client.SendCriticalSession;
import com.demosoft.stlb.client.bean.SessionTransportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by andrii_korkoshko on 06.05.16.
 */
@Controller
@RequestMapping("/admin/stlb")
@Scope("request")
public class STLBClientAdminController {


    @Autowired
    private ReceiveCriticalSession receiveCriticalSession;

    @Autowired
    private SendCriticalSession sendCriticalSession;




    @RequestMapping(value = "/getSessionBean")
    public @ResponseBody SessionTransportBean getSessionTransportObject(HttpSession session){
        SessionTransportBean sessionTransportBean = receiveCriticalSession.receive();
        session.invalidate();
        return sessionTransportBean;
    }

    @RequestMapping(value = "/setSessionBean")
    public @ResponseBody String  setSessionTransportObject(@RequestBody SessionTransportBean sessionTransportBean){
        sendCriticalSession.send(sessionTransportBean);
        return "true";
    }

}
