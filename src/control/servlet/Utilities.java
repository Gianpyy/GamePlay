package control.servlet;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

class Utilities {
    private static final Logger LOGGER = Logger.getLogger(Utilities.class.getName());

    private Utilities() {
        throw new IllegalStateException("Utility class");
    }

    static StringBuilder getRequestBody(HttpServletRequest req){
        //Leggo il corpo della request
        StringBuilder requestBody = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            String line;
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            }
            else {
                LOGGER.log(Level.SEVERE, "reader was null");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        LOGGER.log(Level.INFO, "Request body: {0}", requestBody);

        return requestBody;
    }

    static java.util.Date stringToDate(String date) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            LOGGER.severe(e.toString());
            return null;
        }
    }

}
