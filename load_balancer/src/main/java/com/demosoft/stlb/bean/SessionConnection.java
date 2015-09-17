package com.demosoft.stlb.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
@Scope("session")
public class SessionConnection {

    private Node node;
    private String jSessionId;
}
