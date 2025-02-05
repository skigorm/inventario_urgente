package br.com.devolucao.backend.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://devolucao-rapida-dm4tez1mp-igor-machados-projects-f913e8fa.vercel.app",
                                "devolucao-rapida-at7iofs1n-igor-machados-projects-f913e8fa.vercel.app/:1 Access to XMLHttpRequest at 'https://teste-back-reversa-11.onrender.com/auth/login",
                                "https://devolucao-rapida-at7iofs1n-igor-machados-projects-f913e8fa.vercel.app/",
                                "https://devolucao-rapida-web.vercel.app",
                                "https://inventario-web-m8g3.vercel.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
