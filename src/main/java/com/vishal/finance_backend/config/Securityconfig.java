package com.vishal.finance_backend.config;

import com.vishal.finance_backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Securityconfig {

    private  final CustomUserDetailsService userDetailsService;

    public Securityconfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService= userDetailsService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth->auth

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Dashboard access
                        .requestMatchers("/api/dashboard/**")
                        .hasAnyRole("VIEWER", "ANALYST", "ADMIN")

                        // Financial records
                        .requestMatchers(HttpMethod.GET, "/api/records/**")
                        .hasAnyRole("ANALYST", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/records/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/records/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/records/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/records/**")
                        .hasRole("ADMIN")

                        // User management
                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")

                        // Any other request
                        .anyRequest()
                        .authenticated()
                )
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
