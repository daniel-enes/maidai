package com.agir.maidai.config;

import com.agir.maidai.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("administrador > colaborador");
        return hierarchy;
    }

    private final String[] adminUrls = {
            "/users",
            "/users/**"
    };

    private final String[] collaboratorUrls = {
            "/",
            "/settings",
            "/settings/change-password",
            "/companies",
            "/companies/**",
            "/projects",
            "/projects/**",
            "/scholarships",
            "/scholarships/**",
            "/scholarshipTypes",
            "/scholarshipTypes/**",
            "/advisors",
            "/advisors/**",
            "/people",
            "/people/**",
            "/ppgs",
            "/ppgs/**",
            "/access-denied",
            "/templates/**",
    };

    private final String[] publicUrl = {
            //"/users",
            //"/users/create",
            "/webjars/**",
            "/resources/**",
            "/static/**",
            "/assets/**",
            "/css/**",
            "/js/**",
            "/fonts/**",
            "/favicon.ico",
            "/error",
            "/templates/**", // Allow access to Thymeleaf templates
            "/fragments/**"  // Allow access to Thymeleaf fragments
             };

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth -> {
                auth.requestMatchers(publicUrl).permitAll();
                auth
                        .requestMatchers(collaboratorUrls).hasAnyAuthority("colaborador")
                        .requestMatchers(adminUrls).hasAnyAuthority("administrador")
                        .anyRequest().authenticated();
            }
        );

        http.formLogin(form -> form
            .loginPage("/login").permitAll())
            .logout(logout -> {
                logout.logoutUrl("/logout");
                logout.logoutSuccessUrl("/");
        });

        http.exceptionHandling(configurer ->
                configurer.accessDeniedPage("/access-denied")
        );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);

        return authenticationProvider;
    }
}
