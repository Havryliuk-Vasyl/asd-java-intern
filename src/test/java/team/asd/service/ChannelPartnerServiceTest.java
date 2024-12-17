package team.asd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import team.asd.dao.ChannelPartnerDao;
import team.asd.entity.ChannelPartner;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChannelPartnerServiceTest {

	@InjectMocks
	private ChannelPartnerService channelPartnerService;

	@Mock
	private ChannelPartnerDao channelPartnerDao;

	@BeforeEach
	void setUp() {
		channelPartnerDao = Mockito.mock(ChannelPartnerDao.class);
		channelPartnerService = new ChannelPartnerService(channelPartnerDao);
	}

	@Test
	void testReadById_ValidId() {
		ChannelPartner channelPartner = new ChannelPartner(1, 100, "TP", "Test Partner", "Active", 10.5, 5.0, true, new Timestamp(System.currentTimeMillis()));
		when(channelPartnerDao.readById(1)).thenReturn(Optional.of(channelPartner));

		ChannelPartner result = channelPartnerService.readById(1);

		assertNotNull(result);
		assertEquals("Test Partner", result.getChannelName());
		verify(channelPartnerDao, times(1)).readById(1);
	}

	@Test
	void testReadById_InvalidId() {
		when(channelPartnerDao.readById(99)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> channelPartnerService.readById(99));
		verify(channelPartnerDao, times(1)).readById(99);
	}

	@Test
	void testCreate_ValidChannelPartner() {
		ChannelPartner channelPartner = new ChannelPartner(0, 100, "NP", "New Partner", "Active", 15.0, 7.5, false, new Timestamp(System.currentTimeMillis()));

		channelPartnerService.create(channelPartner);

		verify(channelPartnerDao, times(1)).create(channelPartner);
	}

	@Test
	void testCreate_NullChannelPartner() {
		assertThrows(IllegalArgumentException.class, () -> channelPartnerService.create(null));
		verify(channelPartnerDao, never()).create(any());
	}

	@Test
	void testUpdate_ExistingChannelPartner() {
		ChannelPartner channelPartner = new ChannelPartner(1, 101, "UP", "Updated Partner", "Active", 12.0, 6.0, true, new Timestamp(System.currentTimeMillis()));

		channelPartnerService.update(channelPartner);

		verify(channelPartnerDao, times(1)).update(channelPartner);
	}

	@Test
	void testUpdate_NullChannelPartner() {
		assertThrows(IllegalArgumentException.class, () -> channelPartnerService.update(null));
		verify(channelPartnerDao, never()).update(any());
	}

	@Test
	void testDelete_ValidId() {
		int validId = 1;

		channelPartnerService.delete(validId);

		verify(channelPartnerDao, times(1)).delete(validId);
	}

	@Test
	void testDelete_InvalidId() {
		assertThrows(IllegalArgumentException.class, () -> channelPartnerService.delete(-1));
		verify(channelPartnerDao, never()).delete(anyInt());
	}
}
