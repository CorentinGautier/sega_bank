import bo.Agence;
import dal.AgenceDAO;
import dal.IDAO;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        //creation d'une agence
        IDAO<Long, Agence> dao = new AgenceDAO();
        Agence agence = new Agence(41, "teeest");
        try {
            dao.create(agence);
            System.out.println(agence.toString());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        // update
        System.out.println(agence.toString());
        agence.setAdresse("rue de la muerta");
        dao.update(agence);
        System.out.println(agence.toString());
    }
}
