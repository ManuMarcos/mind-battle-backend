package ar.com.manumarcos.kahootclone.microservices.game_session_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // o "*"
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
