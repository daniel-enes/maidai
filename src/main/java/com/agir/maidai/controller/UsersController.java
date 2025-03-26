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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {

        Optional<User> userOptional = userService.getUserById(id);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            return "users/user";
        }
        return "redirect:/";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "users/form-user";
    }

    @PostMapping
    public String store(@Valid User user, BindingResult bindingResult, Model model) {

        //List<Role> roles = roleService.getAllRoles();
        //model.addAttribute("roles", roles);

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "users/form-user";
        }

        try {
            User newUser = userService.createUser(user);
            return "redirect:/users/" + newUser.getId();
        } catch (Exception e) {

            List<ObjectError> errors = new ArrayList<>();
            errors.add(new ObjectError("globalError", e.getMessage()));
            model.addAttribute("errors", errors);

            return "users/form-user";
        }
    }

    @GetMapping("/{id}/edit")
    /*public String edit(@PathVariable int id, Model model) {*/
    public String edit(@PathVariable int id, Model model) {
        Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "users/form-user";
    }

    @PostMapping("/{id}")
    public String update(@Valid User user, @PathVariable int id, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "users/form-user";
        }

        try {
            User userUpdated = userService.updateUser(user);
            return "redirect:/users/" + userUpdated.getId();
        } catch (Exception e) {

            List<ObjectError> errors = new ArrayList<>();
            errors.add(new ObjectError("globalError", e.getMessage()));
            model.addAttribute("errors", errors);

            return "users/form-user";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        return "/";
    }
}
