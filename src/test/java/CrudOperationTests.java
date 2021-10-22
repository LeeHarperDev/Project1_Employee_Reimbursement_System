import com.ex.controller.Application;
import com.ex.model.Address;
import com.ex.model.Employee;
import com.ex.model.Role;
import com.ex.model.User;
import com.ex.model.dao.DataAccessible;
import com.ex.model.dao.RoleDAO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudOperationTests {

    private static Configuration configuration;
    private static Application app;
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setUp() {
        configuration = new Configuration().configure("hibernate.test.cfg.xml");
        app = new Application();
        app.run(configuration);

        if (configuration != null) {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }

        DataAccessible<Role, Integer> roleStorage = new RoleDAO(sessionFactory);

        Role employeeRole = new Role(1, "EMPLOYEE");
        Role managerRole = new Role(2, "MANAGER");

        roleStorage.create(employeeRole);
        roleStorage.create(managerRole);
    }

    @Test
    public void test1CreateUser() {
        Address address = new Address(1, "7399 Swift Road", null, "Greenbrier", "TN", "37172");
        Employee employee = new Employee(1, "Julie", "Harper", "Female", address);
        Role role = new Role(2, "MANAGER");
        User user = new User(1, employee, role, "jharper@leeharper.dev", "password");

        HttpResponse<String> response = Unirest.post("http://localhost:8080/api/users").body(user.toString()).asString();

        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void test2userCanLogIn() {
        String userBody = "{\"username\":\"jharper@leeharper.dev\",\"password\":\"password\"}";

        HttpResponse<String> response = Unirest.post("http://localhost:8080/api/users/login").body(userBody).asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void test3CreateEmployee() {
        Address address = new Address(1, "7399 Swift Road", null, "Greenbrier", "TN", "37172");
        Employee employee = new Employee(1, "Julie", "Harper", "Female", address);

        HttpResponse<String> response = Unirest.post("http://localhost:8080/api/employees").body(employee.toString()).asString();

        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void test4GetEmployeeList() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/api/employees").asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void test5GetUsersList() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/api/users").asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @AfterClass
    public static void tearDown() {
        app.stop();
    }
}
