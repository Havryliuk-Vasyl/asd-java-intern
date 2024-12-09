package team.asd.dao;

import team.asd.entity.Party;

public interface PartyDao {
	Party readById(int id);

	void create(Party party);

	void update(Party party);

	void delete(Party party);
}
