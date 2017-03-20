package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User to;
	@ManyToOne
	private User from;
	@Lob
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentTime;
	private boolean seenByUser;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSentTime() {
		return sentTime;
	}
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	public boolean isSeenByUser() {
		return seenByUser;
	}
	public void setSeenByUser(boolean seenByUser) {
		this.seenByUser = seenByUser;
	}
	
	
	
   
}
