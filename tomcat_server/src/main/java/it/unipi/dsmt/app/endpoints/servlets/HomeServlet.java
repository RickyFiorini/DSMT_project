package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unipi.dsmt.app.daos.ListingDAO;
import it.unipi.dsmt.app.dtos.ListingDTO;
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

    // To handle "get" request and forward it to the home jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get the current user
            final String currentUsername = AccessController.getUsername(request);

            // TODO PRENDERE TUTTE LE LISTING TRANNE QUELLE DELL'ATTUALE UTENTE
            // Retrieve the list of listings
            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            List<ListingDTO> listingList = listingDAO.getListings();

            // Filter out the current user listings
            listingList = listingList.stream().filter(new Predicate<ListingDTO>() {
                @Override
                public boolean test(ListingDTO listing) {
                    return !listing.getUsername().equals(currentUsername);
                }
            }).collect(Collectors.toList());

            request.setAttribute("listingList", listingList);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

}
