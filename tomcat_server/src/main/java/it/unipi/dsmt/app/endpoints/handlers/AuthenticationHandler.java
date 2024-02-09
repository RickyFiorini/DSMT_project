package it.unipi.dsmt.app.endpoints.handlers;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Optional;

import it.unipi.dsmt.app.dtos.UserLoginDTO;
import it.unipi.dsmt.app.entities.User;
import it.unipi.dsmt.app.utils.AccessController;

import jakarta.servlet.http.HttpServletRequest;

// To handle signup and login requests during authentication
public class AuthenticationHandler {

    // To retrieve user signup info during request
    public static User unpackPostSignup(HttpServletRequest request) throws NoSuchAlgorithmException {
        String username = Optional.ofNullable(request.getParameter("username")).orElse("");
        String password = Optional.ofNullable(request.getParameter("password")).orElse("");
        password = AccessController.encryptPassword(password);
        String firstname = Optional.ofNullable(request.getParameter("name")).orElse("");
        String surname = Optional.ofNullable(request.getParameter("surname")).orElse("");

        User userInfo = new User(username, password, firstname, surname, true, new Date(System.currentTimeMillis()));

        System.out.println(String.format("[Server] -> Received SignUp Request: %s", userInfo.toString()));
        return userInfo;
    }

    // To retrieve user login info during request
    public static UserLoginDTO unpackPostLogin(HttpServletRequest request) throws NoSuchAlgorithmException {
        String username = Optional.ofNullable(request.getParameter("username")).orElse("");
        String password = Optional.ofNullable(request.getParameter("password")).orElse("");
        password = AccessController.encryptPassword(password);

        UserLoginDTO result = new UserLoginDTO(username, password);
        System.out.println(String.format("[Server] -> Received LogIn Request: %s", result.toString()));
        return result;
    }
}
