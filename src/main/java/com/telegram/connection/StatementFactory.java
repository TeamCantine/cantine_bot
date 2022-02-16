package com.telegram.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementFactory {
	public static Statement newReadOnlyStatement() throws Exception {
		return getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	}

	public static Statement newReadOnlyStatement(int resultSetType) throws Exception {
		return getConnection().createStatement(resultSetType, ResultSet.CONCUR_READ_ONLY);
	}

	public static Statement newStatement() throws Exception {
		return getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	}

	public static PreparedStatement newPreparedStatement(String s) throws Exception {
		return getConnection().prepareStatement(s);
	}

	public static PreparedStatement newReadOnlyPreparedStatement(String s) throws Exception {
		return getConnection().prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	}

	public static PreparedStatement newReadOnlyStreamStatement(String s) throws Exception {
		return getConnection().prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	}

	/**
	 * Crea un prepared statement che consente di farsi restituire le chiavi generate
	 * 
	 * @param s
	 *            stringa sql
	 * @return
	 * @throws SQLException
	 */
	public static PreparedStatement newRetrievablePreparedStatement(String s) throws Exception {
		return getConnection().prepareStatement(s, Statement.RETURN_GENERATED_KEYS);
	}

	public static Connection getConnection() throws Exception {

		return ConnectionFactory.getConnection();
	}

	/**
	 * chiude statement e resultSet senza lanciare eccezioni
	 * 
	 * @param s
	 */
	public static void close(ResultSet result, Statement statement) {
		close(result);
		close(statement);
	}

	/**
	 * chiude gli statement senza lanciare eccezioni
	 * 
	 * @param s
	 */
	public static void close(Statement... statements) {
		for (Statement statement : statements) {
			try {
				statement.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * chiude i resultSet senza lanciare eccezioni
	 * 
	 * @param s
	 */
	public static void close(ResultSet... results) {
		for (ResultSet result : results) {
			try {
				result.close();
			} catch (Exception e) {
			}
		}
	}
}
