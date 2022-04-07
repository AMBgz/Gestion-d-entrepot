package Projet;

import java.util.ArrayList;
import java.util.Random;


public class RandomInstructions {
	
	
	private ArrayList<String> instructions;
	private Random gen;
	private int id = 0;
	private int dureeMax = 8; // duree maximum pour monter un meuble, pourra etre param
	private ArrayList<String> piecesDetachees;
	private int volumeMax;
	
	public RandomInstructions(int volume) {
		this.instructions = new ArrayList<String>();
		this.gen = new Random();
		this.piecesDetachees = new ArrayList<String>();
		this.volumeMax = volume;
	}
	
	
	public ArrayList<String> getNewInstructions(int n) {
		
		/**
		 * Fonctions permettant de creer de nouvelles instructions aleatoires
		 */
		
		// on vide la liste d'abord
		this.id = 0;
		this.instructions.clear();
		this.initPiecesDetachees(20);
		
		// ensuite on produit n instructions
		for (int i = 0; i < n; i++) {
			this.generateNewInstruction();
		}
		
		return this.instructions;
	}
	
	public void initPiecesDetachees(int n) {
		/**
		 * fonction qui genere des pieces detachees pour l'utiliser dans la simulation
		 */
		for (int i = 0 ; i < n; i++) {
			this.piecesDetachees.add(this.randomAlphaString(10));
		}
	}
	
	public void generateNewInstruction() {
		/**
		 *  Methode qui permet de generer 1 seule instruction
		 */
		
		
		int r = gen.nextInt(100);
		
		/** on a 10% de chance d'avoir rien
		* 40% de chance d'avoir une commande de meuble
		* 50 % de chance d'avoir un nouveau lot 
		 */
		
		if (r <= 10) {
			this.generateRien();
		} else if (r <= 50) {
			this.generateMeuble();
		} else {
			this.generateLot();
		}
	}
	
	public void generateRien() {
		this.instructions.add("<" + String.valueOf(this.id) + ">" + "rien");
		this.id++;
	}
	public void generateMeuble() {
		String s1 = "<" + String.valueOf(this.id) + ">" + "meuble";
	    int length = 15;
		String nom = this.randomAlphaString(length);
		Piece p = Piece.randomPiece(gen);
		int duree = 1 + gen.nextInt(this.dureeMax);
		int nombreLot = 1 + gen.nextInt(5);
		String s2 = "";
		for (int i = 0; i<nombreLot; i++) {
			s2 += "<" + this.generatePieceDetachee() +">" + "<" + String.valueOf(gen.nextInt(10)) + ">";
		}
		this.instructions.add(s1 + 
				"<" + nom + ">" + 
				"<" + p.toString() + ">" + 
				"<" + duree +  ">" + 
				"<" + nombreLot + ">" +  s2);
		this.id++;
	}
	public void generateLot() {
		String s = "<" + String.valueOf(this.id) + ">" + "lot";
		
		String nom = this.randomAlphaString(15);
		int poids = 1 + gen.nextInt(100);
		int prix = 1 + gen.nextInt(1000);
		int volume = 1 + gen.nextInt(this.volumeMax);
		this.instructions.add( s + 
				"<" + nom + ">" +
				"<" + poids + ">" + 
				"<" + prix + ">" + 
				"<" + volume + ">");
		this.id++;

	}
	
	public String randomAlphaString(int l) {
		/**
		 * Retourne une chaine de caractere alphabetique aleatoire de longueur
		 */
		return gen.ints(97, 123).limit(l)
		.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		.toString();
	}
	
	public String generatePieceDetachee() {
		/**
		 * retourne une piece detachee au hasard
		 */
		return this.piecesDetachees.get(gen.nextInt(this.piecesDetachees.size()));
	}
	

	


}
