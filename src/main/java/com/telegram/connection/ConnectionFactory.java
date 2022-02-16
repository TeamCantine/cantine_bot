package com.telegram.connection;

import com.telegram.security.PropertiesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {

	private static Connection conn = null;

	private static class SingletonHolder {
		private static final ConnectionFactory INSTANCE = new ConnectionFactory();
	}

	public static ConnectionFactory i() {
		return SingletonHolder.INSTANCE;
	}

	public static Connection getConnection() throws Exception {

		if (conn == null) {
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
			Properties prop = new Properties();
			prop.setProperty("user", PropertiesHelper.getDatabaseUser());
			prop.setProperty("password", PropertiesHelper.getDatabasePassword());
			conn = DriverManager.getConnection("jdbc:as400://" + PropertiesHelper.getDatabaseAddress(), prop);
		}

		return conn;
	}
}
