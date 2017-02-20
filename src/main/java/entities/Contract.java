package entities;

import java.io.Serializable;
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
	
	
   
}
