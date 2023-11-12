package control.dao;

import model.ConsoleBean;
import model.VideogiocoBean;

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

    public ConsoleDAO() { /* Costruttore di default vuoto e senza parametri */ }

    @Override
    public void doSave(ConsoleBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String inserimentoConsole = "INSERT INTO " +ConsoleDAO.TABLE_NAME+ " (prodotto, famiglia, annoRilascio, edizione) VALUES (?,?,?,?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Inserisco le informazioni nella tabella prodotto
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(item);

            //Preparo l'inserimento nella tabella console
            preparedStatement = connection.prepareStatement(inserimentoConsole);
            preparedStatement.setString(1,item.getBarcode());
            preparedStatement.setString(2, item.getFamiglia());
            preparedStatement.setInt(3, item.getAnnoRilascio());
            preparedStatement.setString(4, item.getEdizione());

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
                consoleBean.setFamiglia(resultSet.getString("famiglia"));
                consoleBean.setAnnoRilascio(resultSet.getInt("annoRilascio"));
                consoleBean.setEdizione(resultSet.getString("edizione"));
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
                consoleBean.setFamiglia(resultSet.getString("famiglia"));
                consoleBean.setAnnoRilascio(resultSet.getInt("annoRilascio"));
                consoleBean.setEdizione(resultSet.getString("edizione"));
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


    public Collection<ConsoleBean> doRetrieveAllByConsoleName(String name) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ConsoleBean> consoleBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " +ConsoleDAO.TABLE_NAME+" as C INNER JOIN "+ConsoleDAO.SUPER_TABLE_NAME+" as P ON C.prodotto = P.barcode WHERE P.nome = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, name);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nei bean
            while (resultSet.next()) {
                ConsoleBean consoleBean = new ConsoleBean();
                consoleBean.setBarcode(resultSet.getString("barcode"));
                consoleBean.setNome(resultSet.getString("nome"));
                consoleBean.setPrezzo(resultSet.getFloat("prezzo"));
                consoleBean.setFamiglia(resultSet.getString("famiglia"));
                consoleBean.setAnnoRilascio(resultSet.getInt("annoRilascio"));
                consoleBean.setEdizione(resultSet.getString("edizione"));
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

    public void doUpdate(ConsoleBean item) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String consoleUpdate = "UPDATE "+TABLE_NAME+" SET famiglia = ?, annoRilascio = ?, edizione = ? WHERE prodotto = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Eseguo l'update della tabella prodotto
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doUpdate(item);

            //Preparo il PreparedStatement per l'update della tabella videogioco
            preparedStatement = connection.prepareStatement(consoleUpdate);
            preparedStatement.setString(1, item.getFamiglia());
            preparedStatement.setInt(2, item.getAnnoRilascio());
            preparedStatement.setString(3, item.getEdizione());
            preparedStatement.setString(4, item.getBarcode());


            //Eseguo l'update della tabella videogioco
            preparedStatement.executeUpdate();

            //Eseguo il commit degli update
            connection.commit();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
    }
}
