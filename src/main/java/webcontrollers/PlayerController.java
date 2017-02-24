package webcontrollers;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.batch.api.partition.PartitionAnalyzer;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import entities.Player;
import services.PlayerService;
import utilities.Roles;


@Named
@SessionScoped
public class PlayerController implements Serializable{
	
	private Player currentPlayer;
	
	@EJB
	private PlayerService playerService;
	@Inject
	private SecurityController securityControl;
	
	@PostConstruct
	private void init(){
		currentPlayer = securityControl.getCurrentPlayer();
	}
	
	public void uploadFile(FileUploadEvent event){
		byte[] imageContent = event.getFile().getContents();
		playerService.saveImage(currentPlayer, imageContent);
		currentPlayer = null;
	}
	
	public Player getCurrentPlayer() {
		if(currentPlayer == null){
			currentPlayer = securityControl.getCurrentPlayer();
		}
		return currentPlayer;
	}
	
	public String getCurrentPlayersName(){			
		return getCurrentPlayer().getName();
	}
	
	public StreamedContent getCurrentPlayersPicture(){			
		return new DefaultStreamedContent(new ByteArrayInputStream(getCurrentPlayer().getPicture()));
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
