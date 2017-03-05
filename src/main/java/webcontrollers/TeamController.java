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
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import entities.Player;
import entities.Team;
import services.TeamService;

@Named
@SessionScoped
public class TeamController implements Serializable {

	private final static Logger LOGGER = Logger.getLogger(PlayerController.class.getName());

	private Team currentTeam;
	private UploadedFile teamPicture;
	private String teamDate;
	private List<Player> roster;
	private String toBeSignedPlayer;
	private Date currentDate = new Date();
	private String email;
	private String password;
	private String confirmPassword;
	private String oldPassword;
	private String newName;

	@Inject
	private SecurityController securityControl;
	@EJB
	private TeamService teamSrevice;

	@PostConstruct
	private void init() {
		currentTeam = securityControl.getCurrentTeam();
		currentTeam.getCurrentPlayers();
		LOGGER.info("csapat nev: " + currentTeam.getName());
	}

	public void updateTeam() {
		LOGGER.info("UPDATE MEGH�VVA");
		Team temp = new Team();
		temp.setEmail(email);
		temp.setPassword(password);
		byte[] imageContent = teamPicture.getContents();
		temp.setTeamPicture(imageContent);
		temp.setName(newName);
		temp.setFoundedIn(saveFoundationDate());
		teamSrevice.updateTeam(temp, currentTeam.getEmail());
		LOGGER.info("UPDATE LEFUTOTT: " + currentTeam.getEmail());
	}

	public void uploadPicture() {
		if (teamPicture != null) {
			byte[] imageContent = teamPicture.getContents();
			teamSrevice.saveImage(currentTeam, imageContent);
			currentTeam = null;
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("teamProfile.xhtml");
			} catch (IOException e) {
				LOGGER.severe(("Redirect error in teamcontroller"));
			}
			LOGGER.info("Picture uploaded");
		}
	}

	public Date saveFoundationDate() {
		Date fundationDate = null;
		if (teamDate != null) {
			DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			try {
				fundationDate = df.parse(teamDate);
				// teamSrevice.saveFoundationDate(currentTeam, fundationDate);
			} catch (ParseException e) {
				FacesContext.getCurrentInstance().addMessage("Invalid date format",
						new FacesMessage("Invalid date fromat"));

			}
			LOGGER.info("Foundation date updated");
		}
		return fundationDate;
	}

	// csak tesztel�sre
	public StreamedContent pic() {
		return currentTeam.getCurrentPlayers().get(0).streamPicture();
	}

	public void saveEditedTeamInfo() {
		saveFoundationDate();
		// t�bbi adat modositasa
	}

	public String signPlayer() {
		teamSrevice.signPlayer(currentTeam, toBeSignedPlayer);
		toBeSignedPlayer = null;
		currentTeam = null;
		return "teamProfile?faces-redirect=true";
	}

	public DefaultStreamedContent getCurrentTeamsPicture() {
		if (getCurrentTeam().getTeamPicture() == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(getCurrentTeam().getTeamPicture()));
	}

	public String getCurrentTeamName() {
		return getCurrentTeam().getName();
	}

	public String getCurrentTeamsDate() {
		if (currentTeam.getFoundedIn() == null)
			return "yyyy-mm-dd";
		return currentTeam.getFoundedIn().toString();
	}

	public List<Player> getRoster() {
		if (roster == null)
			roster = new ArrayList<Player>(getCurrentTeam().getCurrentPlayers());
		return roster;
	}

	public void setRoster(List<Player> roster) {
		this.roster = roster;
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

	public String getTeamDate() {
		return teamDate;
	}

	public void setTeamDate(String teamDate) {
		this.teamDate = teamDate;
	}

	public String getToBeSignedPlayer() {
		return toBeSignedPlayer;
	}

	public Date getCurrentDate() {

		return currentDate;
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

}
