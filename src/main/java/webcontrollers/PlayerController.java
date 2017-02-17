package webcontrollers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entities.Player;
import services.PlayerService;

@Named
@RequestScoped
public class PlayerController {
	
	private String name;
	private int age;
	@EJB
	PlayerService playerEJB;
	
	public String addPlayer(){
		Player player = new Player();
		player.setName(name);
		player.setAge(age);
		playerEJB.createPlayer(player);
		return "index.xhtml";
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
