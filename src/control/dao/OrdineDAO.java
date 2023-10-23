package control.dao;

import model.OrdineBean;
import model.ProdottoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class OrdineDAO implements IBeanDAO<OrdineBean, Integer>{
    private static DataSource dataSource;

    private static final String ORDINE_TABLE_NAME = "ordine";
    private static final String CONTIENE_TABLE_NAME = "contiene";
    private static final String EFFETTUTATO_TABLE_NAME = "effettuato_da";

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
            preparedStatement.setDate(1, toSqlDate(item.getData()));
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
        return null;
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
