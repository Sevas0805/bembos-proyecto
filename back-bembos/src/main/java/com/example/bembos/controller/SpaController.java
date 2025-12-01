package com.example.bembos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {
    
    // Mapeo simple para rutas que no empiezan con /api
    @GetMapping(value = {"/", "/login", "/catalog", "/builder", "/orders", "/ranking"})
    public String forward() {
        return "forward:/index.html";
    }
}
