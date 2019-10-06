import bo.*;
import dal.AgenceDAO;
import dal.CompteDAO;
import dal.IDAO;
import dal.OperationDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static IDAO<Integer, Operation> operationDAO = new OperationDAO();
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        IDAO<Integer, Compte> dao = new CompteDAO();
        Compte compte =  new CompteEpargne(1, 5000, 266);
        int choice;
        do{
            System.out.println("Vous voulez modifier le compte ?");
            System.out.println("1 : Compte Simple");
            System.out.println("2 : Compte Payant");
            System.out.println("3 : Compte Epargne");
            System.out.println("4 : Annuler");
            Scanner sc = new Scanner(System.in);
             choice = sc.nextInt();
        }while(choice != 1|choice != 2 | choice != 3|choice != 4);
        if(choice == 1){
            if(compte instanceof CompteSimple){
                System.out.println("le compte sélectionné est déjà de ce type.");
            }else{
                CompteSimple compteSimple = new CompteSimple(compte.getId(), compte.getSolde(),compte.getNumAgence());
                dao.update(compteSimple);
            }
        }else  if(choice == 2){
            if(compte instanceof ComptePayant){
                System.out.println("le compte sélectionné est déjà de ce type.");
            }else{
                ComptePayant comptePayant= new ComptePayant(compte.getId(), compte.getSolde(),compte.getNumAgence());
                dao.update(comptePayant);
            }

        }else  if(choice == 3){
            if(compte instanceof CompteEpargne){
                System.out.println("le compte sélectionné est déjà de ce type.");
            }else{
                CompteEpargne compteEpargne= new CompteEpargne(compte.getId(), compte.getSolde(),compte.getNumAgence());
                dao.update(compteEpargne);
            }
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
