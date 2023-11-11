package control.servlet;

import control.dao.ProdottoDAO;
import model.ProdottoBean;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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

    static StringBuilder readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        return result;
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

    static String generateBarcode() throws SQLException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<ProdottoBean> prodottoBeanList = (List<ProdottoBean>) prodottoDAO.doRetrieveAll("");

        LOGGER.log(Level.INFO, "Retrieved {0} products", prodottoBeanList.size());
        for (ProdottoBean p : prodottoBeanList) {
            LOGGER.log(Level.INFO, "{0}", p.getBarcode());
        }

        long lastBarcode = (Long.parseLong(prodottoBeanList.get(prodottoBeanList.size()-1).getBarcode()));
        long barcodeToGenerate = lastBarcode +1;
        LOGGER.log(Level.INFO, "Generated barcode {0}", barcodeToGenerate);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        return numberFormat.format(barcodeToGenerate);
    }
}
