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

import org.omnifaces.util.Ajax;

import entities.Contract;
import entities.Team;
import services.ContractService;
import services.RegistrationService;
import services.TeamService;

@Named
@SessionScoped
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
	private Date validDate;
	private Double amount;
	private Contract selectedContract;

	public void sendContract() {
		Team currentTeam = securityController.getCurrentTeam();
		System.out.println("send contract meghívva: current team = "+currentTeam.getEmail());
		if (currentTeam != null) {
			newContract = new Contract();
			newContract.setSignerTeam(currentTeam);
			newContract.setValidDate(validDate);
			newContract.setAmount(amount);
			try {
				contractService.saveContract(newContract, signingPlayerEmail);
				System.out.println("signingPlayerEmail :"+signingPlayerEmail);
				System.out.println("validDate : "  +validDate);
				System.out.println("amount : "+amount);
			} catch (Exception e) {
				System.out.println("HIBAAAAAAAA" + e.getMessage());
				e.printStackTrace();

			}
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
		//exc.redirect(exc.getRequestContextPath() + "/contract.xhtml");
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

	public String acceptContract() {
		contractService.acceptContract(selectedContract);
		return "playerProfile?faces-redirect=true";
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

	
	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
