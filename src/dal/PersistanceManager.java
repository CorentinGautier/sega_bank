package dal;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PersistanceManager {
    private static final String PROPS_FILE = "./ressources/db.properties";
    private static Connection connection;

    private PersistanceManager() {
    } // prevent initialisation

    public static Connection getConnection() throws IOException, SQLException, ClassNotFoundException {

        if(connection == null || !connection.isValid(5)){
            Properties props = new Properties();

            try(FileInputStream fis = new FileInputStream(PROPS_FILE)){
                props.load(fis);
            }
            String driverClass= props.getProperty("jbdc.class.driver");
            String dbUrl= props.getProperty("jdbc.db.url");
            String login= props.getProperty("jdbc.db.login");
            String password= props.getProperty("jdbc.db.password");

            Class.forName(driverClass);
            connection = DriverManager.getConnection(dbUrl, login, password);
        }
        return connection;
    }
    public static void CloseConnection() throws SQLException {
        if(connection != null && connection.isValid(2)){
            connection.close() ;
        }
    }
}
