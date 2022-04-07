package Projet;
import java.util.Optional;
import java.util.Scanner;


public class Strategie2 extends Entrepot implements Strategie {
	
	private int bas;
	private int haut;
	private int effectifBas;
	private int effectifHaut;
	
	
	
	public Strategie2(int n, int m, int bas, int haut, int effectifBas, int effectifHaut) {
		
		super(n, m);
		
		this.bas = bas;
		this.haut = haut;
		this.effectifBas = effectifBas;
		this.effectifHaut = effectifHaut;
		this.peut_recruter = true;
		this.peut_virer = true;
		
	}



    public void recruter(){
                if(!peut_recruter)return;
                System.out.println(" Un personnel va être recruté, veuillez inscrire ci-dessous: son nom, son prénom, (et si Ouvrier) Sa Spécialité");
                Scanner sc= new Scanner(System.in);
                String nom,prenom;
                if(getEffectif()-getStaff().size()%4!=0){
                    String piece;
                    Chef c=null;
                    Piece piece1;
                    System.out.println("Nom :");
                    nom=sc.next();
                    System.out.println("Prénom :");
                    prenom=sc.next();
                    do{
                        System.out.println("Répondez par le chiffre associé à la salle voulue:");
                        System.out.println("1: Salle à Manger.    3: Salle de Bain      5: Cuisine");
                        System.out.println("2: WC                 4: Chambre            6: Salon");
                        piece = sc.next();
                    }while(!piece.matches("[1-6]+"));
                    piece1=Piece.stringToPiece(Piece.IntToStringPiece(piece));
                    for(Chef k:getStaff()){
                        if(k.getEquipe().size()<4){
                            c=k;
                            break;
                        }
                    }
                    if(c!=null) c.getEquipe().add(new Ouvrier(nom,prenom,c,this,piece1));
                }
                else{
                    int cpt_stock=0;
                    for(Chef p:getStaff()){
                        if(p instanceof ChefStock){
                            cpt_stock+=1;
                        }
                    }
                    if(cpt_stock<=getStaff().size()-cpt_stock){
                        System.out.println("Nom :");
                        nom=sc.next();
                        System.out.println("Prénom :");
                        prenom=sc.next();
                        getStaff().add(new ChefStock(nom,prenom,this));
                    }else {
                        System.out.println("Nom :");
                        nom=sc.next();
                        System.out.println("Prénom :");
                        prenom=sc.next();
                        getStaff().add(new ChefBrico(nom,prenom,this));
                    }
                }
     
     
            }


	
	


	@Override
	public void doit_Recruter() {
		if  (this.tresorerie.getSolde() > haut && this.getEffectif() < effectifHaut) {
			this.recruter();
		}
	}


	@Override
	public void doit_Virer() {
		if (this.tresorerie.getSolde() < bas && this.getEffectif() >= effectifBas) {
			this.recruter();
		}
	}



	public void virer(Personnel p) {
		if (peut_virer = true) {
			boolean renvoi_effectue = false;
			for (Chef c : staff) {
				if (c.getId() == p.getId()) {
					if (c.getEquipe().size() == 0) {
						staff.remove(c);
						renvoi_effectue = true;
						break;
					} else {
						for (Ouvrier o : c.getEquipe()) {
							if (o.getId() == p.getId()) {
								c.getEquipe().remove(c);
								renvoi_effectue = true;
								break;
							}
						}
					}
				}
				if (renvoi_effectue) break;
			}
			if (renvoi_effectue) {
				peut_virer = false;
				System.out.println("L'employé : " + p.getNom() + " " + p.getPrenom()+" a été renvoyé.");
			}
		}
    }


	public void supprimerLot(int id) {
			for (int i = 0; i<this.m; i++) {
				for (int j = 0; j<this.n; j++) {
					if (this.rangees[i][j].isPresent()) {
						Lot l = this.rangees[i][j].get();
						if (l.getId() == id) {
							this.rangees[i][j] = Optional.empty();
						}
					}
				}
			}
		}
	
	
	
	

}
