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
import org.primefaces.model.UploadedFile;

import entities.Contract;
import entities.Player;
import services.ContractService;
import services.PlayerService;
import utilities.Roles;


@Named
@SessionScoped
public class PlayerController implements Serializable{
	
	private final static Logger LOGGER	= Logger.getLogger(PlayerController.class.getName());
	private static final long serialVersionUID = 1L;
	private Player currentPlayer;
	private List<Contract> currentPlayersOffers;
	private Player displayedPlayer;
	private UploadedFile playerPicture;
	
	
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
	
	public void uploadPicture() {
		LOGGER.info("k�p update: "+playerPicture);
		if (playerPicture != null) {
			LOGGER.info("kép feltöltés megkezdödött");
			byte[] imageContent = playerPicture.getContents();
			playerService.saveImage(currentPlayer, imageContent);
			currentPlayer = null;
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("playerProfile.xhtml");
			} catch (IOException e) {
				LOGGER.severe(("Redirect error in teamcontroller"));
			}
			LOGGER.info("Picture uploaded");
		}
	}
	
	public void loadHome(){
		displayedPlayer = currentPlayer;
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
	
	public StreamedContent getCurrentPlayersPicture(){		
		if(getCurrentPlayer().getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(getCurrentPlayer().getPicture()));
	}
	
	public StreamedContent getDisplayedPlayersPicture(){		
		if(getDisplayedPlayer().getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(displayedPlayer.getPicture()));
	}
	
	public StreamedContent getDisplayedPlayersTeamsPicture(){		
		if(getDisplayedPlayer().getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(displayedPlayer.
												getCurrentTeam().getTeamPicture()));
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

	public Player getDisplayedPlayer() {
		if(displayedPlayer == null){
			displayedPlayer = getCurrentPlayer();
		}
		return displayedPlayer;
	}

	public void setDisplayedPlayer(Player displayedPlayer) {
		this.displayedPlayer = displayedPlayer;
	}

	public UploadedFile getPlayerPicture() {
		return playerPicture;
	}

	public void setPlayerPicture(UploadedFile playerPicture) {
		this.playerPicture = playerPicture;
	}
	
	
}
