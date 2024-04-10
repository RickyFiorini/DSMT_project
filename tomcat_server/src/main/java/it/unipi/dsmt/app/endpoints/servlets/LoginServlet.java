package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;

import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.dtos.UserLoginDTO;
import it.unipi.dsmt.app.endpoints.handlers.AuthenticationHandler;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles login requests
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    // To handle "get" login request and forward it to the login or home pages
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = AccessController.getToken(request);
            // if the session is expired, forward the request to the login page
            if (token == null) {
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
                return;
            }
            // else, forward the request to the home page
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // To handle "post" request and forward it to login or home pages
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDAO userDAO = new UserDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            UserLoginDTO userInfo = AuthenticationHandler.unpackPostLogin(request);

            // if the authentication fails, return to the login page
            if (!userDAO.exists(userInfo.getUsername())) {
                ErrorHandler.setPopupErrorMessage(request, "Username doesn't exist");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            if (!userDAO.valid(userInfo.getUsername(), userInfo.getPassword())) {
                ErrorHandler.setPopupErrorMessage(request, "Incorrect Password");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // else, forward the request to the user home page
            AccessController.setToken(request, userInfo.getUsername());
            System.out.print("loginUSERNAME:"+ userInfo.getUsername());
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
