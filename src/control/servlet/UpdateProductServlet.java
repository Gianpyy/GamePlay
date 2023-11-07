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

@WebServlet("/UpdateProduct")
public class UpdateProductServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateProductServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Controllo se l'utente è amministratore
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("isAdmin");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("gestione_catalogo.jsp");

        if (Boolean.TRUE.equals(isAdmin)) {
            //Recupero il requestBody dalla request
            StringBuilder requestBody = Utilities.getRequestBody(req);

            //Inizializzo l'oggetto JSON
            JSONObject json = new JSONObject(requestBody.toString());

            //Recupero il tipo di prodotto
            String productType = "";
            try {
                productType = json.getString("tipo");
            } catch (Exception e) {
                LOGGER.severe(e.toString());
            }

            switch (productType) {
                case "videogioco" -> {
                    try {
                        //Recupero l'id dalla sessione
                        VideogiocoBean videogiocoBean = (VideogiocoBean) req.getSession().getAttribute("product");
                        String productId = videogiocoBean.getBarcode();

                        //Creo il bean
                        VideogiocoBean newVideogame = new VideogiocoBean();
                        newVideogame.setTipo("videogioco");
                        newVideogame.setBarcode(productId);
                        newVideogame.setNome(json.getString("nomeProdotto"));
                        newVideogame.setPrezzo(json.getFloat("prezzo"));
                        newVideogame.setPiattaforma(json.getString("piattaforma"));
                        newVideogame.setEdizione(json.getString("edizione"));
                        newVideogame.setDescrizione(json.getString("descrizione"));
                        newVideogame.setCategoria(json.getString("categoria"));
                        newVideogame.setDataRilascio(Utilities.stringToDate(json.getString("dataRilascio")));
                        newVideogame.setCondizioni(json.getString("condizioni"));
                        String numeroGiocatori = json.getString("minimo") + "-" + json.getString("massimo");
                        newVideogame.setNumeroGiocatori(numeroGiocatori);
                        newVideogame.setEtaPegi(json.getInt("etaPEGI"));

                        //Aggiorno il bean nel database
                        VideogiocoDAO videogiocoDAO = new VideogiocoDAO();
                        videogiocoDAO.doUpdate(newVideogame);

                    } catch (Exception e) {
                        LOGGER.severe(e.toString());
                        resp.addHeader("OPERATION-RESULT", "error");
                        requestDispatcher.forward(req, resp);
                    }
                }

                case "console" -> {
                    try {
                        //Recupero l'id dalla sessione
                        ConsoleBean consoleBean = (ConsoleBean) req.getSession().getAttribute("product");
                        String productId = consoleBean.getBarcode();

                        //Creo il bean
                        ConsoleBean newConsole = new ConsoleBean();
                        newConsole.setTipo("console");
                        newConsole.setBarcode(productId);
                        newConsole.setNome(json.getString("nomeProdotto"));
                        newConsole.setPrezzo(json.getFloat("prezzo"));
                        newConsole.setEdizione(json.getString("edizione"));
                        newConsole.setFamiglia(json.getString("famiglia"));
                        newConsole.setAnnoRilascio(json.getInt("annoRilascio"));

                        //Aggiorno il bean nel database
                        ConsoleDAO consoleDAO = new ConsoleDAO();
                        consoleDAO.doUpdate(newConsole);
                    } catch (Exception e) {
                        LOGGER.severe(e.toString());
                        resp.addHeader("OPERATION-RESULT", "error");
                        requestDispatcher.forward(req, resp);
                    }

                }

                case "gadget" -> {
                    try {
                        //Recupero l'id dalla sessione
                        GadgetBean gadgetBean = (GadgetBean) req.getSession().getAttribute("product");
                        String productId = gadgetBean.getBarcode();

                        //Creo il bean
                        GadgetBean newGadget = new GadgetBean();
                        newGadget.setTipo("gadget");
                        newGadget.setBarcode(productId);
                        newGadget.setNome(json.getString("nomeProdotto"));
                        newGadget.setPrezzo(json.getFloat("prezzo"));
                        newGadget.setProduttore(json.getString("produttore"));
                        newGadget.setSerie(json.getString("serie"));

                        //Aggiorno il bean nel database
                        GadgetDAO gadgetDAO = new GadgetDAO();
                        gadgetDAO.doUpdate(newGadget);
                    } catch (Exception e) {
                        LOGGER.severe(e.toString());
                        resp.addHeader("OPERATION-RESULT", "error");
                        requestDispatcher.forward(req, resp);
                    }
                }

                default -> {
                    resp.addHeader("OPERATION-RESULT", "error");
                    requestDispatcher.forward(req, resp);
                    return;
                }
            }

            //Ritorno alla pagina di gestione catalogo
            try {
                resp.addHeader("OPERATION-RESULT", "success");
                requestDispatcher.forward(req, resp);
            } catch (Exception e) {
                LOGGER.severe(e.toString());
            }
        }
        else{
                resp.addHeader("OPERATION-RESULT", "unauthorized");
                requestDispatcher.forward(req, resp);
            }
        }
}
