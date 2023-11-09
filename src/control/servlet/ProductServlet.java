package control.servlet;

import control.dao.ConsoleDAO;
import control.dao.GadgetDAO;
import control.dao.ProdottoDAO;
import control.dao.VideogiocoDAO;
import model.ConsoleBean;
import model.GadgetBean;
import model.ProdottoBean;
import model.VideogiocoBean;
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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Product")
public class ProductServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProductServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doPost(req,resp);
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

        //Recupero l'id del prodotto dal json
        String productId = "";
        try {
            productId = json.getString("productId");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        ProdottoBean requestedProduct = new ProdottoBean();

        //Recupero la tipologia del prodotto associato all'id
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        try {
            requestedProduct = prodottoDAO.doRetrieveByKey(productId);
            LOGGER.log(Level.INFO, "Retrieved product with id: {0}", productId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        switch (requestedProduct.getTipo()) {
            case "videogioco" -> {
                //Recupero tutte le edizioni del videogioco specificato dall'id nella request
                VideogiocoDAO videogiocoDAO = new VideogiocoDAO();
                LinkedList<VideogiocoBean> videogiocoBeans = new LinkedList<>();
                try {
                    videogiocoBeans = (LinkedList<VideogiocoBean>) videogiocoDAO.doRetrieveAllByVideogameName(requestedProduct.getNome());
                    LOGGER.log(Level.INFO, "Retrieved {0} products", videogiocoBeans.size());
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                //Aggiungo il bean alla request
                req.getSession().setAttribute("prodotto", requestedProduct);
                req.getSession().setAttribute("edizioni-piattaforme", videogiocoBeans);

                //Aggiungo come header nella response il tipo di prodotto
                resp.addHeader("PRODUCT-TYPE", "videogioco");

                //Reindirizzo alla pagina prodotto
                try {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("videogioco.jsp");
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }
            }
            case "console" -> {
                //Recupero tutte le edizioni della console specificata dall'id nella request
                ConsoleDAO consoleDAO = new ConsoleDAO();
                LinkedList<ConsoleBean> consoleBeans = new LinkedList<>();
                try {
                    consoleBeans = (LinkedList<ConsoleBean>) consoleDAO.doRetrieveAllByConsoleName(requestedProduct.getNome());
                    LOGGER.log(Level.INFO, "Retrieved {0} products", consoleBeans.size());
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                //Aggiungo il bean alla request
                req.getSession().setAttribute("prodotto", consoleBeans);
                req.getSession().setAttribute("edizioni-piattaforme", consoleBeans);

                //Aggiungo come header nella response il tipo di prodotto
                resp.addHeader("PRODUCT-TYPE", "console");

                //Reindirizzo alla pagina prodotto
                try {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("console.jsp");
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }
            }
            case "gadget" -> {
                //Recupero le informazioni del gadget dal database e le salvo nel bean
                GadgetDAO gadgetDAO = new GadgetDAO();
                GadgetBean gadgetBean = new GadgetBean();
                try {
                    gadgetBean = gadgetDAO.doRetrieveByKey(productId);
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }

                //Aggiungo il bean alla request
                req.getSession().setAttribute("prodotto", gadgetBean);

                //Aggiungo come header nella response il tipo di prodotto
                resp.addHeader("PRODUCT-TYPE", "gadget");

                //Reindirizzo alla pagina prodotto
                try {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("gadget.jsp");
                    requestDispatcher.forward(req,resp);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString());
                }
            }
            default -> LOGGER.log(Level.SEVERE, "baseg");

            //// TODO: 07/10/2023 Redirect a pagina di errore?
        }


    }
}
