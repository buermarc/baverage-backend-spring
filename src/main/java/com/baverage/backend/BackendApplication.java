package com.baverage.backend;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import com.baverage.backend.databaseConnection.DBConnection;
import com.baverage.backend.MqttPaho.Mqtt;

@SpringBootApplication
public class BackendApplication {

        private static final Logger LOGGER = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(BackendApplication.class, args);
	}

}
