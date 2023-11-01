package control.servlet;

import control.dao.AnagraficaUtenteDAO;
import control.dao.UtenteDAO;
import model.AnagraficaUtenteBean;
import model.UtenteBean;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doPost(req, resp);
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
        String nome = "";
        String cognome = "";
        String codiceFiscale = "";
        String sesso = "";
        String username = "";
        String password = "";
        String formDate = "";
        try {
            nome = json.getString("nome");
            cognome = json.getString("cognome");
            codiceFiscale = json.getString("codiceFiscale");
            sesso = json.getString("sesso");
            username = json.getString("username");
            password = json.getString("password");
            formDate = json.getString("dataNascita");
        } catch (Exception e) {
            LOGGER.severe(e.toString());
        }
        Date dataDiNascita = null;
        try {
            dataDiNascita = Utilities.stringToDate(formDate);
            LOGGER.log(Level.INFO, "Date: {0}", dataDiNascita);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }


        //Se ci sono errori, rispedisco alla pagina di registrazione
        RequestDispatcher dispatcherToRegisterPage = req.getRequestDispatcher("register.jsp");
        if (!isInputValid(req, resp, nome, cognome, codiceFiscale, sesso, username, password, dataDiNascita, dispatcherToRegisterPage)) {
            LOGGER.severe("Invalid input");
            return;
        }


        //Inserisco i dati nelle rispettive tabelle
        UtenteDAO utenteDAO = new UtenteDAO();
        int newUser;
        AnagraficaUtenteDAO anagraficaUtenteDAO = new AnagraficaUtenteDAO();
        try {
            newUser = utenteDAO.doSaveAndReturnKey(new UtenteBean(username, password, false));
            LOGGER.log(Level.INFO, "New user inserted with id: {0}", newUser);
            anagraficaUtenteDAO.doSave(new AnagraficaUtenteBean(codiceFiscale, nome, cognome, dataDiNascita, sesso, newUser));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
            resp.addHeader("OPERATION-RESULT", "error");
                    try {
                        dispatcherToRegisterPage.forward(req, resp);
                    } catch (Exception f) {
                        LOGGER.log(Level.SEVERE, f.toString());
                    }

                    return;
        }

        req.getSession().setAttribute("isLogged", Boolean.TRUE);
        req.getSession().setAttribute("userid", newUser);
        req.getSession().setAttribute("username", username);
        req.getSession().setAttribute("isAdmin", Boolean.FALSE);
        resp.addHeader("OPERATION-RESULT", "success");
        LOGGER.info("User successfully logged in.");


        try {
            dispatcherToRegisterPage.forward(req, resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    private static boolean isInputValid(HttpServletRequest req, HttpServletResponse resp, String nome, String cognome, String codiceFiscale, String sesso, String username, String password, Date dataDiNascita, RequestDispatcher dispatcherToRegisterPage) {
        if (nome == null || nome.trim().isEmpty()) {
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                dispatcherToRegisterPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return false;
        }
        if (cognome == null || cognome.trim().isEmpty()) {
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                dispatcherToRegisterPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return false;
        }
        if (codiceFiscale == null || codiceFiscale.trim().isEmpty()) {
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                dispatcherToRegisterPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return false;
        }
        if (sesso == null || sesso.trim().isEmpty()) {
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                dispatcherToRegisterPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return false;
        }
        if (dataDiNascita == null) {
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                dispatcherToRegisterPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return false;
        }
        if (username == null || username.trim().isEmpty()) {
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                dispatcherToRegisterPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            resp.addHeader("OPERATION-RESULT", "error");
            try {
                dispatcherToRegisterPage.forward(req, resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return false;
        }
        return true;
    }
}
