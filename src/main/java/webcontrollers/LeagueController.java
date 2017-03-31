package webcontrollers;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import services.LeagueService;

@Named
@RequestScoped
public class LeagueController {
	private final Logger LOGGER = Logger.getLogger(LeagueController.class.getName());
	
	@EJB
	LeagueService leagueService;
	
	public void createSeason(){
		LOGGER.info("createSeason() called");
		leagueService.generateSeasonForLeague("Division 1");
	}
}
