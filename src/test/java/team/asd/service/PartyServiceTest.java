package team.asd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.asd.dao.PartyDao;
import team.asd.entity.Party;

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
}
