package Simulation;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Simulation {

	private double M;
	private int N;
	private double minSalaire, maxSalaire;
	private ArrayList<Agent> agents;
	public int idActif;
	public int idConso;
	public Cagnotte cagnotte;
	public int nbToursSimu;
	
	
	
	public Simulation(){
		this.cagnotte = new Cagnotte();
		this.M = 100;
		this.N = 100;
		this.nbToursSimu = 1000;
		this.minSalaire = 0.1*this.M/this.N;
		this.maxSalaire = 0.9*this.M/this.N;
		this.agents = new ArrayList<Agent>();
	}
	
	
	public void init(){
		
		for (int i = 0; i < N; i++) {
			agents.add(new Agent(M/N, "Chomeur", this.minSalaire, this.maxSalaire ));
		}
		
	}
	
	public void afficheAgents(){
		NumberFormat format=NumberFormat.getInstance(); 
		format.setMinimumFractionDigits(2);
		
		
		
		for (Agent agent : this.agents) {
			
			if (agent.getEmployeur()==null){
				System.out.println(agent.getName() + "\t\trichesse : " + format.format(agent.getRichesse()) + "\t\t statut : " 
				+ agent.getStatus() + "\t\t employeur : " + agent.getEmployeur());
			}
			else {
				System.out.println(agent.getName() + "\t\trichesse : " + format.format(agent.getRichesse()) + "\t\t statut : " 
				+ agent.getStatus() + "\t\t employeur : " + agent.getEmployeur().getName());
			}
		
		
		
		}
		
	}
	
	public void regleSelection(){
		// ---- SELECTION A7
		System.out.println("\n----- SELECTION -----");
		this.idActif = (int) (Math.random()*N);
		System.out.println(" Agent actif : " + this.idActif);
		
		this.afficheAgents();
	}
	
	public void regleEmbauche(){
		// ---- EMBAUCHE
				if ((this.agents.get(idActif).getStatus().equalsIgnoreCase("Chomeur"))){
					// Construction du tableau des employeurs potentiels et de la richesse cumulée
					ArrayList<Agent> employeursPotentiels = new ArrayList<Agent>();
					ArrayList<Double> richesseCumulee = new ArrayList<Double>();
					richesseCumulee.add(0.0);
					double richesseMoy = 0;
					for (Agent agent : this.agents) {
						
						richesseMoy += agent.getRichesse();
						
						if ((agent.getStatus().equalsIgnoreCase("Chomeur") || agent.getStatus().equalsIgnoreCase("Employeur")) && this.idActif!=agent.getId()){
							employeursPotentiels.add(agent);
							
							if (richesseCumulee.size()!=0){
								richesseCumulee.add(richesseCumulee.get(richesseCumulee.size()-1) + agent.getRichesse());
							} else {
								richesseCumulee.add(agent.getRichesse());
							}
							
						}
						
					}	
					richesseMoy = richesseMoy/this.N;
					
					System.out.println("\nRichesse Cumulee = " + richesseCumulee);
					System.out.println("Richesse moyenne = " + richesseMoy);
					
					
					// Tirage aléatoire d'un employeur potentiel, en pondérant la proba par la richesse
					double tirageAlea = Math.random()*richesseCumulee.get(richesseCumulee.size()-1);
					boolean flag = true;
					int compteur = 0;
					System.out.println("tirageAlea : " + tirageAlea);
					while(flag && compteur<richesseCumulee.size()-1){
						if (tirageAlea>richesseCumulee.get(compteur) && tirageAlea<richesseCumulee.get(compteur+1)){
							flag = false;
						}
						else {
							compteur += 1;
						}
					}
					
					// Entretien d'embauche (la richesse de l'employeur potentiel est-elle supérieure au salaire moyen ?)
					if ( !employeursPotentiels.isEmpty() && employeursPotentiels.get(compteur).getRichesse() >= richesseMoy ){
						employeursPotentiels.get(compteur).addEmployes(this.agents.get(idActif));
					}
					
					System.out.println("\n----- EMBAUCHE -----");
					this.afficheAgents();
				}		
	}
	
	public void regleConso(){
		// ---- SELECTION B
		this.idConso = (int) (Math.random()*(N-1));
		if(this.idConso>=this.idActif){
			this.idConso+=1;
		}
		
		this.agents.get(idConso).consommer(this.cagnotte);
		
		System.out.println("\n----- CONSOMMATION -----");
		System.out.println("consommateur : " + this.agents.get(idConso).getName());
		this.afficheAgents();
		System.out.println("cagnotte : " + this.cagnotte.getV());
		
	}
	
	public void regleMarche(){
		
		double gainEntreprise = Math.random()*this.cagnotte.getV();
		if(this.agents.get(idActif).getStatus().equalsIgnoreCase("Employe")){
			this.cagnotte.gainCagnotte(gainEntreprise, this.agents.get(idActif).getEmployeur());
		}
		else if(this.agents.get(idActif).getStatus().equalsIgnoreCase("Employeur")){
			this.cagnotte.gainCagnotte(gainEntreprise, this.agents.get(idActif));
		}
		
		System.out.println("\n----- MARCHE PRODUCTION -----");
		this.afficheAgents();
		System.out.println("cagnotte : " + this.cagnotte.getV());
		
		
	}
	
	public void regleLicenciement(){
		
		if(this.agents.get(idActif).getStatus().equalsIgnoreCase("Employeur")){
			
			int nbEmployes = this.agents.get(idActif).getEmployes().size();
			int indexLicencie;
			
			while(this.agents.get(idActif).getRichesse()<nbEmployes*(minSalaire+maxSalaire)/2){
				nbEmployes-=1;
				indexLicencie = (int) Math.random()*nbEmployes;
				this.agents.get(idActif).removeEmployes(this.agents.get(idActif).getEmployes().get(indexLicencie));
			}
			
		}
		
		System.out.println("\n----- LICENCIEMENTS -----");
		this.afficheAgents();
		System.out.println("cagnotte : " + this.cagnotte.getV());
		
		
	}
	
	public void regleSalaires(){
		
		if(this.agents.get(idActif).getStatus().equalsIgnoreCase("Employeur")){			
			this.agents.get(idActif).payerEmployes();
		}
		
		System.out.println("\n----- SALAIRES -----");
		this.afficheAgents();
		System.out.println("cagnotte : " + this.cagnotte.getV());
	}
	
	public void simulation(){
		
		this.init();
		
		for (int i = 0; i < nbToursSimu; i++) {
			this.regleSelection();
			this.regleEmbauche();
			this.regleConso();
			this.regleMarche();
			this.regleSalaires();
			
			System.out.println("-----EMPLOYES PAR AGENT0------");
			for (Agent agent : this.agents.get(0).getEmployes() ) {
				System.out.println(agent.getName());
			}
			System.out.println("\n\n");
		}
		
	}
	
	public static void main(String[] args) {
		
		Simulation simu = new Simulation();
		simu.simulation();

		
	}
	
}
