package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.util.List;

import it.unipi.dsmt.app.daos.NotificationDAO;
import it.unipi.dsmt.app.dtos.NotificationDTO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles the request of the notifications page
@WebServlet(name = "NotificationServlet", value = "/notification")
public class NotificationServlet extends HttpServlet {

    // To handle "get" request and forward it to the notification jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            NotificationDAO notificationDAO = new NotificationDAO(
                    (Connection) getServletContext().getAttribute("databaseConnection"));

            String currentUsername = AccessController.getUsername(request);

            // Retrieve the list of notifications of the user
            List<NotificationDTO> notificationList = notificationDAO.getNotificationFromUser(currentUsername);
            request.setAttribute("notificationList", notificationList);
            request.getRequestDispatcher("/WEB-INF/jsp/notification.jsp").forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
