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

public class BarWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarWebSocketHandler.class);

    public static String getLastRfid() {
        return BarWebSocketHandler.lastRfid;
    }

    private static String lastRfid = "NO_RFID_YET";

    private String mqttServerAddress;
    private String mqttServerPort;

    /*
     * Override default constructor to pass the mqtt address
     */
    public BarWebSocketHandler(String mqttServerAddress, String mqttServerPort) {
        super();
        this.mqttServerAddress = mqttServerAddress;
        this.mqttServerPort = mqttServerPort;
    }

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private MqttClient client = null;

    /*
     * Initialize a connection to the MQTT Broker defined at object construction
     */
    MqttClient initClient() throws MqttException {

        LOGGER.debug("Server name that was loaded is: {}:{}", this.mqttServerAddress, this.mqttServerPort);

        client = new MqttClient("tcp://" + this.mqttServerAddress + ":" + this.mqttServerPort,
                MqttClient.generateClientId());
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception {

                // If we receive a message we expect an RFID
                final String response = new String(m.getPayload()).trim();
                LOGGER.debug("Message: '{}' arrived in mqtt with topic: '{}'", t, response);

                lastRfid = response;

                // Broadcast to all open connections, currently we should only have one
                sessions.forEach(webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(new TextMessage(response));
                    } catch (IOException e) {
                        LOGGER.error("Error occurred.", e);
                    }
                });

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

        LOGGER.debug("Establishe a connection via WebSocket with the id {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
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
