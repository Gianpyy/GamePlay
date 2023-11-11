package control.dao;

import model.PhotoBean;
import model.ProdottoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class PhotoDAO  {
    private static final Logger LOGGER = Logger.getLogger(PhotoDAO.class.getName());
    private static DataSource dataSource;

    private static final String TABLE_NAME = "immagine";
    private static final String PRODUCT_TABLE_NAME = "prodotto";

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

    public PhotoDAO() { /* Costruttore di default vuoto e senza parametri */ }

    public void uploadPhoto(PhotoBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sqlStatement = "INSERT INTO "+TABLE_NAME+" (prodotto, nome, img) VALUES (?,?,?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo la query per la tabella ordine
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, item.getProdottoAssociato());
            preparedStatement.setString(2, item.getNomeImmagine());
            preparedStatement.setBlob(3, item.getImg());

            //Eseguo la query
            preparedStatement.executeUpdate();

            //Effettuo il commit
            connection.commit();

        } finally {
            //Chiudo la connessione
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


    public boolean doDelete(String code) throws SQLException {
        return false;
    }


    public Collection<PhotoBean> doRetrieveAllImagesForProduct(String productId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<PhotoBean> photoBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " +TABLE_NAME+" WHERE prodotto = ?";


        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, productId);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nei bean
            while (resultSet.next()) {
                PhotoBean photoBean = new PhotoBean();
                photoBean.setProdottoAssociato(productId);
                photoBean.setNomeImmagine(resultSet.getString("nome"));
                photoBean.setImg(resultSet.getBinaryStream("img"));
                photoBeanCollection.add(photoBean);
            }
        } finally {
            //Chiudo la connessione
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return photoBeanCollection;
    }

    public InputStream doRetrieveCoverImageForProduct(String productId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sqlStatement = "SELECT img FROM " + TABLE_NAME + " WHERE prodotto = ? AND nome LIKE 'cover.%'";

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBinaryStream("img");
            }
        } finally {
            //Chiudo la connessione
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return null; // Se l'immagine di copertina non è presente
    }

}
