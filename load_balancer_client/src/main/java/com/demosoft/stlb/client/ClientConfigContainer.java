package com.demosoft.stlb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
@Component
public class ClientConfigContainer {

    @Value("${infoPort}")
    private int infoPort;

    public int getInfoPort() {
        return infoPort;
    }

    public void setInfoPort(int infoPort) {
        this.infoPort = infoPort;
    }
}
