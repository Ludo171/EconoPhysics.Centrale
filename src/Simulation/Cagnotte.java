package Simulation;

public class Cagnotte {

	private double V;
	
	public void Cagnotte(double initialAmountCagnotte){
		this.V = initialAmountCagnotte;
	}
	
	public void gainCagnotte(double montant, Agent employeur){
		employeur.setRichesse(employeur.getRichesse() + montant);
		this.setV(this.getV()-montant);
	}

	public double getV() {
		return V;
	}

	public void setV(double v) {
		V = v;
	}
	
	
	
	
}
