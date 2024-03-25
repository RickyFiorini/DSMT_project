package it.unipi.dsmt.app.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

// Class that handles the authentication token and the password
public class AccessController {
    private static final int EXPIRATION_TIME = 86400000; // Tempo di scadenza del JWT (1 giorno)
    private static final String SECRET_KEY = "root";

    /**
     * TOKEN CONTROLLER
     */

    // Set a new authentication token for the specified user
    public static void setToken(HttpServletRequest request, String username) {

        /*Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        */
        request.getSession().setAttribute("TOKEN", /*token*/username);

    }

    // Check the authentication token for the specified user
    public static String getToken(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("TOKEN");
        if (token == null)
            return null;
        return token;

        /*Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        Date expirationDate = claims.getExpiration();
        Date now = new Date();
        if (expirationDate.before(now)) {
            request.getSession().setAttribute("TOKEN", null);
            return null;
        }
*/



    }

    /** END TOKEN CONTROLLER */

    /**
     * PASSWORD CONTROLLER
     *
     * @throws NoSuchAlgorithmException
     */

    // Encrypt and return the password
    public static String encryptPassword(String plaintext) throws NoSuchAlgorithmException {
        byte[] byteString = MessageDigest.getInstance("SHA-256").digest(plaintext.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : byteString) {
            hexString.append(String.format("%02x", hashByte));
        }
        return hexString.toString();

    }

    /** END PASSWORD CONTROLLER */

    // Authenticate the specified user
    public static String getUsername(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("TOKEN");
        if (token == null)
            return null;
        //Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
     //   String username = (String) claims.get("username");
     //   return username;
        return token;
    }
}
