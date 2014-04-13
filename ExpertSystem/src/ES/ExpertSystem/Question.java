/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
public class Question {
    public int getQuestionId() {
        return qid;
    }
    protected int qid;
    protected int typeNum;
    protected String question;
    protected List<String> possibleAnswers;
}
