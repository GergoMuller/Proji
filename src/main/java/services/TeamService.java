package services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import entities.Player;
import entities.Team;
import repositories.TeamRepository;
import repositories.UserRepository;

@Stateless
public class TeamService {

	@Inject
	TeamRepository teamRepo;
	@Inject
	UserRepository userRepo;
	
	
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
	
}
