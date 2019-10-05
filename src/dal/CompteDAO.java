package dal;
import bo.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO implements IDAO<Integer, Compte>{
    private static final String INSERT_QUERY = "INSERT INTO COMPTES(solde, agence) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE COMPTES SET solde = ?, agence = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE * FROM COMPTES WHERE id= ? ";
    private static  final String FIND_QUERY = "SELECT * from COMPTES Where id = ?";
    private static  final String FIND_All = "SELECT * from COMPTE";

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
                ps.setInt(3, object.getId());
                ps.setDouble(1, object.getSolde());
                ps.setInt(2, object.getNumAgence());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Compte findBy(Integer id) throws SQLException, IOException, ClassNotFoundException {
        Compte compte = null ;
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            System.out.println("ok");
            try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setLong(1,id);
                System.out.println(ps);
                try(ResultSet rs = ps.executeQuery()){
                    if( rs.next()) {
                        if(rs.getInt("type") == 3) {
                            compte = new ComptePayant();
                            compte.setId(rs.getInt("id"));
                            compte.setSolde((rs.getInt("solde")));
                        }else if(rs.getInt("type") == 2) {
                            compte = new CompteEpargne();
                            compte.setId(rs.getInt("id"));
                            compte.setSolde((rs.getInt("solde")));
                        }else{
                            compte = new CompteSimple();
                            compte.setId(rs.getInt("id"));
                            compte.setSolde((rs.getInt("solde")));
                        }
                    }
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
            try (PreparedStatement ps = connection.prepareStatement(FIND_All)) {
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        Compte compte ;
                        if(rs.getInt("type") == 3) {
                            compte = new ComptePayant();
                            compte.setId(rs.getInt("id"));
                            compte.setSolde((rs.getInt("solde")));
                        }else if(rs.getInt("type") == 2) {
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
