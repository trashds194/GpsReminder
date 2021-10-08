package com.reminder.webapi.config;

import com.reminder.webapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .and()
                .formLogin().
                loginPage("/login").permitAll().failureUrl("/login-error.html").
                and().
                authorizeRequests().
                antMatchers("/locations/**").
                hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().
                antMatchers("/api/locations/**").
                hasAnyRole("ADMIN", "USER")
                .and()
                .logout().permitAll().logoutSuccessUrl("/login")
                .and()
                .httpBasic();
        httpSecurity.cors().disable().csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .eraseCredentials(true)
                .userDetailsService(authenticationService)
                .passwordEncoder(passwordEncoder());
    }
}
