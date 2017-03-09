package repositories;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Player;
import entities.Team;

@Repository
@Dependent
public abstract class TeamRepository extends AbstractEntityRepository<Team, Long> {
	
	public abstract List<Team> findByAgeGreaterThanEqualsOrderByNameAsc(int age);
	public abstract List<Team> findByAgeGreaterThan(int age);
	public abstract Team findByEmail(String mail);
	public abstract List<Team>  findByNameLike(String param);
}
