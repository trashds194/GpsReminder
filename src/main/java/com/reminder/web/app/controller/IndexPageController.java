package com.reminder.web.app.controller;

import com.reminder.web.app.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class IndexPageController {
    @RequestMapping(value = "/")
    public String index(Model model) {
        if (User.token == null){
            model.addAttribute("user", null);
        }
        else {
            model.addAttribute("user", User.login);
        }
        return "index";
    }
}
