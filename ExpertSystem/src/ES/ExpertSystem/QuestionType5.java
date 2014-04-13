/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ES.ExpertSystem;

import java.util.List;

/**
 *
 * @author drevlen
 */
public class QuestionType5 extends Question{
        QuestionType5(String question, List<String> answers) {
        super.question = question;
        super.qid = 0; // TODO useDBConnection
        super.typeNum = 5;
        super.possibleAnswers = answers;
    }
}
