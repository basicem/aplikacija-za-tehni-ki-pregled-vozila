package dao;

import models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomersDAOTest {

    private CustomerDAO dao;
    @BeforeEach
    public void resetujBazu() throws SQLException {
        InitDB initDB = new InitDB();
        try {
            initDB.delete();
        } catch (SQLException e) {

        }
        initDB.createDB();
        dao = new CustomerDAO();
    }

    @Test
    void testEmployees() {
        ArrayList<Customer> customers = dao.customers();
        assertEquals(3, customers.size());
    }

    @Test
    void addEmployee() {
        Customer customer = new Customer(2, "Niko", "Niko", "Sarajevo 123", "123-123-123");
        dao.addCustomer(customer);
        ArrayList<Customer> customers = dao.customers();
        assertEquals(4, customers.size());

        //ponovo dodajemo
        dao.addCustomer(customer);
        ArrayList<Customer> customers2 = dao.customers();
        assertEquals(4, customers2.size());
    }

}
