package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

@Stateless
public class ContractService {
	
	@Inject
	private ContractRepository contractRepo;
	@Inject
	private TeamRepository teamRepo;
	@Inject
	private PlayerRepository playerRepo;
	
	public void saveContract(Contract newContract, String signingPlayerEmail, String valid, String amount)
			throws ParseException, NoResultException, NumberFormatException{
		
		newContract.setSignedPlayer(playerRepo.findByEmail((signingPlayerEmail)));
		newContract.setAmount(Double.parseDouble(amount));
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		Date validDate;
		validDate = df.parse(valid);
		newContract.setValidDate(validDate);
		contractRepo.save(newContract);
	}
	
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
