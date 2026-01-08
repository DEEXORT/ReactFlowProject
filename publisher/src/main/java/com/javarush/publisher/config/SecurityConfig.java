package com.javarush.publisher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.publisher.auth.WriterDetailsService;
import com.javarush.publisher.config.filter.JwtAuthenticationFilter;
import com.javarush.publisher.config.filter.JwtCookieAuthenticationSuccessHandler;
import com.javarush.publisher.config.provider.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/static/**", "/css/**").permitAll()
                        .requestMatchers("/api/v1.0/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v2.0/writers").permitAll()
                        .requestMatchers("/api/v2.0/login", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(fl -> fl
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(jwtAuthenticationHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .exceptionHandling(ex -> ex
                        .defaultAuthenticationEntryPointFor(restAuthenticationEntryPoint(),
                                new AntPathRequestMatcher("/api/v2.0/**"))
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("access_token")
                        .permitAll());
        return http.build();
    }

    // Code 401 for REST API
    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", System.currentTimeMillis());
            errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            errorDetails.put("error", "Unauthorized");
            errorDetails.put("message", authException.getMessage());
            errorDetails.put("path", request.getRequestURI());

            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writeValue(response.getWriter(), errorDetails);
        };
    }

    private AuthenticationSuccessHandler jwtAuthenticationHandler() {
        return new JwtCookieAuthenticationSuccessHandler(jwtProvider());
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(WriterDetailsService writerDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(writerDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Главный координатор аутентификации
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
