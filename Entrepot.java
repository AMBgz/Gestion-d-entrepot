package Projet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Entrepot {
    protected Tresorerie tresorerie;
    protected Optional<Lot>[][] rangees;
    protected ArrayList<Chef> staff;
    protected ArrayList<Meuble> montageEnCours;
    protected ArrayList<Lot> lotsAajouter;
    protected ArrayList<LotARetirer> lotsARetirer;
    
	boolean peut_virer;
	boolean peut_recruter;
    protected int n;
    protected int m;

	protected class LotARetirer {
		int id;
		int longueur;
		int i;
		int j;
		public LotARetirer(int id, int i, int j, int l ) {
			this.id = id;
			this.longueur = l;
			this.i = i;
			this.j = j;
		}

		@Override
		public String toString() {
			return "LotARetirer{" +
					"id=" + id +
					", longueur=" + longueur +
					", i=" + i +
					", j=" + j +
					'}';
		}

		public int getId() {
			return this.id;
		}
	}

	public boolean alreadyIn(Lot l, ArrayList<LotARetirer> lots) {
		int id = l.getId();
		for (LotARetirer t : lots) {
			if (id == t.id) {
				return true;
			}
		}
		return false;
	}


	public ArrayList<LotARetirer> lotsNecessaires(Meuble mbl){
		ArrayList<LotARetirer> lots = new ArrayList<>();
		HashMap<String, Integer> composants = mbl.getComposants();

		// On parcours chaque composant a reunir pour le meuble
		for (String s : composants.keySet()) {
			int capacite = composants.get(s);

			// On parcours les rangees jusqu'a trouver assez de lot
			for (int i = 0; i < this.m ; i++) {
				for (int j = 0; j < this.n ; j++) {

					if (this.rangees[i][j].isPresent()) {
						Lot currentLot = this.rangees[i][j].get();

						// Si le lot est bien du type qu'on cherche

						if (currentLot.getNom().equalsIgnoreCase(s) && !this.alreadyIn(currentLot, lots)) {

							// S'il n'y a pas assez de volume, alors on continue de chercher
							if (currentLot.getVolume() < capacite) {
								capacite -= currentLot.getVolume();
								lots.add(new LotARetirer(currentLot.getId(), i, j, currentLot.getVolume()));
							}
							// S'il y a juste assez de volume, on arrete de chercher
							if (currentLot.getVolume() == capacite) {
								lots.add(new LotARetirer(currentLot.getId(), i, j, currentLot.getVolume()));
								break;
							}
							// S'il y a trop de volume, on retire qu'une portion du lot et on arrete
							else {
								lots.add(new LotARetirer(currentLot.getId(), i, j, capacite));
							}

						}

					}

				}
			}
		}

		mbl.setPrix(this.calculerPrix(lots));
		return lots;
	}

	public double calculerPrix(ArrayList<LotARetirer> lots) {
		double somme = 0;
		for (LotARetirer l : lots) {
			somme += l.longueur * this.rangees[l.i][l.j].get().getPrixPiece();
		}
		return somme;
	}


	// CONSTRUCTEUR
    @SuppressWarnings("unchecked")
	public Entrepot(int longueur, int hauteur) {
		peut_recruter=true;
		peut_virer=true;
    	this.n = longueur;
    	this.m = hauteur;
    	this.tresorerie = new Tresorerie();
    	this.lotsAajouter= new ArrayList<>();
    	this.staff = new ArrayList<Chef>();
    	this.montageEnCours = new ArrayList<Meuble>();
    	this.lotsARetirer = new ArrayList<LotARetirer>();
    	this.rangees = (Optional<Lot>[][]) new Optional[m][n];
    	for (int i = 0; i<this.m; i++) {
    		for (int j = 0; j<this.n; j++) {
    			this.rangees[i][j] = Optional.empty();
    		}
    	}
    }


	public void setMontageEnCours(Meuble mbl) {
		this.montageEnCours.add(mbl);
	}

	public ArrayList<Chef> getStaff() {
		return staff;
	}
    public double getSolde() {
    	return this.tresorerie.getSolde();
    }

    public ArrayList<LotARetirer> getLotsARetirer(){
    	return this.lotsARetirer;
    }

    public void payerPersonnel(){
    	/*
    	 *  Fonction qui parcours tout le personnel et le paie
    	 */
    	for (Chef c : this.staff) {
    		this.tresorerie.retirerArgent(10); // On enleve 10 par chef
    		for (Ouvrier o : c.getEquipe()) {
    			this.tresorerie.retirerArgent(5); // On enleve 5 par ouvrier
    		}
    	}
    }

	public String inventaire(){
		ArrayList<Lot> a = new ArrayList<Lot>();
		String s = "";
		for (int i = 0; i<this.m; i++) {
			for (int j = 0 ; j < this.n ; j++) {
				if (this.rangees[i][j].isPresent()) {
					if (! a.contains(this.rangees[i][j].get())){
						a.add(this.rangees[i][j].get());
						s = s + this.rangees[i][j].get().toString() + "\n";
					}
				}
			}
		}
		return s;
	}

    public boolean hasStock(Meuble b){
        /*
         * Verifie qu'on a bien en stock les differents composants du meubles
         */
    	HashMap<String, Integer> comp = b.getComposants();
    	/* on parcours chaque composant, on compte combien il y en a dans l'entrepot, si il n'y a pas
    	 * assez de quantite pour un composant, alors on ne peut pas construire le meuble
    	 */
    	for (Map.Entry<String,Integer> m : comp.entrySet()) {
    		if (this.countPiece((String) m.getKey()) < (Integer) m.getValue()) {
    			return false;
    		}
    	}
    	System.out.println("L'entrepot dispose des Pièces nécessaires à la construction du meuble");
    	return true;
    }
    
    private int countPiece(String s) {
    	int cpt = 0;
    	for (int i = 0; i<this.m; i++) {
    		for (int j = 0 ; j<this.n; j++) {
    			if (this.rangees[i][j].isPresent()) {
    				PieceDetachee p = this.rangees[i][j].get().getContenu();
    				if (p.getNom().equalsIgnoreCase(s)) {
    					cpt++;
    				}
    			}
    		}
    	}
    	return cpt;
    }

    
    public boolean canStock(int l){
    	/*
    	 * Algorithme permettant de verifier s'il y a une zone vide contigu de longueur l
    	 */
    	
    	for (int i = 0; i< this.m; i++) {
    		int c = 0; // compteur de place vide
    		for (int j = 0 ; j < this.n; j++) {
    			
    			if (this.rangees[i][j].isEmpty()) {
    				c++;
    			} else {
    				c = 0;
    			}
    			if (c >= l) return true;
    		}
    	}
    	
        return false;
    }
    
    public boolean canStock(Lot l) {
    	return this.canStock(l.getVolume());
    }


	public ArrayList<Lot> getLotsAajouter() {
		return lotsAajouter;
	}

	public int[] placeLibre(int v) {
    	/*
    	 * Renvoie les coordonnees de la place libre de longueur v minimum
    	 */
    	int[] ans = {-1, -1};
    	
    	for (int i = 0; i< this.m; i++) {
    		int c = 0; // compteur de place vide
    		for (int j = 0 ; j < this.n; j++) {
    			if (this.rangees[i][j].isEmpty()) {
    				c++;
    			} else {
    				c = 0;
    			}
    			if (c >= v) {
    				ans[0] = i;
    				ans[1] = j-v+1;
    				return ans;
    			}
    		}
    	}
    	
        return ans;
    }
    
    public void deposerPiece(int i, int j, Lot l) {
    	assert(this.rangees[i][j].isEmpty()); // on verifie que l'emplacement soit vide
    	this.rangees[i][j] = Optional.of(l);
    	
    }
    
    public boolean personnelDispo(String type_tache,Piece Salle){
    	for(Chef p:staff){
			if(p.getClass().getName().equals("Projet.Chef"+type_tache) && p.isDisponible()){
				return true;
			}else{
				for (Ouvrier t:p.getEquipe()) {
					if(type_tache.equals("Brico")){
						if(!t.isOccupe_Meuble()) return true;
					}
					if(t.isDisponible()&&t.getSpecialite()==Salle)return true;
				}
			}
		}
        return false;
    }

	public int nbr_personnelDispo(String type_tache,Piece salle){
    	int cpt=0;
		for(Chef p:staff){
			if(p.getClass().getName().equals("Projet.Chef"+type_tache) && p.isDisponible()){
				cpt+=1;
			}else{
				for (Ouvrier t:p.getEquipe()) {
					if(type_tache=="Brico"){
						if(t.getSpecialite()!=salle) continue;
						if(t.isOccupe_Meuble()) continue;
					}
					if(t.isDisponible()&&t.getSpecialite()==salle) cpt+=1;
				}
			}
		}
		return cpt;
	}

	public Personnel affecterPersonnel(String type_tache,Piece salle){
		for(Chef p:staff){
			if(p.getClass().getName().equals("Projet.Chef"+type_tache) && !p.isOccupe()){
				p.setOccupe(true);
				return p;
			}else{
				for (Ouvrier t:p.getEquipe()) {
					if(type_tache=="Brico"){
						if(t.getSpecialite()!=salle) continue;
						if(!t.isOccupe_Meuble()) return t;
					}
					if(!t.isOccupe()){
						t.setOccupe(true);
						if(type_tache=="Brico") t.setOccupe_Meuble(true);
						return t;
					}
				}
			}
		}
		return null;
	}
	
	public void deplacer(int id, int i, int j) {
		/*
		 * Fonction qui deplace le lot id jusqu'aux coordonnees i,j
		 */
		
		// on cherche le lot id
		int[] location = new int[2];
		boolean trouve = false;
		for (int k = 0; k<this.m; k++) {
			for (int l=0; l<this.n; l++) {
				if (!trouve && this.rangees[i][j].isPresent()) {
					Lot a = this.rangees[i][j].get();
					if (a.getId() == id) {
						location[0] = k; location[1] = l;
						trouve = true;
					}
				}
			}
		}
		
		if (!trouve) return;
		Lot lot = this.rangees[location[0]][location[1]].get();
		int v = lot.getVolume();
		if (!this.placeDisponible(i, j, v)) {
			return;
		}
		// on retire le lot de l'ancien emplacement
		this.supprimerLot(lot.getId(),null);
		
		// on ajoute le lot a l'emplacement prevu
		for (int u = j; u<j+v; u++) {
			this.rangees[i][u] = Optional.of(lot);
		}

		
	}
	
	
	
	public boolean placeDisponible(int i, int j, int v) {
		/*
		 * Fonction qui retourne vrai s'il y a v place a l'endroit i,j de l'entrepot
		 */
		for (int u = j; u<j+v; u++) {
			if (this.rangees[i][u].isPresent()) {
				return false;
			}
		}
		return true;
	}

	public void supprimerLot(int id,Meuble mbl) {

		// On parcours chaque emplacement de l'entrepot
		for (int i = 0; i<this.m; i++) {
			for (int j = 0; j<this.n; j++) {
				// Si on trouve un lot
				if (this.rangees[i][j].isPresent()) {
					Lot l = this.rangees[i][j].get();
					// On regarde si le lot est celui qu'on veut supprimer
					if (l.getId() == id) {
						// On supprime la piece
						this.supprimerPiece(i, j);
					}
				}
			}
		}
	}
	
	public void supprimerLot(LotARetirer l) {
		for (int k = 0; k < l.longueur; k++) {
			this.supprimerPiece(l.i, l.j+k);
		}
	}
	
	public void supprimerLot(ArrayList<LotARetirer> lots, int id) {
		for (LotARetirer l : lots) {
			if (l.id == id) {
				this.supprimerLot(l);
			}
		}
	}
	
	public void supprimerPiece(int i, int j) {
		this.rangees[i][j] = Optional.empty();
	}

	public void setLotsARetirer(ArrayList<LotARetirer> lotsARetirer) {
		this.lotsARetirer.addAll(lotsARetirer);
	}

	public void afficherRangees() {
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				Optional<Lot> l = this.rangees[i][j];
				if (l.isPresent()) {
					System.out.print(l.get().getId() + " ");
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
	}

	public int getEffectif(){
		int cpt=0;
		for(Chef c: getStaff()){
			cpt+=1;
			for(Ouvrier o:c.getEquipe()){
				cpt+=1;
			}
		}
		return cpt;
	}

	public boolean tacheLot(ArrayList<String> tache) {
			PieceDetachee p=new PieceDetachee(tache.get(2),Integer.parseInt(tache.get(3)),Double.parseDouble(tache.get(4)));
			Lot l=new Lot(p,Integer.parseInt(tache.get(5)));
			lotsAajouter.add(l);
			return true;
	}

	public void setTresorerie(double n) {
		this.tresorerie.ajouterArgent(n);
	}
}
