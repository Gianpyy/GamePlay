package control.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());
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
        Boolean isLogged = (Boolean) req.getSession().getAttribute("isLogged");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");

        if(isLogged == null) {
            try {
                requestDispatcher.forward(req,resp);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
            return;
        }

        req.getSession().invalidate();
        LOGGER.info("Session invalidated");
        try {
            requestDispatcher.forward(req,resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }
}
