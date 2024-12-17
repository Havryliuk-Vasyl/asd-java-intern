package team.asd.dao;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import team.asd.entity.ChannelPartner;

import java.sql.*;
import java.util.Optional;

@Component
public class ChannelPartnerDao {
	private final Connection connection;

	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM channel_partner WHERE id = ?";
	private static final String INSERT_CHANNEL_PARTNER_QUERY = "INSERT INTO channel_partner (party_id, abbreviation, channel_name, state, commission, bp_commission, funds_holder, version) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_CHANNEL_PARTNER_QUERY = "UPDATE channel_partner SET party_id = ?, abbreviation = ?, channel_name = ?, state = ?, commission = ?, bp_commission = ?, funds_holder = ?, version = ? WHERE id = ?";
	private static final String DELETE_CHANNEL_PARTNER_QUERY = "DELETE FROM channel_partner WHERE id = ?";

	public ChannelPartnerDao(Connection connection) {
		this.connection = connection;
	}

	public Optional<ChannelPartner> readById(int id) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(mapResultSetToChannelPartner(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while reading ChannelPartner by ID: " + id, e);
		}
		return Optional.empty();
	}

	public void create(ChannelPartner channelPartner) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHANNEL_PARTNER_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			setChannelPartnerPreparedStatement(channelPartner, preparedStatement);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating ChannelPartner failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					channelPartner.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating ChannelPartner failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while creating ChannelPartner: " + channelPartner, e);
		}
	}

	public void update(ChannelPartner channelPartner) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CHANNEL_PARTNER_QUERY)) {

			setChannelPartnerPreparedStatement(channelPartner, preparedStatement);
			preparedStatement.setInt(9, channelPartner.getId());
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Updating ChannelPartner failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while updating ChannelPartner: " + channelPartner, e);
		}
	}

	public void delete(int id) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CHANNEL_PARTNER_QUERY)) {

			preparedStatement.setInt(1, id);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Deleting ChannelPartner failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while deleting ChannelPartner with ID: " + id, e);
		}
	}

	private ChannelPartner mapResultSetToChannelPartner(ResultSet resultSet) throws SQLException {
		return new ChannelPartner(resultSet.getInt("id"), resultSet.getInt("party_id"), resultSet.getString("abbreviation"),
				resultSet.getString("channel_name"), resultSet.getString("state"), resultSet.getDouble("commission"), resultSet.getDouble("bp_commission"),
				resultSet.getBoolean("funds_holder"), resultSet.getTimestamp("version"));
	}

	private void setChannelPartnerPreparedStatement(ChannelPartner channelPartner, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, channelPartner.getPartyId());
		preparedStatement.setString(2, channelPartner.getAbbreviation());
		preparedStatement.setString(3, channelPartner.getChannelName());
		preparedStatement.setString(4, channelPartner.getState());
		preparedStatement.setObject(5, channelPartner.getCommission());
		preparedStatement.setDouble(6, channelPartner.getBpCommission());
		preparedStatement.setBoolean(7, channelPartner.isFundsHolder());
		preparedStatement.setTimestamp(8, channelPartner.getVersion());
	}

	@PreDestroy
	private void closeConnection() throws SQLException {
		connection.close();
	}
}
