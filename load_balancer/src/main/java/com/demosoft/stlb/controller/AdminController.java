package com.demosoft.stlb.controller;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String balancerAdmin(Model m) {
        adminService.getAdminPage(m);
        return "stlbAdmin";
    }

    @RequestMapping(value = "/removeNode", method = RequestMethod.POST)
    private String removeNode(@RequestParam("deletingUrl") String deletingUrl, Model m) {
        adminService.remodeNode(deletingUrl);
        return "redirect:/stlb";
    }
    @RequestMapping(value = "/removeNode", method = RequestMethod.GET)
    private String addNodePage(Model m) {
        return "stlbAdminAddNode";
    }
    @RequestMapping(value = "/addNode", method = RequestMethod.POST)
    private String addNode(@RequestParam("url") String addUrls, Model m) {
        adminService.addNode(addUrls);
        return "redirect:/stlb";
    }
}
