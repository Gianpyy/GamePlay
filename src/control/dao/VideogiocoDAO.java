package control.dao;

import model.VideogiocoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class VideogiocoDAO implements IBeanDAO<VideogiocoBean, String>{
    private static DataSource dataSource;

    //L'entità è debole ed ha la chiave primaria in un'altra tabella, quindi servono due stringhe con i nomi delle tabelle
    private static final String SUPER_TABLE_NAME = "prodotto"; //nome tabella con la chiave primaria nel database
    private static final String TABLE_NAME = "videogioco"; //nome tabella nel database

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

    public VideogiocoDAO() { /* Costruttore di default vuoto e senza parametri */ }

    @Override
    public void doSave(VideogiocoBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String inserimentoVideogioco = "INSERT INTO "+ VideogiocoDAO.TABLE_NAME +"(prodotto, piattaforma, descrizione, dataRilascio, condizioni, numeroGiocatori, etaPEGI, categoria, edizione) VALUES  (?,?,?,?,?,?,?,?,?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Inserisco le informazioni nella tabella prodotto
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(item);

            //Preparo l'inserimento nella tabella videogioco
            preparedStatement = connection.prepareStatement(inserimentoVideogioco);
            preparedStatement.setString(1, item.getBarcode());
            preparedStatement.setString(2, item.getPiattaforma());
            preparedStatement.setString(3, item.getDescrizione());
            preparedStatement.setDate(4, toSqlDate(item.getDataRilascio()));
            preparedStatement.setString(5, item.getCondizioni());
            preparedStatement.setString(6, item.getNumeroGiocatori());
            preparedStatement.setInt(7, item.getEtaPegi());
            preparedStatement.setString(8, item.getCategoria());
            preparedStatement.setString(9, item.getEdizione());

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

        String sqlStatement = "DELETE FROM "+VideogiocoDAO.TABLE_NAME+" as V WHERE V.prodotto = ?";

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
    public VideogiocoBean doRetrieveByKey(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        VideogiocoBean videogiocoBean = new VideogiocoBean();

        String sqlStatement = "SELECT * FROM " +VideogiocoDAO.TABLE_NAME+" as V INNER JOIN "+VideogiocoDAO.SUPER_TABLE_NAME+" as P ON V.prodotto = P.barcode WHERE P.barcode = ?";

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
                videogiocoBean.setBarcode(resultSet.getString("barcode"));
                videogiocoBean.setNome(resultSet.getString("nome"));
                videogiocoBean.setPrezzo(resultSet.getFloat("prezzo"));
                videogiocoBean.setPiattaforma(resultSet.getString("piattaforma"));
                videogiocoBean.setDescrizione(resultSet.getString("descrizione"));
                videogiocoBean.setDataRilascio(resultSet.getDate("dataRilascio"));
                videogiocoBean.setCondizioni(resultSet.getString("condizioni"));
                videogiocoBean.setNumeroGiocatori(resultSet.getString("numeroGiocatori"));
                videogiocoBean.setEtaPegi(resultSet.getInt("etaPEGI"));
                videogiocoBean.setCategoria(resultSet.getString("categoria"));
                videogiocoBean.setEdizione(resultSet.getString("edizione"));
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
        return videogiocoBean;
    }

    @Override
    public Collection<VideogiocoBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<VideogiocoBean> videogiocoBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " +VideogiocoDAO.TABLE_NAME+" as V INNER JOIN "+VideogiocoDAO.SUPER_TABLE_NAME+" as P ON V.prodotto = P.barcode";

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
                VideogiocoBean videogiocoBean = new VideogiocoBean();
                videogiocoBean.setBarcode(resultSet.getString("barcode"));
                videogiocoBean.setNome(resultSet.getString("nome"));
                videogiocoBean.setPrezzo(resultSet.getFloat("prezzo"));
                videogiocoBean.setPiattaforma(resultSet.getString("piattaforma"));
                videogiocoBean.setDescrizione(resultSet.getString("descrizione"));
                videogiocoBean.setDataRilascio(resultSet.getDate("dataRilascio"));
                videogiocoBean.setCondizioni(resultSet.getString("condizioni"));
                videogiocoBean.setNumeroGiocatori(resultSet.getString("numeroGiocatori"));
                videogiocoBean.setEtaPegi(resultSet.getInt("etaPEGI"));
                videogiocoBean.setCategoria(resultSet.getString("categoria"));
                videogiocoBean.setEdizione(resultSet.getString("edizione"));
                videogiocoBeanCollection.add(videogiocoBean);
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

        return videogiocoBeanCollection;
    }


    public Collection<VideogiocoBean> doRetrieveAllByVideogameName(String name) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<VideogiocoBean> videogiocoBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " +VideogiocoDAO.TABLE_NAME+" as V INNER JOIN "+VideogiocoDAO.SUPER_TABLE_NAME+" as P ON V.prodotto = P.barcode WHERE P.nome = ?";

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
                VideogiocoBean videogiocoBean = new VideogiocoBean();
                videogiocoBean.setBarcode(resultSet.getString("barcode"));
                videogiocoBean.setNome(resultSet.getString("nome"));
                videogiocoBean.setPrezzo(resultSet.getFloat("prezzo"));
                videogiocoBean.setPiattaforma(resultSet.getString("piattaforma"));
                videogiocoBean.setDescrizione(resultSet.getString("descrizione"));
                videogiocoBean.setDataRilascio(resultSet.getDate("dataRilascio"));
                videogiocoBean.setCondizioni(resultSet.getString("condizioni"));
                videogiocoBean.setNumeroGiocatori(resultSet.getString("numeroGiocatori"));
                videogiocoBean.setEtaPegi(resultSet.getInt("etaPEGI"));
                videogiocoBean.setCategoria(resultSet.getString("categoria"));
                videogiocoBean.setEdizione(resultSet.getString("edizione"));
                videogiocoBeanCollection.add(videogiocoBean);
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

        return videogiocoBeanCollection;
    }

    public void doUpdate(VideogiocoBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String videogiocoUpdate = "UPDATE "+TABLE_NAME+
                " SET piattaforma = ?, descrizione = ?, dataRilascio = ?, condizioni = ?, numeroGiocatori = ?, etaPEGI = ?, edizione = ?, categoria = ?" +
                " WHERE prodotto = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Eseguo l'update della tabella prodotto
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doUpdate(item);

            //Preparo il PreparedStatement per l'update della tabella videogioco
            preparedStatement = connection.prepareStatement(videogiocoUpdate);
            preparedStatement.setString(1, item.getPiattaforma());
            preparedStatement.setString(2, item.getDescrizione());
            preparedStatement.setDate(3, Utilities.toSqlDate(item.getDataRilascio()));
            preparedStatement.setString(4, item.getCondizioni());
            preparedStatement.setString(5, item.getNumeroGiocatori());
            preparedStatement.setInt(6, item.getEtaPegi());
            preparedStatement.setString(7, item.getEdizione());
            preparedStatement.setString(8, item.getCategoria());
            preparedStatement.setString(9, item.getBarcode());

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


    private static java.sql.Date toSqlDate(Date data) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.set(Calendar.HOUR, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new java.sql.Date(calendar.getTimeInMillis());
    }
}
