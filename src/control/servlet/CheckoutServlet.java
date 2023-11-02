package control.servlet;

import control.dao.OrdineDAO;
import model.OrdineBean;
import model.ProdottoBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CheckoutServlet.class.getName());

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
        //Recupero il requestBody dalla request
        StringBuilder requestBody = Utilities.getRequestBody(req);

        //Inizializzo l'oggetto JSON
        JSONObject json = new JSONObject(requestBody.toString());

        //Recupero il tipo di azione dalla request
        String action = "";
        try {
            action = json.getString("actionType");
        } catch (Exception e) {
            LOGGER.severe(e.toString());
        }

        if (action.equals("checkoutButton")) {
            //Controllo che l'utente sia loggato e che il carrello non sia vuoto
            Boolean isUserLogged = (Boolean) req.getSession().getAttribute("isLogged");
            Boolean isCartEmpty = (Boolean) req.getSession().getAttribute("isCarrelloEmpty");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("carrello.jsp");

            if(Boolean.FALSE.equals(isUserLogged)) {
                resp.addHeader("OPERATION-RESULT", "userNotLogged");
                try {
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                return;
            }

            if (Boolean.TRUE.equals(isCartEmpty)) {
                resp.addHeader("OPERATION-RESULT", "cartEmpty");
                try {
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                return;
            }

            //Reindirizzo alla pagina del checkout
            try {
                resp.addHeader("OPERATION-RESULT", "success");
                requestDispatcher.forward(req,resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
        else if (action.equals("checkout")) {
            //Controllo che indirizzo di spedizione e metodo di pagamento siano stati selezionati
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("checkout.jsp");
            String indirizzo = "";
            String pagamento = "";
            try {
//                indirizzo = json.getString("citta") + "(" + json.getString("provincia") +")" + json.getString("indirizzo") + json.getString("cap");
                indirizzo = json.getString("indirizzo");
                pagamento = json.getString("pagamento");
            } catch (Exception e) {
                LOGGER.severe(e.toString());
            }

            if (indirizzo.trim().isEmpty()) {
                resp.addHeader("OPERATION-RESULT", "error");
                try {
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                return;
            }
            if (pagamento.trim().isEmpty()) {
                resp.addHeader("OPERATION-RESULT", "error");
                try {
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                return;
            }

            //Recupero carrello e contatore prodotti dalla sessione
            List<ProdottoBean> carrello = (List<ProdottoBean>) req.getSession().getAttribute("carrello");
            HashMap<String, Integer> prodottiCounter = (HashMap<String, Integer>) req.getSession().getAttribute("prodottiCounter");


            //Creo il bean dell'ordine
            OrdineBean ordine = new OrdineBean();

            //Assegno all'ordine i valori recuperati dalla request e dalla sessione
            float totale = 0f;
            for (ProdottoBean p : carrello) {
                int quantita = prodottiCounter.get(p.getBarcode());
                ordine.addProdotto(p, quantita);
                totale += p.getPrezzo()*quantita;
            }
            ordine.setIndirizzo(indirizzo);
            ordine.setMetodoPagamento(pagamento);
            ordine.setData(new Date());
            ordine.setTotale(totale);
            ordine.setUserID((Integer) req.getSession().getAttribute("userid"));

            //Faccio salvare l'ordine nel database al DAO
            OrdineDAO ordineDAO = new OrdineDAO();
            try {
                ordineDAO.doSave(ordine);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            //Svuoto carrello e contatore prodotti e li rimuovo dalla sessione
            carrello.clear();
            req.getSession().removeAttribute("carrello");
            prodottiCounter.clear();
            req.getSession().removeAttribute("prodottiCounter");
            req.getSession().setAttribute("isCarrelloEmpty", Boolean.TRUE);

            //Aggiungo le informazioni dell'ordine alla sessione
            req.getSession().setAttribute("ordine", ordine);

            //Reindirizzo alla pagina di riepilogo dell'ordine
            try {
                requestDispatcher = req.getRequestDispatcher("riepilogo_ordine.jsp");
                resp.addHeader("OPERATION-RESULT", "success");
                requestDispatcher.forward(req,resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
    }
}

