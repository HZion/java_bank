package com.sion.bank.config;

import com.sion.bank.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login", "/signup", "/user/login", "/user/signup").permitAll()
                        .anyRequest().authenticated()
                )
<<<<<<< HEAD
//                .formLogin(form -> form
//                        .loginPage("/login")//
//                        .loginProcessingUrl("/user/login")
//                        .defaultSuccessUrl("/home", true)
//                        .failureUrl("/login?error")
//                        .permitAll()
//                )
=======
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
                .formLogin(form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/user/login")
                                .successHandler(customAuthenticationSuccessHandler)
                                .failureUrl("/login?error")
                                .permitAll()
                        )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf()
                    .disable()

<<<<<<< HEAD
        ;


=======
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
<<<<<<< HEAD

=======
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
}