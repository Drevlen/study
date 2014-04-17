/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author drevlen
 */
public class QuestionSystem {
    public QuestionSystem() {
        name = "empty question system";
        qidOrder = new ArrayList();
        questions = new ArrayList();
    }
    public void setName(String newName){
        name = newName;
    }
    public String getName(){
        return name;
    }
    public int getSize() {
        return questions.size();
    }
    public void addQuestion(Question question) {
        questions.add(question);
        qidOrder.add(question.getQuestionId());
    }
    public Question getQuestion(int id){
        return questions.get(qidOrder.get(id));
    }
    private String name;
    private List<Question> questions;
    private List<Integer> qidOrder;
}
