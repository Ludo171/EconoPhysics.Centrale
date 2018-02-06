package Règles;
import Simulation.*;
import java.util.ArrayList;
import java.util.Random;

public class Regle_selection {

	
	
	public int run(ArrayList<Agent> listeAgents){
		
		int N = listeAgents.size();
		int selectedAgent = (int) (Math.random()*N)+1;
		
		return selectedAgent;
	}
	
	
}
