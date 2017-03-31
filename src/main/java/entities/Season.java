package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
public class Season implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique=true)
	private String season;
	@OneToMany(mappedBy="season")
	private List<Match> matches;
	@OneToMany(mappedBy="season", cascade=CascadeType.PERSIST)
	private List<Week> weeks;
	@OneToOne
	private Team champion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public List<Match> getMatches() {
		return matches;
	}
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	public List<Week> getWeeks() {
		return weeks;
	}
	public void setWeeks(List<Week> weeks) {
		this.weeks = weeks;
	}
	public Team getChampion() {
		return champion;
	}
	public void setChampion(Team champion) {
		this.champion = champion;
	}
	
	
	   
}
