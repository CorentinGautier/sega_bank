import bo.Agence;
import bo.Compte;
import bo.CompteSimple;
import bo.Operation;
import dal.AgenceDAO;
import dal.IDAO;
import dal.OperationDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static IDAO<Integer, Operation> operationDAO = new OperationDAO();
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

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



        /*
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
        */
        //remove ------- ok
        dao.remove(agence);
    }
}
