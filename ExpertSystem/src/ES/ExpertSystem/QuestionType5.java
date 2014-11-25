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
    QuestionType5(String question, String correctAnswer) {
        super.question = question;
        super.qid = 0;
        super.typeNum = 5;
        super.correctAnswer = correctAnswer;
    }
    
    QuestionType5(String question, String correctAnswer, int id) {
        this(question, correctAnswer);
        super.qid = id;
    }
    @Override
    public boolean isCorrect(String answer){
        String delims = "_";
        String[] parsedCorrectAnswers = correctAnswer.split(delims);
        String[] parsedAnswers = answer.split(delims);
        double from = Double.parseDouble(parsedAnswers[0]);
        double to = Double.parseDouble(parsedAnswers[1]);
        double fromCorrect = Double.parseDouble(parsedCorrectAnswers[1]);
        double toCorrect = Double.parseDouble(parsedCorrectAnswers[2]);
        double[] united = new double[2];
        double[] common = new double[2];
        united[0] = Math.min(from, fromCorrect);
        united[1] = Math.max(to, toCorrect);
        common[0] = Math.max(from, fromCorrect);
        common[1] = Math.min(to, toCorrect);
        if (common[1] - common[0] < 0)
            return false;
        return Math.abs((united[1] - united[0]) / (common[1] - common[0]) - 1.) < 0.1;
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
    @Override
    public void changeWeight(String answer, double score){
        String delims = "_";
        String[] parsedCorrectAnswers = correctAnswer.split(delims);
        String[] parsedAnswers = answer.split(delims);
        double from = Double.parseDouble(parsedAnswers[0]);
        double to = Double.parseDouble(parsedAnswers[1]);
        double fromCorrect = Double.parseDouble(parsedCorrectAnswers[1]);
        double toCorrect = Double.parseDouble(parsedCorrectAnswers[2]);
        double[] united = new double[2];
        double[] common = new double[2];
        united[0] = Math.min(from, fromCorrect);
        united[1] = Math.max(to, toCorrect);
        common[0] = Math.max(from, fromCorrect);
        common[1] = Math.min(to, toCorrect);
        
        if (Math.abs((united[1] - united[0]) / (common[1] - common[0]) - 1) <= 0.1)
            super.weight = super.weight * score;
        else
            super.weight = super.weight + score * (1 - super.weight);        
    }
}
