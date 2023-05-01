package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicalInspectionTeamDAO extends BaseDAO{
    private PreparedStatement connectTIStatement, countTIStatement;

    @Override
    protected void prepareStatements() {
        try {
            connectTIStatement = dbConnection.getSession().prepareStatement("INSERT INTO technical_inspection_team VALUES(?,?)");
            countTIStatement = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM technical_inspection_team JOIN technical_inspection ON technical_inspection_team.technical_inspection_id = technical_inspection.id WHERE technical_inspection_team.employee_id=? AND technical_inspection.status_of_technical_inspection=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //broj kompletiranih
    public int countcompletedTI(int employeeID) {
        try {
            countTIStatement.setInt(1, employeeID);
            countTIStatement.setString(2, "Kompletiran");
            ResultSet resultSet = countTIStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //broj zakazanih
    public int countScheduledTI(int employeeID) {
        try {
            countTIStatement.setInt(1, employeeID);
            countTIStatement.setString(2, "Zakazan");
            ResultSet resultSet = countTIStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //broj otkazanih
    public int countCanceledTI(int employeeID) {
        try {
            countTIStatement.setInt(1, employeeID);
            countTIStatement.setString(2, "Otkazan");
            ResultSet resultSet = countTIStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void connectTIAndEmployee(int technicalID, int employeeID) {
        try {
            connectTIStatement.setInt(1,technicalID);
            connectTIStatement.setInt(2,employeeID);
            connectTIStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
