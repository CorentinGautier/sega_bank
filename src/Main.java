import bo.Agence;
import dal.AgenceDAO;
import dal.IDAO;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        //creation d'une agence ------------------ OK
        IDAO<Integer, Agence> dao = new AgenceDAO();
        Agence agence = new Agence( 7, 5, "rue de la muerta" );
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
