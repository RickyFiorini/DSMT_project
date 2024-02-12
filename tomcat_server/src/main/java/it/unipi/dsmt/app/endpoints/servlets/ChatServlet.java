package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import it.unipi.dsmt.app.daos.ChatDAO;
import it.unipi.dsmt.app.daos.MessageDAO;
import it.unipi.dsmt.app.daos.NotificationDAO;
import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.dtos.MessageDTO;
import it.unipi.dsmt.app.entities.Chat;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles the request of the chat page
@WebServlet(name = "ChatServlet", value = "/chat")
public class ChatServlet extends HttpServlet {

    // To handle "get" request and forward it to the chat jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            ChatDAO chatDAO = new ChatDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            MessageDAO messageDAO = new MessageDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            UserDAO userDAO = new UserDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            NotificationDAO notificationDAO = new NotificationDAO(
                    (Connection) getServletContext().getAttribute("databaseConnection"));

            String currentUsername = AccessController.getUsername(request);

            int chatID = Integer.parseInt(request.getParameter("chatID"));
            chatDAO.validateChatIDWithUsername(chatID, currentUsername);
            // TODO NON DEVO ELIMINARE LA NOTIFICATION DA QUI, DEVO SOLO MOSTRARLA
            notificationDAO.deleteNotificationFromChatID(chatID);
            String username = chatDAO.getDestinationOfChatID(chatID, currentUsername);
            request.setAttribute("username", username);

            boolean isOnline = userDAO.getOnlineStateOfUsername(username);
            request.setAttribute("isOnline", isOnline);

            List<MessageDTO> messageList = messageDAO.getMessagesFromChatId(chatID);
            request.setAttribute("messageList", messageList);

            request.getRequestDispatcher("/WEB-INF/jsp/chat.jsp").forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // To handle "post" request and forward it to the chat jsp
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            ChatDAO chatDAO = new ChatDAO((Connection) getServletContext().getAttribute("databaseConnection"));

            String currentUsername = AccessController.getUsername(request);
            String username = (String) request.getParameter("username");
            // TODO AGGIUNGERE IL LISTING ID DELLA CHAT
            int listing = 0;

            // check if there is already a chat between these two users
            int retrievedChatID = chatDAO.getChatIDFromUser1User2(username, currentUsername);
            // if it doesn't exist, create and insert it in the db
            if (retrievedChatID == -1) {
                // TODO MANCA IL LISTING ID
                Chat chat = new Chat(currentUsername, username, listing, new Date(System.currentTimeMillis()));
                retrievedChatID = chatDAO.save(chat);
                response.sendRedirect(request.getContextPath() + "/chat?chatID=" + retrievedChatID);
                return;
            }
            // else, perform a "get" request for the specified chat
            else {
                response.sendRedirect(request.getContextPath() + "/chat?chatID=" + retrievedChatID);
                return;
            }
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // To handle the "delete" request
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            ChatDAO chatDAO = new ChatDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            int chatID = Integer.parseInt(request.getParameter("chatID"));
            String currentUsername = AccessController.getUsername(request);
            chatDAO.validateChatIDWithUsername(chatID, currentUsername);
            chatDAO.deleteChatFromChatID(chatID);
            response.setStatus(200);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
