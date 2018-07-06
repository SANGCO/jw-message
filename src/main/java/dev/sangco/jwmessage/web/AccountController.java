package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.UnAuthenticationException;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.service.AccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    public static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/join", method = GET)
    public String joinForm(Model model) {
        model.addAttribute("view", "join");
        model.addAttribute("create", new AccountDto.Create());
        return "account/join";
    }

    @RequestMapping(value = "/join", method = POST)
    public String createAccount(@Valid AccountDto.Create create, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/join";
        }
        accountService.createAccount(create);
        return "redirect:/";
    }

    //  For Spring Security
    @RequestMapping(value = "/login", method = GET)
    public String login(Model model) {
        model.addAttribute("view", "login");
        return "account/login";
    }

//    @RequestMapping(value = "/logout", method = GET)
//    public String logout(Model model) {
//        model.addAttribute("view", "index");
//        return "/account/logout";
//    }

    @RequestMapping(value = "/accessDenied", method = GET)
    public String accessDenied(Model model) {
        model.addAttribute("view", "accessDenied");
        return "account/accessDenied";
    }


    @RequestMapping(value = "/update", method = GET)
    public String updateForm(Model model, Principal principal) {
        model.addAttribute("view", "update");
        model.addAttribute("update", modelMapper.map(accountService.findByAccId(principal.getName()), AccountDto.Create.class));
        return "account/update";
    }


    @RequestMapping(value = "/join/{accId}", method = POST)
    public String update(@PathVariable String accId, @Valid AccountDto.Update update, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/update";
        }

        if (!accId.equalsIgnoreCase(principal.getName())) {
            throw new UnAuthenticationException(accId);
        }

        accountService.updateAccount(accId, update);
        return "redirect:/";
    }
}
