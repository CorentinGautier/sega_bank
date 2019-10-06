package dal;

import bo.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO implements IDAO<Integer, Operation>{

        private static final String INSERT_QUERY = "INSERT INTO OPERATIONS(agence, compte, type_op, montant) VALUES (?, ?, ? , ? )";
        private static final String UPDATE_QUERY = "UPDATE OPERATIONS SET agence = ?, compte = ?, type_op = ?, montant = ? WHERE id = ?";
        private static final String REMOVE_QUERY = "DELETE FROM OPERATIONS WHERE id= ? ";
        private static  final String FIND_QUERY = "SELECT * FROM OPERATIONS WHERE compte = ?";
        private static final String FINDALL_QUERY = "SELECT * FROM OPERATIONS";

    @Override
    public void create(Operation object) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, object.getIdAgence());
                ps.setInt(2, object.getIdCompte());
                ps.setInt(3, object.getType());
                ps.setDouble(4, object.getMontant());
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
    public void remove(Operation object) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(REMOVE_QUERY)) {
                ps.setInt(1, object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void update(Operation object) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
                ps.setInt(1, object.getIdAgence());
                ps.setInt(2, object.getIdCompte());
                ps.setInt(3, object.getType());
                ps.setDouble(4, object.getMontant());
                ps.setInt(5,object.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Operation findBy(int id) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    public List<Operation> find(int id) throws SQLException, IOException, ClassNotFoundException {
        List<Operation> list = new ArrayList<>();

        Connection connection = PersistanceManager.getConnection();
        if(connection != null){
            try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY)) {
                ps.setInt(1,id);

                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()) {
                        Operation operation = new Operation();
                        operation.setId(rs.getInt("id"));
                        operation.setType(rs.getInt("type_op"));
                        operation.setIdAgence((rs.getInt("agence")));
                        operation.setIdCompte(rs.getInt("compte"));
                        operation.setMontant(rs.getDouble("montant"));
                        list.add(operation);
                    }
                }
            }
        }
        return list;
    }


        @Override
        public List<Operation> findAll() throws SQLException, IOException, ClassNotFoundException {
            List<Operation> list = new ArrayList<>();
            Connection connection = PersistanceManager.getConnection();
            if(connection != null){
                try (PreparedStatement ps = connection.prepareStatement(FINDALL_QUERY)) {
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            Operation operation = new Operation();
                            operation.setId(rs.getInt("id"));
                            operation.setIdAgence(rs.getInt("agence"));
                            operation.setIdCompte(rs.getInt("compte"));
                            operation.setType(rs.getInt("type_op"));
                            operation.setMontant(rs.getDouble("montant"));
                            list.add(operation);
                        }
                    }
                }
            }
            return list;
        }
    }


