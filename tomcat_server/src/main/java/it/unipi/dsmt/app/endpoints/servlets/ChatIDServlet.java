package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;

import it.unipi.dsmt.app.daos.ChatDAO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Servlet that handles the request of a specific chat page
@WebServlet(name="ChatID", value = "/chatID")
public class ChatIDServlet extends HttpServlet{

    // To handle "get" request of a specified chat
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try{
            ChatDAO chatDAO = new ChatDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            String currentUsername = AccessController.getUsername(req);
            String sender = (String) req.getParameter("sender");
            // Retrieve the specified chat between the two users
            int chatID = chatDAO.getChatIDFromUser1User2( currentUsername, sender );
            resp.setStatus(200);
            // Response in JSON format
            resp.getWriter().write(String.format("{\"chatID\": %d}", chatID));
        } catch(Exception e) {
            ErrorHandler.safeDispatchToErrorPage(req, resp, e);
        }
    }
}
