package com.baverage.backend.MqttPaho;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import java.util.UUID;

public class Mqtt {
	
	static String message= "";
	static MqttClient client;

	public Mqtt() throws MqttSecurityException, MqttException {
		String message2 = getMessage();
		
	}
	
	public static String getMessage() throws MqttSecurityException, MqttException {
		
	client = new MqttClient(
			"tcp://192.168.0.107", UUID.randomUUID().toString());

			client.setCallback(new MqttCallback() {
			       public void connectionLost(Throwable throwable) { }
				
				public void messageArrived(String t, MqttMessage m) throws Exception {
					System.out.println("Erster String" + t);
					message= new String(m.getPayload());
                                        System.out.println(new String(m.getPayload()));
					transferDataToDatabase("massBavarage", message);
			       }

			       public void deliveryComplete(IMqttDeliveryToken t) { };
			});

			client.connect();

			client.subscribe("massBavarage");
			
			return message;
	}
	
	public static void transferDataToDatabase(String topic, String message) throws MqttException{
		System.out.println("Hallo bin in der methode trandfer");
		switch (topic) {
			case "massBavarage":
                                // mac, rfid, gewicht -> mac -> platz -> bestellung -> neuen Messpunkt -> Gewicht
                                // Rechnen
				System.out.println("Komme ich hier an?");
				int valOfComma= message.indexOf(",");
				System.out.println("Bin nach Index");
				
				String rfid=message.substring(0, valOfComma);
				System.out.println("Nach Substring");
				int mass=Integer.parseInt(message.substring(valOfComma));
				System.out.println("Nach Substring mass");
				System.out.println("RFID: "+ rfid);
				System.out.println("mass: "+ mass);
				// send this via Repo to Database
				break;
			case "aliveMessage":
				System.out.println("alive");
				// send this via Repo to Database
				break;
	        default:
	            System.out.println("Kein Topic angegeben");
	            break;
		}
		client.disconnect();
		System.out.println("Am Ende");
	}

	private String generateClientId() {
		// TODO Auto-generated method stub
		return "11";
	}
}
