package control.dao;

import java.sql.SQLException;
import java.util.Collection;

//T è il Bean da usare
//G è il tipo della chiave primaria
public interface IBeanDAO<T,G> {

        void doSave(T item) throws SQLException;

        boolean doDelete(G code) throws SQLException;

        T doRetrieveByKey(G code) throws SQLException;

        Collection<T> doRetrieveAll(String order) throws SQLException;
}
