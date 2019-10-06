package dal;
import bo.Agence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenceDAO implements IDAO<Integer, Agence>{
    private static final String INSERT_QUERY = "INSERT INTO AGENCES(code, adresse) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE AGENCES SET code = ?, adresse = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE FROM AGENCES WHERE id= ? ";
    private static  final String FIND_QUERY = "SELECT * from AGENCES Where id = ?";
    private static  final String FIND_All_QUERY = "SELECT * from AGENCES";

    @Override
    public void create(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, agence.getCode());
                ps.setString(2, agence.getAdresse());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        agence.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void remove(Agence object) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(REMOVE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void update(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
                ps.setInt(3, agence.getId());
                ps.setInt(1, agence.getCode());
                ps.setString(2, agence.getAdresse());
                ps.executeUpdate();
            }
        }
    }

    public Agence findBy(int id) throws SQLException, IOException, ClassNotFoundException {
        Agence agence = null ;
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1,id);
                try(ResultSet rs = ps.executeQuery()){
                    if( rs.next()) {
                        agence = new Agence();
                        agence.setId(rs.getInt("id"));
                        agence.setCode((rs.getInt("code")));
                        agence.setAdresse(rs.getString("adresse"));
                    }
                }
            }
        }
        return agence;
    }

    @Override
    public List<Agence> findAll() throws SQLException, IOException, ClassNotFoundException {
        List<Agence> list = new ArrayList<>();
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(FIND_All_QUERY)) {
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        Agence agence = new Agence();
                        agence.setId(rs.getInt("id"));
                        agence.setCode( (rs.getInt("code")));
                        agence.setAdresse(rs.getString("adresse"));
                        list.add(agence);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<Agence> find(int id) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }
}
