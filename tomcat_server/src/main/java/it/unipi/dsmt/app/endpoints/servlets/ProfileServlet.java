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
            String currentUsername = AccessController.getUsername(request);
            System.out.print("usernameSERVELT"+ currentUsername);
            UserProfileDTO userInfo = userDAO.getUserFromUsername(currentUsername);
            request.setAttribute("userInfo", userInfo);
            String profileSection = request.getParameter("profileSection");
            // If I am in box section
            if (profileSection.equals("box")) {
                // Retrieve the pokemon box of the current user
                BoxDAO boxDAO = new BoxDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                List<BoxDTO> boxList = boxDAO.getBox(currentUsername);
                request.setAttribute("boxList", boxList);
            }
            // else, if I am in the listings section
            else if (profileSection.equals("Listings")){
                // Retrieve current user listings
                ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                List<ListingDTO> listingList = listingDAO.getListingsByUsername(currentUsername);
                request.setAttribute("listingList", listingList);
            }

            // Set the target profile section to BOX
            request.setAttribute("profileSection", profileSection);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    /**
     TODO TUTTA QUESTA PARTE DI INSERT/DELETE DI UNA LISTING DEVE ESSERE
        GESTITA DA ERLANG E WEBSOCKET (NON QUI NEL SERVLET), E VANNO FATTE LE SEGUENTI COSE:
        - NEW LISTING --> UNA FUNZIONE JS CREA UNA "POST" REQUEST PER PORTARMI NELLA MIA SEZIONE LISTING
                E MANDA LA NUOVA LISTING IN FORMATO JSON ALL'ERLANG NODE DELLE LISTING (TRAMITE WS);
                QUESTO LA AGGIUNGE AL DB E LA INOLTRA AGLI ALTRI UTENTI DELLA HOME, DOVE UNA FUNZIONE JS
                LA AGGIUNGERÀ DINAMICAMENTE ALLA PAGINA
        - DELETE LISTING --> UNA FUNZIONE JS ELIMINA LA LISTING DALLA MIA PAGINA E MANDA IN JSON
                IL LISTING_ID ALL'ERLANG NODE DELLE LISTING (TRAMITE WS); QUESTO LA ELIMINA DAL DB E
                LA INOLTRA AGLI ALTRI UTENTI CON WEBSOCKET: QUELLI NELLA HOME VEDONO SPARIRE LA LISTING,
                MENTRE QUELLI NELLA PAGINA DELLA LISTING VENGONO AVVISATI CON UN POPUP CHE POI LI RIPORTA
                NELLA HOME (ENTRAMBI QUESTI COMPORTAMENTI SI OTTENGONO CON FUNZIONI JS)
     */
    // To handle "post" request when I create a new listing
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {

            // Here profileSection == listings
            String currentUsername = AccessController.getUsername(request);
            int boxID = Integer.parseInt(request.getParameter("boxID"));
            // TODO NELLA NUOVA VERSIONE, DEVO PRENDERE SOLO BOX ID
            //  int pokemonID = Integer.parseInt(request.getParameter("pokemonID"));
            Boolean status = Boolean.valueOf(request.getParameter("status"));
            // Insert a new listing in the database
            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            Listing listing = new Listing(boxID, status, currentUsername, new Timestamp(System.currentTimeMillis()));
            listingDAO.insertListing(listing);

            // Retrieve current user listings
            List<ListingDTO> listingList = listingDAO.getListingsByUsername(currentUsername);
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
