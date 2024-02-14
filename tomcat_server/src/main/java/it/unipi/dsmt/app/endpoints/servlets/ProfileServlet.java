package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import it.unipi.dsmt.app.daos.ListingDAO;
import it.unipi.dsmt.app.daos.OfferDAO;
import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.daos.BoxDAO;
import it.unipi.dsmt.app.dtos.UserProfileDTO;
import it.unipi.dsmt.app.dtos.BoxDTO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;
import it.unipi.dsmt.app.dtos.PokemonDTO;
import it.unipi.dsmt.app.dtos.ListingDTO;
import it.unipi.dsmt.app.entities.Listing;

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

            // Retrieve the current user info
            UserDAO userDAO = new UserDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            String username = AccessController.getUsername(request);
            UserProfileDTO userInfo = userDAO.getUserFromUsername(username);
            request.setAttribute("user_info", userInfo);

            String profilePage = (String) request.getParameter("profilePage");
            // If I am in box page
            if (profilePage.equals("box")) {
                // Retrieve the pokemon box of the current user
                BoxDAO boxDAO = new BoxDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                List<BoxDTO> boxList = boxDAO.getBox(username);
                request.setAttribute("boxList", boxList);
            }
            // else, if I am in the listings page
            else if (profilePage.equals("listings")){
                // Retrieve current user listings
                ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                List<ListingDTO> listingList = listingDAO.getListingsByUsername(username);
                request.setAttribute("listingList", listingList);
            }

            // TODO DISCRIMINARE CON DUE FLAG SE VISUALIZZO IL BOX O I LISTINGS DELL'UTENTE
            // Set the target page to BOX
            // request.setAttribute("profilePage", profilePage);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // TODO QUANDO INSERISCO UNA NUOVA LISTING VOGLIO TORNARE NEL PROFILE NELLA SEZIONE LISTING
    // To handle "post" request when I create a new listing
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Here profilePage == listings
            String profilePage = (String) request.getParameter("profilePage");
            String currentUsername = AccessController.getUsername(request);
            int boxID = Integer.parseInt(request.getParameter("boxID"));
            // TODO NELLA NUOVA VERSIONE, DEVO PRENDERE SOLO POKEMON ID
            //  int pokemonID = Integer.parseInt(request.getParameter("pokemonID"));
            String pokemonName = (String) request.getParameter("pokemonName");
            String pokemonType = (String) request.getParameter("pokemonType");
            String imageURL = (String) request.getParameter("imageURL");

            // Insert a new listing in the database
            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            Listing listing = new Listing(boxID, pokemonName, pokemonType, currentUsername, true, null, new Timestamp(System.currentTimeMillis()), imageURL);
            listingDAO.insertListing(listing);

            // Retrieve current user listings
            List<ListingDTO> listingList = listingDAO.getListingsByUsername(username);
            request.setAttribute("listingList", listingList);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }


    // TODO GESTIRE L'ELIMINAZIONE DI UNA LISTING DA PARTE DELL'UTENTE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            // TODO GESTIRE L'ELIMINAZIONE DI UNA LISTING
            String currentUsername = AccessController.getUsername(request);
            int listingID = Integer.parseInt(request.getParameter("listingID"));

            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            // Check and delete the selected listing
            listingDAO.validateListing(currentUsername, listingID);
            listingDAO.deleteListing(listingID);
            response.setStatus(200);

            // TODO DOPO L'ELIMINAZIONE DI UNA LISTING DEVO NOTIFICARE COLORO CHE HANNO FATTO
            //  UNA OFFER ED ELIMINARE LE OFFER RIGUARDANTE QUELLA LISTING
            //  CON ERLANG?

        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
