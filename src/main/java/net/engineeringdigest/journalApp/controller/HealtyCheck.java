package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealtyCheck {

    @GetMapping("/health-check")
    public String HealtyCheck() {
        return "OK";
    }
}


// Spring Security Concept
// When user login so at the time of login, server just checks your credentials, if it matches then
// that means you are authenticated and it generates a session and that session ID which is basically a
// unique identifier of that session and that session id is returned in the response of that login API
// and After that in every API call that JSession ID will be passed in the request headers in the cookiee and
// this will be used at backend to confirm that the user is authenticated so that is why you dont need to login again & again.

// Security Filter Chain
// When we add spring security dependency, Spring automatically add something called SecurityFilterChain
// A SecurityFilterChain is nothing but a chain of filter, we want a filter in between to check the request and
// authenticate them whether the request is actually correct or not

// What is filter?
// A Filter is a component that intercepts every incoming HTTP request and it performs some logic before the request
// reaches the controller and sometimes even after the response is generated

// Client -> Filters -> Dispatcher Servlet -> Interceptors -> Controllers
// Filter come into play even after the dispatcher servlet and they are actually a part of the Servlet API
// Interceptors are something that intercept