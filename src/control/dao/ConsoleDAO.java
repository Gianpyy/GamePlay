package control.dao;

import model.ConsoleBean;

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

public class ConsoleDAO implements IBeanDAO<ConsoleBean, String>{
    private static DataSource dataSource;

    //L'entità è debole ed ha la chiave primaria in un'altra tabella, quindi servono due stringhe con i nomi delle tabelle
    private static final String SUPER_TABLE_NAME = "prodotto"; //nome tabella con la chiave primaria nel database
    private static final String TABLE_NAME = "console"; //nome tabella nel database

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

    @Override
    public void doSave(ConsoleBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String inserimentoProdotto = "INSERT INTO " + ConsoleDAO.SUPER_TABLE_NAME + " (barcode, nome, prezzo, sconto, tipo) VALUES (?, ?, ?, ?, ?)";
        String inserimentoConsole = "INSERT INTO " +ConsoleDAO.TABLE_NAME+ " (prodotto, famiglia, annoRilascio) VALUES (?,?,?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo l'inserimento nella tabella prodotto
            preparedStatement = connection.prepareStatement(inserimentoProdotto);
            preparedStatement.setString(1, item.getBarcode());
            preparedStatement.setString(2, item.getNome());
            preparedStatement.setFloat(3, item.getPrezzo());
            preparedStatement.setInt(4, item.getSconto());
            preparedStatement.setString(5, item.getTipo());

            //Eseguo la prima query
            preparedStatement.executeUpdate();

            //Preparo l'inserimento nella tabella videogioco
            preparedStatement = connection.prepareStatement(inserimentoConsole);
            preparedStatement.setString(1,item.getBarcode());
            preparedStatement.setString(2, item.getFamiglia());
            preparedStatement.setInt(3, item.getAnnoRilascio());

            //Eseguo la seconda query
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
    public boolean doDelete(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;

        String sqlStatement = "DELETE FROM "+ConsoleDAO.TABLE_NAME+" as C WHERE C.prodotto = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, code);

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
    public ConsoleBean doRetrieveByKey(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ConsoleBean consoleBean = new ConsoleBean();

        String sqlStatement = "SELECT * FROM " +ConsoleDAO.TABLE_NAME+" as C INNER JOIN "+ConsoleDAO.SUPER_TABLE_NAME+" as P ON C.prodotto = P.barcode WHERE P.barcode = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, code);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nei bean
            while (resultSet.next()) {
                consoleBean.setBarcode(resultSet.getString("barcode"));
                consoleBean.setNome(resultSet.getString("nome"));
                consoleBean.setPrezzo(resultSet.getFloat("prezzo"));
                consoleBean.setSconto(resultSet.getInt("sconto"));
                consoleBean.setFamiglia(resultSet.getString("famiglia"));
                consoleBean.setAnnoRilascio(resultSet.getInt("annoRilascio"));
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
        return consoleBean;
    }

    @Override
    public Collection<ConsoleBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ConsoleBean> consoleBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " +ConsoleDAO.TABLE_NAME+" as C INNER JOIN "+ConsoleDAO.SUPER_TABLE_NAME+" as P ON C.prodotto = P.barcode";

        //Aggiungo l'ordine in cui devono essere visualizzati i risultati, se disponibile
        if(order != null && !order.equals("")) {
            sqlStatement += " ORDER BY "+ order + " DESC";
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
                ConsoleBean consoleBean = new ConsoleBean();
                consoleBean.setBarcode(resultSet.getString("barcode"));
                consoleBean.setNome(resultSet.getString("nome"));
                consoleBean.setPrezzo(resultSet.getFloat("prezzo"));
                consoleBean.setSconto(resultSet.getInt("sconto"));
                consoleBean.setFamiglia(resultSet.getString("famiglia"));
                consoleBean.setAnnoRilascio(resultSet.getInt("annoRilascio"));
                consoleBeanCollection.add(consoleBean);
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return consoleBeanCollection;
    }
}
