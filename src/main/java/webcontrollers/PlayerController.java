package webcontrollers;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;

import entities.Player;
import services.PlayerService;
import utilities.Roles;


@Named
@SessionScoped
public class PlayerController implements Serializable{
	
	private final static Logger LOGGER = Logger.getLogger(PlayerController.class.getName());
	
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String password;
	private String confirmPassword;
	private List<String> names;
	private String name;
	private String currentUserName;
	@EJB
	PlayerService playerService;
	@Inject
	SecurityController securityCont;
	
		
	public String getAdults(){
		names = playerService.getByAge(18);
		return "index";
	}
	
	public String login(){
		return "main?faces-redirect=true";
	}
	
	public void checkEmail() {
        if(playerService.isEmailExists(email)){
        	LOGGER.log(Level.FINE, "Email checked: {0}", this.email);
        	Ajax.oncomplete("wrong_email()");
        }
        Ajax.oncomplete("continue_validation()");
    }

    public void registration() throws NoSuchAlgorithmException {
        LOGGER.info("Regisztráció elkezdése");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String b64String = Base64.getEncoder().encodeToString(hash);
            StringBuilder sb = new StringBuilder(firstName);
    		name = sb.append(" ").append(lastName).toString();
    		Player newPlayer = new Player();
    		newPlayer.setName(name);
    		newPlayer.setAge(age);
    		newPlayer.setEmail(email);
    		newPlayer.setPassword(password);
    		newPlayer.addRole(Roles.PLAYER);
    		playerService.createPlayer(newPlayer);
            LOGGER.log(Level.INFO, "USER létrehozva: {0}", newPlayer.toString());
            Ajax.oncomplete("reg_success()");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.severe("Hiba a jelszó titkosítása közben");
        } catch (Exception ex) {
            LOGGER.severe("Exception during registration");
            // üzenet a felületre
        }
    }
	
	
    
	public String getCurrentUserName() {
		if(currentUserName == null)
			currentUserName = playerService.findPlayerName(securityCont.getUserEmail());
		return currentUserName;
	}

	public void setCurrentUserName(String currentUser) {
		this.currentUserName = currentUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	

}
