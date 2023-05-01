package dao;

import enums.VehicleType;
import exceptions.ScheduledDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;
import models.Employee;
import models.TechnicalInspection;
import models.Vehicle;
import services.UserSession;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TechnicalInspectionDAO extends BaseDAO{

    private PreparedStatement getCustomerStatement, getEmployeesInTIStatement, cancelTIStatement, getVehicleStatement, allTIStatement,
            addTIStatement, getIDForTIStatement, updateTIStatement, getEmployeeWithUserName;

    protected void prepareStatements() {
        try {
            allTIStatement = dbConnection.getSession().prepareStatement("SELECT * FROM technical_inspection");
            addTIStatement = dbConnection.getSession().prepareStatement("INSERT INTO technical_inspection VALUES(?,?,?,?,?,?,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)");
            getIDForTIStatement = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM technical_inspection");
            getVehicleStatement = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle WHERE id=?");
            cancelTIStatement = dbConnection.getSession().prepareStatement("UPDATE technical_inspection SET status_of_technical_inspection=? WHERE id=?");
            getEmployeesInTIStatement = dbConnection.getSession().prepareStatement("SELECT * FROM employee JOIN technical_inspection_team ON employee.id = technical_inspection_team.employee_id WHERE technical_inspection_team.technical_inspection_id =?");
            getCustomerStatement = dbConnection.getSession().prepareStatement("SELECT * FROM customer WHERE id=?");
            getEmployeeWithUserName = dbConnection.getSession().prepareStatement("SELECT * FROM employee WHERE user_name=?");
            updateTIStatement = dbConnection.getSession().prepareStatement("UPDATE technical_inspection" +
                    " SET status_of_technical_inspection=?, engine_type=?, engine_tact=?, type_of_fuel=?, type_of_gearbox=?," +
                    "width=?, length=?, height=?," +
                    "places_to_sit=?, places_to_stand=?, places_to_lie_down=?," +
                    "comment=?, valid=?, price=?" +
                    "WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Customer getCustomer(int id) {
        try {
            getCustomerStatement.setInt(1, id);
            ResultSet rs = getCustomerStatement.executeQuery();
            while (rs.next()) {
                Customer klijent = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                return klijent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void addTI(TechnicalInspection technicalInspection) throws ScheduledDate {
        try {
            if(isTIScheduled(technicalInspection)) return;
            ResultSet rs = getIDForTIStatement.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            technicalInspection.setId(id);

            addTIStatement.setInt(1, technicalInspection.getId());
            addTIStatement.setString(2, technicalInspection.getDateOfInspection().toString());
            addTIStatement.setInt(3, technicalInspection.getVehicle().getId());
            addTIStatement.setInt(4, technicalInspection.getCustomer().getId());
            addTIStatement.setString(5, technicalInspection.getTypeOfTechnicalInspection());
            addTIStatement.setString(6, technicalInspection.getStatusOfTechnicalInspection());

            addTIStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isTIScheduled(TechnicalInspection technicalInspection) throws ScheduledDate {
        ArrayList<TechnicalInspection> rezultat = new ArrayList();
        try {
            ResultSet rs = allTIStatement.executeQuery();
            while (rs.next()) {
                TechnicalInspection technicalInspection1 = new TechnicalInspection(rs.getInt(1),  LocalDate.parse(rs.getString(2)), getVehicle(rs.getInt(3)), getCustomer(rs.getInt(4)), rs.getString(5), rs.getString(6));
                if(technicalInspection.equals(technicalInspection1)) {
                    throw new ScheduledDate("Vec zakazan termin");
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateTI(TechnicalInspection technicalInspection) {
        try {
            updateTIStatement.setString(1, technicalInspection.getStatusOfTechnicalInspection());
            updateTIStatement.setString(2, technicalInspection.getEngineType());
            updateTIStatement.setString(3, technicalInspection.getEngineTact());
            updateTIStatement.setString(4, technicalInspection.getTypeOfFuel());
            updateTIStatement.setString(5, technicalInspection.getTypeOfGearbox());

            updateTIStatement.setDouble(6, technicalInspection.getWidth());
            updateTIStatement.setDouble(7, technicalInspection.getLength());
            updateTIStatement.setDouble(8, technicalInspection.getHeight());

            updateTIStatement.setInt(9, technicalInspection.getPlacesToSit());
            updateTIStatement.setInt(10, technicalInspection.getPlacesToStand());
            updateTIStatement.setInt(11, technicalInspection.getPlacesToLieDown());

            updateTIStatement.setString(12, technicalInspection.getComment());
            updateTIStatement.setBoolean(13, technicalInspection.isValid());
            updateTIStatement.setDouble(14, technicalInspection.getPrice());
            updateTIStatement.setInt(15, technicalInspection.getId());

            updateTIStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vehicle getVehicle(int id) {
        try {
            getVehicleStatement.setInt(1, id);
            ResultSet rs = getVehicleStatement.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getString(9));
                return vehicle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cancelTI(TechnicalInspection technicalInspection) {

        try {
            cancelTIStatement.setString(1,"Otkazan");
            cancelTIStatement.setInt(2, technicalInspection.getId());
            cancelTIStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<TechnicalInspection> allTechnical() {
        ArrayList<TechnicalInspection> technicalInspections = new ArrayList<>();
        try {
            ResultSet rs = allTIStatement.executeQuery();
            while (rs.next()) {
                TechnicalInspection technicalInspection1 = new TechnicalInspection(rs.getInt(1), LocalDate.parse(rs.getString(2)), getVehicle(rs.getInt(3)), getCustomer(rs.getInt(4)), rs.getString(5), rs.getString(6));
                technicalInspection1.setEmployees(getEmployees(technicalInspection1));
                technicalInspections.add(technicalInspection1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  technicalInspections;
    }

    public ObservableList<TechnicalInspection> search(Customer klijent, VehicleType vehicleType, LocalDate localDate) {
        ObservableList<TechnicalInspection> technicalInspection = FXCollections.observableArrayList();
        try {
            ResultSet rs = allTIStatement.executeQuery();
            while (rs.next()) {
                TechnicalInspection technicalInspection1 = new TechnicalInspection(rs.getInt(1),  LocalDate.parse(rs.getString(2)), getVehicle(rs.getInt(3)), getCustomer(rs.getInt(4)), rs.getString(5), rs.getString(6));
                technicalInspection1.setEmployees(getEmployees(technicalInspection1));
                if(UserSession.getPrivileges() || technicalInspection1.getEmployees().contains(getEmployeeWithUserName(UserSession.getUserName())))
                    technicalInspection.add(technicalInspection1);
            }
            if(klijent == null && vehicleType == null && localDate == null) return technicalInspection;
            //klijjent
            if(klijent != null && vehicleType == null && localDate == null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getCustomer().getId() == klijent.getId())
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //tipvozila
            if(klijent == null && vehicleType != null && localDate == null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getVehicle().getType().equals(vehicleType.name()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //datum
            if(klijent == null && vehicleType == null && localDate != null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection().toString().equals(localDate.toString()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //klijent + tip vehicles
            if(klijent != null && vehicleType != null && localDate == null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getVehicle().getType().equals(vehicleType.name()) && tehnickiPregled1.getCustomer().getId() == klijent.getId())
                    .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

            //klijent + datum
            if(klijent != null && vehicleType == null && localDate != null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection()
                        .toString()
                        .equals(localDate.toString()) && tehnickiPregled1.getCustomer().getId() == klijent.getId())
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

            //tip vehicles + datum
            if(klijent == null && vehicleType != null && localDate != null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection()
                        .toString()
                        .equals(localDate.toString()) && tehnickiPregled1.getVehicle().getType().equals(vehicleType.name()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));


            //svi
            return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection()
                    .toString()
                    .equals(localDate.toString()) && tehnickiPregled1.getVehicle().getType().equals(vehicleType.name()) && tehnickiPregled1.getCustomer().getId() == klijent.getId())
                    .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    public ArrayList<Employee> getEmployees(TechnicalInspection technicalInspection) {
        try {
            ArrayList<Employee> uposlenici = new ArrayList<>();
            getEmployeesInTIStatement.setInt(1, technicalInspection.getId());
            ResultSet rs = getEmployeesInTIStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                uposlenici.add(employee);
            }
            return uposlenici;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public Employee getEmployeeWithUserName(String korisnickoIme) {
        try {
            getEmployeeWithUserName.setString(1, korisnickoIme);
            ResultSet rs = getEmployeeWithUserName.executeQuery();
            if(rs.next()) {
                Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void recordFile(File izabrani, ObservableList<TechnicalInspection> technicalInspection) {
        if(izabrani != null) {
            try {
                PrintWriter izlaz;
                izlaz = new PrintWriter(new FileWriter(izabrani));
                for (int i = 0; i < technicalInspection.size(); i++) {
                    izlaz.println(technicalInspection.get(i).getDateOfInspection() + ":"
                            + technicalInspection.get(i).getCustomer().getFirstName() + ":"
                            + technicalInspection.get(i).getCustomer().getLastName() + ":"
                            + technicalInspection.get(i).getVehicle().getType() + ":"
                            + technicalInspection.get(i).getVehicle().getBrand() + ":"
                            + technicalInspection.get(i).getVehicle().getModel() + ":"
                            + technicalInspection.get(i).getStatusOfTechnicalInspection() + ":"
                            + technicalInspection.get(i).getEmployees());
                }
                izlaz.close();
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}
