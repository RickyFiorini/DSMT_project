package it.unipi.dsmt.app.endpoints.servlets;

import it.unipi.dsmt.app.daos.BoxDAO;
import it.unipi.dsmt.app.daos.ListingDAO;
import it.unipi.dsmt.app.daos.OfferDAO;
import it.unipi.dsmt.app.dtos.BoxDTO;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@WebServlet(name = "ListingServlet", value = "/listing")
public class ListingServlet extends HttpServlet {

    // To handle "get" request and forward it to the listing jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String currentUsername = AccessController.getUsername(request);
            int listingID = Integer.parseInt(request.getParameter("listingID"));
            System.out.print("LISTINGID"+ listingID);

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

                // Filter out the pokemon already listed or offered
                boxList = boxList.stream().filter(new Predicate<BoxDTO>() {
                    @Override
                    public boolean test(BoxDTO box) {
                        return !box.isListed();
                    }
                }).collect(Collectors.toList());

                request.setAttribute("boxList", boxList);
            }
            request.getRequestDispatcher("/WEB-INF/jsp/listing.jsp").forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    /**
     TODO TUTTA QUESTA PARTE DI INSERT/UPDATE DI UNA NUOVA OFFER O DI UN TRADE DEVE ESSERE
            GESTITA DA ERLANG E WEBSOCKET (NON QUI NEL SERVLET), E VANNO FATTE LE SEGUENTI COSE:
            - NEW OFFER --> DOPO AVER SELEZIONATO IL POKEMON DAL BOX NEL POPUP,
                    UNA FUNZIONE JS NASCONDE IL POPUP E AGGIUNGE DINAMICAMENTE
                    LA MIA OFFER ALLA PAGINA; POI MANDO CON WEBSOCKET LA MIA OFFER IN FORMATO JSON
                    ALL'ERLANG NODE CHE GESTISCE LE OFFER, COSÌ CHE POSSA INSERIRLA NEL DB;
                    INFINE, IL SUDDETTO ERLANG NODE INOLTRA LA MIA OFFER (SEMPRE CON WB)
                    AGLI ALTRI UTENTI CHE STANNO ATTUALMENTE VISUALIZZANDO LA LISTING,
                    E UN'ALTRA FUNZIONE JS LA AGGIUNGERÀ DINAMICAMENTE ANCHE ALLE LORO PAGINE
            - UPDATE OFFER --> STESSO RAGIONAMENTO DI SOPRA, MA INVECE CHE AGGIUNGERE DINAMICAMENTE
                    UNA NUOVA OFFER DEVO MODIFICARE QUELLA GIÀ PRESENTE NEL DB; POI CON WEBSOCKET LA MANDO
                    IN FORMATO JSON ALL'ERLANG NODE CHE GESTISCE LE OFFER, COSÌ CHE POSSA MODIFICARLA NEL DB;
                    INFINE, IL SUDDETTO ERLANG NODE INOLTRA LA MIA OFFER (SEMPRE CON WB)
                    AGLI ALTRI UTENTI CHE STANNO ATTUALMENTE VISUALIZZANDO LA LISTING,
                    E UN'ALTRA FUNZIONE JS LA MODIFICHERÀ DINAMICAMENTE ANCHE NELLE LORO PAGINE
            - TRADE --> DOPO AVER SCELTO L'OFFER VINCENTE, UNA FUNZIONE JS MOSTRA UN POPUP CHE DICHIARA
                    CONCLUSA LA LISTING; POI CON WEBSOCKET MANDO IN JSON LISTING_ID E USERNAME DEL WINNER
                    ALL'ERLANG NODE CHE GESTISCE LE LISTING, COSÌ CHE POSSA MODIFICARE NEL DB LA LISTING
                    AGGIUNGENDO IL NOME DEL WINNER; INOLTRE IL SUDDETTO ERLANG NODE NOTIFICA (SEMPRE CON WB)
                    GLI ALTRI UTENTI NELLA PAGINA CHE LA LISTING È CONCLUSA (E UN'ALTRA FUNZIONE JS MOSTRA
                    UN POPUP CON QUESTA INFORMAZIONE).
                    INOLTRE, L'ERLANG NODE IN QUESTIONE MANDA (CON WS) LA LISTING APPENA CONCLUSA AGLI UTENTI DELLA HOME,
                    COSÌ CHE QUESTA POSSA ESSERE MODIFICATA DINAMICAMENTE CON UNA FUNZIONE JS
            LA PARTE INIZIALE DEL TRADE CHE INTERAGISCE CON I BOX DEL DB (LO SCAMBIO DI POKEMON VERO E PROPRIO)
            PUÒ ESSERE FATTA IN QUESTO SERVLET (ATTIVATO DA UNA "POST" REQUEST DI UNA DELLE FUNZIONE JS PRECEDENTI)
     */

    // To handle "post" request (new  offer/trade) and forward it to the listing or profile jsp
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String res;
            String currentUsername = AccessController.getUsername(request);
            int listingID = Integer.parseInt(request.getParameter("listingID"));

            // TODO DISCRIMINARE I CASI IN CUI HO UNA NUOVA OFFER
            //  DA QUELLI IN CUI HO UN NUOVO TRADE
            // If I have the offerID, I have to handle a new trade
            if (request.getParameter("offerID") != null) {
                // Get the winning offer ID from the request
                int winningOfferID = Integer.parseInt(request.getParameter("offerID"));

                ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                OfferDAO offerDAO = new OfferDAO((Connection) getServletContext().getAttribute("databaseConnection"));

                // Get the winner username and update the listing
                String winner = offerDAO.getUserByOfferID(winningOfferID);
                listingDAO.setWinner(listingID, winner);

                // TODO POI VORREI NOTIFICARE GLI ALTRI? COMUNQUE VADO AL MIO BOX

                response.sendRedirect(request.getContextPath() + "/profile?profileSection=box");
            }
            // Else, I have to handle a new offer
            else {
                // TODO GESTIRE NUOVA OFFER (O MODIFICARE OFFER GIA' ESISTENTE)
                // Get the new offer ID from the request
                int boxID = Integer.parseInt(request.getParameter("boxID"));
                // TODO NELLA NUOVA VERSIONE, BASTA PRENDERE IL POKEMON ID
                //  int pokemonID = Integer.parseInt(request.getParameter("pokemonID"));
                String pokemonOffered = request.getParameter("pokemonOffered");

                ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                OfferDAO offerDAO = new OfferDAO((Connection) getServletContext().getAttribute("databaseConnection"));

                // Check if the current user already made an offer
                OfferDTO offerID = offerDAO.getUserOfferByListing(currentUsername, listingID);
                Offer offer = new Offer(listingID, boxID, currentUsername, false, new Timestamp(System.currentTimeMillis()));
                // if it is a new offer, insert it in the database
                if (offerID == null) {
                    res = offerDAO.insertOffer(offer);
                }
                // else, update the past offer of the current user for this listing
                else {
                    offerDAO.updateOffer(offerID.getOfferID(), offer);
                }

                response.sendRedirect(request.getContextPath() + "/listing");
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
            // TODO GESTIRE L'ELIMINAZIONE DI UNA LISTING
            String currentUsername = AccessController.getUsername(request);
            int listingID = Integer.parseInt(request.getParameter("listingID"));
            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));

            // Check if the current user is the owner of the listing
            listingDAO.validateListing(currentUsername, listingID);
            // Delete the listing
            listingDAO.deleteListing(listingID);
            response.setStatus(200);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}
