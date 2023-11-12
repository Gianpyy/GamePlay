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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupero il requestBody dalla request
        StringBuilder requestBody = Utilities.getRequestBody(req);

        //Inizializzo l'oggetto JSON
        JSONObject json = new JSONObject(requestBody.toString());

        //Recupero il valore da cercare
        String productToSearch = json.getString("searchbar");

        //Recupero i prodotti dal database
        List<ProdottoBean> prodotti = new ArrayList<>();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        try {
            prodotti = (List<ProdottoBean>) prodottoDAO.doSearch(productToSearch);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server");
            LOGGER.log(Level.SEVERE, "Errore interno del server");
        }

        //Salvo i prodotti nella sessione
        req.getSession().setAttribute("searchresult", prodotti);

        //Vado alla pagina di ricerca
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("search_result.jsp");

        try {
            requestDispatcher.forward(req,resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server");
            LOGGER.log(Level.SEVERE, "Errore interno del server");
        }
    }
}
