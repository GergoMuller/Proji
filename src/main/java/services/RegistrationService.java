package services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import entities.City;
import entities.Player;
import entities.Team;
import repositories.CityRepository;
import repositories.PlayerRepository;
import repositories.TeamRepository;
import repositories.UserRepository;

@Stateless
public class RegistrationService {
	
	@Inject
	CityRepository cityRepository;
	@Inject
	TeamRepository teamRepsoitory;
	@Inject
	PlayerRepository playerRepository;
	@Inject
	UserRepository userRepository;

	
	public List<String> getAllCitiyNames(){
			return cityRepository.findAll().stream()
					.sorted((c1,c2) -> c1.getCityName().compareTo(c2.getCityName()))
					.map(c -> new String(c.getCityName()))
					.collect(Collectors.toList());
	}
	
	public boolean isEmailExists(String email){
		try{
		userRepository.findByEmail(email);
		}catch (NoResultException e ){
			return false;
		}
		return true;
	}
	
	public void createTeam(Team team){
		teamRepsoitory.save(team);
	}
	
	public void createPlayer(Player player){
		playerRepository.save(player);
		
	}
}