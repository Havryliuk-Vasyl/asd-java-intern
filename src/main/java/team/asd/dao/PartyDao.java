package team.asd.dao;

import team.asd.entity.Party;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartyDao {
	public Party readById(int id) {
		String query = "SELECT * FROM party WHERE id = ?";
		try (Connection connection = DatabaseManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return new Party(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("state"), resultSet.getString("postal_address"),
						resultSet.getString("email_address"), resultSet.getString("mobile_phone"), resultSet.getString("password"),
						resultSet.getString("currency"), resultSet.getString("user_type"), resultSet.getTimestamp("version"));
			}
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
		return null;
	}

	public void create(Party party) {
		String query = "INSERT INTO party (name, state, postal_address, email_address, mobile_phone, password, currency, user_type, version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DatabaseManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, party.getName());
			preparedStatement.setString(2, party.getState());
			preparedStatement.setString(3, party.getPostalAddress());
			preparedStatement.setString(4, party.getEmailAddress());
			preparedStatement.setString(5, party.getMobilePhone());
			preparedStatement.setString(6, party.getPassword());
			preparedStatement.setString(7, party.getCurrency());
			preparedStatement.setString(8, party.getUserType());
			preparedStatement.setTimestamp(9, party.getVersion());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error" + e.getMessage());
		}
	}

	public void update(Party party) {
		String query = "UPDATE party SET name = ?, state = ?, postal_address = ?, email_address = ?, "
				+ "mobile_phone = ?, password = ?, currency = ?, user_type = ?, version = ? WHERE id = ?";
		try (Connection connection = DatabaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, party.getName());
			preparedStatement.setString(2, party.getState());
			preparedStatement.setString(3, party.getPostalAddress());
			preparedStatement.setString(4, party.getEmailAddress());
			preparedStatement.setString(5, party.getMobilePhone());
			preparedStatement.setString(6, party.getPassword());
			preparedStatement.setString(7, party.getCurrency());
			preparedStatement.setString(8, party.getUserType());
			preparedStatement.setTimestamp(9, party.getVersion());
			preparedStatement.setInt(10, party.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error" + e.getMessage());
		}
	}

	public void delete(Party party) {
		String query = "DELETE FROM party WHERE id = ?";
		try (Connection connection = DatabaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setInt(1, party.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error" + e.getMessage());
		}
	}

}
