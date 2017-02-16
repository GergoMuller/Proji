package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Past;


@Entity
public class Team implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique=true)
	private String teamName;
	@Column(unique=true)
	private String email;
	private String password;
	private int wins;
	private int losses;
	@Temporal(TemporalType.DATE)
	@Past
	private Date foundedIn;
	@OneToMany(mappedBy="currentTeam")
	private List<Player> currentPlayers;
	@ManyToMany(mappedBy="previousTeams")
	private List<Player> previousPlayers;
	@OneToMany(mappedBy="signerTeam", orphanRemoval=true)
	private List<Contract> contracts;
	
	
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	
	
   
}