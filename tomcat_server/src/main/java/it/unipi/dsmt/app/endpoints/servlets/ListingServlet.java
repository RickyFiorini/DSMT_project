package it.unipi.dsmt.app.endpoints.servlets;

import it.unipi.dsmt.app.daos.BoxDAO;
import it.unipi.dsmt.app.daos.ListingDAO;
import it.unipi.dsmt.app.daos.OfferDAO;
import it.unipi.dsmt.app.dtos.ListingDTO;
import it.unipi.dsmt.app.dtos.OfferDTO;
import it.unipi.dsmt.app.dtos.PokemonDTO;
import it.unipi.dsmt.app.entities.Offer;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "ListingServlet", value = "/listing")
public class ListingServlet extends HttpServlet {

    // To handle "get" request and forward it to the listing jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            String currentUsername = AccessController.getUsername(request);
            int listingID = Integer.parseInt(request.getParameter("listingID"));

            // TODO USO L'ID DELLA LISTING SELEZIONATA E NE PRENDO LE INFO DAL DB, OFFER COMPRESE
            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            ListingDTO listing = listingDAO.getListingInfo(listingID);
            request.setAttribute("listing", listing);

            OfferDAO offerDAO = new OfferDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            List<OfferDTO> offerList = offerDAO.getOfferByListing(listingID);
            request.setAttribute("offerList", offerList);

            // TODO PRENDO ANCHE IL BOX DELL'ATTUALE USER PER FARE UNA POSSIBILE OFFER
            BoxDAO boxDAO = new BoxDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            List<PokemonDTO> pokemonList = boxDAO.getBoxByUser(currentUsername);
            request.setAttribute("pokemonList", pokemonList);

            request.getRequestDispatcher("/WEB-INF/jsp/listing.jsp").forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // To handle "post" request (new  offer) and forward it to the listing jsp
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {

            // TODO GESTIRE NUOVA OFFER (O MODIFICARE OFFER GIA' ESISTENTE)
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

    // To handle the "delete" request
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            // TODO GESTIRE L'ELIMINAZIONE DI UNA OFFER
            String currentUsername = AccessController.getUsername(request);
            int offerID = Integer.parseInt(request.getParameter("offerID"));

            OfferDAO offerDAO = new OfferDAO((Connection) getServletContext().getAttribute("databaseConnection"));

            // Check if the selected offer is the current user offer
            offerDAO.validateOfferIDByUsername(offerID, currentUsername);
            // Delete the offer
            offerDAO.deleteUserOfferByID(offerID);
            response.setStatus(200);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
