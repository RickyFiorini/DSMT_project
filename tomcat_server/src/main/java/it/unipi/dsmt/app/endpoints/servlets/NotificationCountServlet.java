package it.unipi.dsmt.app.endpoints.servlets;

import java.io.IOException;
import java.sql.Connection;

import it.unipi.dsmt.app.daos.NotificationDAO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles the request of a specific notification page
@WebServlet(name = "NotificationCount", value = "/notificationcount")
public class NotificationCountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotificationDAO notificationDAO = new NotificationDAO(
                    (Connection) getServletContext().getAttribute("databaseConnection"));
            String requestingUser = AccessController.getUsername(req);
            // Retrieve the number of notification of the specified user
            int number = notificationDAO.getNotificationCountForUser(requestingUser);
            resp.setStatus(200);
            // Response in JSON format
            resp.getWriter().write(String.format("{\"count\": %d}", number));
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(req, resp, e);
        }
    }
}
