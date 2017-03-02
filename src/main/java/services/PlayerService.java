package services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entities.Contract;
import entities.Player;
import entities.User;
import repositories.ContractRepository;
import repositories.PlayerRepository;
import repositories.UserRepository;


@Stateless
@LocalBean
public class PlayerService {
	
	@Inject
	PlayerRepository playerRepo;
	@Inject
	UserRepository userRepo;
	@Inject
	ContractRepository contractRepo;
	
	
	
	public List<String> getByAge(int age){
		return playerRepo.findByAgeGreaterThanEqualsOrderByNameAsc(age)
								.stream()
								.map(x -> x.getName())
								.collect(Collectors.toList());
	}
	
	
	public Player getPlayerByEmail(String email){
		return playerRepo.findByEmail(email);
		
	}
	
	public void saveImage(Player p, byte[] image){
		Player player = playerRepo.findByEmail(p.getEmail());
		player.setPicture(image);
		playerRepo.save(player);
	}
	
	public int numberOfNewContracts(Player player){
		return contractRepo.findBySignedPlayerEqualAndSeenByPlayerEqual(player,false).size();
	}
	
}
