package com.ex.controller.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ex.controller.auth.AuthenticationController;
import com.ex.controller.auth.AuthorizationController;
import com.ex.controller.auth.Roles;
import com.ex.exception.UserNotAuthorizedException;
import io.javalin.http.Context;
import com.ex.model.User;
import com.ex.services.HTMLWriter;

public class ManagerDashboardViewController implements ViewController {
    @Override
    public boolean isAuthorized(User user) {
        AuthorizationController authorization = new AuthorizationController(Roles.MANAGER);

        return authorization.checkIfAuthorized(user);
    }

    @Override
    public void attemptDisplay(Context context) {
        try {
            AuthenticationController auth = new AuthenticationController();
            String token = context.cookieStore("token");
            User user = auth.getUserFromToken(token);

            if (isAuthorized(user)) {
                this.show(context);
            } else {
                throw new UserNotAuthorizedException();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (UserNotAuthorizedException e) {
            context.status(403);
        }
    }

    @Override
    public void show(Context context) {
        HTMLWriter writer = new HTMLWriter("src/main/html/manager/dashboard.html");

        context.status(200);
        context.html(writer.getHtmlString());
    }
}
