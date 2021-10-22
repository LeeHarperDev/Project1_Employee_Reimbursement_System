package com.ex.controller.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import com.ex.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

public class AuthenticationController {

    private static Logger logger = LogManager.getLogger(AuthenticationController.class);

    /***
     *
     * Function: AuthenticationController.authenticateUser(User tempUser, SessionFactory sessionFactory).
     * Purpose: Attempts to authenticate the guest using user information that they provided.
     * Precondition: A valid database connection is established.
     * Postcondition: N/A.
     *
     * @param tempUser The stored credentials supplied by the guest.
     * @param sessionFactory The session factory used to retrieve a collection of user objects.
     * @return The user object if one was successfully found and authenticated, null otherwise.
     *
     */
    public User authenticateUser(User tempUser, SessionFactory sessionFactory) {
        logger.info("Beginning to attempt user authentication.");
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<User> users = session.createCriteria(User.class).addOrder(Order.asc("id")).list();

        for (User user : users) {
            logger.info("Attempting to match user " + user.getUsername() + " with " + tempUser.getUsername() + ".");
            if (user.getUsername().equals(tempUser.getUsername())) {
                logger.info("Found match for username.");
                logger.info("Running BCrypt verification on password.");
                BCrypt.Result verifier = BCrypt.verifyer().verify(tempUser.getPassword().toCharArray(), user.getPassword());

                logger.info("Committing transaction.");
                tx.commit();
                logger.info("Closing session.");
                session.close();
                if (verifier.verified) {
                    logger.info("Returning authenticated user.");
                    return user;
                } else {
                    logger.info("The user credentials were not successfully validated.");
                    return null;
                }
            }
        }

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();
        logger.info("The user credentials were not successfully validated.");
        return null;
    }

    /***
     *
     * Function: AuthenticationController.logOutUser(Context ctx).
     * Purpose: Clears the cookie store associated with the user, effectively destroying their user session.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the logout request.
     *
     */
    public void logOutUser(Context context) {
        context.clearCookieStore();
    }

    /***
     *
     * Function: AuthenticationController.createToken(User user);
     * Purpose: Creates a JSON Web Token with the information of the authenticated user stored in it.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param user The authenticated user that will be stored in the JWT.
     * @return A JWT containing the user information.
     * @throws JsonProcessingException If the user object was not able to be formatted as a JSON string.
     *
     */
    public String createToken(User user) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withClaim("user", jsonUser)
                .withIssuer("Revature")
                .sign(algorithm);
    }

    /***
     *
     * Function: getUserFromToken(String token).
     * Purpose: Retrieves a User object that is stored within the supplied JWT.
     * Precondition: The ticket is not null.
     * Postcondition: N/A.
     *
     * @param token The JWT containing the user information.
     * @return The user object stored within the JWT if one was supplied, null otherwise.
     * @throws JsonProcessingException If the user object was not able to be formatted as a JSON string.
     *
     */
    public User getUserFromToken(String token) throws JsonProcessingException {
        if (token == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(JWT.decode(token).getClaim("user").asString(), User.class);
    }
}
