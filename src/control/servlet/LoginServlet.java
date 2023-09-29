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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

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
        //Recupero username e password dalla request
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        List<String> errors = new ArrayList<>();
        LOGGER.log(Level.INFO, "\nUsername: {0}\nPassword: {1}\n", new String[]{username, password});
        RequestDispatcher dispatcherToLoginPage = req.getRequestDispatcher("login.jsp");

        //Controllo che username e password non siano vuoti
        if(username == null || username.trim().isEmpty()) {
            errors.add("Il campo username non può essere vuoto");
        }
        if(password == null || password.trim().isEmpty()) {
            errors.add("Il campo password non può essere vuoto");
        }

        //Se ci sono errori, spedisco gli errori alla pagina di login
        if(!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            try {
                dispatcherToLoginPage.forward(req,resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return;
        }

        //Recupero l'utente dal database
        UtenteDAO utenteDAO = new UtenteDAO();
        UtenteBean user = new UtenteBean();
        try {
            user = utenteDAO.doRetrieveByUsernameAndPassword(username, password);
            LOGGER.log(Level.INFO,"\nUsername retrieved from database: {0}\nUsername retrieved from database: {1}\n", new String[]{user.getUsername(), user.getPassword()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        //Se l'utente è null, non è stato trovato nulla
        if (user.getUsername() == null) {
            //Rispedisco alla pagina di login
            errors.add("Username e/o password non validi");
            req.setAttribute("errors", errors);
            try {
                dispatcherToLoginPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

        } else {
            req.getSession().setAttribute("isLogged", Boolean.TRUE);
            req.getSession().setAttribute("userid", user.getCodice());
            req.getSession().setAttribute("username", user.getUsername());

            if(user.isAdmin()) {
                req.getSession().setAttribute("isAdmin", Boolean.TRUE);
            }
            else {
                req.getSession().setAttribute("isAdmin", Boolean.FALSE);
            }
            LOGGER.info("User successfully logged in.");
            try {
                resp.sendRedirect("index.jsp");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

        }
    }
}
