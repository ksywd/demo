package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // XSS 헤더
            .headers(headers -> headers.addHeaderWriter((request, response) ->
                response.setHeader("X-XSS-Protection", "1; mode=block")
            ))

            // CSRF 끄기
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            .sessionManagement(session -> session
                .invalidSessionUrl("/session-expired")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
            );

        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
