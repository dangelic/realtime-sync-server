package com.dangelic.realtimesyncserver.config;

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
        config.enableSimpleBroker("/topic"); // Enable a simple in-memory broker
        config.setApplicationDestinationPrefixes("/app"); // Prefix for application destination
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // TODO: Configure CORS for Wildcard (*) usage!
        //         registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/ws").setAllowedOrigins("http://127.0.0.1:5500"); // WebSocket connection endpoint
    }
}
