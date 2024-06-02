package it.unipi.dsmt.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

// To initialize database connection and web app context
public class AppContextListener implements ServletContextListener {
    private Connection db = null;

    // Initialize the connection with the database
    private Connection initDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            db = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%d/%s", "localhost", 3306,"PokemonDB"),
                   "root","root");
            System.out.println(String.format("[MYSQL] -> Connected to database %s","PokemonDB"));
            return db;
        } catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Inner Exception: " + cnfe.getMessage());
            System.out.println("Stack\n:" + cnfe.getStackTrace());
            return null;
        }
    }

    // Initialize the web app context
    public void contextInitialized(ServletContextEvent event) {
        Connection sqldao = initDB();
        // Get the servlet context and store the database connection
        ServletContext context = event.getServletContext();
        context.setAttribute("databaseConnection", sqldao);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
    }

    // Destroy the web app context
    public void contextDestroyed(ServletContextEvent event) {
        // Cleanup code if needed when the web application is destroyed
        ServletContext context = event.getServletContext();
        context.removeAttribute("databaseConnection");
    }
}
