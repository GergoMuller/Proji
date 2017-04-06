package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlTransient;


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
	@ManyToOne
	private TeamGroup group;
	@Lob
	private byte[] teamPicture;
	
	
	@XmlTransient
	public byte[] getTeamPicture() {
		return teamPicture;
	}
	public void setTeamPicture(byte[] teamPicture) {
		this.teamPicture = teamPicture;
	}
	@XmlTransient
	public List<Match> getWonMatches() {
		return wonMatches;
	}
	public void setWonMatches(List<Match> wonMatches) {
		this.wonMatches = wonMatches;
	}
	@XmlTransient
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
	@XmlTransient
	public List<Player> getCurrentPlayers() {
		return currentPlayers;
	}
	public void setCurrentPlayers(List<Player> currentPlayers) {
		this.currentPlayers = currentPlayers;
	}
	@XmlTransient
	public List<Player> getPreviousPlayers() {
		return previousPlayers;
	}
	public void setPreviousPlayers(List<Player> previousPlayers) {
		this.previousPlayers = previousPlayers;
	}
	
}
