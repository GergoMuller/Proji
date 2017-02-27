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
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.omnifaces.cdi.GraphicImageBean;
import org.omnifaces.util.Ajax;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import entities.Player;
import entities.Team;
import services.TeamService;

@Named
@RequestScoped
public class TeamController implements Serializable{
	
	private final static Logger LOGGER = Logger.getLogger(PlayerController.class.getName());
	
	private Team currentTeam;
	private UploadedFile teamPicture;
	private String teamDate;
	private List<Player> roster;
	private String toBeSignedPlayer;
	

	@Inject
	private SecurityController securityControl;
	@EJB
	private TeamService teamSrevice;

	

	@PostConstruct
	private void init(){
		currentTeam = securityControl.getCurrentTeam();
		currentTeam.getCurrentPlayers();
		LOGGER.info("csapat nev: "+currentTeam.getName());
	}
	

	
	public void uploadPicture() {
		if(teamPicture != null){
			byte[] imageContent = teamPicture.getContents();
			teamSrevice.saveImage(currentTeam,imageContent);
			currentTeam = null;
			try{
				FacesContext.getCurrentInstance().getExternalContext().redirect("teamProfile.xhtml");
			}catch(IOException e){
				LOGGER.severe(("Redirect error in teamcontroller"));
			}
			LOGGER.info("Picture uploaded");
		}
	}
	
	public void saveFoundationDate(){
		if(teamDate != null){
			DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			Date fundationDate;
			try {
				fundationDate = df.parse(teamDate);
				teamSrevice.saveFoundationDate(currentTeam,fundationDate);
			} catch (ParseException e) {
				FacesContext.getCurrentInstance().addMessage("Invalid date format",new FacesMessage( "Invalid date fromat"));
				
			}
			LOGGER.info("Foundation date updated");
		}
	}
	
	public void saveEditedTeamInfo(){
		saveFoundationDate();
		// többi adat modositasa
	}
	
	public String signPlayer(){
		teamSrevice.signPlayer(currentTeam, toBeSignedPlayer);
		toBeSignedPlayer = null;
		return "teamProfile?faces-redirect=true";
	}
	
	
	public DefaultStreamedContent getCurrentTeamsPicture(){
		if(getCurrentTeam().getTeamPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(getCurrentTeam().getTeamPicture()));
	}

	public String getCurrentTeamName() {
		return getCurrentTeam().getName();
	}


	public String getCurrentTeamsDate(){
		if(currentTeam.getFoundedIn() == null)
			return "yyyy-mm-dd";
		return currentTeam.getFoundedIn().toString();
	}
	
	
	
	public List<Player> getRoster() {
		if(roster == null)
			roster = new ArrayList<Player>(getCurrentTeam().getCurrentPlayers());
		return roster;
	}


	public void setRoster(List<Player> roster) {
		this.roster = roster;
	}


	public Team getCurrentTeam() {
		if(currentTeam == null){
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

	public String getTeamDate() {
		return teamDate;
	}


	public void setTeamDate(String teamDate) {
		this.teamDate = teamDate;
	}



	public String getToBeSignedPlayer() {
		return toBeSignedPlayer;
	}



	public void setToBeSignedPlayer(String toBeSignedPlayer) {
		this.toBeSignedPlayer = toBeSignedPlayer;
	}
	
	

}
