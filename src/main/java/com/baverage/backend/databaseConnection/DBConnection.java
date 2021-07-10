package com.baverage.backend.databaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	
	public  DriverManager db;
	
	
//	private String url = "jdbc:mariadb://localhost:3306/vehicleDatabase";
//	private String user = "root";
//	private String password = "";
	
	
	public void connectToDatabase() throws SQLException, ClassNotFoundException {
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection con=DriverManager.getConnection(
//		"jdbc:mysql://localhost:3306/","root","1234");
//		
//		String schema= con.getSchema();
//		System.out.println(schema);
	}
	//DriverManager.getConnection(url,user,password);
}
