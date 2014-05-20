/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief choose many from many question
 * @author drevlen
 */
public class QuestionType3 extends Question {
    QuestionType3(String question, List<String> answers, List<Double> weights) {
        super.question = question;
        super.qid = 0;
        super.typeNum = 3;
        assert answers.size() == weights.size();
        super.possibleAnswers = answers;
        weight = weights;
    }
    QuestionType3(String question, List<String> answers, List<Double> weights, int id) {
        this(question, answers, weights);
        super.qid = id;
    }
    @Override
    public List<List<Double> > getWeight(List<String> experts, List<Answer> answers){
        //parse answers to weights
        double[][] correctAnswers = new double[experts.size()][possibleAnswers.size()];
        for (int i = 0; i < experts.size(); i++) {
            for (Answer answer : answers) {
                if (answer.expertName.equals(experts.get(i))) {
                    String delims = "_";
                    String[] parsedAnswers = answer.value.split(delims);
                    for(int j = 0; j < possibleAnswers.size(); j++) {
                        correctAnswers[i][j] = -1;
                        for (String parsedAnswer : parsedAnswers)
                            if (parsedAnswer.equals(possibleAnswers.get(j)))
                                correctAnswers[i][j] = weight.get(j);
                    }
                        
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
                double diff = 0;
                for(int k = 0; k < possibleAnswers.size(); k++)
                    if (correctAnswers[i][k] > 0 &&
                            correctAnswers[j][k] > 0)
                        diff += correctAnswers[i][k];
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
    final private List<Double> weight;
}
