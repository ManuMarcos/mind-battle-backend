package ar.com.manumarcos.kahootclone.gateway.security;

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

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/authorized", "/logout").permitAll()
                        .pathMatchers("/api/users").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oaut2 -> oaut2.jwt(Customizer.withDefaults()))
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}
