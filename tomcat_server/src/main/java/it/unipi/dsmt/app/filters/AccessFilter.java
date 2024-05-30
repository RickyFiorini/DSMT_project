package it.unipi.dsmt.app.filters;

import java.io.IOException;
import java.sql.Connection;

import it.unipi.dsmt.app.daos.UserDAO;
import it.unipi.dsmt.app.utils.AccessController;
import it.unipi.dsmt.app.utils.ErrorHandler;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AccessFilter implements Filter {

    // Check if the user session is still valid
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            UserDAO userDAO = new UserDAO((Connection) req.getServletContext().getAttribute("databaseConnection"));
            String claimingUsername = AccessController.getUsername(req);
            String token = AccessController.getToken(req);
            // if the session is expired, redirect user to login
            if (token == null) {
                if (claimingUsername != null)
                ErrorHandler.setPopupErrorMessage(req, "Invalid/Expired token. Login again.");
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            // if the session is still valid, forward the request
            chain.doFilter(request, response);
        } catch (Exception e) {
            ErrorHandler.safeDispatchToErrorPage((HttpServletRequest) request, (HttpServletResponse) response, e);
            return;
        }
    }

}
