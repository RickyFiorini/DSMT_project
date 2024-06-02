package it.unipi.dsmt.app.endpoints.servlets;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unipi.dsmt.app.daos.ListingDAO;
import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.daos.BoxDAO;
import it.unipi.dsmt.app.dtos.UserProfileDTO;
import it.unipi.dsmt.app.dtos.BoxDTO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;
import it.unipi.dsmt.app.dtos.ListingDTO;

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
            else if (profileSection.equals("listings")){
                // Retrieve current user listings
                ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
                List<ListingDTO> listingList = listingDAO.getListingsByUsername(currentUsername);
                List<ListingDTO> ListingListOpen = listingList.stream().filter(new Predicate<ListingDTO>() {
                    @Override
                    public boolean test(ListingDTO listing) { return listing.getWinner() == null;
                    }
                }).collect(Collectors.toList());
                request.setAttribute("listingList", ListingListOpen);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }

    // To handle "post" request when I create a new listing and redirection to the profile "listings" section
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {

            // Here profileSection == listings
            String currentUsername = AccessController.getUsername(request);

            // Retrieve the current user info
            UserDAO userDAO = new UserDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            UserProfileDTO userInfo = userDAO.getUserFromUsername(currentUsername);
            request.setAttribute("userInfo", userInfo);

            // Retrieve current user listings
            ListingDAO listingDAO = new ListingDAO((Connection) getServletContext().getAttribute("databaseConnection"));
            List<ListingDTO> listingList = listingDAO.getListingsByUsername(currentUsername);
            request.setAttribute("listingList", listingList);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage(request, response, e);
        }
    }
}