package REST;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Team;
import services.TeamService;


@Produces(MediaType.APPLICATION_JSON)
@Path("/teams")
public class TeamRest {
	
	@EJB
	private TeamService teamService;

	@GET
	public List<Team> getAllTeams(){
		return teamService.getAllTeams();
	}
	
	
}
