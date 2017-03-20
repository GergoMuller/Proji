package webcontrollers;

import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Message;
import services.MessageServcice;
import utilities.Roles;

@Named
@RequestScoped
public class MessageController {
	
	@Inject
	private SecurityController securityController;
	@EJB
	private MessageServcice messageService;
	private String toEmail;
	
	private Message editedMessage;
	
	public void sendMessage(){
		editedMessage.setSentTime(new Date());
		if(securityController.isUserInRole(Roles.TEAM))
			editedMessage.setFrom(securityController.getCurrentTeam());
		else if(securityController.isUserInRole(Roles.PLAYER))
			editedMessage.setFrom(securityController.getCurrentPlayer());
		messageService.saveMessage(editedMessage,toEmail);
	}

	public Message getEditedMessage() {
		if(editedMessage == null)
			editedMessage = new Message();
		return editedMessage;
	}

	public void setEditedMessage(Message editedMessage) {
		this.editedMessage = editedMessage;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	

}
