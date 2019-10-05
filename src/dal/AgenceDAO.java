package dal;
import bo.Agence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenceDAO implements IDAO<Long, Agence>{
    private static final String INSERT_QUERY = "INSERT INTO AGENCES(code, adresse) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE AGENCES SET code = ?, adresse = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE * FROM AGENCES WHERE id= ? ";
    private static  final String FIND_QUERY = "SELECT * from AGENCES Where id = ?";

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
                        agence.setId(rs.getInt("id"));
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
                ps.setInt(1, agence.getId());
                ps.setInt(2, agence.getCode());
                ps.setString(3, agence.getAdresse());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Agence findBy(Long aLong) throws SQLException, IOException, ClassNotFoundException {
        Agence agence = null ;
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1,aLong);
                try(ResultSet rs = ps.executeQuery()){
                    agence.setId(rs.getInt("id"));
                    agence.setCode( (rs.getInt("code")));
                    agence.setAdresse(rs.getString("adresse"));
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
            try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
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
}
