package dao;

import models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO extends BaseDAO {

    private PreparedStatement allCustomersStatement, addCustomerStatement, getIdCustomerStatement;

    protected void prepareStatements() {
        try {
            allCustomersStatement = dbConnection.getSession().prepareStatement("SELECT * FROM customer");
            addCustomerStatement = dbConnection.getSession().prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?)");
            getIdCustomerStatement = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM customer");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Customer> customers() {
        ArrayList<Customer> rezultat = new ArrayList();
        try {
            ResultSet rs = allCustomersStatement.executeQuery();
            while (rs.next()) {
                Customer klijent = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                rezultat.add(klijent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void addCustomer(Customer klijent) {
        try {
            if(isCustomerInDB(klijent)) return;
            ResultSet rs = getIdCustomerStatement.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            klijent.setId(id);

            addCustomerStatement.setInt(1, klijent.getId());
            addCustomerStatement.setString(2, klijent.getFirstName());
            addCustomerStatement.setString(3, klijent.getLastName());
            addCustomerStatement.setString(4, klijent.getAddress());
            addCustomerStatement.setString(5, klijent.getPhoneNumber());

            addCustomerStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isCustomerInDB(Customer klijent) {
        try {
            ResultSet rs = allCustomersStatement.executeQuery();
            while (rs.next()) {
                Customer klijent2 = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                if(klijent.equals(klijent2)) {
                    klijent.setId(klijent2.getId());
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
