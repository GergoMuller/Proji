package webcontrollers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;

import entities.City;
import entities.Player;
import entities.Team;
import services.PlayerService;
import services.RegistrationService;
import utilities.Roles;

@Named
@RequestScoped
public class RegistrationController {
	
private final static Logger LOGGER = Logger.getLogger(PlayerController.class.getName());
	
	private static final String USER ="user_player"; 
	private static final String TEAM ="user_team"; 
	private String firstName;
	private String lastName;
	private String teamCity;
	private String teamName;
	private int age;
	private String email;
	private String password;
	private String confirmPassword;
	private List<String> names;
	private String name;
	private String userType;
	private List<String> cities;
	
	@EJB
	PlayerService playerService;
	@EJB
	RegistrationService regService;
	
	
	
		
	
	public void checkEmail() {
        if(regService.isEmailExists(email)){
        	LOGGER.log(Level.FINE, "Email checked: {0}", this.email);
        	Ajax.oncomplete("wrong_email()");
        }
        Ajax.oncomplete("continue_validation()");
    }
	
	public Player createNewPlayer(){
		Player newPlayer = new Player();
		StringBuilder sb = new StringBuilder(firstName);
		name = sb.append(" ").append(lastName).toString();
		newPlayer.setName(name);
		newPlayer.setEmail(email);
		newPlayer.addRole(Roles.PLAYER);
		return newPlayer;
	}
	public Team createNewTeam(){
		Team newTeam = new Team();
		StringBuilder sb = new StringBuilder(teamCity);
		name = sb.append(" ").append(teamName).toString();
		newTeam.setName(name);
		newTeam.setEmail(email);
	    Date date = Calendar.getInstance().getTime();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String now = sdf.format(date);

		
		newTeam.addRole(Roles.TEAM);
		return newTeam;
	}

    public void registration() throws NoSuchAlgorithmException {
        LOGGER.info("Regisztráció elkezdése");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String b64String = Base64.getEncoder().encodeToString(hash);
    		if(userType.equals(USER)){
	    		Player newPlayer = createNewPlayer();
	    		newPlayer.setPassword(b64String);
	    		regService.createPlayer(newPlayer);
	    		LOGGER.log(Level.INFO,"usertype: "+userType);
	            LOGGER.log(Level.INFO, "USER létrehozva: {0}", newPlayer.toString());
    		}
    		else if(userType.equals(TEAM)){
    			Team newTeam = createNewTeam();
    			newTeam.setPassword(b64String);
    			regService.createTeam(newTeam);
    			LOGGER.log(Level.INFO,"usertype: "+userType);
	            LOGGER.log(Level.INFO, "USER létrehozva: {0}", newTeam.toString());
    		}
    		
            Ajax.oncomplete("reg_success()");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.severe("Hiba a jelszó titkosítása közben");
        } catch (Exception ex) {
            LOGGER.severe("Exception during registration");
            // üzenet a felületre
        }
    }
	
	

	public List<String> getCities() {
		if(cities == null){
			cities = regService.getAllCitiyNames();
		}
		return cities;
	}


	public void setCities(List<String> cities) {
		this.cities = cities;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getTeamCity() {
		return teamCity;
	}

	public void setTeamCity(String teamCity) {
		this.teamCity = teamCity;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}

}
