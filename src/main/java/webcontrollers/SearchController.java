package webcontrollers;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Team;
import services.TeamService;

@Named
@RequestScoped
public class SearchController implements Serializable {
	public static final Logger LOGGER = Logger.getLogger(PlayerController.class.getName());
	@EJB
	private TeamService teamService;
	@Inject
	private TeamController teamController;
	
	private DataModel<Team> teamSearchResult;
	private String searchedTeamName;
	
	public void searchForTeamName(){
		searchedTeamName = "%" + searchedTeamName + "%";
		LOGGER.info("Searched for:" + searchedTeamName);
		teamService.getTeamNameSearchResult(searchedTeamName).stream().forEach(System.out::println);
		teamSearchResult = new ListDataModel<Team>(teamService.getTeamNameSearchResult(searchedTeamName));
		searchedTeamName=null;
	}

	public DataModel<Team> getTeamSearchResult() {
		if(teamSearchResult == null){
			teamSearchResult = new ListDataModel<Team>(teamService.getAllTeams());
		}
		return teamSearchResult;
	}
	
	public void selectTeamFromSearchResults(){
		teamController.setDisplayedTeam(teamSearchResult.getRowData());
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
	
	
	
	
}
