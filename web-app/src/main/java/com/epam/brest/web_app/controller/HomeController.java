package com.epam.brest.web_app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Root controller.
 */
@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String defaultPageRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:bands";
        }
        return "redirect:login";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "login";
    }

}
