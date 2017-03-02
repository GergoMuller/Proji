package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import entities.Contract;
import entities.Player;
import entities.Team;
import repositories.ContractRepository;
import repositories.PlayerRepository;
import repositories.TeamRepository;
import repositories.UserRepository;

@Stateless
public class TeamService {

	@Inject
	TeamRepository teamRepo;
	@Inject
	UserRepository userRepo;
	@Inject
	PlayerRepository playerRepo;
	@Inject
	ContractRepository contractRepo;
	
	
	public Team getTeamByEmail(String email){
		return teamRepo.findByEmail(email);
		
	}
	
	public void saveImage(Team team, byte[] image){
		team.setTeamPicture(image);
		teamRepo.save(team);
	}
	
	public void saveFoundationDate(Team team, Date date){
		team.setFoundedIn(date);
		teamRepo.save(team);
	}
	
	public void signPlayer(Team team, String playerMail){
		try{
			Player signingPlayer = playerRepo.findByEmail(playerMail);
			signingPlayer.setCurrentTeam(team);
			team.getCurrentPlayers().add(signingPlayer);
			playerRepo.saveAndFlush(signingPlayer);
			
			//debug
			System.out.println("sikeres ment�s: "+playerMail);
			List<Player> temp = new ArrayList();
			temp = team.getCurrentPlayers();
			for (Player player : temp) {
				System.out.println(player.getName());
			}
		}catch(NoResultException ex){
			System.out.println("Nincs ilyen j�t�kos: "+playerMail);
		}
	}
	
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
	
	
}
