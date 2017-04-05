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

@Stateless
@LocalBean
@Produces(MediaType.APPLICATION_JSON)
@Path("/team")
public class TeamRest {
	
	@EJB
	private TeamService teamService;

	@GET
	@Path("/all-teams")
	public List<Team> getAllTeams(){
		return teamService.getAllTeams();
	}
	
	
}
