/*
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
public class QuestionType6 extends Question {
    QuestionType6(String question, List<String> answers, List<Double> weights) {
        super.question = question;
        super.qid = 0; // TODO useDBConnection
        super.typeNum = 6;
        assert answers.size() == weights.size();
        super.possibleAnswers = answers;
        intervals = new ArrayList<>();
        weight = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            intervals.add(weights.get(4*i));
            intervals.add(weights.get(4*i+1));
            weight.add(weights.get(4*i+2));
            weight.add(weights.get(4*i+3));
        }
            
    }
    QuestionType6(String question, List<String> answers, List<Double> weights, int id) {
        this(question, answers, weights);
        super.qid = id;
    }
    @Override
    public double getWeight(Answer answer) {
        //(ml+mh)*h/2+(wh+wl)*h/4
        return 0;
    }
    @Override
    public List<Double> getWeightAnswers(){
        List<Double> container = new ArrayList<>();
        for (int i = 0; i < intervals.size(); i++) {
            container.add(intervals.get(i));
            container.add(weight.get(i));
        }
        return container;
    }
    final private List<Double> intervals;
    final private List<Double> weight;
}
