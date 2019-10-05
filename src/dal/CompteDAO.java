package dal;
import bo.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO implements IDAO<Long, Compte>{
    private static final String INSERT_QUERY = "INSERT INTO COMPTES(solde, agence) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE COMPTES SET code = ?, adresse = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE * FROM COMPTES WHERE id= ? ";
    private static  final String FIND_QUERY = "SELECT * from COMPTES Where id = ?";

    @Override
    public void create(Compte object) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, object.getId());
                ps.setDouble(2, object.getSolde());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        object.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void remove(Compte object) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(REMOVE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void update(Compte object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.setDouble(2, object.getSolde());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Compte findBy(Long aLong) throws SQLException, IOException, ClassNotFoundException {
        Compte compte = null ;
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1,aLong);
                try(ResultSet rs = ps.executeQuery()){
                    compte.setId(rs.getInt("id"));
                }
            }
        }
        return compte;
    }

    @Override
    public List<Compte> findAll() throws SQLException, IOException, ClassNotFoundException {
        List<Compte> list = new ArrayList<>();
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        Compte compte ;
                        if(rs.getInt("type") == 3) {
                            compte = new ComptePayant();
                            compte.setId(rs.getInt("id"));
                            compte.setSolde((rs.getInt("solde")));
                        }else if(rs.getInt("type") == 1) {
                            compte = new CompteEpargne();
                            compte.setId(rs.getInt("id"));
                            compte.setSolde((rs.getInt("solde")));
                        }else{
                            compte = new CompteSimple();
                            compte.setId(rs.getInt("id"));
                            compte.setSolde((rs.getInt("solde")));
                        }
                        list.add(compte);
                    }
                }
            }
        }
        return list;
    }
}
