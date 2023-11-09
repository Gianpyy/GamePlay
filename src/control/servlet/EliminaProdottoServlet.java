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
import java.util.logging.Logger;

@WebServlet("/DeleteProduct")
public class EliminaProdottoServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(EliminaProdottoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
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

            //Elimino il prodotto associato all'id
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doDelete(productId);
        } catch (Exception e) {
            resp.addHeader("OPERATION-RESULT", "error");
            requestDispatcher.forward(req, resp);
            return;
        }

        //Ritorno alla pagina di gestione catalogo
        resp.addHeader("OPERATION-RESULT", "success");
        requestDispatcher.forward(req, resp);
    }
}

