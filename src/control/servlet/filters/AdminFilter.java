package control.servlet.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "AdminFilter",
        urlPatterns = {"/DeleteOrder", "/UpdateOrderStatus", "/AddProduct", "/DeleteProduct", "/RedirectToEditPage", "/UpdateProduct"}
)
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Controllo se l'utente è amministratore
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("isAdmin");

        if (Boolean.TRUE.equals(isAdmin)) {
            // Se è amministratore, permetti alla richiesta di passare alla servlet
            chain.doFilter(request, response);
        } else {
            // Altrimenti, utente non autorizzato
            resp.addHeader("OPERATION-RESULT", "unauthorized");
            resp.sendRedirect("/static/error_pages/unauthorized.jsp");
        }
    }
}
