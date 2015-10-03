package stlb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index(HttpSession session,Model model) {
        model.addAttribute("sessionId",session.getId());
        return "index";
    }

    @RequestMapping("/perf")
    public String perfIndex(HttpSession session,Model model) {
        System.out.println("perf");
        model.addAttribute("sessionId",session.getId());
        return "index";
    }

    @RequestMapping("/page{number}")
    public String page(@PathVariable String number, Model model) {
        model.addAttribute("number", number);
        return "page";
    }

}