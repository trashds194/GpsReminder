package com.reminder.web.app.controller;

import com.reminder.web.app.model.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AccountPageController {
    private final RestTemplate restTemplate;

    @Value("${backend.link}")
    private String url;

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
    public String loginPost(@ModelAttribute User user, Model model, HttpServletResponse httpServletResponse) throws JSONException {
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
                            url + "login/",
                            HttpMethod.POST,
                            request,
                            String.class);
            HttpHeaders responseHeaders = response.getHeaders();

            addCookie("user_token", responseHeaders, httpServletResponse);
            addCookie("username", responseHeaders, httpServletResponse);

            return "redirect:/reminders";
        } catch (Exception exception) {
            model.addAttribute("loginError", exception.toString());
            return "login";
        }
    }

    private void addCookie(String cookieName, HttpHeaders httpHeaders, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(cookieName, httpHeaders.getFirst(cookieName));
        cookie.setMaxAge(2 * 24 * 60 * 60); //2 day cookies
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);
    }
}
