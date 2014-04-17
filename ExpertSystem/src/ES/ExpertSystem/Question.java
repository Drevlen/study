/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
public abstract class Question {
    Question(){
        possibleAnswers = new ArrayList<>();
    }
    public int getQuestionId() {
        return qid;
    }
    public String getQuestion(){
        return question;
    }
    public int getType(){
        return typeNum;
    }    
    public List<String> getAnswers() {
        return possibleAnswers;
    }
    public abstract double getWeight(Answer answer);

    public abstract List<Double> getWeightAnswers();
    
    protected int qid;
    protected int typeNum;
    protected String question;
    protected List<String> possibleAnswers;
}
