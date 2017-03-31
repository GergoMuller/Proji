package repositories;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.League;

@Repository
public abstract class LeagueRepository extends AbstractEntityRepository<League, Long> {
	public abstract League findByNameEqual(String leagueName);
}
