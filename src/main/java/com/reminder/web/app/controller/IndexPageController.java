package com.reminder.web.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPageController {
    @RequestMapping(value = "/")
    public String index(Model model,
                        @CookieValue(value = "username", defaultValue = "null") String username) {
        model.addAttribute("user", username);
        return "index";
    }
}
