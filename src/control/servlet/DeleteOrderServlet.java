package control.servlet;

import control.dao.OrdineDAO;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/DeleteOrder")
public class DeleteOrderServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DeleteOrderServlet.class.getName());

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

        try {
            //Recupero l'id dell'ordine da cancellare
            Integer orderId = json.getInt("orderId");

            //Aggiorno lo stato dell'ordine nel database
            OrdineDAO ordineDAO = new OrdineDAO();
            boolean operationResult = ordineDAO.doDelete(orderId);

            LOGGER.log(Level.INFO, "Operation result: {0}", operationResult);
            if (operationResult) {
                //Se l'operazione è andata a buon fine, reindirizzo alla pagina con operation result di successo
                resp.addHeader("OPERATION-RESULT", "success");
                requestDispatcher.forward(req,resp);
            }
            else {
                //Altrimenti, informo che c'è stato un errore
                resp.addHeader("OPERATION-RESULT", "error");
                requestDispatcher.forward(req,resp);
            }
        } catch (Exception e) {
            //Ritorno alla pagina della gestione catalogo ed avviso che c'è stato un errore
            LOGGER.severe(e.toString());
            resp.addHeader("OPERATION-RESULT", "error");
            requestDispatcher.forward(req,resp);
        }

        //Ritorno alla pagina di gestione catalogo
        requestDispatcher.forward(req, resp);
    }
}
