package control.servlet;

import control.dao.AnagraficaUtenteDAO;
import control.dao.UtenteDAO;
import model.AnagraficaUtenteBean;
import model.UtenteBean;

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
    private final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

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
        //Recupero i parametri dalla request
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String codiceFiscale = req.getParameter("codiceFiscale");
        String sesso = req.getParameter("sesso");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String formDate = req.getParameter("dataNascita");
        Date dataDiNascita = null;
        try {
            dataDiNascita = formatter.parse(formDate);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        List<String> errors = new ArrayList<>();


        //Controllo che i campi non siano vuoti
        if (nome == null || nome.trim().isEmpty()) {
            errors.add("Inserisci un nome");
        }
        if (cognome == null || cognome.trim().isEmpty()) {
            errors.add("Inserisci un cognome");
        }
        if (codiceFiscale == null || codiceFiscale.trim().isEmpty()) {
            errors.add("Inserisci un codice fiscale");
        }
        if (sesso == null || sesso.trim().isEmpty()) {
            errors.add("Inserisci un sesso");
        }
        if (dataDiNascita == null) {
            errors.add("Inserisci una data di nascita");
        }
        if (username == null || username.trim().isEmpty()) {
            errors.add("Inserisci un username");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.add("Inserisci una password");
        }

        //Se ci sono errori, rispedisco alla pagina di registrazione
        RequestDispatcher dispatcherToRegisterPage = req.getRequestDispatcher("register.jsp");
        if(!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            try {
                dispatcherToRegisterPage.forward(req,resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }

            return;
        }

        //Inserisco i dati nelle rispettive tabelle
        UtenteDAO utenteDAO = new UtenteDAO();
        try {
            utenteDAO.doSave(new UtenteBean(username, password, false));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        UtenteBean newUser = new UtenteBean();
        AnagraficaUtenteDAO anagraficaUtenteDAO = new AnagraficaUtenteDAO();
        try {
            newUser = utenteDAO.doRetrieveByUsernameAndPassword(username, password);
            anagraficaUtenteDAO.doSave(new AnagraficaUtenteBean(codiceFiscale, nome, cognome, dataDiNascita, sesso, newUser.getCodice()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        req.getSession().setAttribute("isLogged", Boolean.TRUE);
        req.getSession().setAttribute("userid", newUser.getCodice());
        req.getSession().setAttribute("username", newUser.getUsername());
        req.getSession().setAttribute("isAdmin", Boolean.FALSE);
        LOGGER.info("User successfully logged in.");


        try {
            resp.sendRedirect("index.jsp");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }
}
