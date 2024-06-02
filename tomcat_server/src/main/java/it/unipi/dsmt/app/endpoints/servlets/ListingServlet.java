package it.unipi.dsmt.app.endpoints.servlets;

import it.unipi.dsmt.app.daos.BoxDAO;
import it.unipi.dsmt.app.daos.ListingDAO;
import it.unipi.dsmt.app.daos.OfferDAO;
import it.unipi.dsmt.app.dtos.BoxDTO;
import it.unipi.dsmt.app.dtos.ListingDTO;
import it.unipi.dsmt.app.dtos.OfferDTO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

// Servlet that handles the request of the listing page
@WebServlet(name = "ListingServlet", value = "/listing")
public class ListingServlet extends HttpServlet {

    // To handle "get" request and forward it to the listing jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get current user of the session
            String currentUsername = AccessController.getUsername(request);
            int listingID = Integer.parseInt(request.getParameter("listingID"));

            // Retrieve selected listing info
            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            ListingDTO listing = listingDAO.getListingInfo(listingID);
            request.setAttribute("listing", listing);

            // Retrieve offers list for the selected listing
            OfferDAO offerDAO = new OfferDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            List<OfferDTO> offerList = offerDAO.getOfferByListing(listingID);
            request.setAttribute("offerList", offerList);

            // If the current user is not the owner of the selected listing
            if (!listing.getUsername().equals(currentUsername)) {
                // Retrieve the pokemon box of the current user
                BoxDAO boxDAO = new BoxDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                List<BoxDTO> boxList = boxDAO.getBox(currentUsername);
                request.setAttribute("boxList", boxList);
            }
            request.getRequestDispatcher("/WEB-INF/jsp/listing.jsp").forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
