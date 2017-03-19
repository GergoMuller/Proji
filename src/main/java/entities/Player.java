package entities;

import java.io.ByteArrayInputStream;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import javax.persistence.*;

import org.omg.DynamicAny.DynAnySeqHelper;
import org.primefaces.model.DefaultStreamedContent;

import utilities.PlayerPosition;


@Entity
public class Player extends User implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	private String description;
	private int age;
	@Lob
	private byte[] picture;
	@Enumerated(EnumType.STRING)
	private PlayerPosition position;
	@OneToMany(orphanRemoval=true, mappedBy="signedPlayer")
	private List<Contract> contract;
	
	@ManyToOne
	@JoinColumn(name="Current_Team")
	private Team currentTeam;
	
	@ManyToMany
	@JoinTable(name="previousteam_player")
	private List<Team> previousTeams;
	
	
	
	public DefaultStreamedContent streamPicture(){
		if(picture == null)
			return null;
		return new DefaultStreamedContent(new ByteArrayInputStream(picture));
	}
	
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
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

	public List<Contract> getContract() {
		return contract;
	}

	public void setContract(List<Contract> contract) {
		this.contract = contract;
	}	
	
   
}
