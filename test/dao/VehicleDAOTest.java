package dao;

import models.Customer;
import models.Employee;
import models.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleDAOTest {
    private VehicleDAO dao;
    @BeforeEach
    public void resetujBazu() throws SQLException {
        InitDB initDB = new InitDB();
        try {
            initDB.delete();
        } catch (SQLException e) {

        }
        initDB.createDB();
        dao = new VehicleDAO();
    }

    @Test
    void addVehicle() {
        Vehicle vehicle = new Vehicle();
        //'Putnički','Suzuki','Ignis',2007,'A12-A-345','JS1VX51L982100325','Siva','Metalik'
        vehicle.setType("Putnički");
        vehicle.setBrand("Suzuki");
        vehicle.setModel("Panda");
        vehicle.setYearOfProduction(2007);
        vehicle.setRegistration("A53-T-345");
        vehicle.setChassisNumber("WAULT64B94N050713");
        vehicle.setColor("White");
        vehicle.setColorType("Metalik");
        dao.addVehicle(vehicle);

        ArrayList<Vehicle> vehicles = dao.vehicles();
        assertEquals(4, vehicles.size());
    }

    @Test
    void testVehicles() {
        ArrayList<Vehicle> vehicles = dao.vehicles();
        assertEquals(3, vehicles.size());
    }

}
