package team.asd.dao;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import team.asd.entity.Party;

import java.sql.*;
import java.util.Optional;

@Component
public class PartyDao {
	private Connection connection;

	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM party WHERE id = ?";
	private static final String INSERT_PARTY_QUERY = "INSERT INTO party (name, state, postal_address, email_address, mobile_phone, password, currency, user_type, version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_PARTY_QUERY = "UPDATE party SET name = ?, state = ?, postal_address = ?, email_address = ?, mobile_phone = ?, password = ?, currency = ?, user_type = ?, version = ? WHERE id = ?";
	private static final String DELETE_PARTY_QUERY = "DELETE FROM party WHERE id = ?";

	public Optional<Party> readById(int id) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(mapResultSetToParty(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while reading Party by ID: " + id, e);
		}
		return Optional.empty();
	}

	public void create(Party party) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PARTY_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			setPartyPreparedStatement(party, preparedStatement);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating Party failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					party.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating Party failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while creating Party: " + party, e);
		}
	}

	public void update(Party party) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PARTY_QUERY)) {

			setPartyPreparedStatement(party, preparedStatement);
			preparedStatement.setInt(10, party.getId());
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Updating Party failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while updating Party: " + party, e);
		}
	}

	public void delete(int id) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PARTY_QUERY)) {

			preparedStatement.setInt(1, id);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Deleting Party failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while deleting Party with ID: " + id, e);
		}
	}

	private Party mapResultSetToParty(ResultSet resultSet) throws SQLException {
		return new Party(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("state"), resultSet.getString("postal_address"),
				resultSet.getString("email_address"), resultSet.getString("mobile_phone"), resultSet.getString("password"), resultSet.getString("currency"),
				resultSet.getString("user_type"), resultSet.getTimestamp("version"));
	}

	private void setPartyPreparedStatement(Party party, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, party.getName());
		preparedStatement.setString(2, party.getState());
		preparedStatement.setString(3, party.getPostalAddress());
		preparedStatement.setString(4, party.getEmailAddress());
		preparedStatement.setString(5, party.getMobilePhone());
		preparedStatement.setString(6, party.getPassword());
		preparedStatement.setString(7, party.getCurrency());
		preparedStatement.setString(8, party.getUserType());
		preparedStatement.setTimestamp(9, party.getVersion());
	}

	@PreDestroy
	private void closeConnection() throws SQLException {
		connection.close();
	}
}
