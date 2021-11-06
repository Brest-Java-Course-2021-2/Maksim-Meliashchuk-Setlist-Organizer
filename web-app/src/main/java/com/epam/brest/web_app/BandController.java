package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Hello MVC controller.
 */
@Controller
public class BandController {

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
                        Model model) {

        System.out.println("hello(name:'" + name + "')");
        model.addAttribute("name", name);
        return "hello";
    }
}