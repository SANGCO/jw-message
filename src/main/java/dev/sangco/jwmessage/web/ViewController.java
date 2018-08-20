package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.domain.MeatCutRepository;
import dev.sangco.jwmessage.domain.SalesPersonRepository;
import dev.sangco.jwmessage.domain.TypeOfBIzRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ViewController {
    public static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private TypeOfBIzRepository typeOfBIzRepository;

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private MeatCutRepository meatCutRepository;

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("view", "index");
        return "index";
    }

    @RequestMapping(value = "/message/form", method = GET)
    public String messageForm(Model model) {
        model.addAttribute("view", "messageForm");
        model.addAttribute("salesPersons", salesPersonRepository.findAll());
        model.addAttribute("typeOfBizs", typeOfBIzRepository.findAll());
        model.addAttribute("meatCuts", meatCutRepository.findAll());
        return "message/form";
    }

    @RequestMapping(value = "/message/list", method = GET)
    public String messageList(Model model) {
        model.addAttribute("view", "messageList");
        return "message/list";
    }

    @RequestMapping(value = "/message/record", method = GET)
    public String messageRecord(Model model) {
        model.addAttribute("view", "messageRecord");
        return "message/record";
    }

    @RequestMapping(value = "/storage/list", method = GET)
    public String storageList(Model model) {
        model.addAttribute("view", "storageList");
        return "storage/list";
    }
}