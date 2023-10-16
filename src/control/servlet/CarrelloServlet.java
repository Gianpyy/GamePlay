package control.servlet;

import control.dao.ProdottoDAO;
import model.ProdottoBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

        //Aggiungo il prodotto al carrello
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

        //Reindirizzo alla pagina del carrello
        try {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("carrello.jsp");
            requestDispatcher.forward(req,resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }
}
