package com.demosoft.stlb.loadbalancer.controller;

import com.demosoft.stlb.loadbalancer.bean.*;
import com.demosoft.stlb.loadbalancer.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    NodeConfigsConteiner nodeConfigsConteiner;

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

    @RequestMapping(value = "/switchNodeStatusFrom-{status}", method = RequestMethod.POST)
    private String switchNodeStatus(@RequestParam("nodeId") String nodeId, @PathVariable boolean status, Model m) {
        if (status) {
            adminService.switchNodeStatus(nodeId, false);
        } else {
            adminService.switchNodeStatus(nodeId, true);
        }

        return "redirect:viewNodes";
    }

    @RequestMapping(value = "/addNode", method = RequestMethod.GET)
    private String addNodePage(Model m) {
        m.addAttribute("addNodeBean", new AddNodeBean());
        return "stlbAdminAddNode";
    }

    @RequestMapping(value = "/addNode", method = RequestMethod.POST)
    private String addNode(@ModelAttribute AddNodeBean addNodeBean, Model m) {
        adminService.addNode(addNodeBean);
        return "redirect:viewNodes";
    }

    @RequestMapping(value = "/viewNodes", method = RequestMethod.GET)
    private String balancerAdminViewNodes(Model m) {
        adminService.getAdminPage(m);
        return "stlbAdminViewNodes";
    }

    @RequestMapping(value = "/nodeInfo-{nodeId}", method = RequestMethod.GET)
    private String nodeInfoPage(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        m.addAttribute("node", node);
        return "stlbAdminNodeInfo";
    }
    @RequestMapping(value = "/nodeInfoFragment-{nodeId}", method = RequestMethod.GET)
    private String nodeInfoPageFragment(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        m.addAttribute("node", node);
        return "stlbAdminNodeInfoFragment";
    }

    @RequestMapping(value = "/nodeLoading-{nodeId}", method = RequestMethod.GET)
    private String nodeLoadingPage(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        m.addAttribute("node", node);
        return "stlbAdminNodeLoading";
    }
    @RequestMapping(value = "/cpuGraph-{nodeId}", method = RequestMethod.GET)
    private String nodeCpuGraph(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        m.addAttribute("node", node);
        return "stlbAdminNodeCpuGraph";
    }
    @RequestMapping(value = "/cpuProcessGraph-{nodeId}", method = RequestMethod.GET)
    private String nodeCpuProcessGraph(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        m.addAttribute("node", node);
        return "stlbAdminNodeCpuProcessGraph";
    }

    @RequestMapping(value = "/mockNode-{nodeId}", method = RequestMethod.GET)
    private String mockNode(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        m.addAttribute("node", node);
        return "stlbAdminMockedNodeInfoFragment";
    }

    @RequestMapping(value = "/memoryGraph-{nodeId}", method = RequestMethod.GET)
    private String nodeMemoryGraph(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        m.addAttribute("node", node);
        return "stlbAdminNodeMemoryGraph";
    }

    @RequestMapping(value = "/setInterval-{nodeId}", method = RequestMethod.POST)
    private String setInterval(@PathVariable("nodeId") String nodeId, Model m, @RequestParam("interval") String interval) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        Integer intervall = Integer.valueOf(interval);
        adminService.setNodeInterval(node, intervall);
        m.addAttribute("node", node);
        return "redirect:nodeInfo-"+node.getNodeId();
    }

    @RequestMapping(value = "/setActivityPointsPerSession-{nodeId}", method = RequestMethod.POST)
    private String setActivityPointsPerSession(@PathVariable("nodeId") String nodeId, Model m, @RequestParam("activityPointsPerSession") String activityPointsPerSession) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        Double activityPointsPerSessionn = Double.valueOf(activityPointsPerSession);
        node.getMockedNode().setActivityPointsPerSession(activityPointsPerSessionn);
        m.addAttribute("node", node);
        return "redirect:nodeInfo-"+node.getNodeId();
    }

    @RequestMapping(value = "/setCriticalLevel-{nodeId}", method = RequestMethod.POST)
    private String setCriticalLevele(@PathVariable("nodeId") String nodeId, Model m, @RequestParam("criticalLevel") String criticalLevel) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        Double criticalLevell = Double.valueOf(criticalLevel);
        node.setCriticalLevel(criticalLevell);
        m.addAttribute("node", node);
        return "redirect:nodeInfo-"+node.getNodeId();
    }

    @RequestMapping(value = "/switchNodeMockStatus-{nodeId}", method = RequestMethod.POST)
    private String switchNodeMockStatus(@PathVariable("nodeId") String nodeId, Model m) {
        Node node = nodeConfigsConteiner.getNodeById(nodeId);
        node.swithcMockStatus();
        m.addAttribute("node", node);
        return "redirect:nodeInfo-"+node.getNodeId();
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
        if (changingBean.getType().equalsIgnoreCase("java.lang.Double")) {
            return Double.valueOf((String) changingBean.getValue());
        }
        if (changingBean.getType().equalsIgnoreCase("java.lang.String")) {
            return String.valueOf((String) changingBean.getValue());
        }if (changingBean.getType().equalsIgnoreCase("java.lang.Integer")) {
            return Integer.valueOf((String) changingBean.getValue());
        }
        return null;
    }

}
