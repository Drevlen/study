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
    public String getCorrectAnswer(){
        return correctAnswer;
    }
    public double getQuestionWeight(){
        return weight;
    }
    public void setQuestionWeight(double newWeight){
        assert newWeight < 0 || newWeight > 1;
        weight = newWeight;
    }
    public int getType(){
        return typeNum;
    }    
    public List<String> getAnswers() {
        return possibleAnswers;
    }
    public void setCorrectAnswer(String answer) {
        correctAnswer = answer;
    }
    public abstract List<List<Double> > getWeight(List<String> experts, List<Answer> answers);

    public abstract List<Double> getWeightAnswers();
    
    public abstract boolean isCorrect(String answer);
    
    public abstract void changeWeight(String answer, double score);
    
    protected int qid;
    protected int typeNum;
    protected String question;
    protected List<String> possibleAnswers;
    protected String correctAnswer;
    protected double weight;
}
