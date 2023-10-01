package control.dao;

import model.AnagraficaUtenteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AnagraficaUtenteDAO implements IBeanDAO<AnagraficaUtenteBean> {
    private static DataSource dataSource;

    private static final String TABLE_NAME = "anagraficautente";

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
    public void doSave(AnagraficaUtenteBean anagrafica) throws SQLException  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sqlStatement = "INSERT INTO " +AnagraficaUtenteDAO.TABLE_NAME + "(codiceFiscale, nome, cognome, sesso, dataDiNascita, codice) VALUES (?,?,?,?,?,?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, anagrafica.getCodiceFiscale());
            preparedStatement.setString(2, anagrafica.getNome());
            preparedStatement.setString(3, anagrafica.getCognome());
            preparedStatement.setString(4, anagrafica.getSesso());
            preparedStatement.setDate(5, AnagraficaUtenteDAO.toSqlDate(anagrafica.getDataDiNascita()));
            preparedStatement.setInt(6, anagrafica.getCodiceUtente());

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

        String sqlStatement = "DELETE FROM "+AnagraficaUtenteDAO.TABLE_NAME+" WHERE codice = ?";

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
                    connection.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return (result != 0);
    }

    @Override
    public AnagraficaUtenteBean doRetrieveByKey(int code) throws SQLException {
        //todo: method implementation
        return null;
    }

    @Override
    public Collection<AnagraficaUtenteBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<AnagraficaUtenteBean> anagraficaUtenteBeanCollection = new LinkedList<>();

        String sqlStatement = "SELECT * FROM " + AnagraficaUtenteDAO.TABLE_NAME;

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
                AnagraficaUtenteBean anagraficaUtenteBean = new AnagraficaUtenteBean();
                anagraficaUtenteBean.setCodiceFiscale(resultSet.getString("codiceFiscale"));
                anagraficaUtenteBean.setNome(resultSet.getString("nome"));
                anagraficaUtenteBean.setCognome(resultSet.getString("cognome"));
                anagraficaUtenteBean.setSesso(resultSet.getString("sesso"));
                anagraficaUtenteBean.setDataDiNascita(resultSet.getDate("data"));
                anagraficaUtenteBean.setCodiceUtente(resultSet.getInt("codice"));
                anagraficaUtenteBeanCollection.add(anagraficaUtenteBean);
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

        return anagraficaUtenteBeanCollection;
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
