package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unipi.dsmt.app.daos.ListingDAO;
import it.unipi.dsmt.app.daos.OfferDAO;
import it.unipi.dsmt.app.dtos.ListingDTO;
import it.unipi.dsmt.app.entities.Offer;
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

            // Filter out the open listings
            List<ListingDTO> closedListingList = listingList.stream().filter(new Predicate<ListingDTO>() {
                @Override
                public boolean test(ListingDTO listing) {
                    return listing.getWinner() != null;
                }
            }).collect(Collectors.toList());
            request.setAttribute("closedListingList", closedListingList);

            // Filter out the closed listings
            List<ListingDTO> openListingList = listingList.stream().filter(new Predicate<ListingDTO>() {
                @Override
                public boolean test(ListingDTO listing) {
                    return listing.getWinner() == null;
                }
            }).collect(Collectors.toList());
            request.setAttribute("openListingList", openListingList);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // TODO FUNZIONE SPOSTATA NEL PROFILE SERVLET CON METODO POST
    // To handle "post" request when I want to create a new listing
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {

            // TODO AGGIUNGERE UNA NUOVA LISTING PER L'UTENTE
            String currentUsername = AccessController.getUsername(request);
            int listingID = Integer.parseInt(request.getParameter("listingID"));
            String pokemonOffered = (String) request.getParameter("pokemonOffered");

            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            OfferDAO offerDAO = new OfferDAO((Connection) getServletContext().getAttribute("databaseConnection"));

            // Check if the current user already made an offer
            int offerID = offerDAO.getUserOfferByListing(currentUsername, listingID);
            // if it is a new offer, insert it in the database
            if (offerID == -1) {
                Offer offer = new Offer(listingID, currentUsername, pokemonOffered, false, new Timestamp(System.currentTimeMillis()));
                offerID = offerDAO.save(offer);
                response.sendRedirect(request.getContextPath() + "/listing?offerID=" + offerID);
                // return;
            }
            // else, update the past offer of the current user for this listing
            else {
                Offer offer = new Offer(listingID, currentUsername, pokemonOffered, false, new Timestamp(System.currentTimeMillis()));
                offerDAO.updateOffer(offerID, offer);
                response.sendRedirect(request.getContextPath() + "/listing?offerID=" + offerID);
            }

            // TODO COSA FARE DOPO UNA OFFER? CON JS E WEBSOCKET VORREI MOSTRARLA DINAMICAMENTE Boeh?


        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

}
