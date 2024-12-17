package team.asd.service;

import org.springframework.stereotype.Service;
import team.asd.dao.ChannelPartnerDao;
import team.asd.entity.ChannelPartner;

@Service
public class ChannelPartnerService {
	private final ChannelPartnerDao channelPartnerDao;

	public ChannelPartnerService(ChannelPartnerDao channelPartnerDao) {
		this.channelPartnerDao = channelPartnerDao;
	}

	public ChannelPartner readById(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than 0");
		}

		try {
			return channelPartnerDao.readById(id)
					.orElseThrow(() -> new RuntimeException("ChannelPartner with ID " + id + " not found"));
		} catch (RuntimeException e) {
			System.err.println("Error in readById: " + e.getMessage());
			throw e;
		}
	}

	public void create(ChannelPartner channelPartner) {
		if (channelPartner == null) {
			throw new IllegalArgumentException("ChannelPartner cannot be null");
		}

		try {
			channelPartnerDao.create(channelPartner);
		} catch (RuntimeException e) {
			System.err.println("Error in create: " + e.getMessage());
			throw e;
		}
	}

	public void update(ChannelPartner channelPartner) {
		if (channelPartner == null || channelPartner.getId() <= 0) {
			throw new IllegalArgumentException("ChannelPartner or its ID cannot be null/invalid");
		}

		try {
			channelPartnerDao.update(channelPartner);
		} catch (RuntimeException e) {
			System.err.println("Error in update: " + e.getMessage());
			throw e;
		}
	}

	public void delete(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than 0");
		}

		try {
			channelPartnerDao.delete(id);
		} catch (RuntimeException e) {
			System.err.println("Error in delete: " + e.getMessage());
			throw e;
		}
	}
}
