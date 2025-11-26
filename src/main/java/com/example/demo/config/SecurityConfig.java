package com.example.demo.config;

import static org.springframework.security.config.Customizer.withDefaults; // 🔹 슬라이드에 있는 정적 import

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링 설정 클래스 지정, 등록된 Bean 생성 시점
@EnableWebSecurity // 스프링 보안 활성화
public class SecurityConfig { // 스프링에서 보안 관리 클래스

    @Bean // 명시적 의존성 주입 : Autowired와 다름
    // 5.7버전 이상 WebSecurityConfigurerAdapter 사용 안 함
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 🔹 XSS 방어 헤더 추가
            .headers(headers -> headers
                .addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block"); // X-XSS-Protection 헤더 설정
                })
            )

            // 🔹 CSRF 기본 활성화
            .csrf(withDefaults())

            // 🔹 세션 관리 설정
            .sessionManagement(session -> session
                .invalidSessionUrl("/session-expired") // 세션 만료 시 이동할 URL
                .maximumSessions(1)                    // 사용자별 최대 세션 수: 1개
                .maxSessionsPreventsLogin(true)        // 동시 세션 제한 (기존 세션 유지, 새 로그인 차단)
            );

        // 필터 체인을 통해 보안설정(HttpSecurity)을 반환
        return http.build();
    }

    @Bean // 암호화 설정
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 저장
    }
}
