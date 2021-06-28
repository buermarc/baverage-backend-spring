package com.baverage.backend.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class MyTextWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyTextWebSocketHandler.class);
    public static String lastRfid = "NORFIDYET";

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private String mqttServerAddress = "tcp://127.0.0.1:1883";

    private MqttClient client = null;

    MqttClient initClient() throws MqttException {

        LOGGER.error("Server name that was loaded is: {}", mqttServerAddress);
        client = new MqttClient(mqttServerAddress, MqttClient.generateClientId());
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception {
                // Expect csv
                String response = new String(m.getPayload()).trim();
                /*
                String[] splittedResponse = response.split(",");
                if (splittedResponse.length != 2) {
                    LOGGER.error("Received read with invalid layou, msg: {}", response);
                    return;
                }
                */
                response = response.trim();
                final String msg = response;
                lastRfid = response;
                sessions.forEach(webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(new TextMessage(msg));
                    } catch (IOException e) {
                        LOGGER.error("Error occurred.", e);
                    }
                });

                /*
                WebSocketSession webSocketSession = sessions.stream()
                        .filter(sessionX -> splittedResponse[0].equals(sessionX.getId())).findAny().orElse(null);

                if (webSocketSession == null) {
                    LOGGER.error("WebSocketSession with the id: {} was not found", splittedResponse[0]);
                    return;
                }
                webSocketSession.sendMessage(new TextMessage(splittedResponse[1]));
                */
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken t) {
            }
        });

        client.connect();

        client.subscribe("rfidTags");
        return client;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        super.afterConnectionEstablished(session);
        try {
            if (this.client == null) {
                this.client = initClient();
            } else {
                if (!this.client.isConnected()) {
                    this.client.connect();
                }
            }
        } catch (MqttException e) {
            LOGGER.error("Found MqttException: {}", e.toString());
            LOGGER.error("Probably server down");
            return;
        }

        MqttMessage message = new MqttMessage(session.getId().getBytes());
        client.publish("rfid_request", message);
        LOGGER.debug("Published rfid request '{}' to topic 'rfid_request'", message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // We should not close the whole mqtt connection each time any websocket disconnects
        // this.client.disconnect();
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        sessions.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                LOGGER.error("Error occurred.", e);
            }
        });
    }
}
