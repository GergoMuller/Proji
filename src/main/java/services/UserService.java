package services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import entities.User;
import repositories.UserRepository;

@Stateless
public class UserService {
	
	@Inject
	private UserRepository userRepo;
	
	public User getUserByEmail(String email){
		return userRepo.findByEmail(email);
	}


}
