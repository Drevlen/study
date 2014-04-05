/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.util.List;

/**
 * @brief choose many from many question
 * @author drevlen
 */
public class QuestionType3 extends Question {
        QuestionType3(String question, List<String> answers, List<Double> weights) {
        super.question = question;
        super.qid = 0; // TODO useDBConnection
        super.typeNum = 2;
        assert answers.size() == weights.size();
        super.possibleAnswers = answers;
        weight = weights;
    }
    public double getWeight(Answer answer) {
        assert answer.qid == qid;
        double result = 0;
        for (int i = 0; i < possibleAnswers.size(); i++) {
            if (possibleAnswers.get(i).equals(answer.value)) 
                result += weight.get(i);
        }
        assert false;
        return result;
    }
    final private List<Double> weight;
}
