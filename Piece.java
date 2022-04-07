package Projet;

import java.util.Random;

public enum Piece {
	
	SALLE_DE_BAIN,
	SALON,
	CHAMBRE,
	WC,
	SALLE_A_MANGER,
	CUISINE,
	Default;
	
	
    /**
     * Permet de transformer un String en Enum Piece
     * @param s est la string de depart
     * @return
     */
    public static Piece stringToPiece(String s) {
    	
    	// a completer avec un switch
		if(s.equalsIgnoreCase("Chambre")){
			return Piece.CHAMBRE;
		}else if(s.equalsIgnoreCase("Salle_de_Bain")){
			return Piece.SALLE_DE_BAIN;
		}else if(s.equalsIgnoreCase("WC")){
			return Piece.WC;
		}else if(s.equalsIgnoreCase("Salon")){
			return Piece.SALON;
		}else if(s.equalsIgnoreCase("Cuisine")){
			return Piece.CUISINE;
		}else if(s.equalsIgnoreCase("Salle_a_Manger")){
			return Piece.SALLE_A_MANGER;
		}else{
			return Default;
		}
    }
	
    public static String IntToStringPiece(String s) {

		// a completer avec un switch
		if(s.equals(("4"))){
			return "Chambre";
		}else if(s.equals(("3"))){
			return "Salle_de_Bain";
		}else if(s.equals(("2"))){
			return "WC";
		}else if(s.equals(("6"))){
			return "Salon";
		}else if(s.equals(("5"))){
			return "Cuisine";
		}else if(s.equals(("1"))){
			return "Salle_a_Manger";
		}else{
			return "";
		}
	}
    
    public static Piece randomPiece(Random r) {
    	Piece[] choix = {SALLE_DE_BAIN, SALON, CHAMBRE, WC, SALLE_A_MANGER, CUISINE};
    	return choix[r.nextInt(choix.length)];
    }

}
