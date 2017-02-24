package repositories;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.City;

@Repository
public interface CityRepository extends EntityRepository<City,Long>{
	
}
