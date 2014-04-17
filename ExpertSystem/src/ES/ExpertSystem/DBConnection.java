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
    public boolean addSystem(QuestionSystem system, String owner)
    throws SQLException {
        try (Connection connection = connect()) {
            String newQidOrder = "";
            Question question;
            String insert;
            Statement statement = connection.createStatement();
            for (int i = 0; i < system.getSize(); i++) {
                question = system.getQuestion(i);
                insert = "INSERT INTO `Questions`(`question`, `type`, `system`) "
                        + "VALUES ('" + question.getQuestion() + "', '" 
                        + Integer.toString(question.getType()) + "','" 
                        + system.getName() +"')";
                PreparedStatement preparedStatement = connection.prepareStatement(
                        insert, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.executeUpdate();  
                ResultSet rs = preparedStatement.getGeneratedKeys();  
                rs.next();  
                newQidOrder += Integer.toString(rs.getInt(1)) + " ";
                
                List<String> answers = question.getAnswers();
                List<Double> weights = question.getWeightAnswers();
                for (int j = 0; j < answers.size(); j++) {
                    switch(question.getType()){
                        case 1:
                        case 4:
                        case 5://we shouldnt be here
                            break;
                        case 2:
                            insert = "INSERT INTO `Questions2`(`qid`,`answer`,`weight`) "
                            + "VALUES ('" + rs.getInt(1) + "', '" 
                            + answers.get(j) + "', '" + weights.get(j) +"')"; 
                            statement.executeUpdate(insert); break;
                        case 3:
                            insert = "INSERT INTO `Questions3`(`qid`,`answer`,`weight`) "
                            + "VALUES ('" + rs.getInt(1) + "', '" 
                            + answers.get(j) + "','" + weights.get(j) +"')"; 
                            statement.executeUpdate(insert); break;
                        case 6:
                            insert = "INSERT INTO `Questions6`(`qid`,`answer`, "
                            + "`intervalFrom`,`intervalTo`,`weightFrom`,`weightTo`)"
                            + "VALUES ('" + rs.getInt(1) + "', '" + answers.get(j) 
                            + "','" + weights.get(4*j) + "','" + weights.get(4*j+2)
                            + "','" + weights.get(4*j+1) + "','" + weights.get(4*j+3)        
                            +"')"; 
                            statement.executeUpdate(insert); break;
                        case 7:
                            insert = "INSERT INTO `Questions7`(`qid`,`answer`,`weight`) "
                            + "VALUES ('" + rs.getInt(1) + "', '" 
                            + answers.get(j) + "','" + weights.get(j) +"')"; 
                            statement.executeUpdate(insert); break;
                    }
                }
            }
            insert = "INSERT INTO `QuestionSystems`(`name`, `creatorName`, "
                    + "`questionOrder`) VALUES ('" + system.getName() + "', '" 
                    + owner + "','" + newQidOrder +"')";
            statement.executeUpdate(insert);
                        
            connection.close();
            return true;
        }
    }
    
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/QuestionSystem", 
                "SomeExpert", "Bdq4x5tr8Tsz7A9B");
    }
}
