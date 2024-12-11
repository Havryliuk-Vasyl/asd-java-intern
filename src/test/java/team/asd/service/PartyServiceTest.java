package team.asd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.asd.dao.PartyDao;
import team.asd.entity.Party;

import java.sql.Timestamp;

class PartyServiceTest {

	private PartyService partyService;
	private PartyDao partyDao;

	@BeforeEach
	void setUp() {
		partyService = new PartyService(partyDao);
	}

	@Test
	void testReadById_ValidId() {

	}

	@Test
	void testCreate_Party(){
		Party party = new Party(0, "name", "state", "postalAddress", "emailAddress", "mobilePhone", "password", "currency", "userType", )
	}
}
