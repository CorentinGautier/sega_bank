import bo.Agence;
import dal.AgenceDAO;
import dal.IDAO;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        //creation d'une agence
        IDAO<Long, Agence> dao = new AgenceDAO();
        Agence agence = new Agence(21, "5 rue du type");
        try {
            dao.create(agence);
            System.out.println(agence);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        // update
        agence.setAdresse("rue de la muerta");
        dao.update(agence);
    }
}
