package Projet;

import java.util.ArrayList;

public interface Strategie {


	public void setMontageEnCours(Meuble mbl);
	public void recruter();
	public void virer(Personnel P);
    public void doit_Recruter();
    public void doit_Virer();
    public void deposerPiece(int i, int j, Lot l);
	public Personnel affecterPersonnel(String type_tache,Piece salle);
	public void supprimerLot(int id);

}
