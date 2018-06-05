package dev.sangco.jwmessage.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

@Controller
public class ViewController {
    public static final Logger log =  LoggerFactory.getLogger(ViewController.class);

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/accounts/join", method = GET)
    public String joinForm() {
    	return "/account/join";
    }

    @RequestMapping(value = "/accounts/login")
    public String login() {
        return "/account/login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @RequestMapping(value = "/companies/update", method = GET)
    public String registForm() {
        return "/company/update";
    }

    @RequestMapping(value = "/admin/test", method = GET)
    public String admin() {
        return "admin";
    }
}