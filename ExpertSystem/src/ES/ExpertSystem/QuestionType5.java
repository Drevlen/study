/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;

import java.util.ArrayList;
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
    public List<List<Double> > getWeight(List<String> experts, List<Answer> answers){
        //parse answers to weights
        double[][] correctAnswers = new double[experts.size()][2];
        for (int i = 0; i < experts.size(); i++) {
            for (Answer answer : answers) {
                if (answer.expertName.equals(experts.get(i))) {
                    String delims = "_";
                    String[] parsedAnswers = answer.value.split(delims);
                    for(int j = 0; j < 2; j++) {
                        correctAnswers[i][j] = Double.parseDouble(parsedAnswers[j]);
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
                if (Math.min(correctAnswers[i][1], correctAnswers[j][1]) >= 
                        Math.max(correctAnswers[i][0], correctAnswers[j][0]))
                    diff = (Math.min(correctAnswers[i][1], correctAnswers[j][1])
                         - Math.max(correctAnswers[i][0], correctAnswers[j][0])) 
                         * (1 / (correctAnswers[i][1] - correctAnswers[i][0])
                         + 1 / (correctAnswers[j][1] - correctAnswers[j][0])) 
                         / 2;
                quality.get(i).add(diff);
                if (diff != 0 && diff < min)
                    min = diff;
            }
        }
        return quality;
    }
    @Override
    public List<Double> getWeightAnswers(){
        return null;
    }
}
