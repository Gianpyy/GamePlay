package control.servlet;

import control.dao.UtenteDAO;
import model.UtenteBean;

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
        //Recupero i parametri dalla request
        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("newpassword");
        String newPasswordRepeat = req.getParameter("newpasswordrepeat");
        List<String> errors = new ArrayList<>();

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
            errors.add("La vecchia password è errata.");
        }

        //Controllo che la nuova password e la password ripetuta coincidano
        if (!newPassword.equals(newPasswordRepeat)) {
            //Se non coincide, aggiungo l'errore
            errors.add("Nuova password e nuova password ripetuta non coincidono.");
        }

        //Se ci sono errori, rispedisco al form
        if(!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("edit_password.jsp");
            try {
                requestDispatcher.forward(req,resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
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
            resp.sendRedirect("profile.jsp");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }
}
