package webcontrollers;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entities.Player;
import services.PlayerService;
import utilities.Roles;


@Named
@SessionScoped
public class PlayerController implements Serializable{
	
	
	private String name;
	private int age;
	private String email;
	private String password;
	private List<String> names;
	@EJB
	PlayerService playerEJB;
	
	public String addPlayer(){
		Player player = new Player();
		player.setName(name);
		player.setAge(age);
		player.setEmail(email);
		player.setPassword(password);
		player.addRole(Roles.PLAYER);
		playerEJB.createPlayer(player);
		return "index";
	}
	
	
	public String getAdults(){
		names = playerEJB.getByAge(18);
		return "index";
	}
	
	public String login(){
		return "main?faces-redirect=true";
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	

}
