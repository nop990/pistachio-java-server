package com.nop990.pistachio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {
    @RequestMapping("/")
    public String forwardToStatic() {
        // This will forward to index.html in src/main/resources/static
        return "forward:/index.html";
    }
}
