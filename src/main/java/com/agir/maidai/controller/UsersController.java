package com.agir.maidai.controller;

import com.agir.maidai.entity.User;
import com.agir.maidai.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/users/create")
    public String create(Model model) {

        model.addAttribute("user", new User());

        return "form-user";
    }

    @PostMapping("/users")
    public String store(@Valid User user, Model model) {

        Optional<User> optionalUser = userService.getUserByEmail(user.getEmail());

        if(optionalUser.isPresent()) {

            model.addAttribute("error", "Informe outro email.");
            model.addAttribute("user", new User());

            return "/users/create";
        }

        User newUser = userService.createUser(user);

        System.out.println(newUser);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }
}
