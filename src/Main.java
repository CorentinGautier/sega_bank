import bo.*;
import dal.AgenceDAO;
import dal.CompteDAO;
import dal.IDAO;
import dal.OperationDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static IDAO<Integer, Operation> operationDAO = new OperationDAO();
    private static Scanner sc = new Scanner( System.in );
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        IDAO<Integer, Compte> dao = new CompteDAO();
        Compte compte =  new CompteEpargne(1, 5000, 266);
        int response;
        boolean first = true;
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }

            System.out.println("Vous voulez modifier le compte actuel ?");
            System.out.println("1 : Compte Simple");
            System.out.println("2 : Compte Payant");
            System.out.println("3 : Compte Epargne");
            System.out.println("4 : Annuler");
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( response < 1 || response > 4 );

        switch ( response ) {
            case 1:
                if(compte instanceof CompteSimple){
                    System.out.println("le compte sélectionné est déjà de ce type.");
                }else{
                    System.out.println("passage du compte en compte simple");
                    CompteSimple compteSimple = new CompteSimple(compte.getId(), compte.getSolde(),compte.getNumAgence());
                    dao.update(compteSimple);
                }
                break;
            case 2:
                if(compte instanceof ComptePayant){
                    System.out.println("le compte sélectionné est déjà de ce type.");
                }else{
                    System.out.println("passage du compte en compte payant");
                    ComptePayant comptePayant= new ComptePayant(compte.getId(), compte.getSolde(),compte.getNumAgence());
                    dao.update(comptePayant);
                }
                break;
            case 3:
                if(compte instanceof CompteEpargne){
                    System.out.println("le compte sélectionné est déjà de ce type.");
                }else{
                    System.out.println("passage du compte en compte epargne");
                    CompteEpargne compteEpargne= new CompteEpargne(compte.getId(), compte.getSolde(),compte.getNumAgence());
                    dao.update(compteEpargne);
                }
                break;
        }

        /*
              IDAO<Integer, Operation> dao = new OperationDAO();
        IDAO<Integer, Operation> dao = new OperationDAO();
        Operation operation = new Operation();
        dao.find(4);
       //System.out.println(    dao.find(4));
        List<Operation> list =  dao.find(4);;
        operation.ExporteCSVOperation(list);


         //creation d'une agence ------------------ OK
        IDAO<Integer, Agence> dao = new AgenceDAO();
        Agence agence = new Agence( 7, 75001, "rue de la muerta" );
        Compte compte = new CompteSimple(1,0,agence.getCode());
        Scanner sc = new Scanner(System.in);
        System.out.println("1- Versement :");
        System.out.println("2- Retrait");
        int type = sc.nextInt();
        sc.nextLine();
        System.out.println("Montant de l'opération : ");
        int montant  = sc.nextInt();
        sc.nextLine();

        Operation op = new Operation(agence.getCode(),compte.getId(),type,montant);
        System.out.println(op.getIdAgence());
        operationDAO.create(op);
        op.setMontant(300);
        System.out.println(op.getMontant());
        operationDAO.update(op);
        List<Operation> list = operationDAO.find(compte.getId());
        for(Operation operation : list){
            System.out.println(operation);
        }
        System.out.println(op.getId());
        operationDAO.remove(op);




        try {
            dao.create(agence);
            System.out.println(agence.toString());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        // update   -------------OK
        System.out.println(agence.toString());
        agence.setAdresse("rue de la muerta");
        dao.update(agence);
        System.out.println(agence.toString());
        //agence find by ---------------OK
        System.out.println( dao.findBy(4));
        //findAll -------------         OK
        System.out.println( dao.findAll());

        //remove ------- ok
        dao.remove(agence);
         */


}
}
