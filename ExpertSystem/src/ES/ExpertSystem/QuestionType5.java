/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;

import java.util.List;

/**
 *
 * @author drevlen
 */
public class QuestionType5 extends Question{
    QuestionType5(String question) {
        super.question = question;
        super.qid = 0;
        super.typeNum = 5;
    }
    
    QuestionType5(String question, int id) {
        this(question);
        super.qid = id;
    }
    @Override
    public double getWeight(Answer answer) {
        //TODO weight of interval
        return Double.parseDouble(answer.value);
    }
    @Override
    public List<Double> getWeightAnswers(){
        return null;
    }
}
