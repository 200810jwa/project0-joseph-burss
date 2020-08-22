package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * The Connection utility class that is designed to obtain a Connection to the database
 * is often structured as a Singleton
 */
public class ConnectionUtil {

	private static Connection conn = null;
	
	private ConnectionUtil() {
		super();
	}
	
	public static Connection getConnection() {
		try {
			if(conn != null && !conn.isClosed()) {
				return conn;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("WE FAILED TO REUSE A CONNECTION");
			return null;
		}
		
		// In order to obtain a Connection
		// We will need the credentials to our DB
		// as well as the location of our DB
		// The location of the DB is represented as a "Connection String"
		// This includes information such as what SQL driver we are using,
		// what the ip address is, what the port is,
		// and what the database name is
		
		// The driver is going to be based on postgres, and it will specifically be
		// "jdbc:postgresql:"
		
		// jdbc:postgresql://host_name:port/DB_name
		// String url = "jdbc:postgresql://revature-training.ct8kaamvwwus.us-east-2.rds.amazonaws.com:5432/jwa200810";
		
		// We directly have our username and password of our database written directly in our source code
		// As soon as I push this to GitHub, we have exposed our credentials to the world
		// Not very ideal
		//String username = "root";
		//String password = "myPassword";
		
		// The better solution is to store all of this information: url, username, and password
		// in environment variables on the computer
		
		// Then inside Java we can access the environment variables
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		
		// Additional note: If you modify your environment variables, you must restart STS
		
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("WE FAILED TO GET A CONNECTION!");
			return null;
		}
		
		return conn;
	}
}