/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.util.ArrayList;
import java.util.List;
/**
 * @brief choose one from many question
 * @author drevlen
 */
public class QuestionType2 extends Question{
    QuestionType2(String question, List<String> answers, List<Double> weights) {
        super.question = question;
        super.qid = 0;
        super.typeNum = 2;
        assert answers.size() == weights.size();
        super.possibleAnswers = answers;
        weight = weights;
    }
    
    QuestionType2(String question, List<String> answers, List<Double> weights, int id) {
        this(question, answers, weights);
        super.qid = id;
    }
    
    @Override
    public List<List<Double> > getWeight(List<String> experts, List<Answer> answers){
        //parse answers to weights
        double[] correctAnswers = new double[experts.size()];
        for (int i = 0; i < experts.size(); i++) {
            for (Answer answer : answers) {
                if (answer.expertName.equals(experts.get(i))) {
                        correctAnswers[i] = getWeight(answer);
                }
            }
        }
        //diff and find min
        double min = 1;
        List<List<Double> > quality = new ArrayList<>();
        for (int i = 0; i < experts.size() - 1; i++) {
            quality.add(new ArrayList<Double>());
            for (int j = i + 1; j < experts.size(); j++)
            {
                double diff = Math.abs(correctAnswers[i] 
                        - correctAnswers[j]);
                quality.get(i).add(diff);
                if (diff != 0 && diff < min)
                    min = diff;
            }
        }
        //revert
        List<List<Double> > revertedQuality = new ArrayList<>();
        for (int i = 0; i < quality.size(); i++) {
            revertedQuality.add(new ArrayList<Double>());
            for (int j = 0; j < quality.get(i).size(); j++) 
                if (quality.get(i).get(j) != 0)
                    revertedQuality.get(i).add(1 / quality.get(i).get(j));
                else
                    revertedQuality.get(i).add(2 / min);
        }
        return revertedQuality;
    }
    
    @Override
    public List<Double> getWeightAnswers(){
        return weight;
    }
    
    private Double getWeight(Answer answer) {
        for (int i = 0; i < possibleAnswers.size(); i++) {
            if (possibleAnswers.get(i).equals(answer.value))
                return weight.get(i);
        }
        return null;
    }
    
    final private List<Double> weight;
}
