package control.dao;

import model.OrdineBean;
import model.ProdottoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdineDAO implements IBeanDAO<OrdineBean, Integer>{
    private static final Logger LOGGER = Logger.getLogger(OrdineDAO.class.getName());
    private static DataSource dataSource;

    private static final String ORDINE_TABLE_NAME = "ordine";
    private static final String CONTIENE_TABLE_NAME = "contiene";
    private static final String EFFETTUTATO_TABLE_NAME = "effettuato_da";
    private static final String UTENTE_TABLE_NAME = "utente";

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

    public OrdineDAO() { /* Costruttore di default vuoto e senza parametri */ }

    @Override
    public void doSave(OrdineBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String inserimentoOrdine = "INSERT INTO "+ORDINE_TABLE_NAME+" (dataAcquisto, metodoPagamento, importoTotale, indirizzoSpedizione, stato) VALUES (?,?,?,?,?)";
        String inserimentoContiene = "INSERT INTO "+CONTIENE_TABLE_NAME+" (prodotto, ordine, quantita, nome, prezzo) VALUES (?,?,?,?,?)";
        String inserimentoEffettuatoDa = "INSERT INTO "+EFFETTUTATO_TABLE_NAME+" (ordine, utente) VALUES (?,?)";

        try{
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo la query per la tabella ordine
            preparedStatement = connection.prepareStatement(inserimentoOrdine, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, Utilities.toSqlDate(item.getData()));
            preparedStatement.setString(2, item.getMetodoPagamento());
            preparedStatement.setFloat(3, item.getTotale());
            preparedStatement.setString(4, item.getIndirizzo());
            preparedStatement.setString(5, "Pagamento ricevuto");


            //Eseguo la query
            preparedStatement.executeUpdate();

            //Recupero l'id autogenerato
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int orderID = -1;
            if (generatedKeys.next()) {
                orderID = generatedKeys.getInt(1);
            }

            //Preparo le query per la tabella contiene
            List<ProdottoBean> prodotti = item.getProdotti();
            List<Integer> quantitaProdotti = item.getQuantitaProdotti();
            for (int i = 0; i < prodotti.size(); i++) {
                preparedStatement = connection.prepareStatement(inserimentoContiene);
                preparedStatement.setString(1, prodotti.get(i).getBarcode());
                preparedStatement.setInt(2, orderID);
                preparedStatement.setInt(3, quantitaProdotti.get(i));
                preparedStatement.setString(4, prodotti.get(i).getNome());
                preparedStatement.setFloat(5, prodotti.get(i).getPrezzo());
                preparedStatement.executeUpdate();
            }

            //Preparo la query per la tabella "effettuato_da"
            preparedStatement = connection.prepareStatement(inserimentoEffettuatoDa);
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, item.getUserID());
            preparedStatement.executeUpdate();


            //Effettuo il commit
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
    public boolean doDelete(Integer code) throws SQLException {
        return false;
    }

    @Override
    public OrdineBean doRetrieveByKey(Integer code) throws SQLException {
        return null;
    }

    @Override
    public Collection<OrdineBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<OrdineBean> ordineBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM "+ORDINE_TABLE_NAME;

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato nella query nella lista di bean
            while (resultSet.next()){
                OrdineBean ordineBean = new OrdineBean();
                ordineBean.setNumeroOrdine(resultSet.getInt("numeroOrdine"));
                ordineBean.setData(resultSet.getDate("dataAcquisto"));
                ordineBean.setMetodoPagamento(resultSet.getString("metodoPagamento"));
                ordineBean.setTotale(resultSet.getFloat("importoTotale"));
                ordineBean.setStato(resultSet.getString("stato"));
                ordineBean.setIndirizzo(resultSet.getString("indirizzoSpedizione"));
                ordineBeanCollection.add(ordineBean);
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

        return ordineBeanCollection;
    }

    public Collection<OrdineBean> doRetrieveByUserId(int userid) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<OrdineBean> ordineBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT o.*, c.* FROM "+UTENTE_TABLE_NAME+ " as u INNER JOIN " +
                EFFETTUTATO_TABLE_NAME+" AS ed ON u.codice = ed.utente INNER JOIN "+ORDINE_TABLE_NAME+" AS o ON ed.ordine = o.numeroOrdine INNER JOIN " +
                CONTIENE_TABLE_NAME+" AS c ON o.numeroOrdine = c.ordine WHERE u.codice = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userid);

            //Eseguo la query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Salvo il risultato della query nel bean
            int prevOrderId = 0; //Inizializzato a 0 perché il primo ordine deve essere sempre diverso dal precedente
            boolean isFirst = true; //Serve a far andare avanti il ciclo
            OrdineBean ordineBean = new OrdineBean();
            while (resultSet.next()) {
                //Recupero l'id dell'ordine
                int orderID = resultSet.getInt("numeroOrdine");
                LOGGER.log(Level.INFO, "Current orderID: {0}", orderID);

                //Se l'id dell'ordine corrente è uguale all'id dell'ordine precedente, l'ordine conteneva più prodotti
                //quindi non devo creare un nuovo ordine ma solo aggiungere il prodotto
                if (orderID == prevOrderId) {
                    LOGGER.info("Same id as previous row");
                    //Aggiungo i dati del prodotto all'ordine
                    ProdottoBean prodottoBean = new ProdottoBean();
                    prodottoBean.setBarcode(resultSet.getString("prodotto"));
                    prodottoBean.setNome(resultSet.getString("nome"));
                    prodottoBean.setPrezzo(resultSet.getFloat("prezzo"));
                    ordineBean.addProdotto(prodottoBean, resultSet.getInt("quantita"));
                }
                //Invece, se l'id è diverso, devo creare un nuovo ordine da aggiungere alla lista degli ordini
                else {
                    //Non è più il primo ordine, posso aggiungere alla collection
                    if (!isFirst) {
                        ordineBeanCollection.add(ordineBean);
                        LOGGER.log(Level.INFO, "Added order {0} to collection", ordineBean.getNumeroOrdine());
                    }

                    //Aggiungo i dati dell'ordine
                    ordineBean = new OrdineBean();
                    ordineBean.setNumeroOrdine(orderID);
                    ordineBean.setData(resultSet.getDate("dataAcquisto"));
                    ordineBean.setMetodoPagamento(resultSet.getString("metodoPagamento"));
                    ordineBean.setTotale(resultSet.getFloat("importoTotale"));
                    ordineBean.setStato(resultSet.getString("stato"));
                    ordineBean.setIndirizzo(resultSet.getString("indirizzoSpedizione"));

                    //Aggiungo i dati del prodotto all'ordine
                    ProdottoBean prodottoBean = new ProdottoBean();
                    prodottoBean.setBarcode(resultSet.getString("prodotto"));
                    prodottoBean.setNome(resultSet.getString("nome"));
                    prodottoBean.setPrezzo(resultSet.getFloat("prezzo"));
                    ordineBean.addProdotto(prodottoBean, resultSet.getInt("quantita"));

                    //Setto la flag a false, se arrivo qui sono necessariamente nel primo ordine
                    isFirst = false;

                    //Salvo l'id dell'ordine in prevOrderId
                    prevOrderId = orderID;
                }
            }
            LOGGER.info("End of while");
            //Se isFirst è true vuol dire che non è stato trovato alcun ordine
            if (!isFirst) {
                ordineBeanCollection.add(ordineBean);
            }
            LOGGER.log(Level.INFO, "Added order {0} to collection", ordineBean.getNumeroOrdine());
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return ordineBeanCollection;
    }

    public boolean doUpdateOrderStatus(Integer orderId, String orderStatus) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;

        String sqlStatement = "UPDATE ordine SET stato = ? WHERE numeroOrdine = ?";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, orderStatus);
            preparedStatement.setInt(2, orderId);

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
