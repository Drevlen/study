/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
/**
 *
 * @author drevlen
 */
public class DBConnection {
    private Connection connect() throws SQLException {
        Properties properties=new Properties();
        properties.setProperty("user","SomeExpert");
        properties.setProperty("password","Bdq4x5tr8Tsz7A9B");
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/QuestionSystem", properties);
    }
        
    public String getExpert(String name, String password)
    throws SQLException {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT name FROM `Experts` WHERE name='" 
                    + name + "' AND password='" + password + "'";
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                String Name = results.getString("name");
                connection.close();
                return Name;
            }
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
        
    public List<String> getAllSystems()
    throws SQLException {
        List<String> expertNames = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT name FROM `QuestionSystems`";
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                expertNames.add(results.getString("name"));
            }
            connection.close();
        }
        return expertNames;
    }

    public List<String> getAllSystems(String owner)
    throws SQLException {
        List<String> expertNames = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT name FROM `QuestionSystems`"
                                        + "WHERE creatorName='" + owner + "'";
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                expertNames.add(results.getString("name"));
            }
            connection.close();
        }
        return expertNames;
    }
    
    public List<Answer> getAnswers() 
    throws SQLException {
        List<Answer> expertNames = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM `Answers`";
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                expertNames.add(new Answer(results.getInt("qid"),
                results.getString("expertName"), results.getString("value")));
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
            if (statement.executeUpdate(insert) > 0) {
                connection.close();
                return true;
            }
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
                if (question == null)
                    return false;
                insert = "INSERT INTO `Questions`(`question`, `type`, `system`, `correct`, `weight`) "
                        + "VALUES ('" + question.getQuestion() + "', '" 
                        + Integer.toString(question.getType()) + "','" 
                        + system.getName()+ "','" + question.getCorrectAnswer() 
                        + "','" + Double.toString(question.getQuestionWeight())
                        + "')";
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
    
    public boolean addAnswer(String expertName, 
            int questionId, String answer) throws SQLException {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String insert = "INSERT INTO `Answers`(`qid`, `expertName`, `value`) "
                    + "VALUES ('" + questionId + "', '" + expertName
                    + "', '" + answer + "')";
            if (statement.executeUpdate(insert) > 0) {
                connection.close();
                return true;
            }
        }
        return false;
    }

    
    public List<Integer> getOrder(String name) 
    throws SQLException {
        List<Integer> order = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT questionOrder FROM `QuestionSystems` "
                    + "WHERE name='" + name + "'";
            ResultSet results = statement.executeQuery(select);
            String notParsedOrder = "";
            while (results.next()) {
                notParsedOrder = results.getString("questionOrder");
            }
            String delims = "[ ]";
            String[] tokens = notParsedOrder.split(delims);
            for (String token : tokens) {
                order.add(Integer.parseInt(token));
            }
            connection.close();
        }
        return order;
    }
    
    public Question getQuestion(String systemName, int qid)
    throws SQLException {
        Question question;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM `Questions` "
                    + "WHERE qid='" + Integer.toString(qid) + "' AND "
                    + "system='" + systemName + "'";
            ResultSet results = statement.executeQuery(select);
            int type = 0;
            String questionString = null;
            String correctAnswer = null;
            double weight = 0;
            while (results.next()) {
                type = results.getInt("type");
                questionString = results.getString("question");
                correctAnswer = results.getString("correct");
                weight = results.getDouble("weight");
            }
            connection.close();
            if (questionString == null)
                return null;
            switch (type) {
                case 1:
                    question = new QuestionType1(questionString, correctAnswer, qid);
                    break;
                case 2:
                    question = setQuestion2(questionString, correctAnswer, qid);
                    break;
                case 3:
                    question = setQuestion3(questionString, correctAnswer, qid);
                    break;
                case 4:
                    question = new QuestionType4(questionString, correctAnswer, qid);
                    break;
                case 5:
                    question = new QuestionType5(questionString, correctAnswer, qid);
                    break;
                case 6:
                    question = setQuestion6(questionString, correctAnswer, qid);
                    break;
                case 7:
                    question = setQuestion7(questionString, correctAnswer, qid);
                    break;
                default:
                    return null;
            }
            question.setQuestionWeight(weight);
        }
        return question;
    }

    private Question setQuestion2(String questionString, String correctAnswer, int qid)
    throws SQLException {
        Question question;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM `Questions2` "
                    + "WHERE qid='" + Integer.toString(qid) + "'";
            ResultSet results = statement.executeQuery(select);
            List<String> answers = new ArrayList<>();
            List<Double> weights = new ArrayList<>();
            while (results.next()) {
                answers.add(results.getString("answer"));
                weights.add(results.getDouble("weight"));
            }
            connection.close();
            question = new QuestionType2(questionString, answers, weights, correctAnswer, qid);
        }
        return question;
    }
    
    private Question setQuestion3(String questionString, String correctAnswer, int qid)
    throws SQLException {
        Question question;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM `Questions3` "
                    + "WHERE qid='" + Integer.toString(qid) + "'";
            ResultSet results = statement.executeQuery(select);
            List<String> answers = new ArrayList<>();
            List<Double> weights = new ArrayList<>();
            while (results.next()) {
                answers.add(results.getString("answer"));
                weights.add(results.getDouble("weight"));
            }
            connection.close();
            question = new QuestionType3(questionString, answers, weights, correctAnswer, qid);
        }
        return question;
    }
    
    private Question setQuestion6(String questionString, String correctAnswer, int qid)
    throws SQLException {
        Question question;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM `Questions6` "
                    + "WHERE qid='" + Integer.toString(qid) + "'";
            ResultSet results = statement.executeQuery(select);
            List<String> answers = new ArrayList<>();
            List<Double> weights = new ArrayList<>();
            while (results.next()) {
                answers.add(results.getString("answer"));
                weights.add(results.getDouble("intervalFrom"));
                weights.add(results.getDouble("intervalTo"));
                weights.add(results.getDouble("weightFrom"));
                weights.add(results.getDouble("weightTo"));
            }
            connection.close();
            question = new QuestionType6(questionString, answers, weights, correctAnswer, qid);
        }
        return question;
    }    
    
    private Question setQuestion7(String questionString, String correctAnswer, int qid)
    throws SQLException {
        Question question;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM `Questions7` "
                    + "WHERE qid='" + Integer.toString(qid) + "'";
            ResultSet results = statement.executeQuery(select);
            List<String> answers = new ArrayList<>();
            List<Double> weights = new ArrayList<>();
            while (results.next()) {
                answers.add(results.getString("answer"));
                weights.add(results.getDouble("weight"));
            }
            connection.close();
            question = new QuestionType7(questionString, answers, weights, correctAnswer, qid);
        }
        return question;
    }

    public boolean setWeight(Question question) 
            throws SQLException {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            String update = "UPDATE `Questions` SET `weight`= " 
                    + question.getQuestionWeight()
                    + "WHERE qid='" + Integer.toString(question.qid) + "'";
            if (statement.executeUpdate(update) > 0) {
                connection.close();
                return true;
            }
        }
        return false;
    }
}
