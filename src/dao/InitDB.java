package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InitDB {
    protected DBConnection dbConnection;

    public InitDB() {
        dbConnection = DBConnection.getInstance();
    }

    public void delete() throws SQLException {
        Statement stmt = dbConnection.getSession().createStatement();
        stmt.executeUpdate("DELETE FROM employee");
        stmt.executeUpdate("DELETE FROM vehicle");
        stmt.executeUpdate("DELETE FROM customer");
        stmt.executeUpdate("DELETE FROM technical_inspection_team");
        stmt.executeUpdate("DELETE FROM technical_inspection");
    }

    public void createDB() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = dbConnection.getSession().createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
