package control.dao;

import model.ProdottoBean;

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

public class ProdottoDAO implements IBeanDAO<ProdottoBean, String> {
    private static DataSource dataSource;

    private static final String TABLE_NAME = "prodotto";

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

    public ProdottoDAO() { /* Costruttore di default vuoto e senza parametri */}

    @Override
    public synchronized void doSave(ProdottoBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sqlStatement = "INSERT INTO " + ProdottoDAO.TABLE_NAME + " (barcode, nome, prezzo, sconto, tipo) VALUES (?, ?, ?, ?, ?)";

        try{
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, item.getBarcode());
            preparedStatement.setString(2, item.getNome());
            preparedStatement.setFloat(3, item.getPrezzo());
            preparedStatement.setInt(4, item.getSconto());
            preparedStatement.setString(5, item.getTipo());

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
    public boolean doDelete(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;

        String sqlStatement = "DELETE FROM " +ProdottoDAO.TABLE_NAME+" WHERE barcode = ?";

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
    public ProdottoBean doRetrieveByKey(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ProdottoBean prodottoBean = new ProdottoBean();

        String sqlStatement = "SELECT * FROM " + ProdottoDAO.TABLE_NAME + " WHERE  barcode = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, code);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nel bean
            while (resultSet.next()) {
                prodottoBean.setNome(resultSet.getString("nome"));
                prodottoBean.setBarcode(resultSet.getString("barcode"));
                prodottoBean.setPrezzo(resultSet.getFloat("prezzo"));
                prodottoBean.setSconto(resultSet.getInt("sconto"));
                prodottoBean.setTipo(resultSet.getString("tipo"));
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

        return prodottoBean;
    }

    @Override
    public Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ProdottoBean> prodottoBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " + ProdottoDAO.TABLE_NAME;

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
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setNome(resultSet.getString("nome"));
                prodottoBean.setBarcode(resultSet.getString("barcode"));
                prodottoBean.setPrezzo(resultSet.getFloat("prezzo"));
                prodottoBean.setSconto(resultSet.getInt("sconto"));
                prodottoBean.setTipo(resultSet.getString("tipo"));
                prodottoBeanCollection.add(prodottoBean);
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

        return prodottoBeanCollection;
    }

    public String doRetrieveProductTypeById(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ProdottoBean> prodottoBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT tipo FROM " + ProdottoDAO.TABLE_NAME+ " WHERE  barcode = ?";
        String productType;

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, code);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query
            productType = resultSet.getString("tipo");

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

        return productType;
    }

    public void doUpdate(ProdottoBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String prodottoUpdate = "UPDATE "+TABLE_NAME+" SET nome = ?, prezzo = ? WHERE barcode = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement per l'update della tabella prodotto
            preparedStatement = connection.prepareStatement(prodottoUpdate);
            preparedStatement.setString(1, item.getNome());
            preparedStatement.setFloat(2, item.getPrezzo());
            preparedStatement.setString(3, item.getBarcode());

            //Eseguo l'update della tabella prodotto
            preparedStatement.executeUpdate();

            //Eseguo il commit
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

    }
}
