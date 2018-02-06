package Simulation;

public class Agent {
	
	
	public static int compteurAgentId;
	private int id;
	private String name;
	private double wealth;
	private String status;
	
	
	public Agent(double initialWealth, String initialStatus){
		
		this.compteurAgentId +=1;
		this.id = this.compteurAgentId;
		this.name = "agent" + id;
		this.wealth = initialWealth;
		this.status = initialStatus;
		
	}
	
	
	
	
	
	
}
