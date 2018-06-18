package dev.sangco.jwmessage.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ViewController {
    public static final Logger log =  LoggerFactory.getLogger(ViewController.class);

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("view", "index");
        return "index";
    }

    @RequestMapping(value = "/accounts/join", method = GET)
    public String joinForm(Model model) {
        model.addAttribute("view", "join");
        return "account/join";
    }

    //  For Spring Security
    @RequestMapping(value = "/accounts/login", method = GET)
    public String login(Model model) {
        model.addAttribute("view", "login");
        return "account/login";
    }

    @RequestMapping(value = "/accounts/logout", method = GET)
    public String logout(Model model) {
        model.addAttribute("view", "index");
        return "index";
    }

    @RequestMapping(value = "/accounts/accessDenied", method = GET)
    public String accessDenied(Model model) {
        model.addAttribute("view", "accessDenied");
        return "account/accessDenied";
    }


    @RequestMapping(value = "/accounts/update", method = GET)
    public String update(Model model) {
        model.addAttribute("view", "update");
        return "account/update";
    }

    @RequestMapping(value = "/message/form", method = GET)
    public String messageForm(Model model) {
        model.addAttribute("view", "messageForm");
        return "/message/form";
    }

    @RequestMapping(value = "/message/list", method = GET)
    public String messageList(Model model) {
        model.addAttribute("view", "messageList");
        return "/message/list";
    }

    @RequestMapping(value = "/message/record", method = GET)
    public String messageRecord(Model model) {
        model.addAttribute("view", "messageRecord");
        return "/message/record";
    }

    @RequestMapping(value = "/storage/list", method = GET)
    public String storageList(Model model) {
        model.addAttribute("view", "storageList");
        return "/storage/list";
    }
}