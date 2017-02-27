package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Past;


@Entity
public class Team extends User implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private int wins;
	private int losses;
	@Temporal(TemporalType.DATE)
	@Past
	private Date foundedIn;
	@OneToMany(mappedBy="currentTeam", fetch=FetchType.EAGER )
	private List<Player> currentPlayers;
	@ManyToMany(mappedBy="previousTeams")
	private List<Player> previousPlayers;
	@OneToMany(mappedBy="signerTeam", orphanRemoval=true)
	private List<Contract> contracts;
	@OneToMany(mappedBy="winnerTeam") 
	private List<Match> wonMatches;
	@Lob
	private byte[] teamPicture;
	
	
	
	public byte[] getTeamPicture() {
		return teamPicture;
	}
	public void setTeamPicture(byte[] teamPicture) {
		this.teamPicture = teamPicture;
	}
	public List<Match> getWonMatches() {
		return wonMatches;
	}
	public void setWonMatches(List<Match> wonMatches) {
		this.wonMatches = wonMatches;
	}
	public List<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	public Date getFoundedIn() {
		return foundedIn;
	}
	public void setFoundedIn(Date foundedIn) {
		this.foundedIn = foundedIn;
	}
	public List<Player> getCurrentPlayers() {
		return currentPlayers;
	}
	public void setCurrentPlayers(List<Player> currentPlayers) {
		this.currentPlayers = currentPlayers;
	}
	public List<Player> getPreviousPlayers() {
		return previousPlayers;
	}
	public void setPreviousPlayers(List<Player> previousPlayers) {
		this.previousPlayers = previousPlayers;
	}
	
}
