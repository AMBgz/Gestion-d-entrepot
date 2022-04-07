package Projet;
import java.util.HashMap;
import java.util.Map;


public class Meuble {
    private double prix;
    private String nom;
    private Piece salle;
    private int dureeConstruction;
    private HashMap<String,Integer> pieces;
    private HashMap<String, Integer> piecesConstruction;

    public void addPiece(String p,int b) {
        if(pieces.containsKey(p)){
            pieces.put(p,pieces.get(p).intValue()+b);
        }else{
            pieces.put(p,b);
        }
    }

    public double getPrix() {
        return prix;
    }

    public void ajouterPieceConstruction(PieceDetachee p, int nombre) {
        if (this.piecesConstruction.containsKey(p.getNom())) {
            // ajout de piece
            this.piecesConstruction.put(p.getNom(), nombre + piecesConstruction.get(p.getNom()));
        } else {
            this.piecesConstruction.put(p.getNom(), nombre);
        }
    }

    public void removePiece(PieceDetachee p){
        pieces.remove(p.getNom());
    }

    public Meuble(String name, Piece salle, int tps){
        this.pieces= new HashMap<>();
        this.nom=name;
        this.prix=0;
        this.salle=salle;
        this.dureeConstruction=tps;
    }


    public boolean piecesSuffisantes() {
        for (String s : this.pieces.keySet()) {
            if (! this.piecesConstruction.containsKey(s)) {
                return false;
            }
            if (this.piecesConstruction.get(s) == this.pieces.get(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Meuble{" +
                "prix=" + prix +
                ", nom='" + nom + '\'' +
                ", salle=" + salle +
                ", dureeConstruction=" + dureeConstruction +
                ", pieces=" + pieces +
                ", piecesConstruction=" + piecesConstruction +
                '}';
    }


    public HashMap<String, Integer> getComposants(){
        return this.pieces;
    }

    public int getDureeConstruction() {
        return this.dureeConstruction;
    }

    public String getNom() {
        return this.nom;
    }
    public Piece getSalle() {
        return this.salle;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getNbrPieces(){
        int somme=0;
        for(Map.Entry<String,Integer> k:pieces.entrySet()){
            somme+=k.getValue();
        }
        return somme;
    }
}

