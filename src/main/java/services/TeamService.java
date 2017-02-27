package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import entities.Player;
import entities.Team;
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
		//team.getCurrentPlayers().add(signingPlayer);
		playerRepo.save(signingPlayer);
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
}
