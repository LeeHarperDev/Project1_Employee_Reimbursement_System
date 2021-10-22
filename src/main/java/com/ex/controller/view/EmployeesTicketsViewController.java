package com.ex.controller.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ex.controller.auth.AuthenticationController;
import io.javalin.http.Context;
import com.ex.model.User;
import com.ex.services.HTMLWriter;

public class EmployeesTicketsViewController implements ViewController {
    @Override
    public boolean isAuthorized(User user) {
        return true;
    }

    @Override
    public void attemptDisplay(Context context) {
        try {
            AuthenticationController auth = new AuthenticationController();
            String token = context.cookieStore("token");
            User user = auth.getUserFromToken(token);

            if (isAuthorized(user)) {
                this.show(context);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show(Context context) {
        HTMLWriter writer = new HTMLWriter("src/main/html/employee/view_employee_tickets.html");

        context.status(200);
        context.html(writer.getHtmlString());
    }
}
