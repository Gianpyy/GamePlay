package control.servlet;

import control.dao.UtenteDAO;
import model.UtenteBean;
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

@WebServlet("/EditPassword")
public class ModificaPasswordServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModificaPasswordServlet.class.getName());

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

        //Recupero i parametri dalla request
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("edit_password.jsp");
        String oldPassword = "";
        String newPassword = "";
        String newPasswordRepeat = "";
        try {
            oldPassword = json.getString("oldPassword");
            newPassword = json.getString("newPassword");
            newPasswordRepeat = json.getString("newPasswordRepeat");
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                requestDispatcher.forward(req,resp);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString());
            }

            return;
        }

        //Controllo che la nuova password e la password ripetuta coincidano
        if (!newPassword.equals(newPasswordRepeat)) {
            //Se non coincide, aggiungo l'errore
            resp.addHeader("OPERATION-RESULT", "newPasswordNotSame");
            try {
                requestDispatcher.forward(req,resp);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString());
            }

            return;
        }

        //Recupero i dati associati all'utente
        UtenteBean utente = new UtenteBean();
        UtenteDAO utenteDAO = new UtenteDAO();
        try {
            utente = utenteDAO.doRetrieveByKey((int) req.getSession().getAttribute("userid"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        //Controllo che la password del form coincida con la password nel database
        if (!utente.getPassword().equals(oldPassword)) {
            //Se non coincide, aggiungo l'errore
            resp.addHeader("OPERATION-RESULT", "oldPasswordError");
            try {
                requestDispatcher.forward(req,resp);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString());
            }

            return;
        }

        //Se non ci sono errori, vado a modificare la password nel database
        try {
            utenteDAO.doUpdatePassword(utente.getCodice(), newPassword);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        //Reindirizzo alla pagina utente
        try {
            resp.addHeader("OPERATION-RESULT", "success");
            requestDispatcher = req.getRequestDispatcher("index.jsp");
            try {
                requestDispatcher.forward(req,resp);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }
}
