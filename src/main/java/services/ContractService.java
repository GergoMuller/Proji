package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import entities.Contract;
import entities.Player;
import repositories.ContractRepository;
import repositories.TeamRepository;

@Stateless
public class ContractService {
	
	@Inject
	private ContractRepository contractRepo;
	@Inject
	private TeamRepository teamRepo;
	
	
	public Contract getContractById(Long id){
		return contractRepo.findBy(id);
	}
	
	public String getTeamNameToContract(Long teamId){
		return teamRepo.findBy(teamId).getName();
	}
	
	public List<Contract> getPlayersContracts(Player player){
		return contractRepo.findBySignedPlayerEqual(player);
	}
}
