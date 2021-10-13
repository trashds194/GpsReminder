package com.reminder.webapi.controller;

import com.reminder.webapi.model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Controller
public class ReminderPageController {
    private final RestTemplate restTemplate;

    @Autowired
    public ReminderPageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/reminders")
    public String read(@RequestParam(name = "access_token") String token, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<List<Reminder>> responseEntity =
                restTemplate.exchange(
                        "http://localhost:8080/api/reminders",
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Reminder>>() {
                        }
                );
        List<Reminder> reminders = responseEntity.getBody();

        model.addAttribute("reminders", reminders);
        return "reminders";
    }

    @GetMapping(value = "/reminders/{login}")
    public String readAllUserLocations(@RequestParam(name = "access_token") String token, @PathVariable(name = "login") String login, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<List<Reminder>> responseEntity =
                restTemplate.exchange(
                        "http://localhost:8080/api/reminders/" + login,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Reminder>>() {
                        }
                );
        List<Reminder> reminders = responseEntity.getBody();

        model.addAttribute("reminders", reminders);
        return "reminders";
    }

    @GetMapping(value = "/reminders/{login}/{id}")
    public String readOneUserLocations(@RequestParam(name = "access_token") String token, @PathVariable(name = "login") String login,@PathVariable(name = "id") int id ,Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Reminder> responseEntity =
                restTemplate.exchange(
                        "http://localhost:8080/api/reminders/" + login + "/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<Reminder>() {
                        }
                );
        Reminder reminders = responseEntity.getBody();

        model.addAttribute("reminders", reminders);
        return "reminders";
    }
}
