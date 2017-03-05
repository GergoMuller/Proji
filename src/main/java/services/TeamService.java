package services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
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
			System.out.println("sikeres mentés: "+playerMail);
			List<Player> temp = new ArrayList();
			temp = team.getCurrentPlayers();
			for (Player player : temp) {
				System.out.println(player.getName());
			}
		}catch(NoResultException ex){
			System.out.println("Nincs ilyen játékos: "+playerMail);
		}
	}
	
	

	public void updateTeam(Team temp, String email) {
		//hashelni a passwordot!!!
		Team team = getTeamByEmail(email);
		if (temp.getEmail().length() != 0) {
			team.setEmail(temp.getEmail());
		}
		if (temp.getPassword().length() != 0) {
			try{
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = digest.digest(temp.getPassword().getBytes(StandardCharsets.UTF_8));
	            String b64String = Base64.getEncoder().encodeToString(hash);
	            team.setPassword(b64String);
			}catch(NoSuchAlgorithmException e){
				System.out.println("Hiba a hashelésnél");
			}
		}
		if (temp.getTeamPicture() != null) {
			team.setTeamPicture(temp.getTeamPicture());
		}
		if (temp.getFoundedIn() != null) {
			team.setFoundedIn(temp.getFoundedIn());
		}
		if (temp.getName().length() != 0) {
			team.setName(temp.getName());
		}
		teamRepo.save(team);
		System.out.println("update megtörtént: "+email);
	}

	
	
}
