package repositories;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Player;

@Repository
@Dependent
public abstract class PlayerRepository extends AbstractEntityRepository<Player, Long> {
	
}
