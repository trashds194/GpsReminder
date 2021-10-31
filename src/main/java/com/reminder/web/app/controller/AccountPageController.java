package com.reminder.web.app.controller;

import com.reminder.web.app.model.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class AccountPageController {
    private final RestTemplate restTemplate;

    private String urlSwitcher() {
        String url;
        boolean test = false;
        if (test) {
            url = "http://localhost:8080/api/login/";
            return url;
        } else {
            url = "https://gps-reminder.herokuapp.com/api/login/";
            return url;
        }
    }

    //TODO: delete comment and unusable lines

    @Autowired
    public AccountPageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/login")
    public String loginGet(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginPost(@ModelAttribute User user, Model model) throws JSONException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject userJson = new JSONObject();
        userJson.put("username", user.getUsername());
        userJson.put("password", user.getPassword());

        System.out.println(userJson);

        HttpEntity<String> request = new HttpEntity<>(userJson.toString(), requestHeaders);

        try {
            HttpEntity<String> response =
                    restTemplate.exchange(
                            urlSwitcher(),
                            HttpMethod.POST,
                            request,
                            String.class);
            HttpHeaders responseHeaders = response.getHeaders();

            User.token = responseHeaders.getFirst("user_token");
            User.login = responseHeaders.getFirst("login");

            model.addAttribute("tokens", User.token);
            model.addAttribute("accounts", User.login);

            System.out.println(User.token);
            System.out.println(User.login);
            return "redirect:/reminders";
        } catch (Exception exception) {
            model.addAttribute("loginError", exception.toString());
            return "login";
        }
    }
}
