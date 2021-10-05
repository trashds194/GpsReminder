package com.reminder.webapi.controller;

import com.reminder.webapi.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.reminder.webapi.service.LocationService;

import java.util.List;

@Controller
public class IndexController {
    @RequestMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String index() {
        return "index";
    }
}
