package webcontrollers;

import java.text.ParseException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import entities.Contract;
import entities.Team;
import services.ContractService;
import services.PlayerService;
import services.TeamService;

@Named
@RequestScoped
public class ContractController {
	
	@Inject
	private SecurityController securityController;
	@EJB
	private PlayerService playerSrevice;
	@EJB
	private TeamService teamSrevice;
	@EJB
	ContractService contractService;
	
	
	private Contract newContract;
	private String signingPlayerEmail;
	private String validDate;
	private String amount;
	private Contract selectedContract;
	
	public void sendContract(){
		Team currentTeam = securityController.getCurrentTeam();
		if(currentTeam != null){
			newContract.setSignerTeam(currentTeam);
			try{
				teamSrevice.saveContract(newContract, signingPlayerEmail, validDate, amount);
			} catch (ParseException e) {
				FacesContext.getCurrentInstance().addMessage("Invalid date format",new FacesMessage( "Invalid date fromat"));
			} catch(NoResultException e){
				FacesContext.getCurrentInstance().addMessage("Invalid email-address",new FacesMessage( "Invalid email-address"));
			} catch(NumberFormatException e){
				FacesContext.getCurrentInstance().addMessage("Invalid amount",new FacesMessage( "Invalid amount"));
			}catch(Exception e){System.out.println("HIBAAAAAAAA");}
		}
		
	}

	public Contract getNewContract() {
		if(newContract == null)
			newContract = new Contract();
		return newContract;
	}
	
	public void loadContract(Long id){
		System.out.println("aaaaaaaaaaaaaaaaaaa:" +id);
		selectedContract = contractService.getContractById(id);
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
		return selectedContract;
	}

	public void setSelectedContract(Contract selectedContract) {
		this.selectedContract = selectedContract;
	}
	
	
	
}
