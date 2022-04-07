package Projet;

public class ChefBrico extends Chef implements Bricoler {

	private Meuble meuble_en_construction;
	
	public ChefBrico(String nom, String prenom, Entrepot e) {
		super(nom, prenom, e);
	}
	
	public void monterMeuble(Meuble m) {
		this.setTempsTravailRestant(m.getDureeConstruction());
		meuble_en_construction=m;
	}


	public Meuble getMeuble_en_construction() {
		return meuble_en_construction;
	}

	public void setMeuble_en_construction(Meuble meuble_en_construction) {
		this.meuble_en_construction = meuble_en_construction;
	}
}
