package Projet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
class SimulationTest {

    private static Strategie1 Strategie_test;

    @BeforeEach
    public void setupTest(){
        Strategie_test=new Strategie1(10,10);
        ChefStock B=new ChefStock("Bob","Dylan", Strategie_test);
        Strategie_test.getStaff().add(B);
        Strategie_test.getStaff().get(0).getEquipe().add(new Ouvrier("Fred","Sabatier",B,Strategie_test,Piece.CUISINE));
        Strategie_test.getStaff().get(0).getEquipe().add(new Ouvrier("Bob","Sabatier",B,Strategie_test,Piece.CHAMBRE));
    }
    @AfterEach
    public void finishTest(){
        Strategie_test=null;
    }

    @Test
    public void getEffectifTest(){
        assertEquals(3,Strategie_test.getEffectif());
    }

    @Test
    public void AffecterPersonnelTest(){
        assertEquals(Strategie_test.affecterPersonnel("Stock",null).getId(),Strategie_test.getStaff().get(0).getId());
    }

    @ParameterizedTest
    @CsvSource({"Brico,CHAMBRE,1","Brico,CUISINE,1","Brico,SALLE_A_MANGER,0"})
    public void nbr_PersonneDispoTest(String tache,String p,int resultat){
        assertEquals(Strategie_test.nbr_personnelDispo(tache,Piece.stringToPiece(p)),resultat);
    }

    @Test
    public void DeposerPieceTest(){
        PieceDetachee p=new PieceDetachee("Ecrou",5,15.5);
        Lot l=new Lot(p,10);
        Strategie_test.deposerPiece(0,0,l);
        assertEquals(Strategie_test.rangees[0][0].get().getId(),l.getId());
    }
}