package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import entities.Contract;
import entities.Player;
import repositories.ContractRepository;
import repositories.PlayerRepository;
import repositories.TeamRepository;
import utilities.InvalidContractOperationException;

@Stateless
public class ContractService {
	
	@Inject
	private ContractRepository contractRepo;
	@Inject
	private TeamRepository teamRepo;
	@Inject
	private PlayerRepository playerRepo;
	
	public void saveContract(Contract newContract, String signingPlayerEmail) throws NoResultException{ 
			
		newContract.setSignedPlayer(playerRepo.findByEmail((signingPlayerEmail)));
		newContract.setTeamAccepted(true);
		newContract.setSendDate(new Date());
		contractRepo.save(newContract);
	}
	
	public void acceptContract(Contract contract){
		contract.setPlayerAccepted(true);
		if(contractRepo.countValidContracts(contract.getSignedPlayer()) == 0){
			if(contract.isTeamAccepted()){
				Player acceptingPlayer = contract.getSignedPlayer();
				acceptingPlayer.setCurrentTeam(contract.getSignerTeam());
				playerRepo.save(acceptingPlayer);
			}
			contractRepo.save(contract);
			System.out.println("Player signed");
		}
		else{
			System.out.println("You already has a valid contract");
			throw new InvalidContractOperationException("You already have an active");
		}
	}
	
	public void setContractSeen(Contract selectedContract){
		selectedContract.setSeenByPlayer(true);
		contractRepo.save(selectedContract);
	}
	
	public Contract getContractById(Long id){
		return contractRepo.findBy(id);
	}
	
	public String getTeamNameToContract(Long teamId){
		return teamRepo.findBy(teamId).getName();
	}
	
	public List<Contract> getPlayersContracts(Player player){
		List<Contract> contracts = contractRepo.findBySignedPlayerEqual(player);
		contracts.sort((c1,c2) -> c1.getSendDate().compareTo(c2.getSendDate()));
		Collections.reverse(contracts);
		return contracts;
	}
	
	public void rejectContract(Contract rejectedContract){
		rejectedContract.setTeamAccepted(false);
		contractRepo.save(rejectedContract);
	}
}
