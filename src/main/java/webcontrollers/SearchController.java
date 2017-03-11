package webcontrollers;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Player;
import entities.Team;
import services.PlayerService;
import services.TeamService;

@Named
@ViewScoped
public class SearchController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final Logger LOGGER = Logger.getLogger(PlayerController.class.getName());
	@EJB
	private TeamService teamService;
	@EJB
	private PlayerService playerService;
	@Inject
	private TeamController teamController;
	@Inject
	private PlayerController playerController;
	
	private DataModel<Team> teamSearchResult;
	private DataModel<Player> playerSearchResult;
	private String searchedTeamName;
	private String searchedPlayerName;
	
	public void searchForTeamName(){
		searchedTeamName = "%" + searchedTeamName + "%";
		LOGGER.info("Searched for:" + searchedTeamName);
		teamSearchResult = new ListDataModel<>(teamService.getTeamNameSearchResult(searchedTeamName));
		searchedTeamName=null;
	}
	
	public void searchForPlayerName(){
		searchedPlayerName = "%" + searchedPlayerName + "%";
		LOGGER.info("Searched for:" + searchedPlayerName);
		playerSearchResult = new ListDataModel<>(playerService.getPlayerNameSearchResult(searchedPlayerName));
		searchedPlayerName=null;
	}

	public DataModel<Team> getTeamSearchResult() {
		if(teamSearchResult == null){
			teamSearchResult = new ListDataModel<>(teamService.getAllTeams());
		}
		return teamSearchResult;
	}
	
	public void selectTeamFromSearchResults(){
		teamController.setDisplayedTeam(teamSearchResult.getRowData());
		LOGGER.info(teamSearchResult.getRowData().getName() + " selected from the list");
	}
	
	public void selectPlayerFromSearchResults(){
		playerController.setDisplayedPlayer(playerSearchResult.getRowData());
		LOGGER.info(playerSearchResult.getRowData().getName() + " selected from the list");
	}

	public void setTeamSearchResult(DataModel<Team> teamSearchResult) {
		this.teamSearchResult = teamSearchResult;
	}
	public String getSearchedTeamName() {
		return searchedTeamName;
	}
	public void setSearchedTeamName(String searchedTeamName) {
		this.searchedTeamName = searchedTeamName;
	}

	public DataModel<Player> getPlayerSearchResult() {
		if(playerSearchResult == null){
			playerSearchResult = new ListDataModel<>(playerService.getAllPlayers());
		}
		return playerSearchResult;
	}

	public void setPlayerSearchResult(DataModel<Player> playerSearchResult) {
		this.playerSearchResult = playerSearchResult;
	}

	public String getSearchedPlayerName() {
		return searchedPlayerName;
	}

	public void setSearchedPlayerName(String searchedPlayerName) {
		this.searchedPlayerName = searchedPlayerName;
	}
	
	
	
	
}
