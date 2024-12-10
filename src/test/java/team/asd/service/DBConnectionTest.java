package team.asd.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {

	@Test
	void connectionTest() {
		Assertions.assertTrue(connect());
	}

	private boolean connect() {
		String url = "jdbc:mysql://185.126.115.194:3306/asd";
		String user = "dev";
		String password = "testP@55w0rd";

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			if (connection != null) {
				System.out.println("Success DB connection");
				return true;
			} else {
				System.out.println("Failed to make connection");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("Connection failed: " + e.getMessage());
			return false;
		}
	}
}
