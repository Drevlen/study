/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author drevlen
 */
public class DBConnection {
    public String getExpert(String name, String password)
    throws SQLException {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT name FROM `Experts` WHERE name='" 
                    + name + "' AND password='" + password + "'";
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                return results.getString("name");
            }
            connection.close();
        }
        return null;
    }
    public List<String> getAllExpers()
    throws SQLException {
        List<String> expertNames = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT name FROM `Experts`";
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                expertNames.add(results.getString("name"));
            }
            connection.close();
        }
        return expertNames;
    }
        
    public List<String> getAllSystems() //TODO for systems
    throws SQLException {
        List<String> expertNames = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT name FROM `Experts`";
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                expertNames.add(results.getString("name"));
            }
            connection.close();
        }
        return expertNames;
    }
    public boolean addExpert(String name, String password)
    throws SQLException {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String insert = "INSERT INTO `Experts`(`name`, `password`) "
                    + "VALUES ('" + name + "', '" + password + "')";
            if (statement.executeUpdate(insert) > 0)
                return true;
            connection.close();
        }
        return false;
    }
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/QuestionSystem", 
                "SomeExpert", "Bdq4x5tr8Tsz7A9B");
    }
}
