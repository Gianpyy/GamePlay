package control.dao;

import model.GadgetBean;
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

public class GadgetDAO implements IBeanDAO<GadgetBean, String> {
    private static DataSource dataSource;

    //L'entità è debole ed ha la chiave primaria in un'altra tabella, quindi servono due stringhe con i nomi delle tabelle
    private static final String SUPER_TABLE_NAME = "prodotto"; //nome tabella con la chiave primaria nel database
    private static final String TABLE_NAME = "gadget"; //nome tabella nel database

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

    public GadgetDAO() { /* Costruttore di default vuoto e senza parametri */}
    @Override
    public void doSave(GadgetBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String inserimentoGadget = "INSERT INTO " + GadgetDAO.TABLE_NAME + "(prodotto, produttore, serie) VALUES  (?,?,?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Inserisco le informazioni nella tabella prodotto
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(item);

            //Preparo l'inserimento nella tabella gadget
            preparedStatement = connection.prepareStatement(inserimentoGadget);
            preparedStatement.setString(1, item.getBarcode());
            preparedStatement.setString(2, item.getProduttore());
            preparedStatement.setString(3, item.getSerie());

            //Eseguo la seconda query
            preparedStatement.executeUpdate();
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

    @Override
    public boolean doDelete(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;

        String sqlStatement = "DELETE FROM "+GadgetDAO.TABLE_NAME+" as G WHERE G.prodotto = ?";

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
    public GadgetBean doRetrieveByKey(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        GadgetBean gadgetBean = new GadgetBean();

        String sqlStatement = "SELECT * FROM " +GadgetDAO.TABLE_NAME+" as G INNER JOIN "+GadgetDAO.SUPER_TABLE_NAME+" as P ON G.prodotto = P.barcode WHERE P.barcode = ?";

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
                gadgetBean.setBarcode(resultSet.getString("barcode"));
                gadgetBean.setNome(resultSet.getString("nome"));
                gadgetBean.setPrezzo(resultSet.getFloat("prezzo"));
                gadgetBean.setProduttore(resultSet.getString("produttore"));
                gadgetBean.setSerie(resultSet.getString("serie"));
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
        return gadgetBean;
    }

    @Override
    public Collection<GadgetBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<GadgetBean> gadgetBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " +GadgetDAO.TABLE_NAME+" as G INNER JOIN "+GadgetDAO.SUPER_TABLE_NAME+" as P ON G.prodotto = P.barcode";

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
                GadgetBean gadgetBean = new GadgetBean();
                gadgetBean.setBarcode(resultSet.getString("barcode"));
                gadgetBean.setNome(resultSet.getString("nome"));
                gadgetBean.setPrezzo(resultSet.getFloat("prezzo"));
                gadgetBean.setProduttore(resultSet.getString("produttore"));
                gadgetBean.setSerie(resultSet.getString("serie"));
                gadgetBeanCollection.add(gadgetBean);
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
        return gadgetBeanCollection;
    }

    public void doUpdate(GadgetBean item) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String gadgetUpdate = "UPDATE "+TABLE_NAME+" SET produttore = ?, serie = ? WHERE prodotto = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            ///Eseguo l'update della tabella prodotto
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doUpdate(item);

            //Preparo il PreparedStatement per l'update della tabella videogioco
            preparedStatement = connection.prepareStatement(gadgetUpdate);
            preparedStatement.setString(1, item.getProduttore());
            preparedStatement.setString(2, item.getSerie());
            preparedStatement.setString(3, item.getBarcode());

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
