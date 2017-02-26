package webcontrollers;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Team;

@Named
@SessionScoped
public class TeamController implements Serializable{
	
	private final static Logger LOGGER = Logger.getLogger(PlayerController.class.getName());
	
	private Team currentTeam;

	@Inject
	private SecurityController securityControl;

	

	@PostConstruct
	private void init(){
		currentTeam = securityControl.getCurrentTeam();
		LOGGER.info("csapat nev: "+currentTeam.getName());
	}

	public String getTeamName() {
		return currentTeam.getName();
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
	
	

	

	


}
