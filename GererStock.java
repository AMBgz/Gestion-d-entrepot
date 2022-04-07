package Projet;

public interface GererStock {
	
	public void ajouterLot(Lot l);
	public void deplacerLot(int id, int i, int j);
	public void retirerLot(Entrepot.LotARetirer id);
}
