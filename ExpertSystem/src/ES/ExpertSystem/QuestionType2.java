/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.util.List;
/**
 * @brief choose one from many question
 * @author drevlen
 */
public class QuestionType2 extends Question{
    QuestionType2(String question, List<String> answers, List<Double> weights) {
        super.question = question;
        super.qid = 0; // TODO useDBConnection
        super.typeNum = 2;
        assert answers.size() == weights.size();
        super.possibleAnswers = answers;
        weight = weights;
    }
    public double getWeight(Answer answer) {
        assert answer.qid == qid;
        for (int i = 0; i < possibleAnswers.size(); i++) {
            if (possibleAnswers.get(i).equals(answer.value)) 
                return weight.get(i);
        }
        assert false;
        return -1;
    }
    final private List<Double> weight;
}
