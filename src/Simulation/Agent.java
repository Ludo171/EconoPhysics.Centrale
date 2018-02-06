package Simulation;

import java.util.ArrayList;

public class Agent {
	
	
	public static int compteurAgentId;
	private int id;
	private String name;
	private double richesse;
	private String status;
	private Agent employeur;
	private ArrayList<Agent> employes;
	private double maxSalaire;
	private double minSalaire;
	
	public Agent(double richesseInitiale, String initialStatus, double minSalaire, double maxSalaire){
		
		
		
		this.id = this.compteurAgentId;
		this.compteurAgentId +=1;
		this.name = "agent" + id;
		this.richesse = richesseInitiale;
		this.status = initialStatus;
		System.out.println(this.name);
		this.employeur = null;
		this.employes = new ArrayList<Agent>();
		this.minSalaire = minSalaire;
		this.maxSalaire = maxSalaire;
		
		
	}

	
	public void consommer(Cagnotte cagnotte){
		
		double consommation = Math.random()*this.getRichesse();
		this.setRichesse(this.getRichesse() - consommation);
		cagnotte.setV(cagnotte.getV() + consommation);
		
	}
	
	public void payerEmployes(){
		
		double salaire;
		
		for (Agent agent : this.getEmployes()) {
			
			salaire = Math.random()*(this.maxSalaire-this.minSalaire)+this.minSalaire;
			
			if(salaire < this.getRichesse()) {
				this.setRichesse(this.getRichesse() - salaire);
				agent.setRichesse(agent.getRichesse() + salaire);
			}
			else{
				salaire = Math.random()*this.getRichesse();
				this.setRichesse(this.getRichesse() - salaire);
				agent.setRichesse(agent.getRichesse() + salaire);
			}
						
		}
		
	}
	
	public static int getCompteurAgentId() {
		return compteurAgentId;
	}

	public static void setCompteurAgentId(int compteurAgentId) {
		Agent.compteurAgentId = compteurAgentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRichesse() {
		return richesse;
	}

	public void setRichesse(double richesse) {
		this.richesse = richesse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Agent getEmployeur() {
		return employeur;
	}

	public void setEmployeur(Agent employeur) {
		this.employeur = employeur;
	}

	public ArrayList<Agent> getEmployes() {
		return employes;
	}

	public void addEmployes(Agent employe) {
		this.employes.add(employe);
		this.setStatus("Employeur");
		employe.setStatus("Employe");
		employe.setEmployeur(this);
	}

	public void removeEmployes(Agent employe){
		this.employes.remove(employe);
		employe.setStatus("Chomeur");
		
		if(this.getEmployes().isEmpty()){
			this.setStatus("Chaumeur");
		}
		
	}
	
	
	
	
}
