package com.effectivo.BugTracker.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerController {

    @GetMapping("/")
    public String documentation(){
        return "redirect:/swagger-ui.html";
    }
}
