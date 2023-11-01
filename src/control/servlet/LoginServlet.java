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
        //Recupero il requestBody dalla request
        StringBuilder requestBody = Utilities.getRequestBody(req);

        //Inizializzo l'oggetto JSON
        JSONObject json = new JSONObject(requestBody.toString());

        //Recupero username e password dalla request
        String username = "";
        String password = "";
        try {
            username = json.getString("username");
            password = json.getString("password");
        } catch (Exception e) {
            LOGGER.severe(e.toString());
        }
        RequestDispatcher dispatcherToLoginPage = req.getRequestDispatcher("login.jsp");

        //Controllo che username e password non siano vuoti
        if(username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()){
            //Se sono vuoti, rispedisco alla pagina di login visualizzando l'errore
            resp.addHeader("OPERATION-RESULT", "error");
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
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        //Se l'utente è null, non è stato trovato nulla
        if (user.getUsername() == null) {
            //Rispedisco alla pagina di login
//            req.setAttribute("error", "L'username e/o la password inseriti non sono corretti.");
            resp.addHeader("OPERATION-RESULT", "noUserFound");
            try {
                dispatcherToLoginPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

        } else {
            resp.addHeader("OPERATION-RESULT", "success");
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
                dispatcherToLoginPage.forward(req,resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

        }
    }
}