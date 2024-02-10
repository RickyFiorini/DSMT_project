package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.util.List;

import it.unipi.dsmt.app.daos.ChatDAO;
import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.dtos.UserProfileDTO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;
import it.unipi.dsmt.app.dtos.ChatDTO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles the request of the profile page
@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    // To handle "get" request and forward it to the user profile jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDAO userDAO = new UserDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            String username = AccessController.getUsername(request);
            UserProfileDTO userInfo = userDAO.getUserFromUsername(username);
            request.setAttribute("user_info", userInfo);
            ChatDAO chatDAO = new ChatDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            List<ChatDTO> chatList = chatDAO.getChatsFromUsername(username);
            request.setAttribute("chatList", chatList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
