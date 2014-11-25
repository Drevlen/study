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
        name = "empty question system";
        qidOrder = new ArrayList();
        questions = new ArrayList();
        isCorrectAnswers = new ArrayList();
        answers = new ArrayList();
        correctNumber = 0;
        questionPosition = 0;
    }
    
    public QuestionSystem(String name_) {
        name = name_;
        qidOrder = new ArrayList();
        questions = new ArrayList();
        isCorrectAnswers = new ArrayList();
        answers = new ArrayList();
        correctNumber = 0;
        questionPosition = 0;
    }
    
    public void setName(String newName){
        name = newName;
    }
    public String getName(){
        return name;
    }
    public int getSize() {
        return questions.size();
    }
    public void addQuestion(Question question) {
        int i;
        for (i = 0; i < questions.size(); i++)
            if (question.getQuestionWeight() < questions.get(i).getQuestionWeight())
                break;
        questions.add(i, question);
        qidOrder.add(i, question.getQuestionId());
        isCorrectAnswers.add(false);
        questionPosition = qidOrder.size() / 2;
    }
    
    public Question getQuestion(int id){
        for (Question question : questions) 
            if (question.getQuestionId() == qidOrder.get(id))
                return question;
        return null;
    }
    
    public Question getQuestion(){
        for (Question question : questions) 
            if (question.getQuestionId() == qidOrder.get(questionPosition))
                return question;
        return null;
    }
    
    public List<Question> changeWeights(Expert expert){
        for (Answer answer : answers) {
            for (Question question : questions)
                if (answer.qid == question.getQuestionId())
                    question.changeWeight(answer.value, expert.getScore());
        }
        return questions;
    }
    
    public boolean hasQid(int qid) {
        for(Question question : questions) 
            if (question.getQuestionId() == qid)
                return true;
        return false;
    }
    
    public List<Integer> getGroupedQid(Integer type) {
        List<Integer> qids = new ArrayList<>();
        for(Question question : questions) 
            if (question.getType() == type)
                qids.add(question.getQuestionId());
        return qids;
    }
    
    public void addAnswer(Answer answer, boolean isCorrect){
        answers.add(answer);
        isCorrectAnswers.set(questionPosition, isCorrect);
        if (isCorrect) {
            correctNumber++;
            questionPosition += (qidOrder.size() - questionPosition) * 
                    ((correctNumber) / (answers.size())) / 2;
        } else {
            questionPosition -= questionPosition * 
                    ((answers.size() - correctNumber) / (answers.size())) / 2;
        }
        for(; questionPosition < questions.size(); questionPosition++)
            if (!isAnswered(qidOrder.get(questionPosition)))
                break;
        if (questionPosition == questions.size())
            for(questionPosition--; questionPosition >= 0; questionPosition--)
                if (!isAnswered(qidOrder.get(questionPosition)))
                    break;
    }
    
    public boolean isAnswered(int qid){
        for (Answer givenAnswer : answers)
                if (qid == givenAnswer.qid)
                    return true;
        return false;
    }

    public boolean isFinished(Expert expert){
        if (questionPosition < 0 || questionPosition >= qidOrder.size())
            return true;
        boolean isLowest = true;
        for (int i = 0; i < isCorrectAnswers.size() && i <= 3; i++)
            if (isCorrectAnswers.get(i) || !isAnswered(i))
                isLowest = false;
        boolean isHighest = true;
        for (int i = 0; i < isCorrectAnswers.size() && i <= 3; i++)
            if (!isCorrectAnswers.get(isCorrectAnswers.size() - i - 1))
                isHighest = false;
        return isHighest || isLowest;
    }
    
    private String name;
    private final List<Question> questions;
    private final List<Integer> qidOrder;
    private final List<Answer> answers;
    private final List<Boolean> isCorrectAnswers;
    private int questionPosition;
    private int correctNumber;
}
