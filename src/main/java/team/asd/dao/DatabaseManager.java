package team.asd.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseManager {
	@Value("${db.url}")
	private static String url;

	@Value("${db.user}")
	private static String user;

	@Value("${db.password}")
	private static String password;

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
}
