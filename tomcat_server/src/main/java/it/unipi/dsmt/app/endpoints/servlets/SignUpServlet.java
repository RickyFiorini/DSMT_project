package it.unipi.dsmt.app.endpoints.servlets;

import java.io.IOException;
import java.sql.Connection;

import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.endpoints.handlers.AuthenticationHandler;
import it.unipi.dsmt.app.entities.User;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles signup requests
@WebServlet(name = "SignUp", value = "/signup")
public class SignUpServlet extends HttpServlet {

    // To handle "get" signup request and forward it to the signup page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            String targetJSP = "/WEB-INF/jsp/signup.jsp";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetJSP);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // To handle "post" request and forward it to signup or home pages
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDAO userDAO = new UserDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            User userInfo = AuthenticationHandler.unpackPostSignup(request);
            String resultMessage = userDAO.save(userInfo);

            // if user signup goes wrong, return to signup page
            if (resultMessage != "") {
                ErrorHandler.setPopupErrorMessage(request, resultMessage);
                response.sendRedirect(request.getContextPath() + "/signup");
                return;
            }

            // else, forward the request to the user home page
            AccessController.setToken(request, userInfo.getUsername());
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
