package com.reminder.webapi.controller;

import com.reminder.webapi.model.Location;
import com.reminder.webapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LocationPageController {
    private final LocationService locationService;

    @Autowired
    public LocationPageController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/locations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String read(Model model) {
        final List<Location> locations = locationService.readAll();

        model.addAttribute("locations", locations);
        return "locations";
    }
}
