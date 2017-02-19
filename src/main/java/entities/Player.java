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
	private String description;
	private int age;
	@Enumerated(EnumType.STRING)
	private PlayerPosition position;
	@OneToOne(orphanRemoval=true)
	private Contract contract;
	
	@ManyToOne
	@JoinColumn(name="Current_Team")
	private Team currentTeam;
	
	@ManyToMany
	@JoinTable(name="previousteam_player")
	private List<Team> previousTeams;
	
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public PlayerPosition getPosition() {
		return position;
	}
	public void setPosition(PlayerPosition position) {
		this.position = position;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
