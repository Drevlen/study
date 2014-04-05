/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author drevlen
 */
public class ExpertSystem {
    
    public ExpertSystem() {
        db = new DBConnection();
        questionSystem = new QuestionSystem();
    }
    
    public String login(String name, String password) {
        try {
            if (name.equals(db.getExpert(name, password))) {
                currentUser = new Expert(name);
                return "Вітаю " + name;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return "Помилка з’єднання з базою " + ex.getSQLState();
        }
        return "Невірний логін або пароль";
    }
    
    public String addExpert(String name, String password) {
        if (name.isEmpty())
            return "Реєстрація неуспішна, ім’я користувача не вказане.";
        if (password.length() < 6)
            return "Реєстрація неуспішна, пароль менший шести символів.";
        
        try {
            List<String> allExperts = db.getAllExpers();
            if (allExperts.contains(name)) {
                return "Користувач з таким іменем уже існує";
            } else {
                if (db.addExpert(name, password)) {
                    return "Користувач " + name + " доданий. Тепер ви може увійти.";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return "Помилка з’єднання з базою " + ex.getSQLState();
        }
        return "Невірний логін або пароль";
    }
    
    public String createQuestionSystem(String name) {
        if (name.isEmpty())
            return "Назва пуста";
        if (questionSystem.getSize() == 0)
            return "Жодного питання не додано";
        try {
            if (db.getAllSystems().contains(name))
                return "Опитування з такою назвою вже існує";
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return "Помилка з’єднання з базою " + ex.getSQLState();
        }
        
        return "Додавання не реалізоване";
    }
    
    public void addQuestion(Question anyQuestion) {
        
    }
   
    private final DBConnection db;
    private List<String> availableQuestionSystems;
    private QuestionSystem questionSystem;
    private Expert currentUser;
    
        
}
