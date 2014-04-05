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
public class QuestionSystem {
    public QuestionSystem() {
        qidOrder = new ArrayList();
    }
    public int getSize() {
        return qidOrder.size();
    }
    private List<Integer> qidOrder;
}
