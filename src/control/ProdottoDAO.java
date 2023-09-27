package control;

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

public class ProdottoDAO implements IBeanDAO<ProdottoBean> {
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
    public void doSave(ProdottoBean product) throws SQLException {
        //todo: method implementation
    }

    @Override
    public boolean doDelete(int code) throws SQLException {
        return false;
    }

    @Override
    public ProdottoBean doRetrieveByKey(int code) throws SQLException {
        return null;
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
}
