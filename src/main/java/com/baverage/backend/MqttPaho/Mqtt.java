package com.baverage.backend.MqttPaho;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.baverage.backend.DatabaseConnection.Bestellungen;
import com.baverage.backend.DatabaseConnection.Glaeser;
import com.baverage.backend.DatabaseConnection.Messpunkte;
import com.baverage.backend.DatabaseConnection.Stati;
import com.baverage.backend.repo.BestellungRepo;
import com.baverage.backend.repo.MesspunktRepo;

@Component
public class Mqtt {

    private static final Logger LOGGER = LoggerFactory.getLogger(Mqtt.class);

    @Autowired
    private BestellungRepo bestellungRepo;

    @Autowired
    private MesspunktRepo messpunktRepo;

    @Value("${mqtt.server.address}")
    private String mqttServerAddress;

    @Value("${mqtt.server.port}")
    private String mqttServerPort;

    private MqttClient client;


    @PostConstruct
    public void createConnection() throws MqttSecurityException, MqttException {

        LOGGER.debug("Registered MQTT Handler");

        client = new MqttClient("tcp://" + this.mqttServerAddress + ":" + this.mqttServerPort, UUID.randomUUID().toString());

        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
            }

            public void messageArrived(String t, MqttMessage m) throws Exception {
                String message = new String(m.getPayload());
                topicHandler("massBeverage", message);
            }

            public void deliveryComplete(IMqttDeliveryToken t) {
            };
        });

        client.connect();
        client.subscribe("massBeverage");
    }

    public void topicHandler(String topic, String message) throws MqttException {
        switch (topic) {
            case "massBeverage":
                // mac, rfid, gewicht -> mac -> platz -> bestellung -> neuen Messpunkt ->
                // Gewicht
                // Rechnen
                String[] parts = message.split(",");
                List<String> partsList = Arrays.asList(parts).stream().map(x -> x.trim()).collect(Collectors.toList());

                if (partsList.size() != 3) {
                    LOGGER.warn(
                            "Received message: '{}', which has not the expected layout 'MAC: STRING , RFID: STRING , MASS: INT'",
                            message);
                    break;
                }

                String mac = partsList.get(0);
                if (mac == null) {
                    LOGGER.warn("mac is null! Failed to take the mac from the received message: '{}'", message);
                    break;
                }
                String rfid = partsList.get(1);
                if (rfid == null) {
                    LOGGER.warn("rfid is null! Failed to take the rfid from the received message: '{}'", message);
                    break;
                }

                int mass = -1;
                try {
                    // Should be save to take 2 because we can assure that the list contains exactly
                    // 3 elements
                    mass = Integer.parseInt(partsList.get(2));
                } catch (Exception e) {
                    LOGGER.warn("Failed to parse an String to INT. Expected {} to be parseable. Exepction received: {}",
                            partsList.get(2), e.toString());
                    break;
                }

                LOGGER.debug("MAC: {}", mac);
                // based on mac we can get the current active order
                LOGGER.debug("RFID: {}", rfid);
                LOGGER.debug("mass: {}", mass);

                Bestellungen bestellung = null;
                try {
                    List<Bestellungen> multBest = new ArrayList<>();
                    multBest.addAll(bestellungRepo.getOrderByMacWhereStatusIn(mac, Stati.Status.BESTELLT.getId(),
                            Stati.Status.VORBEREITET.getId()));
                    if (multBest.size() != 1) {
                        LOGGER.warn(
                                "For one place either null or multiple orders are either ordered or ready, this should not happen. There should be exactly one order that matches the current place but we found '{}'",
                                multBest.size());
                    }
                    bestellung = multBest.get(0);
                    if (bestellung == null) {
                        LOGGER.error("Did not find any matching order");

                        break;
                    }
                } catch (Exception e) {
                    LOGGER.warn("Query failed: {}", e.toString());
                    break;
                }




                double initialgewicht = bestellung.getInitialgewicht();
                double leergewicht = (double) bestellung.getGlas().getLeergewicht();
                double fuellstand = (double) mass / (initialgewicht - leergewicht);

                // Check if the order is set to ordered, if yes set it to delivered
                if (bestellung.getStatus().getId() == Stati.Status.VORBEREITET.getId()) {

                    if (!bestellung.getGlas().getRfid().equals(rfid)) {
                        // We would expect a different bottle based on the rfid
                        // If the rfid we got has the same getrank than exchange the rfid's of both orders
                        // select b from Bestellung b where b.glas.rfid = :rfid and b.status.id in (:status_two, :status_three)
                        // if (rfidBestellung.getraenk.id == bestellung.getraenk.id) {
                        //     Do exchange and save both
                        // }
                        List<Bestellungen> multRfidBest = new ArrayList<>();
                        multRfidBest.addAll(bestellungRepo.getOrderByRfidWhereStatusIn(rfid, Stati.Status.BESTELLT.getId(), Stati.Status.GELIEFERT.getId()));

                        if (multRfidBest.size() != 1) {
                            LOGGER.warn(
                                    "For one place either null or multiple orders are either ordered or ready, this should not happen. There should be exactly one order that matches the current place but we found '{}'",
                                    multRfidBest.size());
                        }
                        // Bestellung -> got by mac -> is safe the bestellung that we expect
                        Bestellungen rfidBestellung = multRfidBest.get(0);
                        if (bestellung.getGetraenk().getId() == rfidBestellung.getGetraenk().getId()) {

                            Glaeser tmpGlas =  bestellung.getGlas();
                            bestellung.setGlas(rfidBestellung.getGlas());
                            rfidBestellung.setGlas(tmpGlas);

                            // Save the swap
                            bestellungRepo.save(bestellung);
                            bestellungRepo.save(rfidBestellung);

                        } else {
                            // When receiving the bottle for the first time on a place:
                            // We received the wrong bottle and it contains a different drink -> This is bad -> Seems like the waiter made a mistake
                            // TODO Send an alert message somewhere to indicate that a bottle has been placed on the wrong place
                            LOGGER.warn("Received a wrong bottle with the wrong beverage in it on place {}", mac);
                            break;
                        }
                    }

                    bestellung.getStatus().setId(Stati.Status.GELIEFERT.getId());
                    bestellung.setZeitpunkt_geliefert(new Date());
                    bestellungRepo.save(bestellung);

                } else {
                    if (!bestellung.getGlas().getRfid().equals(rfid)) {
                        // We already delievered the latest order to the place but somehow meanwhile we received a new bottle that should not be seen, so we just ignore it and do not send a new messwert
                        LOGGER.warn("The bottle rfid encountered on a place that has already been delivered with a different bottle(rfid) somehow changed");
                        break;
                    }

                }

                // If the fuelllstand is less than zero than the messwert must be less than zero. This should not be possible so lets log it.
                if (fuellstand < 0) {
                    LOGGER.warn(
                            "Current fuellstand is less than zero. bestellung.id {}, bestellung.platz.id {}, initialgewicht {},"
                                    + "leergewicht {}, fuellstand {}, messwert {}",
                            bestellung.getId(), bestellung.getPlatz().getId(), initialgewicht, leergewicht, fuellstand,
                            mass);
                }

                Messpunkte messpunkt = new Messpunkte();
                messpunkt.setFuellstand(fuellstand);
                messpunkt.setZeitpunkt(new Date());
                messpunkt.setBestellungen(bestellung);
                messpunktRepo.save(messpunkt);
                LOGGER.debug("Saved messpunkt");
                // send this via Repo to Database
                break;
            case "aliveMessage":
                LOGGER.info("alive");
                // send this via Repo to Database
                break;
            default:
                LOGGER.warn("Given topic: '{}' did not match any topic handlers.", topic);
                break;
        }
    }

    public void disconnect() throws MqttException {
        client.disconnect();
    }
}
