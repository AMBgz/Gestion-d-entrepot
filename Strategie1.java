package Projet;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Strategie1 extends Entrepot implements Strategie{

		boolean peut_virer;
		boolean peut_recruter;

		// CONSTRUCTEUR
	    public Strategie1(int longueur, int hauteur) {
	    	super(longueur, hauteur);
	    	peut_recruter=true;
	    	peut_recruter=true;
	    }





	public void recruter(){

	    	if(!peut_recruter){
	    		return;
			}

	    	System.out.println(" Un personnel va être recruté, veuillez inscrire ci-dessous: son nom, son prénom, (et si Ouvrier) Sa Spécialité");
	    	Scanner sc= new Scanner(System.in);
	    	String nom,prenom;
	    	if(getEffectif()-getStaff().size()%4!=0){
	    		String piece;
	    		Chef c=null;
	    		Piece piece1;
	    		System.out.println("Nom :");
	    		nom=sc.next();
				System.out.println("Prénom :");
				prenom=sc.next();
				do{
					System.out.println("Répondez par le chiffre associé à la salle voulue:");
					System.out.println("1: Salle à Manger.    3: Salle de Bain      5: Cuisine");
					System.out.println("2: WC                 4: Chambre            6: Salon");
					piece = sc.next();
				}while(!piece.matches("[1-6]+"));
				piece1=Piece.stringToPiece(Piece.IntToStringPiece(piece));
				for(Chef k:getStaff()){
					if(k.getEquipe().size()<4){
						c=k;
						break;
					}
				}
				if(c!=null) c.getEquipe().add(new Ouvrier(nom,prenom,c,this,piece1));
			}
	    	else{
	    		int cpt_stock=0;
	    		for(Chef p:getStaff()){
	    			if(p instanceof ChefStock){
	    				cpt_stock+=1;
					}
				}
	    		if(cpt_stock<=getStaff().size()-cpt_stock){
					System.out.println("Nom :");
					nom=sc.next();
					System.out.println("Prénom :");
					prenom=sc.next();
					getStaff().add(new ChefStock(nom,prenom,this));
				}else {
					System.out.println("Nom :");
					nom=sc.next();
					System.out.println("Prénom :");
					prenom=sc.next();
					getStaff().add(new ChefBrico(nom,prenom,this));
				}
			}


	    }

	@Override
	public void virer(Personnel p) {
			if (peut_virer = true) {
				boolean renvoi_effectue = false;
				for (Chef c : staff) {
					if (c.getId() == p.getId()) {
						if (c.getEquipe().size() == 0) {
							staff.remove(c);
							renvoi_effectue = true;
							break;
						} else {
							for (Ouvrier o : c.getEquipe()) {
								if (o.getId() == p.getId()) {
									c.getEquipe().remove(c);
									renvoi_effectue = true;
									break;
								}
							}
						}
					}
					if (renvoi_effectue) break;
				}
				if (renvoi_effectue) {
					peut_virer = false;
					System.out.println("L'employé : " + p.getNom() + " " + p.getPrenom()+" a été renvoyé.");
				}
			}
	    }


	@Override
	public void doit_Recruter() { //Stratégie Triviale, on recrute si on a personne pour effectuer une tâche
	    	if(!peut_recruter)return;
	    	boolean doit_Recruter=false;
	    	if((lotsARetirer.size()+lotsAajouter.size())>getEffectif()){
	    		recruter();
			}
	}

	@Override
	public void doit_Virer() {//Stratégie Triviale, on vire  si on a trop de personnel pour le nombre de tâche
		if(!peut_virer) return;
		if((lotsARetirer.size()+lotsAajouter.size())>getEffectif()-1) {
			for (Chef c : getStaff()) {
				if (c.getEquipe().size()==0){
					if(c instanceof ChefBrico && ((ChefBrico) c).getMeuble_en_construction()!=null) continue;
					virer(c);
					return;
				}
			}
			for(Chef c:getStaff()){
				for(Ouvrier o:c.getEquipe()){
					if(!o.isOccupe_Meuble()){
						virer(o);
					}
				}
			}
		}

	}

	public void supprimerLot(int id) {
			for (int i = 0; i<this.m; i++) {
				for (int j = 0; j<this.n; j++) {
					if (this.rangees[i][j].isPresent()) {
						Lot l = this.rangees[i][j].get();
						if (l.getId() == id) {
							this.rangees[i][j] = Optional.empty();
						}
					}
				}
			}
		}

}


