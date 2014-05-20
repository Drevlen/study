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
    
    public QuestionSystem(String name_) {
        name = name_;
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
        for (Question question : questions) {
            if (question.getQuestionId() == qidOrder.get(id))
                return question;
        }
        return null;
    }
    
    public boolean hasQid(int qid) {
        for(Question question : questions) 
            if (question.getQuestionId() == qid)
                return true;
        return false;
    }
    
    public List<Integer> getGroupedQid(Integer type) {
        List<Integer> qids = new ArrayList<>();
        for(Question question : questions) 
            if (question.getType() == type)
                qids.add(question.getQuestionId());
        return qids;
    }
    
    private String name;
    private final List<Question> questions;
    private final List<Integer> qidOrder;
}
