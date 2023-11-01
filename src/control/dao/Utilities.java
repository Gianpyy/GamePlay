package control.dao;

import java.util.logging.Logger;

public class Utilities {
    private static final Logger LOGGER = Logger.getLogger(control.dao.Utilities.class.getName());

    private Utilities() {
        throw new IllegalStateException("Utility class");
    }



    static java.sql.Date toSqlDate(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        }

        return null;
    }
}
