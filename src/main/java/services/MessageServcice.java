package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import entities.Message;
import entities.User;
import repositories.MessageRepository;
import repositories.UserRepository;

@Stateless
public class MessageServcice {
	
	@Inject
	private MessageRepository messageRepo;
	@Inject
	private UserRepository userRepo;
	
	
	public void saveMessage(Message sendingMessage, String toEmail){
		User toUser = userRepo.findByEmail(toEmail);
		if(toUser != null){
			sendingMessage.setTo(toUser);
			messageRepo.save(sendingMessage);
		}
	}

}
