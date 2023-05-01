package dao;

import exceptions.WrongVINNumber;
import exceptions.WrongRegistrationNumber;
import models.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAO extends BaseDAO{

    private PreparedStatement addVehicleStatement, getIdVehicleStatement, allVehicalesStatement,
            getVehicaleWithChassisNumberStatement, getVehicaleWithRegistration;


    protected void prepareStatements() {
        try {
            allVehicalesStatement = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle");
            addVehicleStatement = dbConnection.getSession().prepareStatement("INSERT INTO vehicle VALUES(?,?,?,?,?,?,?,?,?)");
            getIdVehicleStatement = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM vehicle");
            getVehicaleWithChassisNumberStatement = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle WHERE chassis_number=?");
            getVehicaleWithRegistration = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle WHERE registration=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Vehicle> vehicles() {
        ArrayList<Vehicle> rezultat = new ArrayList();
        try {
            ResultSet rs = allVehicalesStatement.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getString(9));
                rezultat.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void addVehicle(Vehicle vehicle) {
        try {
            ResultSet rs = getIdVehicleStatement.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            vehicle.setId(id);

            addVehicleStatement.setInt(1, vehicle.getId());
            addVehicleStatement.setString(2, vehicle.getType());
            addVehicleStatement.setString(3, vehicle.getBrand());
            addVehicleStatement.setString(4, vehicle.getModel());
            addVehicleStatement.setInt(5, vehicle.getYearOfProduction());
            addVehicleStatement.setString(6, vehicle.getRegistration());
            addVehicleStatement.setString(7, vehicle.getChassisNumber());
            addVehicleStatement.setString(8, vehicle.getColor());
            addVehicleStatement.setString(9, vehicle.getColorType());

            addVehicleStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isChassisNumberTaken(String text, Vehicle vehicle) throws WrongVINNumber {
        try {
            getVehicaleWithChassisNumberStatement.setString(1, text);
            ResultSet resultSet = getVehicaleWithChassisNumberStatement.executeQuery();
            if(resultSet.next()) {
                if(vehicle == null) throw new WrongVINNumber("VIN already exists in database");
                if(resultSet.getInt(1) == vehicle.getId() && resultSet.getString(7).equals(vehicle.getChassisNumber())) {
                    return true;
                }
                throw new WrongVINNumber("VIN already exists in database");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRegistrationTaken(String text, Vehicle vehicle) throws WrongRegistrationNumber {
        try {
            getVehicaleWithRegistration.setString(1, text);
            ResultSet resultSet = getVehicaleWithRegistration.executeQuery();
            if(resultSet.next()) {
                if(vehicle == null) throw new WrongRegistrationNumber("Registration already exists in database");
                if(resultSet.getInt(1) == vehicle.getId() && resultSet.getString(6).equals(vehicle.getChassisNumber())) {
                    return true;
                }
                throw new WrongRegistrationNumber("Registration already exists in database");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
