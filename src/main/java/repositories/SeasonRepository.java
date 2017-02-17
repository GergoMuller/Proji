package repositories;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Player;
import entities.Season;

@Repository
@Dependent
public abstract class SeasonRepository extends AbstractEntityRepository<Season, Long> {
	
}
