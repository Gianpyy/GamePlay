package control.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface IBeanDAO<T> {

        void doSave(T product) throws SQLException;

        boolean doDelete(int code) throws SQLException;

        T doRetrieveByKey(int code) throws SQLException;

        Collection<T> doRetrieveAll(String order) throws SQLException;
}
