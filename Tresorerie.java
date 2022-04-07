package Projet;

public class Tresorerie {
	
	private double solde;
	
	public Tresorerie(){
		solde=0;
	}
	public double getSolde() {
		return this.solde;
	}
	
	public void ajouterArgent(double n) {
		this.solde += n;
	}
	
	public boolean canWithdraw(int n) {
		return this.solde >= n;
	}
	
	public void retirerArgent(int n) {
		this.solde -= n;
	}

}
