package com.reminder.web.app.controller;

import com.reminder.web.app.model.Reminder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Controller
public class ReminderPageController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.link}")
    private String url;

    @GetMapping(value = "/reminders/all")
    public String read(Model model,
                       @CookieValue(value = "username", defaultValue = "null") String username,
                       @CookieValue(value = "user_token", defaultValue = "null") String token) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("body", requestHeaders);
        ResponseEntity<List<Reminder>> responseEntity =
                restTemplate.exchange(
                        url + "reminders/all",
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Reminder>>() {
                        }
                );
        System.out.println(responseEntity.getStatusCodeValue());
        List<Reminder> reminders = responseEntity.getBody();

        model.addAttribute("reminders", reminders);
        return "reminders";
    }

    @GetMapping(value = "/reminders")
    @CrossOrigin(origins = "https://gps-reminder.herokuapp.com", allowCredentials = "true")
    public String readAllUserLocations(Model model,
                                       @CookieValue(value = "username", defaultValue = "null") String username,
                                       @CookieValue(value = "user_token", defaultValue = "null") String token) {

        System.out.println(token);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("body", requestHeaders);
        ResponseEntity<List<Reminder>> responseEntity =
                restTemplate.exchange(
                        url + "reminders/",
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Reminder>>() {
                        }
                );
        List<Reminder> reminders = responseEntity.getBody();

        model.addAttribute("reminders", reminders);
        return "reminders";
    }

    @GetMapping(value = "/reminders/{id}")
    public String readOneUserLocations(@PathVariable(name = "id") int id, Model model,
                                       @CookieValue(value = "username", defaultValue = "null") String username,
                                       @CookieValue(value = "user_token", defaultValue = "null") String token) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("body", requestHeaders);
        ResponseEntity<Reminder> responseEntity =
                restTemplate.exchange(
                        url + "reminders/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<Reminder>() {
                        }
                );
        Reminder reminders = responseEntity.getBody();
        model.addAttribute("reminders", reminders);
        return "reminder";
    }

    @GetMapping(value = "/reminders/add")
    public String addReminderGet(@CookieValue(value = "user_token", defaultValue = "null") String token) {
        if (token == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return "reminder-add";
    }

    @PostMapping(value = "/reminders/add")
    public String addReminderPost(Model model, Reminder reminder,
                                  @CookieValue(value = "username", defaultValue = "null") String username,
                                  @CookieValue(value = "user_token", defaultValue = "null") String token) throws JSONException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        JSONObject reminderJson = new JSONObject();
        reminderJson.put("title", reminder.getTitle());
        reminderJson.put("description", reminder.getDescription());
        reminderJson.put("placeName", reminder.getPlaceName());

        System.out.println(reminderJson);
        HttpEntity<String> request = new HttpEntity<>(reminderJson.toString(), requestHeaders);
        System.out.println(request);

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url + "reminders/",
                            HttpMethod.POST,
                            request,
                            String.class);
            return "redirect:/reminders";
        } catch (Exception exception) {
            model.addAttribute("error", exception.toString());
            return "reminder-add";
        }
    }

    @GetMapping(value = "/reminders/{id}/change")
    public String changeReminderGet(@PathVariable(name = "id") int id, Model model,
                                    @CookieValue(value = "username", defaultValue = "null") String username,
                                    @CookieValue(value = "user_token", defaultValue = "null") String token) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("body", requestHeaders);
        ResponseEntity<Reminder> responseEntity =
                restTemplate.exchange(
                        url + "reminders/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<Reminder>() {
                        }
                );
        Reminder reminders = responseEntity.getBody();

        model.addAttribute("reminders", reminders);
        return "reminder-change";
    }

    @PostMapping(value = "/reminders/{id}/change")
    public String changeReminderPost(@PathVariable(name = "id") int id, Model model, Reminder reminder,
                                     @CookieValue(value = "username", defaultValue = "null") String username,
                                     @CookieValue(value = "user_token", defaultValue = "null") String token) throws JSONException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        JSONObject reminderJson = new JSONObject();
        reminderJson.put("title", reminder.getTitle());
        reminderJson.put("description", reminder.getDescription());
        reminderJson.put("placeName", reminder.getPlaceName());

        System.out.println(reminderJson);
        HttpEntity<String> request = new HttpEntity<>(reminderJson.toString(), requestHeaders);
        System.out.println(request);

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url + "reminders/" + id,
                            HttpMethod.PUT,
                            request,
                            String.class);
            return "redirect:/reminders";
        } catch (Exception exception) {
            model.addAttribute("error", exception.toString());
            return "reminder-change";
        }
    }

    @DeleteMapping(value = "/reminders/del/{id}")
    public String delReminder(@PathVariable(name = "id") long id, Model model,
                              @CookieValue(value = "username", defaultValue = "null") String username,
                              @CookieValue(value = "user_token", defaultValue = "null") String token) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("body", requestHeaders);
        ResponseEntity<Reminder> responseEntity =
                restTemplate.exchange(
                        url + "reminders/" + id,
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<Reminder>() {
                        }
                );
        Reminder reminders = responseEntity.getBody();

        model.addAttribute("reminders", reminders);
        return "reminder";
    }
}
