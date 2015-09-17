package com.demosoft.stlb.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class NodeConfigsConteiner {

    @Value("#{'${nodesUrls}'.split(',')}")
    private List<String> nodesUrls = new ArrayList<>();
}
