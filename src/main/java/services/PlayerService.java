package services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
import entities.Team;
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
	
	public void updatePlayer(Player temp, Player currentPlayer) {
		System.out.println("cserélendõ név: "+temp.getName());
		//hashelni a passwordot!!!
		Player team = currentPlayer;
		System.out.println("CURRENT TEAM: "+currentPlayer.getEmail());
		if (temp.getEmail() != null && !("").equals(temp.getEmail())) {
			System.out.println("EMAIL CSERE: "+temp.getEmail());
			team.setEmail(temp.getEmail());
		}
		if (temp.getPassword() != null && !("").equals(temp.getPassword())) {
			try{
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = digest.digest(temp.getPassword().getBytes(StandardCharsets.UTF_8));
	            String b64String = Base64.getEncoder().encodeToString(hash);
	            team.setPassword(b64String);
			}catch(NoSuchAlgorithmException e){
				System.out.println("Hiba a hashelésnél");
			}
		}
		if (!("").equals(temp.getAge())) {
			team.setAge(temp.getAge());
		}
		if (temp.getName() != null && !("").equals(temp.getName())) {
			team.setName(temp.getName());
		}
		playerRepo.save(team);
		System.out.println("update megtörtént: "+team.getEmail());
	}
	
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
	
	public List<Player> getAllPlayers(){
		return playerRepo.findAll().stream()
						 .sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
						 .collect(Collectors.toList());
	}
	
	public List<Player> getPlayerNameSearchResult(String searchParam){
		return playerRepo.findByNameLike(searchParam).stream()
				 		 .sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
				 		 .collect(Collectors.toList());
	}
	
}
