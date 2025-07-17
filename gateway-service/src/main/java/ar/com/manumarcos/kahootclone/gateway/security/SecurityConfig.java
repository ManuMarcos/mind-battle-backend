package ar.com.manumarcos.kahootclone.gateway.security;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @PostConstruct
    public void init() {
        System.out.println("SecurityConfig cargado correctamente");
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/sessions/*/start").authenticated()
                        .pathMatchers(HttpMethod.POST, "/api/quizzes/**").authenticated()
                        .pathMatchers("/api/auth/**", "/games-ws/**", "/api/sessions/**").permitAll()
                        .pathMatchers("/v3/api-docs/**","/docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
