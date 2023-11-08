package control.dao;

import model.UtenteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class UtenteDAO implements IBeanDAO<UtenteBean, Integer>{
    private static DataSource dataSource;

    private static final String TABLE_NAME = "utente";

    //Ottengo la risorsa tramite lookup
    static {
        try {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");

            dataSource = (DataSource) envContext.lookup("jdbc/GamePlayDB");
        } catch(NamingException e) {
            e.printStackTrace();
        }
    }

    public UtenteDAO() { /* Costruttore di default senza parametri*/ }

    @Override
    public void doSave(UtenteBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sqlStatement = "INSERT INTO " + UtenteDAO.TABLE_NAME + " (username, psw, amministratore) VALUES (?, ?, ?)";

        try{
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, item.getUsername());
            preparedStatement.setString(2, item.getPassword());
            preparedStatement.setBoolean(3, false);

            //Eseguo la query
            preparedStatement.executeUpdate();
            connection.commit();

        //Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                }
            finally {
                if (connection != null)
                    connection.close();
            }
        }
    }

    public int doSaveAndReturnKey(UtenteBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int userID = -1;

        String sqlStatement = "INSERT INTO " + UtenteDAO.TABLE_NAME + " (username, psw, amministratore) VALUES (?, ?, ?)";

        try{
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, item.getUsername());
            preparedStatement.setString(2, item.getPassword());
            preparedStatement.setBoolean(3, false);

            //Eseguo la query
            preparedStatement.executeUpdate();
            connection.commit();

            //Recupero l'id autogenerato
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userID = generatedKeys.getInt(1);
            }

            //Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            }
            finally {
                if (connection != null)
                    connection.close();
            }
        }

        return userID;
    }

    @Override
    public boolean doDelete(Integer code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;

        String sqlStatement = "DELETE FROM " +UtenteDAO.TABLE_NAME+" WHERE codice = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, code);

            //Eseguo la query
            result = preparedStatement.executeUpdate();
            connection.commit();
        //Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return (result != 0);
    }

    @Override
    public UtenteBean doRetrieveByKey(Integer code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UtenteBean utenteBean = new UtenteBean();

        String sqlStatement = "SELECT * FROM " + UtenteDAO.TABLE_NAME +" WHERE codice = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, code);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nel bean
            while (resultSet.next()) {
                utenteBean.setCodice(resultSet.getInt("codice"));
                utenteBean.setUsername(resultSet.getString("username"));
                utenteBean.setPassword(resultSet.getString("psw"));
                utenteBean.setAdmin(resultSet.getBoolean("amministratore"));
            }

        //Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return utenteBean;
    }

    @Override
    public Collection<UtenteBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<UtenteBean> utenteBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " + UtenteDAO.TABLE_NAME;

        //Aggiungo l'ordine in cui devono essere visualizzati i risultati, se disponibile
        if(order != null && !order.equals("")) {
            sqlStatement += " ORDER BY "+ order;
        }

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nei bean
            while (resultSet.next()) {
                UtenteBean utenteBean = new UtenteBean();
                utenteBean.setCodice(resultSet.getInt("codice"));
                utenteBean.setUsername(resultSet.getString("username"));
                utenteBean.setPassword(resultSet.getString("psw"));
                utenteBean.setAdmin(resultSet.getBoolean("amministratore"));
                utenteBeanCollection.add(utenteBean);
            }
        //Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return utenteBeanCollection;
    }

    public UtenteBean doRetrieveByUsernameAndPassword(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UtenteBean utenteBean = new UtenteBean();

        String sqlStatement = "SELECT * FROM " + UtenteDAO.TABLE_NAME +" WHERE BINARY(username) = BINARY(?) AND BINARY(psw) = BINARY(?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nel bean
            while (resultSet.next()) {
                utenteBean.setCodice(resultSet.getInt("codice"));
                utenteBean.setUsername(resultSet.getString("username"));
                utenteBean.setPassword(resultSet.getString("psw"));
                utenteBean.setAdmin(resultSet.getBoolean("amministratore"));
            }

            //Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return utenteBean;
    }

    public boolean doUpdatePassword(int code, String newPassword) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;

        String sqlStatement = "UPDATE " +UtenteDAO.TABLE_NAME+" SET psw = ? WHERE codice = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, code);


            //Eseguo la query
            result = preparedStatement.executeUpdate();
            connection.commit();
            //Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return (result != 0);
    }
}
