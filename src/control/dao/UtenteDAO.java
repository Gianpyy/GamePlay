package control.dao;

import model.UtenteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class UtenteDAO implements IBeanDAO<UtenteBean>{
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
    public synchronized void doSave(UtenteBean user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sqlStatement = "INSERT INTO " + UtenteDAO.TABLE_NAME + " (amministratore, username, psw) VALUES (?, ?, ?)";

        try{
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setBoolean(1, user.isAdmin());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

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

    @Override
    public boolean doDelete(int code) throws SQLException {
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
    public UtenteBean doRetrieveByKey(int code) throws SQLException {
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

        String sqlStatement = "SELECT * FROM " + UtenteDAO.TABLE_NAME +" WHERE username = ? AND psw = ?";

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
}
