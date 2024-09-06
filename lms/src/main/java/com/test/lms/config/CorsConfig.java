package com.test.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 허용할 origin 설정 (여기서는 React 앱의 주소)
        config.addAllowedOrigin("http://localhost:3000");
        
        // 자격 증명 허용 (쿠키 등)
        config.setAllowCredentials(true);
        
        // 허용할 헤더
        config.addAllowedHeader("*");
        
        // 허용할 HTTP 메서드
        config.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
