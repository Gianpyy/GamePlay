package control.servlet;

import control.dao.ProdottoDAO;
import model.ProdottoBean;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Carrello")
public class CarrelloServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CarrelloServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.doGet(req, resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupero il tipo di azione dalla request
        String action = req.getParameter("actionType");
        JSONObject json = new JSONObject();
        if (action == null) {
            //Recupero il requestBody dalla reqyest
            StringBuilder requestBody = getRequestBody(req);

            //Analizzo il JSON della request
            json = new JSONObject(requestBody.toString());

            //Recupero il tipo di azione dalla request
            try {
                action = json.getString("actionType");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }

        switch (Objects.requireNonNull(action)) {
            case "addProduct" -> {
                LOGGER.info("Switch case: addProduct");

                //Recupero l'id del prodotto
                String productId = (String) req.getSession().getAttribute("productId");

                //Recupero il prodotto da inserire nel carrello
                ProdottoBean prodottoDaAggiungere = new ProdottoBean();
                try {
                    prodottoDaAggiungere = new ProdottoDAO().doRetrieveByKey(productId);
                    LOGGER.log(Level.INFO, "Product retrieved");
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                //Recupero la flag del carrello
                Boolean isCarrelloEmpty = (Boolean) req.getSession().getAttribute("isCarrelloEmpty");

                //Se è già presente un carrello, aggiungo il prodotto al carrello dell'utente
                if(Boolean.FALSE.equals(isCarrelloEmpty)) {
                    //Recupero carrello e contatore prodotti
                    List<ProdottoBean> carrello = (List<ProdottoBean>) req.getSession().getAttribute("carrello");
                    HashMap<String, Integer> prodottiCounter = (HashMap<String, Integer>) req.getSession().getAttribute("prodottiCounter");

                    //Aggiungo il prodotto e incremento il contatore
                    //Se il prodotto è già presente, devo solo aggiornare il contatore dei prodotti, altrimenti devo anche aggiungere il prodotto alla lista dei prodotti
                    final String prodottoDaAggiungereBarcode = prodottoDaAggiungere.getBarcode();
                    boolean isAlreadyPresent = carrello.stream().anyMatch(prodottoBean -> prodottoBean.getBarcode().equals(prodottoDaAggiungereBarcode));
                    if (isAlreadyPresent) {
                        prodottiCounter.merge(prodottoDaAggiungere.getBarcode(), 1, Integer::sum);
                    }
                    else {
                        carrello.add(prodottoDaAggiungere);
                        prodottiCounter.put(prodottoDaAggiungere.getBarcode(), 1);
                    }

                    //Aggiungo carrello e contatore alla sessione
                    req.getSession().setAttribute("carrello", carrello);
                    req.getSession().setAttribute("prodottiCounter", prodottiCounter);

                    //Altrimenti, creo il carrello e aggiungo il primo prodotto
                } else {
                    //Setto la flag del carrello
                    req.getSession().setAttribute("isCarrelloEmpty", Boolean.FALSE);

                    //Creo il carrello e il contatore dei prodotti
                    List<ProdottoBean> carrello = new LinkedList<>();
                    HashMap<String, Integer> prodottiCounter = new HashMap<>();

                    //Aggiungo il prodotto e incremento il contatore
                    carrello.add(prodottoDaAggiungere);
                    prodottiCounter.put(prodottoDaAggiungere.getBarcode(), 1);

                    //Aggiungo carrello e contatore alla sessione
                    req.getSession().setAttribute("carrello", carrello);
                    req.getSession().setAttribute("prodottiCounter", prodottiCounter);
                }
            }

            case "emptyCart" -> {
                LOGGER.info("Switch case: emptyCart");

                //Recupero carrello e contatore prodotti
                List<ProdottoBean> carrello = (List<ProdottoBean>) req.getSession().getAttribute("carrello");
                HashMap<String, Integer> prodottiCounter = (HashMap<String, Integer>) req.getSession().getAttribute("prodottiCounter");

                //Svuoto carrello e contatore prodotti e li rimuovo dalla sessione
                carrello.clear();
                req.getSession().removeAttribute("carrello");
                prodottiCounter.clear();
                req.getSession().removeAttribute("prodottiCounter");
                req.getSession().setAttribute("isCarrelloEmpty", Boolean.TRUE);
            }

            case "removeProduct" -> {
                //Recupero l'id del prodotto dal JSON
                String productId = json.getString("productId");

                //Recupero carrello e contatore prodotti
                List<ProdottoBean> carrello = (List<ProdottoBean>) req.getSession().getAttribute("carrello");
                HashMap<String, Integer> prodottiCounter = (HashMap<String, Integer>) req.getSession().getAttribute("prodottiCounter");

                //Rimuovo il prodotto dal carrello e dal contatore prodotti
                carrello.removeIf(prodottoBean -> prodottoBean.getBarcode().equals(productId));
                prodottiCounter.remove(productId);

                //Se il prodotto che ho rimosso era l'ultimo, devo considerare il carrello come vuoto
                if(carrello.isEmpty()) {
                    req.getSession().removeAttribute("carrello");
                    req.getSession().removeAttribute("prodottiCounter");
                    req.getSession().setAttribute("isCarrelloEmpty", Boolean.TRUE);
                }
            }

            case "updateCart?????" -> {
                LOGGER.info("Switch case: updateCart");
            }

            default -> {
                //Reindirizzo alla jsp per ora
                resp.sendRedirect("index.jsp");
            }
        }



        //Reindirizzo alla pagina del carrello
        try {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("carrello.jsp");
            requestDispatcher.forward(req,resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }



    private StringBuilder getRequestBody(HttpServletRequest req){
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

}
