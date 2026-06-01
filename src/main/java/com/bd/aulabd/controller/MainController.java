package com.bd.aulabd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bd.aulabd.model.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        UsuarioService us = context.getBean(UsuarioService.class);
        if (us.autenticar(username, password)) {
            session.setAttribute("username", username.trim());
            return "redirect:/escalacoes";
        }
        model.addAttribute("erro", "Usuário ou senha inválidos.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/?logout";
    }
}
