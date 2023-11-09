package control.servlet;

import control.dao.ConsoleDAO;
import control.dao.GadgetDAO;
import control.dao.ProdottoDAO;
import control.dao.VideogiocoDAO;
import model.ConsoleBean;
import model.GadgetBean;
import model.VideogiocoBean;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RedirectToEditPage")
public class RedirectToEditPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("gestione_catalogo.jsp");

        //Recupero il requestBody dalla request
        StringBuilder requestBody = Utilities.getRequestBody(req);

        //Inizializzo l'oggetto JSON
        JSONObject json = new JSONObject(requestBody.toString());

        //Recupero l'id del prodotto da eliminare
        try {
            String productId = json.getString("productId");

            //Recupero il prodotto dal database
            String productType = json.getString("productType");
            switch (productType) {
                case "videogioco" -> {
                    VideogiocoDAO videogiocoDAO = new VideogiocoDAO();
                    VideogiocoBean videogiocoBean = videogiocoDAO.doRetrieveByKey(productId);

                    //Aggiungo il prodotto alla sessione
                    req.getSession().setAttribute("product", videogiocoBean);

                    //Aggiungo il tipo di prodotto all'header della risposta
                    resp.addHeader("OPERATION-RESULT", productType);
                }

                case "console" -> {
                    ConsoleDAO consoleDAO = new ConsoleDAO();
                    ConsoleBean consoleBean = consoleDAO.doRetrieveByKey(productId);

                    //Aggiungo il prodotto alla sessione
                    req.getSession().setAttribute("product", consoleBean);

                    //Aggiungo il tipo di prodotto all'header della risposta
                    resp.addHeader("OPERATION-RESULT", productType);
                }

                case "gadget" -> {
                    GadgetDAO gadgetDAO = new GadgetDAO();
                    GadgetBean gadgetBean = gadgetDAO.doRetrieveByKey(productId);

                    //Aggiungo il prodotto alla sessione
                    req.getSession().setAttribute("product", gadgetBean);

                    //Aggiungo il tipo di prodotto all'header della risposta
                    resp.addHeader("OPERATION-RESULT", productType);

                }

                default -> {
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req, resp);
                    return;
                }
            }
        } catch (Exception e) {
            resp.addHeader("OPERATION-RESULT", "error");
            requestDispatcher.forward(req, resp);
            return;
        }

        //Ritorno alla pagina di gestione catalogo
        requestDispatcher.forward(req, resp);
    }
}
