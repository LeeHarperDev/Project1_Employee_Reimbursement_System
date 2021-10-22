package com.ex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ex.controller.api.*;
import com.ex.controller.auth.AuthenticationController;
import com.ex.controller.auth.Roles;
import com.ex.controller.view.*;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.staticfiles.Location;
import com.ex.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import com.ex.services.HTMLWriter;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Application {
    private Logger logger = LogManager.getLogger(Application.class);
    private SessionFactory sessionFactory;
    private Javalin app;

    private void configure(Configuration configuration) {
        logger.info("Configuring Javalin Session Factory.");
        if (configuration != null) {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            this.sessionFactory = configuration.buildSessionFactory(builder.build());
        }
    }

    public void run(Configuration configuration) {
        this.configure(configuration);
        logger.info("Initiating Javalin web server.");
        this.app = Javalin.create(config -> config.addStaticFiles("/public", Location.CLASSPATH)).start(8080);

        logger.info("Initiating Javalin routes.");
        app.routes(() -> {
            // This route directs the user/guest to the homepage of the web application.
            get("/", ctx -> {
                logger.info("A GET request was sent to \"/\".");
                HTMLWriter writer = new HTMLWriter("src/main/html/all/login.html");

                ctx.status(200);
                ctx.html(writer.getHtmlString());
            });

            // This route directs the employee/manager user to their respective dashboard.
            get("/dashboard", ctx -> {
                logger.info("A GET request was sent to \"/dashboard\".");
                AuthenticationController auth = new AuthenticationController();
                String token = ctx.cookieStore("token");

                User user = auth.getUserFromToken(token);

                if (user != null) {
                    if (user.getRole().getId() == Roles.EMPLOYEE.ordinal()) {
                        ViewController employeeDashboardView = new EmployeeDashboardViewController();
                        employeeDashboardView.attemptDisplay(ctx);

                    } else if (user.getRole().getId() == Roles.MANAGER.ordinal()) {
                        ViewController managerDashboardView = new ManagerDashboardViewController();
                        managerDashboardView.attemptDisplay(ctx);

                    } else {
                        ctx.redirect("/");
                    }
                } else {
                    ctx.redirect("/");
                }
            });

            // This route directs the user to a view containing a list of their tickets.
            get("/tickets", ctx -> {
                logger.info("A GET request was sent to \"/tickets\".");
                ViewController employeeTicketsView = new EmployeesTicketsViewController();
                employeeTicketsView.attemptDisplay(ctx);
            });

            // This route directs the user to a view containing their user profile.
            get("/profile", ctx -> {
                logger.info("A GET request was sent to \"/profile\".");
                ViewController employeeProfileView = new EmployeeProfileViewController();
                employeeProfileView.attemptDisplay(ctx);
            });

            // This route directs the user to a view containing a form that allows them to update their profile.
            get("/profile/edit", ctx -> {
                logger.info("A GET request was sent to \"/profile/edit\".");
                ViewController updateEmployeeInfoViewController = new UpdateEmployeeInfoViewController();
                updateEmployeeInfoViewController.attemptDisplay(ctx);
            });

            // This route directs the user to their respective dashboard.
            get("/admin/employees", ctx -> {
                logger.info("A GET request was sent to \"/admin/employees\".");
                ViewController employeeListView = new EmployeeListViewController();

                employeeListView.attemptDisplay(ctx);
            });

            get("/admin/employees/add", ctx -> {
                logger.info("A GET request was sent to \"/admin/employees/add\".");
                ViewController employeeRegisterView = new EmployeeRegisterViewController();

                employeeRegisterView.attemptDisplay(ctx);
            });

            get("/admin/employees/{id}", ctx -> {
                logger.info("A GET request was sent to \"/admin/employees/{id}\".");
                ctx.cookie("searchedEmployeeId", ctx.pathParam("id"), 100);

                ViewController managerEmployeeProfileView = new ManagerEmployeeProfileViewController();
                managerEmployeeProfileView.attemptDisplay(ctx);
            });

            get("/admin/employees/{id}/tickets", ctx -> {
                logger.info("A GET request was sent to \"/admin/employees/{id}\".");
                ctx.cookie("searchedEmployeeId", ctx.pathParam("id"), 100);

                ViewController manageEmployeesTicketsView = new ManagerManageEmployeesTicketsViewController();
                manageEmployeesTicketsView.attemptDisplay(ctx);
            });

            get("/admin/tickets", ctx -> {
                logger.info("A GET request was sent to \"/admin/tickets\".");
                ViewController allTicketsView = new AllTicketsViewController();
                allTicketsView.attemptDisplay(ctx);
            });

            ApiBuilder.crud("/api/employees/{id}", new EmployeeHttpController(this.sessionFactory));
            ApiBuilder.crud("/api/users/{id}", new UserHttpController(this.sessionFactory));
            ApiBuilder.crud("/api/tickets/{id}", new TicketHttpController(this.sessionFactory));
            ApiBuilder.crud("/api/determinations/{id}", new DeterminationTypeHttpController(this.sessionFactory));

            post("/api/tickets/{id}/close", ctx -> {
                logger.info("A POST request was sent to \"/api/tickets/{id}/close\".");
                TicketDeterminationController ticketDeterminationController = new TicketDeterminationController(this.sessionFactory);
                ticketDeterminationController.makeDetermination(ctx, ctx.pathParam("id"));
            });

            get("/api/employees/{id}/tickets", ctx -> {
                logger.info("A GET request was sent to \"/api/employees/{id}/tickets\".");
                EmployeeHttpController employeeController = new EmployeeHttpController(this.sessionFactory);
                employeeController.getAllTickets(ctx, ctx.pathParam("id"));
            });

            post("/api/users/login", ctx -> {
                logger.info("A POST request was sent to \"/api/users/login\".");
                AuthenticationController auth = new AuthenticationController();
                ObjectMapper mapper = new ObjectMapper();
                User tempUser = mapper.readValue(ctx.body(), User.class);

                User loggedInUser = auth.authenticateUser(tempUser, this.sessionFactory);

                if (loggedInUser != null) {
                    String token = auth.createToken(loggedInUser);

                    ctx.cookieStore("token", token);
                    ctx.cookie("employeeId", Integer.toString(loggedInUser.getEmployee().getId()));
                    ctx.status(200);
                } else {
                    ctx.status(401);
                }
            });
            get("/api/logout", ctx -> {
                logger.info("A GET request was sent to \"/api/logout\".");
                AuthenticationController auth = new AuthenticationController();
                ctx.removeCookie("employeeId");
                auth.logOutUser(ctx);
                ctx.redirect("/");
            });
        });
    }

    public void stop() {
        this.app.stop();
    }
}
