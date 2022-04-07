package Projet;

public abstract class Personnel<T extends Entrepot & Strategie> {
	protected T entrepot;
	private static int id_count = 0;
	private int id;
	private String nom;
	private String prenom;
	private int tempsTravailRestant;
	private boolean occupe;

	public boolean isOccupe() {
		return occupe;
	}

	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}
	public Personnel(String nom,String prenom, T e) {
		this.nom = nom;
		this.prenom = prenom;
		this.entrepot=e;
		this.id = id_count;
		Personnel.id_count ++;
		this.tempsTravailRestant = 0;
	}

	public Entrepot getEntrepot() {
		return entrepot;
	}

	@Override
	public String toString() {
		return "Personnel{" +
				" id=" + id +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", occupe='" + occupe + '\'' +
				", tempsTravailRestant=" + tempsTravailRestant +
				'}';
	}

	public int getId() {
		return this.id;
	}
	public String getNom() {
		return this.nom;
	}
	public String getPrenom() {
		return this.prenom;
	}
	public boolean isDisponible() {
		return !this.occupe;
	}
	public int getTempsTravailRestant() {
		return this.tempsTravailRestant;
	}
	public void setTempsTravailRestant(int t){
		this.tempsTravailRestant = t;
	}



	public void ajouterTempsTravailRestant(int t) {
		if ((this instanceof ChefBrico && this.isOccupe())){
			if(this.tempsTravailRestant+t<=0){
				entrepot.montageEnCours.remove(((ChefBrico) this).getMeuble_en_construction());
				entrepot.setTresorerie(((ChefBrico) this).getMeuble_en_construction().getPrix());
				System.out.println("Le meuble "+((ChefBrico) this).getMeuble_en_construction().getNom()+" a été vendu, la trésorerie a été incrémenté de : "+((ChefBrico) this).getMeuble_en_construction().getPrix());
				System.out.println("Solde actuel: "+ entrepot.getSolde());
				((ChefBrico) this).setMeuble_en_construction(null);
			}
		}
		if((this instanceof Ouvrier && ((Ouvrier) this).isOccupe_Meuble())){
			if(this.tempsTravailRestant+t<=0){
				entrepot.montageEnCours.remove(((Ouvrier) this).getMeuble_en_construction());
				entrepot.setTresorerie(((Ouvrier) this).getMeuble_en_construction().getPrix());
				System.out.println("Le meuble "+((Ouvrier) this).getMeuble_en_construction().getNom()+" a été vendu, la trésorerie a été incrémenté de : "+((Ouvrier) this).getMeuble_en_construction().getPrix());
				System.out.println("Solde actuel: "+ entrepot.getSolde());
				((Ouvrier) this).setMeuble_en_construction(null);
				((Ouvrier) this).setOccupe_Meuble(false);
			}
		}


		if(this.tempsTravailRestant<=0) occupe=false;
		this.tempsTravailRestant += t;
	}
	
}
