package webcontrollers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
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

import org.omg.DynamicAny.DynAnySeqHelper;
import org.omnifaces.util.Ajax;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import entities.Contract;
import entities.Player;
import entities.Team;
import services.ContractService;
import services.PlayerService;
import services.RegistrationService;
import utilities.Roles;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Named
@SessionScoped
public class PlayerController implements Serializable {

	private final static Logger LOGGER = Logger.getLogger(PlayerController.class.getName());
	private static final long serialVersionUID = 1L;
	private Player currentPlayer;
	private List<Contract> currentPlayersOffers;
	private Player displayedPlayer;
	private UploadedFile playerPicture;
	private String firstName;
	private String name;
	private String email;
	private String password;
	private String confirmPassword;
	private String oldPassword;
	private Date birthDate;
	private UploadedFile teamPicture;

	@EJB
	private PlayerService playerService;
	@Inject
	private SecurityController securityControl;
	@EJB
	private ContractService contractService;
	@EJB
	private RegistrationService regService;

	@PostConstruct
	private void init() {
		currentPlayer = securityControl.getCurrentPlayer();
	}

	public void updateEntity() {
		System.out.println(name);
		Player temp = new Player();
		temp.setEmail(email);
		temp.setPassword(password);
		uploadPicture();
		temp.setName(name);
		LocalDate now = LocalDate.now();
		LocalDate birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Long diff = ChronoUnit.YEARS.between(birth, now);
		diff--;
		System.out.println(diff);
		temp.setAge(diff.intValue());
		playerService.updatePlayer(temp, currentPlayer);
		LOGGER.info("UPDATE LEFUTOTT: " + currentPlayer.getEmail());
	}

	public boolean checkEmail() {
		System.out.println("checking email");
		if (regService.isEmailExists(email)) {
			Ajax.oncomplete("invalidEmail()");
			return false;
		}
		Ajax.oncomplete("checkOldPassword()");
		return true;
	}

	public void checkPassword() throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(oldPassword.getBytes(StandardCharsets.UTF_8));
		String b64String = Base64.getEncoder().encodeToString(hash);
		if (b64String.equals(currentPlayer.getPassword())) {
			Ajax.oncomplete("validatePassword()");
		} else {
			Ajax.oncomplete("invalidPassword()");
		}
	}

	public void uploadPicture() {
		LOGGER.info("k�p update: " + playerPicture);
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

	public void loadHome() {
		displayedPlayer = currentPlayer;
	}

	public int getNumberOfNewContracts() {
		//return playerService.numberOfNewContracts(currentPlayer);
		return (int)currentPlayer.getContract().stream().filter(c -> c.isSeenByPlayer()== false).count();
	}
	
	public int getNumberOfUnseenMessages(){
		return new Long(currentPlayer.getInbox().stream().filter( m -> !m.isSeenByUser()).count()).intValue();
	}

	public Player getCurrentPlayer() {
		if (currentPlayer == null) {
			currentPlayer = securityControl.getCurrentPlayer();
		}
		return currentPlayer;
	}

	public StreamedContent getCurrentPlayersPicture() {
		if (getCurrentPlayer().getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(getCurrentPlayer().getPicture()));
	}

	public StreamedContent getDisplayedPlayersPicture() {
		if (getDisplayedPlayer().getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(displayedPlayer.getPicture()));
	}

	public StreamedContent getDisplayedPlayersTeamsPicture() {
		if (getDisplayedPlayer().getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(displayedPlayer.getCurrentTeam().getTeamPicture()));
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<Contract> getCurrentPlayersOffers() {
		if (currentPlayersOffers == null)
			currentPlayersOffers = contractService.getPlayersContracts(currentPlayer);
		return currentPlayersOffers;
//		currentPlayersOffers=currentPlayer.getContract();
//		return currentPlayersOffers;
	}

	public void setCurrentPlayersOffers(List<Contract> currentPlayersOffers) {
		this.currentPlayersOffers = currentPlayersOffers;
	}

	public Player getDisplayedPlayer() {
		if (displayedPlayer == null) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPasswrod) {
		this.oldPassword = oldPasswrod;
	}

	public String getCurrentName() {
		return currentPlayer.getName();
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public UploadedFile getTeamPicture() {
		return teamPicture;
	}

	public void setTeamPicture(UploadedFile teamPicture) {
		this.teamPicture = teamPicture;
	}

	public String getCurrentEmail() {
		return currentPlayer.getEmail();
	}
}
