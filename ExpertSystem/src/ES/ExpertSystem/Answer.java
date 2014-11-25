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
public class Answer {
    Answer (int questionID, String name, String answer) {
        qid = questionID;
        expertName = name;
        value = answer;
    }
    final public int qid;
    final public String expertName;
    final public String value;
}
