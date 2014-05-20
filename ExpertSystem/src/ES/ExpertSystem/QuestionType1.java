/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;

import java.util.List;
import java.util.ArrayList;

/**
 * @brief yes no questions
 * @author drevlen
 */
public class QuestionType1 extends Question {
    QuestionType1(String question){
        super.question = question;
        super.qid = 0;
        super.typeNum = 1;     
    }
    QuestionType1(String question, int id){
        this(question);
        super.qid = id;
    }
    
    @Override
    public List<Double> getWeightAnswers(){
        return null;
    }
    
    @Override
    public List<List<Double> > getWeight(List<String> experts, List<Answer> answers){
        int[] correctAnswers = new int[experts.size()];
        for (int i = 0; i < experts.size(); i++) {
            for (Answer answer : answers) {
                if (answer.expertName.equals(experts.get(i))) {
                    if (answer.value.equals("Так"))
                        correctAnswers[i] = 1;
                    else
                        correctAnswers[i] = 0;
                }
            }
        }
        List<List<Double> > quality = new ArrayList<>();
        for (int i = 0; i < experts.size() - 1; i++) {
            quality.add(new ArrayList<Double>());
            for (int j = i + 1; j < experts.size(); j++) 
                if (correctAnswers[i] == correctAnswers[j])
                    quality.get(i).add(1.0);
                else
                    quality.get(i).add(0.0);
        }
        return quality;
    }
}
