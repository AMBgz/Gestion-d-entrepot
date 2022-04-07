package Projet;

import java.util.Map;

public class Ouvrier extends Personnel implements Bricoler, GererStock {
	
	private Chef chef;
	private Piece specialite;
	private boolean Occupe_Meuble;
	private Meuble meuble_en_construction;

	public Ouvrier(String nom, String prenom, Chef f, Entrepot e,Piece Specialite) {
		super(nom, prenom, e);
		this.chef = f;
		this.specialite=Specialite;
	}
	
	public Chef getChef() {
		return this.chef;
	}
	
	public Piece getSpecialite() {
		return this.specialite;
	}

	public void monterMeuble(Meuble m) {
		assert(this.specialite == m.getSalle()); // on verifie que la specialite de l'ouvrier est la bonne
		this.setTempsTravailRestant(m.getDureeConstruction());
		setOccupe_Meuble(true);
		meuble_en_construction=m;
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
	}

	public void retirerLot(Entrepot.LotARetirer l) {
		this.entrepot.supprimerLot(l);
	}
	
	public void pieces_meuble(Meuble mbl){
		for(Map.Entry<String, Integer> k: mbl.getComposants().entrySet()){
			k.getKey();
		}
	}

	public boolean isOccupe_Meuble() {
		return Occupe_Meuble;
	}

	public Meuble getMeuble_en_construction() {
		return meuble_en_construction;
	}

	public void setMeuble_en_construction(Meuble meuble_en_construction) {
		this.meuble_en_construction = meuble_en_construction;
	}

	public void setOccupe_Meuble(boolean occupe_Meuble) {
		Occupe_Meuble = occupe_Meuble;
	}
}
