package com.baverage.backend.webSocketConfig;

import com.baverage.backend.handler.MyTextWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Value("${mqtt.server.address}")
    private String mqttServerAddress;

    @Value("${mqtt.server.port}")
    private String mqttServerPort;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new MyTextWebSocketHandler(mqttServerAddress, mqttServerPort), "/web-socket");
    }
}
