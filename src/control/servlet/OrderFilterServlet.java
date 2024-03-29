package control.servlet;

import control.dao.OrdineDAO;
import model.OrdineBean;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/OrderFilter")
public class OrderFilterServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OrderFilterServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("gestione_ordini.jsp");

        //Recupero il requestBody dalla request
        StringBuilder requestBody = Utilities.getRequestBody(req);

        //Inizializzo l'oggetto JSON
        JSONObject json = new JSONObject(requestBody.toString());

        //Recupero il tipo di filtro
        String filterType = "";
        try {
            filterType = json.getString("filterType");
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            resp.addHeader("OPERATION-RESULT", "error");
            requestDispatcher.forward(req,resp);
            return;
        }

        //Recupero gli ordini dal database
        List<OrdineBean> filteredOrders = new ArrayList<>();
        switch (filterType) {
            case "data" -> {
                //Recupero i dati dal requestBody
                Date fromDate = null;
                Date toDate = null;

                try {
                    fromDate = Utilities.stringToDate(json.getString("dataInizio"));
                    toDate = Utilities.stringToDate(json.getString("dataFine"));

                    //Recupero gli ordini dal database
                    OrdineDAO ordineDAO = new OrdineDAO();
                    filteredOrders = (List<OrdineBean>) ordineDAO.doRetrieveByDatePeriod(fromDate, toDate);
                    LOGGER.log(Level.INFO, "Retrieved {0} orders", filteredOrders.size());
                } catch (Exception e) {
                    LOGGER.severe(e.toString());
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req,resp);
                    return;
                }
            }

            case "orderID" -> {
                //Recupero i dati dal request body
                int orderID = -1;
                try {
                    orderID = json.getInt("orderID");

                    //Recupero gli ordini dal database
                    OrdineDAO ordineDAO = new OrdineDAO();
                    OrdineBean filtered = ordineDAO.doRetrieveByKey(orderID);

                    if (filtered != null) {
                        filteredOrders.add(filtered);
                    }
                } catch (Exception e) {
                    LOGGER.severe(e.toString());
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req,resp);
                    return;
                }
            }

            case "userId" -> {
                //Recupero i dati dal request body
                int userID = -1;
                try {
                    userID = json.getInt("userId");

                    //Recupero gli ordini dal database
                    OrdineDAO ordineDAO = new OrdineDAO();
                    filteredOrders = (List<OrdineBean>) ordineDAO.doRetrieveByUserId(userID);
                    LOGGER.log(Level.INFO, "Retrieved {0} orders", filteredOrders.size());
                } catch (Exception e) {
                    LOGGER.severe(e.toString());
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req,resp);
                    return;
                }
            }
        }

        //Aggiungo gli ordini al JSON da inviare come risposta
        JSONArray ordiniResponse = new JSONArray();
        if (!filteredOrders.isEmpty()) {
            LOGGER.log(Level.INFO, "Orders in response: {0}", filteredOrders.size());
            for (OrdineBean ordine : filteredOrders) {
                JSONObject jsonOrdine = new JSONObject();
                jsonOrdine.put("numeroOrdine", ordine.getNumeroOrdine());
                jsonOrdine.put("data", ordine.getData());
                jsonOrdine.put("totale", ordine.getTotale());
                jsonOrdine.put("metodoPagamento", ordine.getMetodoPagamento());
                jsonOrdine.put("stato", ordine.getStato());
                jsonOrdine.put("indirizzo", ordine.getIndirizzo());

                ordiniResponse.put(jsonOrdine);
            }
        }

        LOGGER.log(Level.INFO, "Response to send: {0}", ordiniResponse.toString());

        //Invio gli ordini come risposta
        resp.setContentType("application/json");
        resp.getWriter().write(ordiniResponse.toString());
    }
}
