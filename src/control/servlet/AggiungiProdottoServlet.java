package control.servlet;

import control.dao.ConsoleDAO;
import control.dao.GadgetDAO;
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

@WebServlet("/AddProduct")
public class AggiungiProdottoServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AggiungiProdottoServlet.class.getName());

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

        //Recupero il tipo di prodotto da inserire
        String tipo = "";
        try {
            tipo = json.getString("tipo");
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            resp.addHeader("OPERATION-RESULT", "error");
            requestDispatcher.forward(req,resp);
            return;
        }

        switch (tipo) {
            case "videogioco" -> {
                try {
                    //Creo il bean da inserire nel database
                    VideogiocoBean videogiocoBean = new VideogiocoBean();
                    videogiocoBean.setTipo("videogioco");
                    videogiocoBean.setBarcode(Utilities.generateBarcode());
                    videogiocoBean.setNome(json.getString("nomeProdotto"));
                    videogiocoBean.setPrezzo(json.getFloat("prezzo"));
                    videogiocoBean.setPiattaforma(json.getString("piattaforma"));
                    videogiocoBean.setEdizione(json.getString("edizione"));
                    videogiocoBean.setDescrizione(json.getString("descrizione"));
                    videogiocoBean.setCategoria(json.getString("categoria"));
                    videogiocoBean.setDataRilascio(Utilities.stringToDate(json.getString("dataRilascio")));
                    videogiocoBean.setCondizioni(json.getString("condizioni"));
                    String numeroGiocatori = json.getString("minimo") + "-" +json.getString("massimo");
                    videogiocoBean.setNumeroGiocatori(numeroGiocatori);
                    videogiocoBean.setEtaPegi(json.getInt("etaPEGI"));

                    //Inserisco il bean nel database
                    VideogiocoDAO videogiocoDAO = new VideogiocoDAO();
                    videogiocoDAO.doSave(videogiocoBean);

                } catch (Exception e) {
                    LOGGER.severe(e.toString());
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req,resp);
                }
            }

            case "gadget" -> {
                try {
                    //Creo il bean da inserire nel database
                    GadgetBean gadgetBean = new GadgetBean();
                    gadgetBean.setTipo("gadget");
                    gadgetBean.setBarcode(Utilities.generateBarcode());
                    gadgetBean.setNome(json.getString("nomeProdotto"));
                    gadgetBean.setPrezzo(json.getFloat("prezzo"));
                    gadgetBean.setProduttore(json.getString("produttore"));
                    gadgetBean.setSerie(json.getString("serie"));

                    //Inserisco il bean nel database
                    GadgetDAO gadgetDAO = new GadgetDAO();
                    gadgetDAO.doSave(gadgetBean);

                } catch (Exception e) {
                    LOGGER.severe(e.toString());
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req,resp);
                }
            }

            case "console" -> {
                try {
                    //Creo il bean da inserire nel database
                    ConsoleBean consoleBean = new ConsoleBean();
                    consoleBean.setTipo("console");
                    consoleBean.setBarcode(Utilities.generateBarcode());
                    consoleBean.setNome(json.getString("nomeProdotto"));
                    consoleBean.setPrezzo(json.getFloat("prezzo"));
                    consoleBean.setEdizione(json.getString("edizione"));
                    consoleBean.setFamiglia(json.getString("famiglia"));
                    consoleBean.setAnnoRilascio(json.getInt("annoRilascio"));

                    //Inserisco il bean nel database
                    ConsoleDAO consoleDAO = new ConsoleDAO();
                    consoleDAO.doSave(consoleBean);

                } catch (Exception e) {
                    LOGGER.severe(e.toString());
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req,resp);
                }
            }

            default -> {
                resp.addHeader("OPERATION-RESULT", "error");
                requestDispatcher.forward(req,resp);
                return;
            }
        }

        //Ritorno alla pagina di gestione catalogo
        resp.addHeader("OPERATION-RESULT", "success");
        requestDispatcher.forward(req,resp);
    }
}
