package Projet;

public class PieceDetachee {
    private double prix;
    private int poids;
    private String nom;

    public PieceDetachee(String name,int masse,double pri){
        this.nom=name;
        this.poids=masse;
        this.prix=pri;

    }
    public PieceDetachee(String nom){
        this.nom=nom;
    }
    
    public double getPrix() {
    	return this.prix;
    }

    public int getPoids() {
    	return this.poids;
    }
    public String getNom() {
    	return this.nom;
    }
}
