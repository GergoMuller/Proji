package webcontrollers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entities.Player;
import entities.User;
import services.PlayerService;

@Named
@SessionScoped
public class SecurityController implements Serializable {
	
	@EJB
	private PlayerService playerService;
	
	public String logOut(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "main?faces-redirect=true";
	}
	
	public String getUserEmail() {
		ExternalContext ectx = FacesContext.getCurrentInstance()
				.getExternalContext();
		return ((ectx == null) || (ectx.getUserPrincipal() == null)) ? null
				: ectx.getUserPrincipal().getName();
	}

	public Player getCurrentPlayer() {
		return playerService.getPlayerByEmail(getUserEmail());
		
	}


}
