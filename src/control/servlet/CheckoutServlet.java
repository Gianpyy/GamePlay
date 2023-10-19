package control.servlet;

import control.dao.OrdineDAO;
import model.OrdineBean;
import model.ProdottoBean;

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
        //Recupero il tipo di azione dalla request
        String action = req.getParameter("actionType");

        if (action.equals("checkoutButton")) {
            //Controllo che l'utente sia loggato e che il carrello non sia vuoto
            List<String> errors = new ArrayList<>();
            Boolean isUserLogged = (Boolean) req.getSession().getAttribute("isLogged");
            Boolean isCartEmpty = (Boolean) req.getSession().getAttribute("isCarrelloEmpty");

            if(Boolean.FALSE.equals(isUserLogged)) {
                //Aggiungo l'errore alla lista degli errori
                errors.add("Devi essere autenticato per effettuare un ordine.");
            }

            if (Boolean.TRUE.equals(isCartEmpty)) {
                //Aggiungo l'errore alla lista degli errori
                errors.add("Non è possibile effettuare un ordine con un carrello vuoto.");
            }

            //Se ci sono errori, rispedisco alla pagina del carrello e invio gli errori
            if (Boolean.FALSE.equals(errors.isEmpty())) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("carrello.jsp");
                req.setAttribute("errors", errors);
                try {
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }
            }

            //Reindirizzo alla pagina del checkout
            resp.sendRedirect("checkout.jsp");
        }
        else if (action.equals("checkout")) {
            //Recupero carrello e contatore prodotti dalla sessione
            List<ProdottoBean> carrello = (List<ProdottoBean>) req.getSession().getAttribute("carrello");
            HashMap<String, Integer> prodottiCounter = (HashMap<String, Integer>) req.getSession().getAttribute("prodottiCounter");


            //Recupero i dati dell'ordine dalla request
            String indirizzo = req.getParameter("indirizzo");
            String pagamento = req.getParameter("pagamento");

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

            //Svuoto carrello e contatore prodotti
            carrello.clear();
            prodottiCounter.clear();

            //Reindirizzo alla homepage (per ora)
            try {
                resp.sendRedirect("index.jsp");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
    }
}

