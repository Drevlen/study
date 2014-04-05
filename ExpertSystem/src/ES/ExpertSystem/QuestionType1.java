/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;

/**
 * @brief yes no questions
 * @author drevlen
 */
public class QuestionType1 extends Question {
    QuestionType1(String question){
        super.question = question;
        super.qid = 0; // TODO useDBConnection
        super.typeNum = 1;
        super.possibleAnswers.add("Так");
        super.possibleAnswers.add("Ні");        
    }
    
    public double getWeight(Answer answer) {
        assert answer.qid == qid;
        if ("Так".equals(answer.value)) {
            return 1;
        }
        if ("Ні".equals(answer.value)) {
            return 0;
        }
        assert false;
        return -1;
    }
}
