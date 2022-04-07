package Projet;

public class Lot {
	
	private PieceDetachee contenu;
	private static int count_id = 0;
	private int id;
	private int volume;

	
	public void setContenu(PieceDetachee contenu) {
		this.contenu = contenu;
	}

	
	public Lot() {
		this.id = Lot.count_id;
		Lot.count_id++;
		
	}
	
	public Lot(PieceDetachee p, int volume) {
		this.id = Lot.count_id;
		this.volume = volume;
		this.contenu = p;
		Lot.count_id ++;
	}
	
	public double getPrixPiece() {
		return this.contenu.getPrix();
	}
	
	public double getPrixTotal() {
		return this.contenu.getPrix() * this.volume;
	}
	
	public PieceDetachee getContenu(){
		return this.contenu;
	}
	
	public String getNom() {
		return this.contenu.getNom();
	}
	
	public int getVolume() {
		return this.volume;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void ajouterVolume(int v) {
		this.volume += volume;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Lot lot = (Lot) o;
		return id == lot.id;
	}

	public String toString() {
		String s = "";
		s += "Nom : " + this.getNom() + "\n";
		s += "Volume :" + this.getVolume() + "\n";
		return s;
	}



}
