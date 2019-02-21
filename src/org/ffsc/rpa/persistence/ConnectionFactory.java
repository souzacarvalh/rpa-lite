package org.ffsc.rpa.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.ffsc.rpa.util.PropertiesFacade;

public class ConnectionFactory {

	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DERBY_DRIVER = "";
	
	public enum SGDB{ MYSQL, APACHE_DERBY }

	public static Connection getConnection(SGDB bd)
			throws ClassNotFoundException, SQLException {

		String database = PropertiesFacade.getProperty("database.server");
		String user     = PropertiesFacade.getProperty("database.user");
		String password = PropertiesFacade.getProperty("database.password");
		
		switch (bd) {
			case MYSQL:
				Class.forName(MYSQL_DRIVER);
				break;
			case APACHE_DERBY:
				Class.forName(DERBY_DRIVER);
				break;
			default:
				return null;
		}

		return DriverManager.getConnection(database, user, password);
	}
}