package repositories;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import entities.Contract;
import entities.Player;

@Repository
@Dependent
public abstract class ContractRepository extends AbstractEntityRepository<Contract, Long> {
	
	public abstract List<Contract> findBySignedPlayerEqualAndSeenByPlayerEqual(Player player, boolean seen);
	public abstract List<Contract> findBySignedPlayerEqual(Player player);
	@Query("select count(c) from Contract c where c.signedPlayer = ?1 and c.teamAccepted = true and c.playerAccepted = true")
	public abstract long countValidContracts(Player player);
	
}