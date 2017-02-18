package repositories;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Contract;


@Repository
@Dependent
public abstract class ContractRepository extends AbstractEntityRepository<Contract, Long> {
	
}