package com.baverage.backend;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baverage.backend.DatabaseConnection.DBConnection;
import com.baverage.backend.MqttPaho.Mqtt;

@SpringBootApplication
public class BackendApplication {

        private static final Logger LOGGER = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(BackendApplication.class, args);
                /*
		DBConnection db = new DBConnection();
		db.connectToDatabase();
                */
                try {
                    Mqtt mqtt = new Mqtt();
                } catch (Exception e) {
                    LOGGER.error(e.toString());
                }

	}

}
