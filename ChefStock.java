package Projet;

public class ChefStock extends Chef implements GererStock {

	
	public ChefStock(String nom, String prenom, Entrepot e) {
		super(nom, prenom, e);
	}

	public void ajouterLot(Lot l) {
		if(!this.entrepot.canStock(l.getVolume())){
			//System.out.println("Le lot ne peut pas être stocké.");
			return; // on verifie qu'on peut stocker
		}
		int[] coord= this.entrepot.placeLibre(l.getVolume());
		for (int i = 0; i < l.getVolume(); i++) {
			this.entrepot.deposerPiece(coord[0], coord[1] + i, l);
		}
	}
	public void deplacerLot(int id, int i, int j) {
		this.entrepot.deplacer(id, i, j);
	};
	public void retirerLot(Entrepot.LotARetirer l) {
		this.entrepot.supprimerLot(l);
	}
}
