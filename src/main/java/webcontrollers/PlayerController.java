package webcontrollers;

import java.util.List;

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
	private List<String> names;
	@EJB
	PlayerService playerEJB;
	
	public String addPlayer(){
		Player player = new Player();
		player.setName(name);
		player.setAge(age);
		playerEJB.createPlayer(player);
		return "index";
	}
	
	
	public String getAdults(){
		names = playerEJB.getByAge(18);
		return "index";
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
