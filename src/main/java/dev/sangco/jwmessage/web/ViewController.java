package dev.sangco.jwmessage.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {
    public static final Logger log =  LoggerFactory.getLogger(ViewController.class);

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/accounts/join", method = RequestMethod.GET)
    public String joinForm() {
        return "join";
    }
}