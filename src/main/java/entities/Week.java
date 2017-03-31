package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
public class Week implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToMany
	@JoinColumn(name="week")
	private List<Match> matchesForTheWeek;
	@ManyToOne
	private Season season;
	private int weekCount;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Match> getMatchesForTheWeek() {
		return matchesForTheWeek;
	}
	public void setMatchesForTheWeek(List<Match> matchesForTheWeek) {
		this.matchesForTheWeek = matchesForTheWeek;
	}
	public int getWeekCount() {
		return weekCount;
	}
	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
   
}
