package webcontrollers;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Ajax;

import entities.Player;
import entities.Team;
import entities.User;
import services.PlayerService;
import services.TeamService;
import services.UserService;
import utilities.Roles;

@Named
@RequestScoped
public class SecurityController implements Serializable {
	
	@EJB
	private PlayerService playerService;
	
	@EJB
	private TeamService teamService;
	
	@EJB
	private UserService userService;
	private String loginEmail;
	private String password;
	private String forwardURL;
	
	
	@PostConstruct
	private void init(){
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		forwardURL = (String)exc.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
		if(forwardURL != null){
			String forwardQuery = (String)exc.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
			if(forwardQuery != null){
				forwardURL += "?" + forwardQuery;
			}
		}
	}
	
	public void login() throws IOException{
		FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext exc = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)exc.getRequest();
        
        try{
        	request.login(loginEmail, password);
        	User loggedInUser = userService.getUserByEmail(loginEmail);
        	exc.getSessionMap().put("user", loggedInUser);
        	if(forwardURL == null){
        		if(isUserInRole(Roles.ADMIN))
        			forwardURL = exc.getRequestContextPath() + "/adminPage.xhtml";
        		else if(isUserInRole(Roles.TEAM))
        			forwardURL = exc.getRequestContextPath() + "/pages/team/teamProfile.xhtml";
        		else if(isUserInRole(Roles.PLAYER))
        			forwardURL = exc.getRequestContextPath() + "/pages/player/playerProfile.xhtml";
        	}
        	exc.redirect(forwardURL);
        }catch(ServletException e){
        	e.printStackTrace();
        	Ajax.oncomplete("invalid_login()");
        	
        }  
	}
	
	public void logOut() throws IOException{
		ExternalContext exc =FacesContext.getCurrentInstance().getExternalContext();
		exc.invalidateSession();
		exc.redirect(exc.getRequestContextPath() + "/index.xhtml");
	}
	
	public String getUserEmail() {
		ExternalContext ectx = FacesContext.getCurrentInstance()
				.getExternalContext();
		return ((ectx == null) || (ectx.getUserPrincipal() == null)) ? null
				: ectx.getUserPrincipal().getName();
	}

	public Player getCurrentPlayer() {
		if(isUserInRole(Roles.PLAYER))
			return playerService.getPlayerByEmail(getUserEmail());
		else
			return null;
	}
	
	
	public Team getCurrentTeam(){
	if(isUserInRole(Roles.TEAM))
		return teamService.getTeamByEmail(getUserEmail());
	else
		return null;
	}
	
	public boolean isUserInRole(String role){
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		return exc.isUserInRole(role);
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
