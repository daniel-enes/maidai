package com.agir.maidai.controller;

import com.agir.maidai.entity.Role;
import com.agir.maidai.entity.User;
import com.agir.maidai.service.RoleService;
import com.agir.maidai.service.UserService;
import jakarta.validation.groups.Default;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String store(@Validated({User.CreateGroup.class, Default.class}) User user,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {

        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "users/form-user";
        }

        try {
            User newUser = userService.createUser(user);
            redirectAttributes.addFlashAttribute("success", "O usuário foi cadastrado");
            return "redirect:/users";
            //return "redirect:/users/" + newUser.getId();
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
    
    @PutMapping("/{id}")
    public String update(@Validated(User.UpdateGroup.class) User user,
                         @PathVariable int id,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "users/form-user";
        }

        try {
            //user.setId(id);
            User userUpdated = userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "As alterações foram bem-sucedidas.");
            return "redirect:/users";
            //return "redirect:/users/" + userUpdated.getId();
        } catch (Exception e) {

            List<ObjectError> errors = new ArrayList<>();
            errors.add(new ObjectError("globalError", e.getMessage()));
            model.addAttribute("errors", errors);

            return "users/form-user";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
