package webcontrollers;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Ajax;

import entities.Contract;
import entities.Team;
import services.ContractService;
import services.RegistrationService;
import services.TeamService;
import utilities.InvalidContractOperationException;

@Named
@ViewScoped
public class ContractController implements Serializable {

	@Inject
	private SecurityController securityController;
	@EJB
	private TeamService teamService;
	@EJB
	private ContractService contractService;
	@EJB
	private RegistrationService regService;

	private Contract newContract;
	private String signingPlayerEmail;
	private Contract selectedContract;

	public void sendContract() {
		Team currentTeam = securityController.getCurrentTeam();
		System.out.println("send contract meghívva: current team = "+currentTeam.getEmail());
		newContract.setSignerTeam(currentTeam);
		try {
			contractService.saveContract(newContract, signingPlayerEmail);
		} catch (Exception e) {
			System.out.println("HIBA  nincs ilyen jatekos" + e.getMessage());
			e.printStackTrace();
		}
	}

	public Contract getNewContract() {
		if (newContract == null)
			newContract = new Contract();
		return newContract;
	}

	public void loadContract(Long id) throws IOException {
		selectedContract = contractService.getContractById(id);
		contractService.setContractSeen(selectedContract);
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		exc.getFlash().put("contract", selectedContract);
	}

	public boolean checkEmail() {
		System.out.println("checking email");
		if (regService.isEmailExists(signingPlayerEmail)) {
			Ajax.oncomplete("finishContract()");
			return true;
		}
		Ajax.oncomplete("invalidEmail()");
		return false;
	}
	
	public void rejecttContract(){
		contractService.rejectContract(selectedContract);
		selectedContract = contractService.getContractById(selectedContract.getId());
	}
	
	public boolean isContractRejected(){
		if(selectedContract == null)
			return true;
		return !selectedContract.isTeamAccepted();
	}
	public boolean isContractAccepted(){
		if(selectedContract == null)
			return true;
		return selectedContract.isTeamAccepted() && selectedContract.isPlayerAccepted();
	}

	public void acceptContract() {
		try{
		contractService.acceptContract(selectedContract);
		selectedContract = contractService.getContractById(selectedContract.getId());
		}catch(Exception ex){
			Ajax.oncomplete("$.growl.error({message: 'You already have an ACTIVE cntract!'})");
		}
	}

	public void setNewContract(Contract newContract) {
		this.newContract = newContract;
	}

	public String getSigningPlayerEmail() {
		return signingPlayerEmail;
	}

	public void setSigningPlayerEmail(String signingPlayerEmail) {
		this.signingPlayerEmail = signingPlayerEmail;
	}

	public Contract getSelectedContract() {
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		return (Contract) exc.getFlash().get("contract");
		// return selectedContract;
	}

	public void setSelectedContract(Contract selectedContract) {
		this.selectedContract = selectedContract;
	}
}
