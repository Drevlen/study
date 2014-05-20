/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
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
            return "Помилка з’єднання з базою " 
                    + ex.getSQLState() + " " + ex.getMessage();
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
            return "Помилка з’єднання з базою " 
                    + ex.getSQLState() + " " + ex.getMessage();
        }
        return "Невірний логін або пароль";
    }
    
    public String createQuestionSystem(String name) {
        if (name.isEmpty())
            return "Назва пуста";
        if (currentUser == null)
            return "Власник опитування невідомий. Увійдіть!";
        if (questionSystem.getSize() == 0)
            return "Жодного питання не додано";
        try {
            if (db.getAllSystems().contains(name))
                return "Опитування з такою назвою вже існує";
            questionSystem.setName(name);
            if (db.addSystem(questionSystem, currentUser.getName()))
                return "Опитування "+ name +" додано";
            
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return "Помилка з’єднання з базою " 
                    + ex.getSQLState() + " " + ex.getMessage();
        }
        
        return "Додавання не здійснене";
    }

    public String addQuestion(String question, int type, 
            List<String> answers, List<String> weights) {
        if (question.isEmpty())
            return "Помилка. Питання не заповнене.";
        if (type > 0 && type != 3 && type != 4) {
            if (answers.isEmpty())
                return "Помилка. Не вказано жодного варіанту відповіді.";
            if (weights.isEmpty())
                return "Помилка. Не вказано ваги варіантів відповіді.";
        }
            
        List<Double> doubleWeights = new ArrayList();
        for (String weight : weights) {
            doubleWeights.add(Double.parseDouble(weight));
        }
        Question anyQuestion;
        switch(type){
            case 0:
                anyQuestion = new QuestionType1(question, questionSystem.getSize()); break;
            case 1:
                anyQuestion = new QuestionType2(question, answers, 
                        doubleWeights, questionSystem.getSize()); break;
            case 2:
                anyQuestion = new QuestionType3(question, answers, 
                        doubleWeights, questionSystem.getSize()); break;
            case 3:
                anyQuestion = new QuestionType4(question, questionSystem.getSize()); break;
            case 4:
                anyQuestion = new QuestionType5(question, questionSystem.getSize()); break;
            case 5:
                anyQuestion = new QuestionType6(question, answers, 
                        doubleWeights, questionSystem.getSize()); break;
            case 6:
                anyQuestion = new QuestionType7(question, answers, 
                        doubleWeights, questionSystem.getSize()); break;
            default: return "Невідомий тип";
        }
        questionSystem.addQuestion(anyQuestion);
        return "Додано питання " + Integer.toString(questionSystem.getSize());
    }
   
    public String addAnswer(String answer, int questionID) {
        if (currentUser == null)
            return "Експерт невідомий. Увійдіть!";
        Question question = questionSystem.getQuestion(questionID);
        if (question == null)
            return "Помилка питання не відоме.";
        try {
            db.addAnswer(currentUser.getName(), question.getQuestionId(), answer);
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return "Помилка з’єднання з базою " 
                    + ex.getSQLState() + " " + ex.getMessage();
        }
        
        return "Відповідь додано.";
    }
    
    public String selectSystem(String name) {
        try {
            if (currentUser == null)
                return "Невідомий користувач. Увійдіть.";
            questionSystem = new QuestionSystem(name);
            List<Integer> qidOrder = db.getOrder(name);
            if (qidOrder.isEmpty())
                return "Не вдалось знайти опитування";
            
            for (Integer qid : qidOrder) {
                questionSystem.addQuestion(db.getQuestion(name, qid));
            }
            
            return "Обрано опитування " + name;
            
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return "Помилка з’єднання з базою " 
                    + ex.getSQLState() + " " + ex.getMessage();
        }

    }
    
    public List<String> getAllSystemsOwned() {
        try {
            List<String> systems = db.getAllSystems(currentUser.getName());
            return systems;
            
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<String> getAllSystems() {
        try {
            List<String> systems = db.getAllSystems();
            return systems;
            
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<String> getAllExperts() {
        try {
            List<String> systems = db.getAllExpers();
            return systems;
            
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public QuestionSystem getSystem() {
        return questionSystem;
    }
    
    public void clearSystem() {
        questionSystem = null;
        questionSystem = new QuestionSystem();
    }
    
    public Expert getExpert() {
        return currentUser;
    }
    
    public List<String> getBestExpert() {
        try {
        if (questionSystem == null)
            return null;
        if (currentUser == null)
            return null;
        List<Answer> answers = db.getAnswers();
        List<String> experts = new ArrayList<>();
        for (Answer answer : answers) {
            if (!experts.contains(answer.expertName)
                    && questionSystem.hasQid(answer.qid))
                experts.add(answer.expertName);
        }

        ArrayList<String> expertsWeight = new ArrayList<>();
        if (experts.size() == 1) {
            expertsWeight.add(experts.get(0) + "_1.0");
            return expertsWeight;
        }
        if (experts.isEmpty())
            return null;
        
        
        List<Double> firstTypeAnswers = qualityByType(experts, answers, 1);
        List<Double> secondTypeAnswers = qualityByType(experts, answers, 2);
        List<Double> thirdTypeAnswers = qualityByType(experts, answers, 3);
        List<Double> fourthTypeAnswers = qualityByType(experts, answers, 4);
        List<Double> fifthTypeAnswers = qualityByType(experts, answers, 5);
        List<Double> sixthTypeAnswers = qualityByType(experts, answers, 6);
        List<Double> seventhTypeAnswers = qualityByType(experts, answers, 7);
        
        List<Integer> firstType = questionSystem.getGroupedQid(1);
        List<Integer> secondType = questionSystem.getGroupedQid(2);
        List<Integer> thirdType = questionSystem.getGroupedQid(3);
        List<Integer> fourthType = questionSystem.getGroupedQid(4);
        List<Integer> fifthType = questionSystem.getGroupedQid(5);
        List<Integer> sixthType = questionSystem.getGroupedQid(6);
        List<Integer> seventhType = questionSystem.getGroupedQid(7);
        
        int numQuestions = questionSystem.getSize();
        double[] quality = new double[experts.size()];
        //normalize 
        for (int i = 0; i < experts.size(); i++) {
            quality[i] += firstType.size() * 
                    firstTypeAnswers.get(i) / numQuestions;
            quality[i] += secondType.size() * 
                    secondTypeAnswers.get(i) / numQuestions;
            quality[i] += thirdType.size() * 
                    thirdTypeAnswers.get(i) / numQuestions;
            quality[i] += fourthType.size() * 
                    fourthTypeAnswers.get(i) / numQuestions;
            quality[i] += fifthType.size() * 
                    fifthTypeAnswers.get(i) / numQuestions;
            quality[i] += sixthType.size() * 
                    sixthTypeAnswers.get(i) / numQuestions;
            quality[i] += seventhType.size() * 
                    seventhTypeAnswers.get(i) / numQuestions;
        }    
        
        double sum = 0;
        for (Double q : quality) 
            sum += q;
        
        for (int i = 0; i < experts.size(); i++) {
            expertsWeight.add(experts.get(i) + "_" 
                    + Double.toString(quality[i] / sum));
        }
        return expertsWeight;
        
        } catch (SQLException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
List<Double> qualityByType(List<String> experts, List<Answer> answers, int type) {
    //reading
    double[][] sumMatrix = new double[experts.size()][experts.size()];
    for(int i = 0; i < questionSystem.getSize(); i++) {
        Question question = questionSystem.getQuestion(i);
        if (question.getType() != type)
            continue;
        List<Answer> relevantAnswers = new ArrayList<>();
        for (Answer answer : answers)
            if (answer.qid == question.getQuestionId())
                relevantAnswers.add(answer);
        
        List<List<Double> > questionMatrix = question.getWeight(experts, relevantAnswers);
        for (int j = 0; j < questionMatrix.size(); j++) {
            for (int k = 0; k < questionMatrix.get(j).size(); k++) {
                sumMatrix[j][j + k + 1] += questionMatrix.get(j).get(k);
            }
        }                
    }
    //normalize matrix
    double sum = 0;
    for(int i = 0; i < experts.size(); i++) 
        for (int j = 0; j < experts.size(); j++)
            sum += sumMatrix[i][j];
    if (sum == 0)
        sum = 1;
    for(int i = 0; i < experts.size(); i++) 
        for (int j = 0; j < experts.size(); j++)
            sumMatrix[i][j] = sumMatrix[i][j] / sum;
    
    //combine to experts
    List<Double> quality = new ArrayList<>();
    for(int i = 0; i < experts.size(); i++) {
        double singleQuality = 0;
        for (int j = 0; j < experts.size(); j++)
            singleQuality += sumMatrix[i][j] + sumMatrix[j][i];
        quality.add(singleQuality);
    }
    
    //normalize experts
    sum = 0;
    for (double q : quality) 
        sum += q;
    if (sum == 0)
        sum = 1;
    List<Double> normalizedQuality = new ArrayList<>();
    for (double q : quality) 
        normalizedQuality.add(q / sum);
    return normalizedQuality;
}

List<Double> qualityBySeventhType(List<String> experts, List<Answer> answers) {
     //reading
    double[][] sumMatrix = new double[experts.size()][experts.size()];
    
    for(int i = 0; i < questionSystem.getSize(); i++) {
        Question question = questionSystem.getQuestion(i);
        if (question.getType() != 7)
            continue;
        List<Answer> relevantAnswers = new ArrayList<>();
        for (Answer answer : answers)
            if (answer.qid == question.getQuestionId())
                relevantAnswers.add(answer);
        
        List<List<Double> > questionMatrix = question.getWeight(experts, relevantAnswers);
        for (int j = 0; j < questionMatrix.size(); j++) {
            for (int k = 0; k < questionMatrix.get(j).size(); k++) {
                sumMatrix[j+1][k+1] += questionMatrix.get(j).get(k);
            }
        }                
    }
    //normalize matrix
    double sum = 0;
    for(int i = 0; i < experts.size(); i++) 
        for (int j = 0; j < experts.size(); j++)
            sum += sumMatrix[i][j];
    for(int i = 0; i < experts.size(); i++) 
        for (int j = 0; j < experts.size(); j++)
            sumMatrix[i][j] = sumMatrix[i][j] / sum;
    
    //combine to experts
    List<Double> quality = new ArrayList<>();
    for(int i = 0; i < experts.size(); i++) {
        double singleQuality = 0;
        for (int j = 0; j < experts.size(); j++)
            singleQuality += sumMatrix[i][j] + sumMatrix[j][i];
        quality.add(singleQuality);
    }
    
    //normalize experts
    sum = 0;
    for (double q : quality) 
        sum += q;
    List<Double> normalizedQuality = new ArrayList<>();
    for (double q : quality) 
        normalizedQuality.add(q / sum);
    return normalizedQuality;
}

    private final DBConnection db;
    private QuestionSystem questionSystem;
    private Expert currentUser;
}
