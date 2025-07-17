package ar.com.manumarcos.kahootclone.gateway.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtDecoderConfig {

    @Value("${jwt.secret}")
    private String secretBase64;

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder(){
        byte[] secretBytes = Base64.getDecoder().decode(secretBase64);
        SecretKeySpec secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.toString());
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }
}
