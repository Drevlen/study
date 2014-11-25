/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;

/**
 *
 * @author drevlen
 */
public class Expert {
    public Expert(String expertName) {
        name = expertName;
        scoreCorrect = 0;
        scoreWrong = 0;
    }
    public String getName() {
        return name;
    }
    public void addScore(double questionWeight, boolean isCorrect) {
        if (isCorrect)
            scoreCorrect += questionWeight;
        else 
            scoreWrong += questionWeight;
    }
    public double getScore() {
        if (scoreCorrect > 0)
            return scoreCorrect / (scoreCorrect + scoreWrong);//5.1
        else
            return scoreCorrect;
    }
    
    private final String name;
    private double scoreCorrect;
    private double scoreWrong;
}
