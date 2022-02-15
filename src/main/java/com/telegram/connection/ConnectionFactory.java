package com.telegram.connection;

import java.sql.Connection;
import java.sql.DriverManager;

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
			Class.forName("org.sqlite.JDBC");
			// DriverManager.registerDriver(new JDBC());

			conn = DriverManager.getConnection("jdbc:sqlite:" + DataBaseUtils.i().getDatabasePath());
		}

		return conn;
	}
}
