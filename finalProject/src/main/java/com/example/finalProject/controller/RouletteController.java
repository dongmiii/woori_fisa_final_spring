package com.example.finalProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/roulette")
public class RouletteController {

    @GetMapping("/roulette")
    public String roulette() {
        return "roulette/roulette"; // templates/roulette/roulette.html
    }

    @GetMapping("/loading")
    public String loading(@RequestParam("character") String character, Model model) {
        model.addAttribute("character", character);
        return "roulette/loading"; // templates/roulette/loading.html
    }
}