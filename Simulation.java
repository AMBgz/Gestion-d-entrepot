package Projet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Simulation <T extends Entrepot & Strategie> {// Cette classe a la particularité d'être composée d'un paramètre générique héritant de l'entrepot et implémentant l'interface Stratégie
    /*
    Cette implémentation permet donc de pouvoir subir une stratégie particulière de rangement et de recrutement
    tout en ayant accés au fonction d'Entrepot

     */


    private ArrayList<ArrayList<String>> instructions;
    private int time;
    private T Stratégie;
    public ArrayList<ArrayList<String>> getInstructions() {
        return instructions;
    }

    public Simulation(T stratégie) {
        Stratégie=stratégie;
        instructions = new ArrayList<ArrayList<String>>();
        time=0;
    }



    /** Fonction commandeUtilisateur qui prends l'id de l'instruction en paramètre et ajoute à la liste des instructions la liste des instructions Stratégierées par l'utilisateur **/

    public void commandeUtilisateur(Integer time){
        Scanner sc=new Scanner(System.in);
        ArrayList<String> inst=new ArrayList<>();
        inst.add(time.toString());// on ajoute directemStratégie l'id
        String ordre;
        if(inst.size()<2){ // On demande le type de la demande
            do {
                System.out.println("Quel est le type de votre demande ? ");
                System.out.println("Meuble ? ");
                System.out.println("Lot ?");
                System.out.println("Inventaire ?");
                ordre = sc.next();
                if (ordre.equalsIgnoreCase("Inventaire")) {
                    Stratégie.inventaire();
                    ordre = "";
                }

            }while(!ordre.matches("[Meuble||Lot||rien||meuble||lot||Rien||Inventaire||inventaire]+"));
            char c=ordre.charAt(0);
            ordre.replace(c,Character.toUpperCase(c));
            inst.add(ordre);
        }

        if(inst.get(1).contains("Lot")||inst.get(1).contains("lot")){// le lot est une instruction qui a besoin d'un nombre fixe de paramètre
            System.out.println("Quel est le nom de votre lot ?");
            do{
                ordre = sc.next();
            }while(!ordre.matches("([A-Z])([0-9]||' '||[a-z]||[A-Z])+"));
            inst.add(ordre);
            System.out.println("Quel est le poids de votre lot?");
            do{
                ordre = sc.next();
            }while(!ordre.matches("[0-9]+"));
            inst.add(ordre);
            System.out.println("Quel est le prix de votre lot ?");
            do{
                ordre = sc.next();
            }while(!ordre.matches("[0-9]+"));
            inst.add(ordre);
            System.out.println("Quel est le volume de votre lot");
            do{
                ordre = sc.next();
            }while(!ordre.matches("[0-9]+"));
            inst.add(ordre);
        }else if(inst.get(1).contains("Meuble")||inst.get(1).contains("meuble")){// meuble est une fonctions qui a un certain nombre de champs obligatoires
            System.out.println("Quel est le nom de votre meuble ?");
            do{
                ordre = sc.next();
            }while(!ordre.matches("([A-Z])([0-9]||' '||[a-z]||[A-Z])+"));
            inst.add(ordre);
            System.out.println("Quel est la pièce auquel votre meuble est affecté ?");
            do{
                System.out.println("Répondez par le chiffre associé à la salle voulue:");
                System.out.println("1: Salle à Manger.    3: Salle de Bain      5: Cuisine");
                System.out.println("2: WC                 4: Chambre            6: Salon");
                ordre = sc.next();
            }while(!ordre.matches("[1-6]+"));
            ordre=Piece.IntToStringPiece(ordre);
            inst.add(ordre);
            System.out.println("Quel est la durée de construction de votre meuble ?");
            do{
                ordre = sc.next();
            }while(!ordre.matches("[0-9]+"));
            inst.add(ordre);

            System.out.println("De combien de pièce est composé votre meuble ?"); // on demande ensuite combien de pièce comporte le meuble
            do{
                ordre = sc.next();
            }while(!ordre.matches("[0-9]+"));
            int j=Integer.parseInt(ordre);
            int k=j;
            while(j!=0){
                System.out.println("Quel est le nom de la pièce "+(k-j)+" ?");
                do{
                    ordre = sc.next();
                }while(!ordre.matches("([A-Z])([0-9]||' '||[a-z]||[A-Z])+"));
                inst.add(ordre);
                System.out.println("Combien vous en faut-il ?");
                do{
                    ordre = sc.next();
                }while(!ordre.matches("[0-9]+"));
                inst.add(ordre);
                j-=1;
            }
        }else {
            System.out.println("Aucune action ne sera réalisée lors de ce pas de temps");
        }
        instructions.add(inst);

    }

    /** Fonction lectureFichier prenant en paramètre un String du nom du fichier, il regroupe toutes les instructions du fichier dans une liste de listes**/
    public void lectureFichier(String fichier) {
        try {
            String ligne_actuelle;
            BufferedReader rd = new BufferedReader(new FileReader(fichier));

            while ((ligne_actuelle = rd.readLine()) != null) {
                ArrayList<String> instr = new ArrayList<>();

                String[] split = ligne_actuelle.split(" |<|>");// On split l'instruction du fichier par les < > et les espaces, on se retrouve avec une liste remplie d'informations et d'élémStratégies vides


                int i = 0;
                while (i <= (split.length - 1)) {    // on ajoute tout les élémStratégies de split dans une petite liste de string contenant toute les informations données.
                    instr.add(split[i]);
                    i += 1;
                }
                instr.removeAll(Collections.singleton(""));// on retire chacune des itération des sections vides, on a alors une liste de String, ne contenant que l'essStratégieiel.

                instructions.add(instr);// on ajoute la liste à la grande liste qui regroupera  toutes les instructions.
            }
            rd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean tacheMeuble(int i){
        boolean tache_effectuée=false;
        Meuble mbl=new Meuble(instructions.get(i).get(2),Piece.stringToPiece(instructions.get(i).get(3)),Integer.parseInt(instructions.get(i).get(4)));
        int j=5;
        while(j<instructions.get(i).size()){ // On stocke l'information dans une map avec comme clé le nom de la pièce, ainsi que son nombre en valeur.
            mbl.addPiece(instructions.get(i).get(j),Integer.parseInt(instructions.get(i).get(j+1)));
            j+=2;
        }

        if(Stratégie.hasStock(mbl) && Stratégie.personnelDispo("Brico",mbl.getSalle()) ){ //on vérifie le stock et la dispo du personnel
            Personnel P= Stratégie.affecterPersonnel("Brico",mbl.getSalle());
            if(P instanceof Ouvrier){// on check si le personnel est un Ouvrier
                ((Ouvrier) P).monterMeuble(mbl);
                tache_effectuée=true;
            }else {
                ((ChefBrico) P).monterMeuble(mbl); // sinon c'est forcément un chefBrico
                tache_effectuée = true;
            }

            //On ajoute le meuble dans la liste d'attente
            Stratégie.setMontageEnCours(mbl);
            //On ajoute les lots necessaire au meuble à une liste d'attente de lots
            Stratégie.setLotsARetirer(Stratégie.lotsNecessaires(mbl));

            /*On prend ici le nombre de personnel dispo pour stocker les éléments, pour permettre d'incrémenter le temps de travail
            * de l'ouvrier/chefBrico qui s'occuper du meuble, toute cette partie permet de trouver une Borne Superieur au temps d'attente avant que les lots soit acheminés
            * ainsi, quand le chef brico commence à construire le meuble, les lots viendront d'etre acheminé ou du moins depuis quelques pas de temps
             */
            int Nombre_stock=Stratégie.nbr_personnelDispo("Stock",mbl.getSalle());
            ArrayList<Personnel> Staff_stock=new ArrayList<>();
            for(int k=0;k<Nombre_stock;k++){
                Personnel tmp=Stratégie.affecterPersonnel("Stock",null);
                Staff_stock.add(tmp);
            }

            int Volume_Total=Stratégie.lotsNecessaires(mbl).size();
           if(Nombre_stock==Volume_Total){
               for(int l=0;l<Volume_Total;l++){
                   Staff_stock.get(l).setTempsTravailRestant(1);
               }
           }else {
               for (int m = 0; m < Nombre_stock; m++) {
                   Staff_stock.get(m).setTempsTravailRestant(Volume_Total / Nombre_stock);
               }
               for (int g = 0; g < (Volume_Total % Nombre_stock); g++) {
                   Staff_stock.get(g).setTempsTravailRestant((Volume_Total / Nombre_stock) + 1);
               }
           }

           //Stream qui donne le max des temps de travaux du personnel stock
           P.ajouterTempsTravailRestant(Staff_stock.stream().max((x,y)->{
               if(x.getTempsTravailRestant()>y.getTempsTravailRestant()) return 1;
               if(x.getTempsTravailRestant()<y.getTempsTravailRestant()) return -1;
               else return 0;
           }).get().getTempsTravailRestant());
            return tache_effectuée;
        }else{
            mbl=null;
            System.out.println("L'entrepot ne possède pas les ressources necessaire à la construction du meuble.");

            return tache_effectuée;
        }

    }



    public void Afficher(){
        System.out.println("-----------------------------------------------------------------------------------");

        System.out.println("Solde : "+Stratégie.getSolde());
        int cpt=0;
        for(Chef p:Stratégie.getStaff()){
            cpt+=1;
            for(Ouvrier o:p.getEquipe()){
                cpt+=1;
            }
        }
        System.out.println("Nombre Ouvriers :"+ cpt);
        System.out.println("-----------------------------------------------------------------------------------");
    }

    /*
    Fonction qui permet de faire passer 1 pas de temps à tout les ouvrier occupés ou non.
    Ici, le parti pris a été de ne pas assigner des ouvrier au stock de lots en particulier.
    Les ouvriers travaillent en continu tant qu'ils ont un meuble à construire ou que des Lots sont en fil d'attente.
    Ainsi dés qu'un ouvrier est libre, il peut aller acheminer des lots vers l'entrepot ou les retirer pour la construction d'un meuble
     */


    void Passer1temps(){ // le temps passe ...
        Stratégie.peut_recruter=true;
        Stratégie.peut_virer=true;


        for(Chef f:Stratégie.getStaff()){
            if(f instanceof ChefStock && f.isOccupe()&&Stratégie.getLotsARetirer().size()>0){
                if(Stratégie.getLotsARetirer().size()>0){
                    ((ChefStock) f).retirerLot(Stratégie.getLotsARetirer().get(0));
                    Stratégie.getLotsARetirer().remove(0);
                }

            }else if (f instanceof ChefStock && f.isOccupe() && Stratégie.getLotsAajouter().size()>0){
                ((ChefStock) f).ajouterLot(Stratégie.getLotsAajouter().get(0));
                Stratégie.getLotsAajouter().remove(0);
            }
            if(f.isOccupe()){
                f.ajouterTempsTravailRestant(-1);
            }
            for(Ouvrier o:f.getEquipe()){
                if(!o.isOccupe_Meuble()&&Stratégie.getLotsARetirer().size()>0){
                    if(Stratégie.getLotsARetirer().size()>0){
                        o.retirerLot(Stratégie.getLotsARetirer().get(0));
                        Stratégie.getLotsARetirer().remove(0);
                    }

                }else if(!o.isOccupe_Meuble()&&Stratégie.getLotsAajouter().size()>0){
                    o.ajouterLot(Stratégie.getLotsAajouter().get(0));
                    Stratégie.getLotsAajouter().remove(0);
                }
                if(o.isOccupe()||o.isOccupe_Meuble()){
                    o.ajouterTempsTravailRestant(-1);
                }
            }
        }
    }



    // Fonction permettant de lancer la Simulation.
    public void run(String[] args){
        if(args.length!=0){
            lectureFichier(args[0]);
        }
        int time=0;
        boolean utilisateur_fin=true;
        Scanner scan=new Scanner(System.in);
        do{
            String tache_courante="";
            if(args.length==0){
                commandeUtilisateur(time+1);
            }

            Afficher();

            boolean tache_effectuee=false;


            if(time<instructions.size()) {
                 tache_courante= instructions.get(time).get(1);
            }
            switch (tache_courante){
                case "Meuble": case "meuble":
                    System.out.println("Une tâche meuble a été réceptionnée.");
                    tache_effectuee=tacheMeuble(time);
                    break;
                case "Lot": case "lot":
                    System.out.println("Une tâche Lot a été réceptionnée.");
                    tache_effectuee=Stratégie.tacheLot(instructions.get(time));
                    break;
                default:
                    break;
            }
            Stratégie.payerPersonnel();
            Passer1temps();
            System.out.println(Stratégie.getLotsARetirer());
            Stratégie.doit_Recruter();
            Stratégie.doit_Virer();
            String ordre="";
            time+=1;
            System.out.println("VOULEZ VOUS CONTINUER LA SIMULATION ? O: Oui N: Non");
            System.out.println("Tapez Inventaire pour le consulter");

            do{
                System.out.println();
                ordre = scan.next();
                if (ordre.equalsIgnoreCase("Inventaire")) {
                    Stratégie.afficherRangees();
                    ordre="";
                }
            }while(!ordre.matches("O|N"));
            if(ordre.equals("N")) utilisateur_fin=false;
        }while(utilisateur_fin);
        scan.close();
    }
    
    
    

}