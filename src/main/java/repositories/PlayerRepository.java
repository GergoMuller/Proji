package repositories;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Player;
import entities.User;

@Repository
@Dependent
public abstract class PlayerRepository extends AbstractEntityRepository<Player, Long> {
	
	public abstract List<Player> findByAgeGreaterThanEqualsOrderByNameAsc(int age);
	public abstract List<Player> findByAgeGreaterThan(int age);
	public abstract Player findByEmail(String mail);
	public abstract List<Player> findByNameLike(String searchParam);
}
