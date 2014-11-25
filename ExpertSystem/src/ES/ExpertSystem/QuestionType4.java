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
public class QuestionType4 extends Question {
    QuestionType4(String question, String correctAnswer){
        super.question = question;
        super.qid = 0;
        super.typeNum = 4;     
        super.correctAnswer = correctAnswer;
    }
    
    QuestionType4(String question, String correctAnswer, int id){
        this(question, correctAnswer);
        super.qid = id; 
    }
    @Override
    public boolean isCorrect(String answer){
        String delims = "_";
        String[] parsedAnswers = correctAnswer.split(delims);
        double doubleAnswer = Double.parseDouble(answer);
        return Double.parseDouble(parsedAnswers[1]) <=  doubleAnswer
                && Double.parseDouble(parsedAnswers[2]) >= doubleAnswer;
    }
    @Override
    public List<List<Double> > getWeight(List<String> experts, List<Answer> answers){
        //parse answers to weights
        double[] correctAnswers = new double[experts.size()];
        for (int i = 0; i < experts.size(); i++) {
            for (Answer answer : answers) {
                if (answer.expertName.equals(experts.get(i))) {
                        correctAnswers[i] = Integer.parseInt(answer.value);
                }
            }
        }
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        //find min find max
        for (int i = 0; i < experts.size(); i++) {
            if (min > correctAnswers[i])
                min = correctAnswers[i];
            if (max < correctAnswers[i])
                max = correctAnswers[i];
        }
        max = max - min;
        //diff and find min
        min = Double.MAX_VALUE;
        List<List<Double> > quality = new ArrayList<>();
        for (int i = 0; i < experts.size() - 1; i++) {
            quality.add(new ArrayList<Double>());
            for (int j = i + 1; j < experts.size(); j++)
            {
                double diff = Math.abs(correctAnswers[i] 
                        - correctAnswers[j]) / max;
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
        return null;
    }
    @Override
    public void changeWeight(String answer, double score){
        String delims = "_";
        String[] parsedAnswers = correctAnswer.split(delims);
        double correct = Math.abs(Double.parseDouble(answer) 
                - Double.parseDouble(parsedAnswers[0])) 
                / Math.abs(Double.parseDouble(parsedAnswers[1]) 
                        - Double.parseDouble(parsedAnswers[2]));
        if (correct < 0.5)
            super.weight = super.weight * score;
        else
            super.weight = super.weight + score * (1 - super.weight);
    }
}
