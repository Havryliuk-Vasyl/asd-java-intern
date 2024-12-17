package team.asd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import team.asd.dao.PartyDao;
import team.asd.entity.Party;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartyServiceTest {

	@InjectMocks
	private PartyService partyService;

	@Mock
	private PartyDao partyDao;

	@BeforeEach
	void setUp() {
		partyDao = Mockito.mock(PartyDao.class);
		partyService = new PartyService(partyDao);
	}

	@Test
	void testReadById_ValidId() {
		Party party = new Party(1, "Test Name", "Active", "123 Street", "test@test.com", "123456789", "password123", "USD", "Admin",
				new Timestamp(System.currentTimeMillis()));
		when(partyDao.readById(1)).thenReturn(Optional.of(party));

		Party result = partyService.readById(1);

		assertNotNull(result);
		assertEquals("Test Name", result.getName());
		verify(partyDao, times(1)).readById(1);
	}

	@Test
	void testReadById_InvalidId() {
		when(partyDao.readById(99)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> partyService.readById(99));
		verify(partyDao, times(1)).readById(99);
	}

	@Test
	void testCreate_ValidParty() {
		Party party = new Party(null, "Test Name", "Active", "123 Street", "test@test.com", "123456789", "password123", "USD", "Admin",
				new Timestamp(System.currentTimeMillis()));

		partyService.create(party);

		verify(partyDao, times(1)).create(party);
	}

	@Test
	void testCreate_NullParty() {
		assertThrows(IllegalArgumentException.class, () -> partyService.create(null));
		verify(partyDao, never()).create(any());
	}

	@Test
	void testUpdate_ExistingParty() {
		Party party = new Party(1, "Test Name", "Active", "123 Street", "test@test.com", "123456789", "password123", "USD", "Admin",
				new Timestamp(System.currentTimeMillis()));

		partyService.update(party);

		verify(partyDao, times(1)).update(party);
	}

	@Test
	void testUpdate_NullParty() {
		assertThrows(IllegalArgumentException.class, () -> partyService.update(null));
		verify(partyDao, never()).update(any());
	}

	@Test
	void testDelete_ValidId() {
		int validId = 1;

		partyService.delete(validId);

		verify(partyDao, times(1)).delete(validId);
	}

	@Test
	void testDelete_InvalidId() {
		assertThrows(IllegalArgumentException.class, () -> partyService.delete(-1));
		verify(partyDao, never()).delete(anyInt());
	}
}
