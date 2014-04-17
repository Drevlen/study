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
public class QuestionType7 extends Question {
    QuestionType7(String question, List<String> answers, List<Double> weights) {
        super.question = question;
        super.qid = 0;
        super.typeNum = 7;
        assert answers.size() == weights.size();
        super.possibleAnswers = answers;
        weight = weights;
    }
    
    QuestionType7(String question, List<String> answers, List<Double> weights, int id) {
        this(question, answers, weights);
        super.qid = id;
    }
    @Override
    public double getWeight(Answer answer) {
        return weight.get(possibleAnswers.indexOf(answer.value));
    }
    @Override
    public List<Double> getWeightAnswers(){
        return weight;
    }
    final private List<Double> weight;
}
