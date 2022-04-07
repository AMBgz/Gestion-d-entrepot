package Projet;

import java.util.ArrayList;

public abstract class Chef extends Personnel {
	
	private ArrayList<Ouvrier> equipe;
	
	public Chef(String nom, String prenom, Entrepot e) {
		super(nom, prenom, e);
		this.equipe = new ArrayList<Ouvrier>();
	}
	
	public ArrayList<Ouvrier> getEquipe(){
		return this.equipe;
	}

}
