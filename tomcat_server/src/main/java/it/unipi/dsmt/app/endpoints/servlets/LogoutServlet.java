package it.unipi.dsmt.app.endpoints.servlets;

import it.unipi.dsmt.app.utils.ErrorHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles logout requests
@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    // To handle "get" logout request and redirect it to the login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
