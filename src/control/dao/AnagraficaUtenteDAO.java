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

public class AnagraficaUtenteDAO implements IBeanDAO<AnagraficaUtenteBean, String> {
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
    public void doSave(AnagraficaUtenteBean item) throws SQLException  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sqlStatement = "INSERT INTO " +AnagraficaUtenteDAO.TABLE_NAME + "(codiceFiscale, nome, cognome, sesso, dataDiNascita, codice) VALUES (?,?,?,?,?,?)";

        try {
            //Ottengo la connessione
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            //Preparo il PreparedStatement
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, item.getCodiceFiscale());
            preparedStatement.setString(2, item.getNome());
            preparedStatement.setString(3, item.getCognome());
            preparedStatement.setString(4, item.getSesso());
            preparedStatement.setDate(5, AnagraficaUtenteDAO.toSqlDate(item.getDataDiNascita()));
            preparedStatement.setInt(6, item.getCodiceUtente());

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

        String sqlStatement = "DELETE FROM "+AnagraficaUtenteDAO.TABLE_NAME+" WHERE codice = ?";

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
                    connection.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return (result != 0);
    }

    @Override
    public AnagraficaUtenteBean doRetrieveByKey(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        AnagraficaUtenteBean anagraficaUtenteBean = new AnagraficaUtenteBean();

        String sqlStatement = "SELECT * FROM "+AnagraficaUtenteDAO.TABLE_NAME+" WHERE codiceFiscale = ?";

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
                anagraficaUtenteBean.setCodiceFiscale(resultSet.getString("codiceFiscale"));
                anagraficaUtenteBean.setNome(resultSet.getString("nome"));
                anagraficaUtenteBean.setCognome(resultSet.getString("cognome"));
                anagraficaUtenteBean.setSesso(resultSet.getString("sesso"));
                anagraficaUtenteBean.setDataDiNascita(resultSet.getDate("dataDiNascita"));
                anagraficaUtenteBean.setCodiceUtente(resultSet.getInt("codice"));
            }//Chiudo la connessione
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return anagraficaUtenteBean;
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
