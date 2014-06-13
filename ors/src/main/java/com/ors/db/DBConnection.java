package com.ors.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static Connection conn = null;
	private static Properties props = null;

	static {
		props = new Properties();
		try {
			props.load(DBConnection.class
					.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Class.forName(props.getProperty("driverClass"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		try {
			conn = DriverManager.getConnection(props.getProperty("url"),
					props.getProperty("username"),
					props.getProperty("password"));
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConn() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}