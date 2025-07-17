package ar.com.manumarcos.kahootclone.microservices.game_session_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.security.Principal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Value("${jwt.secret}")
    private String secretKey;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> protectedDestinations = List.of(
            "/session/{gameSessionId}/start"
    );

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accesor = StompHeaderAccessor.wrap(message);
        StompCommand command = accesor.getCommand();
        if (command == null) return message;

        if(command.equals(StompCommand.SEND)){
            String destination = accesor.getDestination();

            boolean isProtected = destination != null &&
                    protectedDestinations.stream().anyMatch(pattern -> pathMatcher.match(pattern, destination));

            if(isProtected){
                String authHeader = accesor.getFirstNativeHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")){
                    throw new IllegalArgumentException("Falta el token JWT para ruta protegida : " + destination);
                }

                try{
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                            .build()
                            .parseClaimsJws(authHeader.substring(7))
                            .getBody();
                    String username = claims.getSubject();
                    accesor.setUser(new Principal() {
                        @Override
                        public String getName() {
                            return username;
                        }
                    });
                }
                catch (Exception e){
                    throw new IllegalArgumentException("JWT invalido: " + e.getMessage());
                }
            }
        }
        return message;
    }

}
