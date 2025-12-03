package org.delcom.app.configs;

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
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. MATIKAN CSRF (Supaya tombol Login bisa diklik dan tidak error 405)
            .csrf(csrf -> csrf.disable())

            // 2. IZINKAN SEMUA (Kita sudah pakai AuthInterceptor, jadi ini dilonggarkan saja)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // 3. Matikan Login Bawaan (Agar tidak bentrok dengan form login kita)
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}