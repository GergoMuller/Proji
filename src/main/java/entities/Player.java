package entities;

import java.io.ByteArrayInputStream;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

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

	@OneToMany(mappedBy="signedPlayer") //majd orphan removal
	private List<Contract> contract;
	
	@ManyToOne
	@JoinColumn(name="Current_Team")
	private Team currentTeam;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="previousteam_player")
	private List<Team> previousTeams;
	
	@XmlTransient
	@Transient
	public DefaultStreamedContent getStreamPicture(){
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
	@XmlTransient
	public List<Team> getPreviousTeams() {
		return previousTeams;
	}
	public void setPreviousTeams(List<Team> previousTeams) {
		this.previousTeams = previousTeams;
	}
	@XmlTransient
	public Team getCurrentTeam() {
		return currentTeam;
	}
	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}
	@XmlTransient
	public List<Contract> getContract() {
		return contract;
	}

	public void setContract(List<Contract> contract) {
		this.contract = contract;
	}
	
	@Transient
	@XmlTransient
	public Team getPreviousTeam(){
		return previousTeams.get(previousTeams.size()-1);
	}
}
