package webcontrollers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import entities.Player;
import entities.Team;
import entities.TeamGroup;
import services.LeagueService;
import services.PlayerService;
import services.TeamService;

@Named
@SessionScoped
public class TeamController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(TeamController.class.getName());

	private Team currentTeam;
	private UploadedFile teamPicture;
	private Date teamDate;
	private String toBeSignedPlayer;
	private String email;
	private String password;
	private String confirmPassword;
	private String oldPassword;
	private String newName;
	private Team displayedTeam;
	private Player displayedPlayer;
	private String groupName;
	private boolean teamDisplayed = true;

	@Inject
	private SecurityController securityControl;
	@EJB
	private TeamService teamService;
	@EJB
	private PlayerService playerService;
	@EJB
	private LeagueService leagueService;

	@PostConstruct
	private void init() {
		currentTeam = securityControl.getCurrentTeam();
		currentTeam.getCurrentPlayers();
		LOGGER.info("csapat nev: " + currentTeam.getName());
	}

	public void updateEntity() {
		LOGGER.info("k�p: "+teamPicture);
		Team temp = new Team();
		temp.setEmail(email);
		temp.setPassword(password);
		TeamGroup group = leagueService.getByName(groupName);
		temp.setGroup(group);
		uploadPicture();
		temp.setName(newName);
		temp.setFoundedIn(teamDate);
		teamService.updateTeam(temp, currentTeam);
		LOGGER.info("UPDATE LEFUTOTT: " + currentTeam.getEmail());
	}

	public void uploadPicture() {
		LOGGER.info("k�p update: "+teamPicture);
		if (teamPicture != null) {
			System.out.println("kép feltöltés megkezdödött");
			byte[] imageContent = teamPicture.getContents();
			teamService.saveImage(currentTeam, imageContent);
			currentTeam = null;
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("teamProfile.xhtml");
			} catch (IOException e) {
				LOGGER.severe(("Redirect error in teamcontroller"));
			}
			LOGGER.info("Picture uploaded");
		}
	}
	
	public String signPlayer() {
		teamService.signPlayer(currentTeam, toBeSignedPlayer);
		toBeSignedPlayer = null;
		currentTeam = null;
		return "teamProfile?faces-redirect=true";
	}

	public DefaultStreamedContent getCurrentTeamsPicture() {
		if (getCurrentTeam().getTeamPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(getCurrentTeam().getTeamPicture()));
	}
	
	public StreamedContent getRosterImage(){
		 FacesContext context = FacesContext.getCurrentInstance();
	     if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
	    	 return new DefaultStreamedContent();
	     }
	     long id =Long.parseLong(context.getExternalContext().getRequestParameterMap().get("playerId"));
	     Player player = playerService.getPlayerById(id);
	     return new DefaultStreamedContent(new ByteArrayInputStream(player.getPicture()));
	}
	
	public void loadHome(){
		displayedTeam = currentTeam;
		teamDisplayed = true;
	}

	public String getCurrentTeamName() {
		return getCurrentTeam().getName();
	}
	
	public List<Player> getRoster(){
		return teamService.getRoster(displayedTeam);
	}

	public String getCurrentTeamsDate() {
		if (currentTeam.getFoundedIn() == null)
			return "yyyy-mm-dd";
		return currentTeam.getFoundedIn().toString();
	}
	
	public StreamedContent getDisplayedPlayersPicture() {
		if (displayedPlayer == null || displayedPlayer.getPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(displayedPlayer.getPicture()));
	}

	public StreamedContent getDisplayedPlayersTeamsPicture() {
		if (displayedPlayer == null || displayedPlayer.getCurrentTeam() == null || displayedPlayer.getCurrentTeam().getTeamPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(displayedPlayer.getCurrentTeam().getTeamPicture()));
	}
	
	public StreamedContent getDisplayedTeamsPicture() {
		if (displayedTeam.getTeamPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(displayedTeam.getTeamPicture()));
	}

	public Team getCurrentTeam() {
		if (currentTeam == null) {
			currentTeam = securityControl.getCurrentTeam();
		}
		return currentTeam;
	}
	

	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}

	public UploadedFile getTeamPicture() {
		return teamPicture;
	}

	public void setTeamPicture(UploadedFile teamPicture) {
		this.teamPicture = teamPicture;
	}

	
	public Date getTeamDate() {
		return teamDate;
	}

	public void setTeamDate(Date teamDate) {
		this.teamDate = teamDate;
	}

	public String getToBeSignedPlayer() {
		return toBeSignedPlayer;
	}

	public void setToBeSignedPlayer(String toBeSignedPlayer) {
		this.toBeSignedPlayer = toBeSignedPlayer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date actualTeamDate() {
		return currentTeam.getFoundedIn();
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

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getCurrentEmail() {
		return currentTeam.getEmail();
	}

	public Team getDisplayedTeam() {
		if(displayedTeam == null)
			displayedTeam = getCurrentTeam();
		return displayedTeam;
	}

	public void setDisplayedTeam(Team displayedTeam) {
		this.displayedTeam = displayedTeam;
	}

	public Player getDisplayedPlayer() {
		return displayedPlayer;
	}

	public void setDisplayedPlayer(Player displayedPlayer) {
		this.displayedPlayer = displayedPlayer;
	}

	public boolean isTeamDisplayed() {
		return teamDisplayed;
	}

	public void setTeamDisplayed(boolean isTeamDisplayed) {
		this.teamDisplayed = isTeamDisplayed;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
}
