package control.servlet;

import control.dao.PhotoDAO;
import model.PhotoBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

@WebServlet("/UploadImage")
@MultipartConfig
public class UploadImageServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UploadImageServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Recupero il prodotto associato dalle informazioni nella richiesta
        String prodottoAssociato = req.getParameter("productId");

        // Recupero le immagini dalla richiesta
        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            if ("img".equals(part.getName())) {
                try {
                    // Creo un bean per ogni immagine
                    PhotoBean img = new PhotoBean();
                    img.setProdottoAssociato(prodottoAssociato);
                    img.setImg(part.getInputStream());
                    img.setNomeImmagine(part.getSubmittedFileName());

                    // Inserisco il bean nel database
                    PhotoDAO photoDAO = new PhotoDAO();
                    photoDAO.uploadPhoto(img);

                } catch (Exception e) {
                    LOGGER.severe(e.toString());
                    resp.addHeader("OPERATION-RESULT", "error");
                    return;
                }
            }
        }

        resp.addHeader("OPERATION-RESULT", "success");
    }
}

