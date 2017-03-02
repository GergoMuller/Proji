package repositories;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import entities.Contract;
import entities.Player;

@Repository
@Dependent
public abstract class ContractRepository extends AbstractEntityRepository<Contract, Long> {
	
	public abstract List<Contract> findBySignedPlayerEqualAndSeenByPlayerEqual(Player player, boolean seen);
	public abstract List<Contract> findBySignedPlayerEqual(Player player);
	
}