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
	@ManyToOne
	@JoinColumn(name="player_id")
	private Player signedPlayer;
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team signerTeam;
	private boolean teamAccepted=false;
	private boolean playerAccepted=false;
	@Lob
	private String content;
	private double amount;
	@Temporal(TemporalType.DATE)
	private Date validDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;
	private boolean seenByPlayer;
	
	
	
	
	public boolean isSeenByPlayer() {
		return seenByPlayer;
	}
	public void setSeenByPlayer(boolean seenByPlayer) {
		this.seenByPlayer = seenByPlayer;
	}
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
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	
	
	
	
   
}
