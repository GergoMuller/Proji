package repositories;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Match;
import entities.Player;

@Repository
@Dependent
public abstract class MatchRepository extends AbstractEntityRepository<Match, Long> {
	
}