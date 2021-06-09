package com.baverage.backend;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import DatabaseConnection.DBConnection;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(BackendApplication.class, args);
		DBConnection db= new DBConnection();
		db.connectToDatabase();

	}

}
