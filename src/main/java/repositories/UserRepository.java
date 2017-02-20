package repositories;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.User;

@Repository
@Dependent
public abstract class UserRepository extends AbstractEntityRepository<User, Long> {
	
	public abstract User findByEmail(String mail);

}
