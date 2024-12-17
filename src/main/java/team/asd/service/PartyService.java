package team.asd.service;

import org.springframework.stereotype.Service;
import team.asd.dao.PartyDao;
import team.asd.entity.Party;

@Service
public class PartyService {
	private final PartyDao partyDao;

	public PartyService(PartyDao partyDao) {
		this.partyDao = partyDao;
	}

	public Party readById(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than 0");
		}

		try {
			return partyDao.readById(id)
					.orElseThrow(() -> new RuntimeException("Party with ID " + id + " not found"));
		} catch (RuntimeException e) {
			System.err.println("Error in readById: " + e.getMessage());
			throw e;
		}
	}

	public void create(Party party) {
		if (party == null) {
			throw new IllegalArgumentException("Party cannot be null");
		}

		try {
			partyDao.create(party);
		} catch (RuntimeException e) {
			System.err.println("Error in create: " + e.getMessage());
			throw e;
		}
	}

	public void update(Party party) {
		if (party == null || party.getId() <= 0) {
			throw new IllegalArgumentException("Party or its ID cannot be null/invalid");
		}

		try {
			partyDao.update(party);
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
			partyDao.delete(id);
		} catch (RuntimeException e) {
			System.err.println("Error in delete: " + e.getMessage());
			throw e;
		}
	}
}
