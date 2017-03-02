package webcontrollers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import entities.Contract;
import entities.Player;
import services.ContractService;
import services.PlayerService;
import utilities.Roles;


@Named
@SessionScoped
public class PlayerController implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Player currentPlayer;
	private List<Contract> currentPlayersOffers;
	
	
	@EJB
	private PlayerService playerService;
	@Inject
	private SecurityController securityControl;
	@EJB
	private ContractService contractService;
	
	@PostConstruct
	private void init(){
		currentPlayer = securityControl.getCurrentPlayer();
	}
	
	public void uploadFile(FileUploadEvent event) throws IOException{
		byte[] imageContent = event.getFile().getContents();
		playerService.saveImage(currentPlayer, imageContent);
		currentPlayer = null;
		FacesContext.getCurrentInstance().getExternalContext().redirect("playerProfile.xhtml");
	}
	
	public int getNumberOfNewContracts(){
		return playerService.numberOfNewContracts(currentPlayer);
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
		if(getCurrentPlayer().getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(getCurrentPlayer().getPicture()));
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	

	public List<Contract> getCurrentPlayersOffers() {
		if(currentPlayersOffers == null)
			currentPlayersOffers = contractService.getPlayersContracts(currentPlayer);
		return currentPlayersOffers;
	}

	public void setCurrentPlayersOffers(List<Contract> currentPlayersOffers) {
		this.currentPlayersOffers = currentPlayersOffers;
	}
	
}
