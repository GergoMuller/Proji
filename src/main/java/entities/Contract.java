package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class Contract implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne
	private Player signedPlayer;
	@ManyToOne
	@JoinColumn(name="team")
	private Team signerTeam;
	private boolean teamAccepted=false;
	private boolean playerAccepted=false;
	private String content;
	private double amount;
	@Temporal(TemporalType.DATE)
	private Date validDate;
	
	
	
	
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Player getSignedPlayer() {
		return signedPlayer;
	}
	public void setSignedPlayer(Player signedPlayer) {
		this.signedPlayer = signedPlayer;
	}
	public Team getSignerTeam() {
		return signerTeam;
	}
	public void setSignerTeam(Team signerTeam) {
		this.signerTeam = signerTeam;
	}
	public boolean isTeamAccepted() {
		return teamAccepted;
	}
	public void setTeamAccepted(boolean teamAccepted) {
		this.teamAccepted = teamAccepted;
	}
	public boolean isPlayerAccepted() {
		return playerAccepted;
	}
	public void setPlayerAccepted(boolean playerAccepted) {
		this.playerAccepted = playerAccepted;
	}
	
	
	
   
}
