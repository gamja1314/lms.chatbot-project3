package com.test.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, 
                                                        UserDetailsService userDetailsService, 
                                                        BCryptPasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .cors((cors) -> cors.and())
    //         .csrf((csrf) -> csrf.disable())
    //         .authorizeHttpRequests(authorizeRequests -> authorizeRequests
    //             .requestMatchers("/member/signup", "/member/login", "/index", "/css/**", "/js/**", "/", "/api/**").permitAll() 
    //             .anyRequest().authenticated()
    //         )
    //         .formLogin(formLogin -> formLogin
    //             .loginPage("/member/login")
    //             .loginProcessingUrl("/member/login")
    //             .permitAll()
    //         )
    //         .logout(logout -> logout
    //             .logoutUrl("/member/logout")
    //             .logoutSuccessUrl("/index") 
    //             .permitAll()
    //         );
    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/member/signup", "/member/login", "/index", "/css/**", "/js/**", "/", "/api/**").permitAll() 
                .anyRequest().authenticated()
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("{\"error\":\"Unauthorized\"}");
                })
            )
            .addFilterBefore(new JsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout
                .logoutUrl("/api/member/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().write("{\"message\":\"Logout successful\"}");
                })
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager(null, userDetailsService, null));
        filter.setFilterProcessesUrl("/api/member/login");
        return filter;
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            System.out.println("로그인 성공");
            response.sendRedirect("/");
        };
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            System.out.println("로그인 실패: " + exception.getMessage());
            response.sendRedirect("/member/login?error");
        };
    }
    
}