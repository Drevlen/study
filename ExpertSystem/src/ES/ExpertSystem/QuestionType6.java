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
    QuestionType6(String question, List<String> answers, List<Double> anwerWeights,
            String correctAnswer) {
        super.question = question;
        super.qid = 0; // TODO useDBConnection
        super.typeNum = 6;
        assert answers.size() == anwerWeights.size();
        super.possibleAnswers = answers;
        intervals = new ArrayList<>();
        weights = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            intervals.add(anwerWeights.get(4*i));
            intervals.add(anwerWeights.get(4*i+1));
            weights.add(anwerWeights.get(4*i+2));
            weights.add(anwerWeights.get(4*i+3));
        }
        super.correctAnswer = correctAnswer;   
    }
    
    QuestionType6(String question, List<String> answers, List<Double> weights, 
            String correctAnswer, int id) {
        this(question, answers, weights, correctAnswer);
        super.qid = id;
    }
 
    public Double parseFuzzyWord(String answer){
        String delims = "_";
        String[] parsedAnswers = answer.split(delims);
        
        Double[] fuzzyWordAnswer = new Double[4];
        
        for(int i = 0; i < possibleAnswers.size(); i++) {
            if (possibleAnswers.get(i).equals(parsedAnswers[1])) {
                fuzzyWordAnswer[0] = intervals.get(2 * i) - weights.get(2 * i);
                fuzzyWordAnswer[1] = intervals.get(2 * i);
                fuzzyWordAnswer[2] = intervals.get(2 * i + 1);
                fuzzyWordAnswer[3] = intervals.get(2 * i + 1) + weights.get(2 * i + 1);
                break;
            }
        }
        
        if (parsedAnswers[0].equals("Більш")) {
            fuzzyWordAnswer[0] += fuzzyWordAnswer[1] - fuzzyWordAnswer[0];
            fuzzyWordAnswer[3] -= fuzzyWordAnswer[3] - fuzzyWordAnswer[2];
        } else if (parsedAnswers[0].equals("Менш")) {
            fuzzyWordAnswer[0] -= fuzzyWordAnswer[1] - fuzzyWordAnswer[0];
            fuzzyWordAnswer[3] += fuzzyWordAnswer[3] - fuzzyWordAnswer[2];
        }
        
        if (parsedAnswers.length > 3)
        {
            int wordIndex = parsedAnswers.length == 5 ? 4 : 3;
            String secondModifier = parsedAnswers.length == 5 ? parsedAnswers[3] : "";
            
            Double[] secondFuzzyWordAnswer = new Double[4];
            for(int i = 0; i < possibleAnswers.size(); i++) {
                if (possibleAnswers.get(i).equals(parsedAnswers[wordIndex])) {
                    secondFuzzyWordAnswer[0] = intervals.get(2 * i) - weights.get(2 * i);
                    secondFuzzyWordAnswer[1] = intervals.get(2 * i);
                    secondFuzzyWordAnswer[2] = intervals.get(2 * i + 1);
                    secondFuzzyWordAnswer[3] = intervals.get(2 * i + 1) + weights.get(2 * i + 1);
                    break;
                }
            }
            
            if (secondModifier.equals("Більш")) {
                secondFuzzyWordAnswer[0] += secondFuzzyWordAnswer[1] 
                        - secondFuzzyWordAnswer[0];
                secondFuzzyWordAnswer[3] -= secondFuzzyWordAnswer[3] 
                        - secondFuzzyWordAnswer[2];
            } else if (secondModifier.equals("Менш")) {
                secondFuzzyWordAnswer[0] -= secondFuzzyWordAnswer[1] 
                        - secondFuzzyWordAnswer[0];
                secondFuzzyWordAnswer[3] += secondFuzzyWordAnswer[3] 
                        - secondFuzzyWordAnswer[2];
            }

            if (Math.max(fuzzyWordAnswer[1], secondFuzzyWordAnswer[1]) 
                    <= Math.min(fuzzyWordAnswer[2], secondFuzzyWordAnswer[2])) {
                
                if (parsedAnswers[2].equals("І")) {
                    fuzzyWordAnswer[0] = Math.min(fuzzyWordAnswer[0], 
                            secondFuzzyWordAnswer[0]);
                    fuzzyWordAnswer[3] = Math.max(fuzzyWordAnswer[3], 
                            secondFuzzyWordAnswer[3]);
                    fuzzyWordAnswer[1] = Math.max(fuzzyWordAnswer[1], 
                            secondFuzzyWordAnswer[1]);
                    fuzzyWordAnswer[2] = Math.min(fuzzyWordAnswer[2], 
                            secondFuzzyWordAnswer[2]);
                } else if (parsedAnswers[2].equals("Або")) {
                    fuzzyWordAnswer[0] = Math.min(fuzzyWordAnswer[0], 
                            secondFuzzyWordAnswer[0]);
                    fuzzyWordAnswer[3] = Math.max(fuzzyWordAnswer[3], 
                            secondFuzzyWordAnswer[3]);
                    fuzzyWordAnswer[1] = Math.min(fuzzyWordAnswer[1], 
                            secondFuzzyWordAnswer[1]);
                    fuzzyWordAnswer[2] = Math.max(fuzzyWordAnswer[2], 
                            secondFuzzyWordAnswer[2]);
                    
                }
            }  
        }
        
        return (fuzzyWordAnswer[1] + fuzzyWordAnswer[2]) / 2
                + ((fuzzyWordAnswer[3] - fuzzyWordAnswer[2]) 
                    - (fuzzyWordAnswer[1] - fuzzyWordAnswer[0])
                ) / 4;
    }
    @Override
    public boolean isCorrect(String answer){
        String delims = "_";
        String[] parsedAnswers = correctAnswer.split(delims);
        double doubleAnswer = parseFuzzyWord(answer);
        return Double.parseDouble(parsedAnswers[1]) <=  doubleAnswer
                && Double.parseDouble(parsedAnswers[2]) >= doubleAnswer;
    }
    @Override
    public List<List<Double> > getWeight(List<String> experts, List<Answer> answers){
        //parse answers to weights
        double[] correctAnswers = new double[experts.size()];
        for (int i = 0; i < experts.size(); i++) 
            for (Answer answer : answers) 
                if (answer.expertName.equals(experts.get(i))) {
                        correctAnswers[i] = parseFuzzyWord(answer.value);
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
            for (int j = i + 1; j < experts.size(); j++) {
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
        List<Double> container = new ArrayList<>();
        for (int i = 0; i < intervals.size(); i++) {
            container.add(intervals.get(i));
            container.add(weights.get(i));
        }
        return container;
    }
    @Override
    public void changeWeight(String answer, double score){
        String delims = "_";
        String[] parsedAnswers = correctAnswer.split(delims);
        double correct = Math.abs(parseFuzzyWord(answer)
                - Double.parseDouble(parsedAnswers[0])) 
                / Math.abs(Double.parseDouble(parsedAnswers[1]) 
                        - Double.parseDouble(parsedAnswers[2]));
        if (correct < 0.5)
            super.weight = super.weight * score;
        else
            super.weight = super.weight + score * (1 - super.weight);
    }
    final private List<Double> intervals;
    final private List<Double> weights;
}
