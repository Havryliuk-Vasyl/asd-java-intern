package team.asd.service;

import team.asd.dao.PartyDao;
import team.asd.entity.Party;

public class PartyService {
	private final PartyDao partyDao;

	public PartyService(PartyDao partyDao) {
		this.partyDao = partyDao;
	}

	public Party readById(int id) {
		if (id > 0) {
			return partyDao.readById(id);
		}

		return null;
	}

	public void create(Party party) {
		if (party != null) {
			partyDao.create(party);
		}
	}

	public void update(Party party) {
		if (party != null) {
			partyDao.update(party);
		}
	}

	public void delete(Party party) {
		if (party != null) {
			partyDao.delete(party);
		}
	}
}