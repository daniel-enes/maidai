package com.agir.maidai.controller;

import com.agir.maidai.entity.Role;
import com.agir.maidai.entity.User;
import com.agir.maidai.service.RoleService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String index(Model model) {

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable int id, Model model) {

        Optional<User> userOptional = userService.getUserById(id);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            return "users/user";
        }
        return "redirect:/";
    }

    @GetMapping("/users/create")
    public String create(Model model) {

        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "users/form-user";
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

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
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

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "users/access-denied";
    }

}
