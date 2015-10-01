package com.demosoft.stlb.controller;

import com.demosoft.stlb.bean.ConfigChangingBean;
import com.demosoft.stlb.bean.Configs;
import com.demosoft.stlb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Andrii_Korkoshko on 30.09.2015.
 */
@Controller
@RequestMapping("/stlb")
@Scope("request")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private Configs configs;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String balancerAdmin(Model m) {
        adminService.getAdminPage(m);
        return "stlbAdmin";
    }

    @RequestMapping(value = "/removeNode", method = RequestMethod.POST)
    private String removeNode(@RequestParam("deletingUrl") String deletingUrl, Model m) {
        adminService.remodeNode(deletingUrl);
        return "redirect:viewNodes";
    }

    @RequestMapping(value = "/addNode", method = RequestMethod.GET)
    private String addNodePage(Model m) {
        return "stlbAdminAddNode";
    }

    @RequestMapping(value = "/addNode", method = RequestMethod.POST)
    private String addNode(@RequestParam("url") String addUrls, Model m) {
        adminService.addNode(addUrls);
        return "redirect:viewNodes";
    }

    @RequestMapping(value = "/viewNodes", method = RequestMethod.GET)
    private String balancerAdminViewNodes(Model m) {
        adminService.getAdminPage(m);
        return "stlbAdminViewNodes";
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    private String configPage(Model m) {
        m.addAttribute("configs", configs.getConfigMap());
        return "stlbAdminParams";
    }

    @RequestMapping(value = "/editConfig", method = RequestMethod.GET)
    private String editConfigPage(@RequestParam("configName") String configName, Model m) {
        Object config = configs.getConfigMap().get(configName);

        m.addAttribute("configName", configName);
        m.addAttribute("configValue", config);
        m.addAttribute("configType", config.getClass().getName());
        m.addAttribute("configChangingBean", new ConfigChangingBean());
        return "stlbAdminEditParams";
    }

    @RequestMapping(value = "/editConfig", method = RequestMethod.POST)
    private String editConfig(ConfigChangingBean changingBean, Model m) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println(changingBean);

        m.addAttribute("configName", changingBean.getName());
        m.addAttribute("configValue", changingBean.getValue());
        m.addAttribute("configType", changingBean.getType());
        m.addAttribute("configChangingBean", new ConfigChangingBean());
        configs.getConfigMap().put(changingBean.getName(), parseValue(changingBean));
        return "stlbAdminEditParams";
    }

    private Object parseValue(ConfigChangingBean changingBean) {
        if (changingBean.getType().equalsIgnoreCase("java.lang.Boolean")) {
            return Boolean.valueOf((String) changingBean.getValue());
        }
        return null;
    }

}
