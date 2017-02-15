package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
public class Player implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique=true)
	private String email;
	private String name;
	private String password;
	@ManyToOne
	@JoinColumn(name="Current_Team")
	private Team currentTeam;
	
	@ManyToMany
	@JoinTable(name="previousteam_player")
	private List<Team> previousTeams;
	
	
	
	
	public List<Team> getPreviousTeams() {
		return previousTeams;
	}
	public void setPreviousTeams(List<Team> previousTeams) {
		this.previousTeams = previousTeams;
	}
	public Team getCurrentTeam() {
		return currentTeam;
	}
	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
   
}
