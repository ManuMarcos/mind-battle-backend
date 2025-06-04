package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); //Para suscribirse
        config.setApplicationDestinationPrefixes("/app"); //Para enviar
        config.setUserDestinationPrefix("/user");//Donde el usuario recibe
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/games-ws")//Endpoint al que se conectan los clientes
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

}
