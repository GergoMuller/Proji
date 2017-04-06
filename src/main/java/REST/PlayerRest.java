package REST;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Player;
import services.PlayerService;


@Produces(MediaType.APPLICATION_JSON)
@Path("/players")
public class PlayerRest {
	
	@EJB
	private PlayerService playerService;
	
	@GET
	public List<Player> getAllPlayers(){
		return playerService.getAllPlayers();
	}
	
	@GET
	@Path("/{name}")
	public List<Player> getByName(@PathParam("name") String name){
		List<Player> players = playerService.getPlayerNameSearchResult("%" + name + "%");
		return players;
				//players.stream().map(p -> p.getEmail()).collect(Collectors.toList());
	}

}
