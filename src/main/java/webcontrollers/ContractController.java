package webcontrollers;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
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
@RequestScoped
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
	private String validDate;
	private String amount;
	private Contract selectedContract;
	
	public void sendContract(){
		Team currentTeam = securityController.getCurrentTeam();
		if(currentTeam != null){
			newContract.setSignerTeam(currentTeam);
			if(checkEmail()){
				try{
					contractService.saveContract(newContract, signingPlayerEmail, validDate, amount);
				} catch (ParseException e) {
					FacesContext.getCurrentInstance().addMessage("Invalid date format",new FacesMessage( "Invalid date fromat"));
				} catch(NoResultException e){
					FacesContext.getCurrentInstance().addMessage("Invalid email-address",new FacesMessage( "Invalid email-address"));
					System.out.println("NoResultExceptiont caught");
				} catch(NumberFormatException e){
					FacesContext.getCurrentInstance().addMessage("Invalid amount",new FacesMessage( "Invalid amount"));
					System.out.println("NumberFormatException caught");
				}catch(Exception e){
					System.out.println("HIBAAAAAAAA");
					FacesContext.getCurrentInstance().addMessage("Invalid parameters for the contract",new FacesMessage( "Invalid parameters for the contract"));
				}
			}
			else{
				System.out.println("Non-existing email");
			}
		}
		
	}

	public Contract getNewContract() {
		if(newContract == null)
			newContract = new Contract();
		return newContract;
	}
	
	public void loadContract(Long id) throws IOException{
		selectedContract = contractService.getContractById(id);
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		exc.getFlash().put("contract",selectedContract);
		exc.redirect(exc.getRequestContextPath() + "/contract.xhtml");
	}
	
	public boolean checkEmail() {
        if(regService.isEmailExists(signingPlayerEmail)){
        	return true;
        }
        //Ajax.oncomplete("invalidEmail()");
        return false;
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

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Contract getSelectedContract() {
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		return (Contract)exc.getFlash().get("contract");
		//return selectedContract;
	}

	public void setSelectedContract(Contract selectedContract) {
		this.selectedContract = selectedContract;
	}
}
