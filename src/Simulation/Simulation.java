package Simulation;

import java.util.ArrayList;

public class Simulation {

	private double M;
	private int N;
	private double minSalary, maxSalary;
	private ArrayList<Agent> agents;
	public int indexActif;
	
	
	
	public Simulation(){
		this.M = 1000;
		this.N = 10;
		this.minSalary = 0.1*this.M/this.N;
		this.maxSalary = 0.9*this.M/this.N;
	}
	
	
	public void init(){
		
		for (int i = 0; i < N; i++) {
			agents.add(new Agent(M/N, "Unemployed"));
		}
		
	}
	
	public void simulation(){
		this.indexActif = (int) (Math.random()*N)+1;
		
	}
	
	
	
}
