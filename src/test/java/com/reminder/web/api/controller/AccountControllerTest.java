package com.reminder.web.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reminder.web.WebApplication;
import com.reminder.web.api.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WebApplication.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @MockBean
    private AccountController accountController;

    private final String API_GATEWAY = "/api";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login() {
    }

    @Test
    public void create() {
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void readAllAccounts_ThenExpect200() throws Exception {
        given(accountController.read()).willReturn(ResponseEntity.ok().body(Collections.emptyList()));

        this.mockMvc.perform(get(API_GATEWAY + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void readAllAccounts_ThenExpect403() throws Exception {
        given(accountController.read()).willReturn(ResponseEntity.status(403).build());

        this.mockMvc.perform(get(API_GATEWAY + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void readOneAccount_ThenExpect200() throws Exception {
        given(accountController.read(anyLong())).willReturn(ResponseEntity.ok().body(new Account()));

        this.mockMvc.perform(get(API_GATEWAY + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void readOneAccount_ThenExpect403() throws Exception {
        given(accountController.read(anyLong())).willReturn(ResponseEntity.status(403).build());

        this.mockMvc.perform(get(API_GATEWAY + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void readOneAccount_ThenExpect404() throws Exception {
        given(accountController.read(anyLong())).willReturn(ResponseEntity.notFound().build());

        this.mockMvc.perform(get(API_GATEWAY + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void update() throws Exception {
        Account testAccount = new Account("admin", "admin");
        when(accountController.update(anyLong(), eq(testAccount)))
                .thenReturn(ResponseEntity.ok().build());

        this.mockMvc.perform(putJson(API_GATEWAY + "/accounts/1", testAccount))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void deleteOneAccount_ThenExpect200() throws Exception {
        given(accountController.read(anyLong())).willReturn(ResponseEntity.notFound().build());

        this.mockMvc.perform(delete(API_GATEWAY + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void deleteOneAccount_ThenExpect304() throws Exception {
        given(accountController.delete(anyLong())).willReturn(ResponseEntity.status(304).build());

        this.mockMvc.perform(delete(API_GATEWAY + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotModified());
    }

    public static MockHttpServletRequestBuilder putJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return put(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}