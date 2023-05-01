package dao;

import enums.StatusOfTechnicalInspection;
import exceptions.ScheduledDate;
import models.Employee;
import models.TechnicalInspection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserSession;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TechnicalInspectionDAOTest {

    public CustomerDAO customerDAO;
    public VehicleDAO vehicleDAO;
    public TechnicalInspectionDAO technicalInspectionDAO;
    public EmployeeDAO employeeDAO;
    public TechnicalInspectionTeamDAO technicalInspectionTeamDAO;

    @BeforeEach
    public void resetujBazu() throws SQLException {
        InitDB initDB = new InitDB();
        try {
            initDB.delete();
        } catch (SQLException e) {

        }
        initDB.createDB();

        technicalInspectionDAO = new TechnicalInspectionDAO();
        vehicleDAO = new VehicleDAO();
        employeeDAO = new EmployeeDAO();
        technicalInspectionTeamDAO = new TechnicalInspectionTeamDAO();
        customerDAO = new CustomerDAO();
    }

    @Test
    void addTI() {

        try {

            TechnicalInspection technicalInspection = new TechnicalInspection();
            technicalInspection.setVehicle(vehicleDAO.vehicles().get(0));
            technicalInspection.setCustomer(customerDAO.customers().get(0));
            technicalInspection.setVrstaTehnickogPregleda("Preventivni");
            technicalInspection.setStatusOfTechnicalInspection(StatusOfTechnicalInspection.Zakazan);
            technicalInspection.setDateOfInspection(LocalDate.now().plusMonths(3));
            technicalInspection.getEmployees().add(employeeDAO.getEmployeeWithUserName(UserSession.getUserName()));
            technicalInspectionDAO.addTI(technicalInspection);
            System.out.println(technicalInspectionDAO.allTechnical());
            assertEquals(6, technicalInspectionDAO.allTechnical().size());

        } catch (ScheduledDate scheduledDate) {
           scheduledDate.printStackTrace();
        }
    }

    @Test
    void cancelTI() {
        TechnicalInspection technicalInspection = technicalInspectionDAO.allTechnical().get(2);
        technicalInspectionDAO.cancelTI(technicalInspection);

        assertEquals("Otkazan", technicalInspectionDAO.allTechnical().get(2).getStatusOfTechnicalInspection());
    }

    @Test
    void getEmployees() {
        ArrayList<Employee> employees =  technicalInspectionDAO.getEmployees(technicalInspectionDAO.allTechnical().get(2));
        assertEquals("[Meho Mehić, Darko Darkić, Dado Dadić, Lana Lanić]", employees.toString());
    }
}
