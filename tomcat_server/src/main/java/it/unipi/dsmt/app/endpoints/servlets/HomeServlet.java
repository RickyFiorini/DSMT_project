package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.dtos.UserProfileDTO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles the request of the home page
@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {

    // To handle "get" request forward it to the home jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get the current user
            final String currentUsername = AccessController.getUsername(request);
            UserDAO userDAO = new UserDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            // Retrieve the list of users from the db
            List<UserProfileDTO> usersList = userDAO.getUsers();

            // Filter out the current user
            usersList = usersList.stream().filter(new Predicate<UserProfileDTO>() {
                @Override
                public boolean test(UserProfileDTO user) {
                    return !user.getUsername().equals(currentUsername);
                }
            }).collect(Collectors.toList());

            // Filter out the offline users
            List<UserProfileDTO> onlineList = usersList.stream().filter(new Predicate<UserProfileDTO>() {
                @Override
                public boolean test(UserProfileDTO user) {
                    return user.isOnline_flag();
                }
            }).collect(Collectors.toList());

            request.setAttribute("usersList", usersList);
            request.setAttribute("onlineUsers", onlineList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

}
