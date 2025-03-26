package com.agir.maidai.controller;

import com.agir.maidai.entity.User;
import com.agir.maidai.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SettingsController(UserService userService, PasswordEncoder passwordEncoder, UsersController usersController) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String settingsPage() {
        return "settings";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOptional = userService.getUserByEmail(email);

        if(userOptional.isEmpty()) {
            model.addAttribute("error", "Usuário não encontrado");
            return "settings";
        }

        User user = userOptional.get();

        if(!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Senha atual incorreta");
            return "settings";
        }

        if(!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "As novas senhas não coincidem");
            return "settings";
        }

        if(newPassword.length() < 8) {
            model.addAttribute("error", "A senha deve ter pelo menos 8 caracteres");
            return "settings";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);

        model.addAttribute("success", true);
        return "settings";

    }
}
