package Projet;

import java.util.Scanner;

public class main {
    public static void main(String args[]){

    	Scanner scan = new Scanner(System.in);
        int n;
        int m;
    	// On demande les dimensions de l'entrepot
    	
    	System.out.println("Quelle hauteur d'entrepot voulez vous ?");

        n = scan.nextInt();
        System.out.println("Quelle longueur voulez vous ?");

        m = scan.nextInt();

        // On choisit la strategie
    	
    	Strategie1 entrepot = new Strategie1(n,m);
    	
    	
        
        // On forme l'equipe de depart
    	
        ChefStock B=new ChefStock("Bob","Dylan", entrepot);
        entrepot.getStaff().add(B);
        entrepot.getStaff().get(0).getEquipe().add(new Ouvrier("Fred","Sabatier",B,entrepot,Piece.CUISINE));
        entrepot.getStaff().get(0).getEquipe().add(new Ouvrier("Bob","Sabatier",B,entrepot,Piece.CHAMBRE));
        entrepot.getStaff().get(0).getEquipe().add(new Ouvrier("Fred","Dylan",B,entrepot,Piece.SALON));
        entrepot.getStaff().get(0).getEquipe().add(new Ouvrier("Jean","Trombonne",B,entrepot,Piece.SALLE_DE_BAIN));
        entrepot.getStaff().get(0).getEquipe().add(new Ouvrier("Michel","Trompette",B,entrepot,Piece.SALLE_A_MANGER));

        
        // On initialise la simulation
        
        Simulation<Strategie1> S=new Simulation<Strategie1>(entrepot);
        
        
        // lancement de la simulation
        
        S.run(args);
    }
}
