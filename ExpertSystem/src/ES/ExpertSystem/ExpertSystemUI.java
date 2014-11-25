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


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author drevlen
 */
public class ExpertSystemUI extends javax.swing.JFrame implements ActionListener, DocumentListener, ChangeListener {

    /**
     * Creates new form ExpertSystemUI
     */
    public ExpertSystemUI() {
        es = new ExpertSystem();
        initComponents();
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        statusLabel.setText("Натиснуто клавішу: " + actionCommand);
        switch (actionCommand) {
            case "Увійти" : {
                statusLabel.setText(es.login(boxExpertName.getText(), 
                        new String(boxExpertPass.getPassword())));
                break;
            }
            case "Зареєструвати" : {
                statusLabel.setText(es.addExpert(boxExpertName.getText(),
                        new String(boxExpertPass.getPassword())));
                break;
            }
            case "Завершити створення опитування" : {
                for (String QuestionName : QuestionNames) {
                    if (QuestionName.equals(boxSystemName.getText())) {
                        statusLabel.setText("Недопустима назва опитування");
                        break;
                    }
                }
                statusLabel.setText(es.createQuestionSystem(boxSystemName.getText()));
                break;
            }
            case "comboBoxChanged": {
                if (nothingSelected == true)
                        break;
                String name = (String) ((JComboBox) e.getSource()).getName();
                
                String choosen = (String) ((JComboBox) e.getSource()).getSelectedItem();
                statusLabel.setText("Обрано : " + choosen);
                
                if (name.equals("boxAllSystemsView")) {
                    currentQuestionSelected = 0;
                    statusLabel.setText(es.selectSystem(choosen));
                    expertsWeight = es.getBestExpert();
                    changeSubViewPanelView();
                    if (expertsWeight == null)
                        statusLabel.setText("Не вдалося визначити найкращого експерта.");
                }
                
                if (name.equals("boxAllSystemsPoll")) {
                    currentQuestionSelected = 0;
                    statusLabel.setText(es.selectSystem(choosen));
                    changeSubViewPanel(subViewPanelPoll, true);
                }
                
                if (name.equals("boxQuestionType")) {
                    for (int index = 0; index < QuestionNames.length; index++) {
                        if (QuestionNames[index].equals(choosen)) {
                            boxAnswer = new ArrayList<>();
                            boxAnswerWeight = new ArrayList<>();
                            checkAnswers = new ArrayList<>();
                            currentTypeSelected = index;
                            changeSubCreatePanel();
                            break;
                        }                        
                    }
                }
                break;
            }
            case "Додати питання": {
                List<String> stringCorrectAnswers = new ArrayList();
                List<String> stringAnswers = new ArrayList();
                List<String> stringWeights = new ArrayList();
                for(int i = 0; i < boxAnswer.size(); i++) {
                    if (boxAnswer.get(i).getText().isEmpty())
                        continue;

                    if (currentTypeSelected == 5) {
                        stringAnswers.add(boxAnswer.get(i).getText());
                        if (boxAnswerWeight.get(4*i).getText().isEmpty()
                                || boxAnswerWeight.get(4*i+1).getText().isEmpty()
                                || boxAnswerWeight.get(4*i+2).getText().isEmpty()
                                || boxAnswerWeight.get(4*i+3).getText().isEmpty()
                            )
                        {
                            statusLabel.setText("Пусте поле");
                            return;
                        }
                        stringWeights.add(boxAnswerWeight.get(4*i).getText());
                        stringWeights.add(boxAnswerWeight.get(4*i+1).getText());
                        stringWeights.add(boxAnswerWeight.get(4*i+2).getText());
                        stringWeights.add(boxAnswerWeight.get(4*i+3).getText());
                    } else {
                        stringAnswers.add(boxAnswer.get(i).getText());
                        if (boxAnswerWeight.get(i).getText().isEmpty())
                        {
                            statusLabel.setText("Пусте поле");
                            return;
                        }
                        stringWeights.add(boxAnswerWeight.get(i).getText());   
                    }                    
                }

                //parse answers
                for (int i = 0; i < checkAnswers.size(); i++) {
                    if (currentTypeSelected == 5 
                            || currentTypeSelected == 4
                            || currentTypeSelected == 3) {
                        if (boxCorrectAnswer.getText().isEmpty()
                                || boxCorrectAnswerFrom.getText().isEmpty()
                                || boxCorrectAnswerTo.getText().isEmpty())
                        {
                            statusLabel.setText("Пусте поле в правильній відповіді.");
                            return;
                        }
                        stringCorrectAnswers.add(boxCorrectAnswer.getText());
                        stringCorrectAnswers.add(boxCorrectAnswerFrom.getText());
                        stringCorrectAnswers.add(boxCorrectAnswerTo.getText());
                    } else {
                        if (currentTypeSelected == 0) {
                            if (checkAnswers.get(i).getState()) {
                                    if (i == 0)
                                        stringCorrectAnswers.add("Так");
                                    else
                                        stringCorrectAnswers.add("Ні");
                            }
                        } else 
                            if (checkAnswers.get(i).getState()) {
                                if (boxAnswer.get(i).getText().isEmpty()) {
                                    statusLabel.setText("Пусте відповідь обрана правильною.");
                                    return;
                                } 
                                stringCorrectAnswers.add(boxAnswer.get(i).getText());
                            }
                    }
                }
                if (stringCorrectAnswers.isEmpty()) {
                    statusLabel.setText("Не вказано правильної відповіді.");
                    return;
                }
                String questionWeight = boxWeightQuestion.getText();
                if (questionWeight == null || questionWeight.isEmpty()) {
                    statusLabel.setText("Не вказано вартість питання.");
                    return;
                }
                    
                statusLabel.setText(es.addQuestion(boxQuestion.getText(), 
                        currentTypeSelected, stringAnswers, stringWeights,
                        stringCorrectAnswers, questionWeight));
                currentQuestionSelected = es.getSystem().getSize() - 1;
                changeSubViewPanel(subViewPanelCreate, false);
                break;  
            }
                case "Наступне Питання": {
                    if (currentQuestionSelected < es.getSystem().getSize() - 1)
                        currentQuestionSelected++; 
                    statusLabel.setText("Питання №" 
                            + Integer.toString(currentQuestionSelected)); 
                    changeSubViewPanel(subViewPanelCreate, false); break;
                }
                case "Попереднє Питання": {
                    if (currentQuestionSelected > 0) 
                        currentQuestionSelected--; 
                    statusLabel.setText("Питання №" 
                            + Integer.toString(currentQuestionSelected)); 
                    changeSubViewPanel(subViewPanelCreate, false); break;
                }
                case "Відповісти": {
                    if (es.getSystem().isFinished(es.getExpert())) {
                        statusLabel.setText("Пройдено " + es.getExpert().getName() 
                                + " Оцінка " + Double.toString(es.getExpert().getScore()));
                        break;
                    }
                    String answer = null;
                    switch (es.getSystem().
                            getQuestion().getType()) {
                        case 1:
                            answer = getSelectedButtonText(group);
                            break;
                        case 2:
                            answer = getSelectedButtonText(group);
                            break;
                        case 3:
                            answer = "";
                            for (JRadioButton radioAnswer : radioAnswers) {
                                if (radioAnswer.isSelected()) 
                                    answer += radioAnswer.getText() + "_";
                            }
                            break;
                        case 4:
                            answer = boxYourAnswer1.getText();
                            break;
                        case 5:
                            if (boxYourAnswer1.getText() == null 
                                    || boxYourAnswer2.getText() == null)
                                break;
                            answer = boxYourAnswer1.getText() 
                                    + "_" + boxYourAnswer2.getText();
                            break;
                        case 6:                   
                            answer = (String)boxFirstModifier.getSelectedItem() 
                                    + "_" + (String)boxFirstWord.getSelectedItem()
                                    + "_" + (String)boxSecondModifier.getSelectedItem()
                                    + "_" + (String)boxThirdModifier.getSelectedItem()
                                    + "_" + (String)boxSecondWord.getSelectedItem();
                            break;
                        case 7:
                            answer = getSelectedButtonText(group);
                            break;
                        default:
                            statusLabel.setText("Невідомий тип питання."); 
                            return;
                    }
                    if (answer == null || answer.equals("")) {
                        statusLabel.setText("Відповідь не обрана.");
                        return;
                    }
                    statusLabel.setText(es.addAnswer(answer));
                    if (es.getSystem().isFinished(es.getExpert())){
                        statusLabel.setText("Пройдено " + es.getExpert().getName() 
                                + " Оцінка " + Double.toString(es.getExpert().getScore()));
                        es.changeScores();
                    }
                    changeSubViewPanel(subViewPanelPoll, true);
                    
                    statusLabel.setText(answer);
                    break;
                }
            default : break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ContentUi = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        ContentUi.addChangeListener(this);
        ContentUi.setMinimumSize(new java.awt.Dimension(800, 600));
        ContentUi.setName("TabName"); // NOI18N
        getContentPane().add(ContentUi, java.awt.BorderLayout.CENTER);
        /////////////////////////////////
        panel1 = makeInputMenu();
        ContentUi.addTab("Вхід", panel1);
        panel1.setName("Вхід");
        panel1.setPreferredSize(new Dimension(600, 400));

        panel2 = makeConstructorMenu();
        ContentUi.addTab("Конструктор", panel2);
        panel2.setName("Конструктор");
        panel2.setPreferredSize(new Dimension(600, 400));

        panel3 = makePollMenu();
        ContentUi.addTab("Опитування", panel3);
        panel3.setName("Опитування");
        panel3.setPreferredSize(new Dimension(600, 400));

        panel4 = makeViewMenu();
        ContentUi.addTab("Перегляд", panel4);
        panel4.setName("Перегляд");
        panel4.setPreferredSize(new Dimension(600, 400));
        //////////////////////////////
        ContentUi.getAccessibleContext().setAccessibleName("");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        statusLabel.setText("Статус");
        statusLabel.setName("statusLabel"); // NOI18N
        jPanel1.add(statusLabel);
        statusLabel.getAccessibleContext().setAccessibleName("statusLabel");
        statusLabel.getAccessibleContext().setAccessibleParent(this);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.getAccessibleContext().setAccessibleName("statusBar");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected JComponent makeInputMenu () {
            JPanel inputPanel = new JPanel(false);
            JLabel labelName = new JLabel("Введіть ім’я:");
            boxExpertName = new JTextField(25);
            JLabel labelPass = new JLabel("Введіть пароль:");
            boxExpertPass = new JPasswordField(25);
            JButton buttonLog = new JButton("Увійти");
            buttonLog.addActionListener(this);
            JButton buttonReg = new JButton("Зареєструвати");
            buttonReg.addActionListener(this);


            SpringLayout layout = new SpringLayout();
            layout.putConstraint(SpringLayout.EAST, labelName, -40, 
                    SpringLayout.HORIZONTAL_CENTER, inputPanel);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, labelName, -20, 
                    SpringLayout.VERTICAL_CENTER, inputPanel);
            layout.putConstraint(SpringLayout.WEST, boxExpertName, -20, 
                    SpringLayout.HORIZONTAL_CENTER, inputPanel);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, boxExpertName, -20, 
                    SpringLayout.VERTICAL_CENTER, inputPanel);

            layout.putConstraint(SpringLayout.EAST, labelPass, -40, 
                    SpringLayout.HORIZONTAL_CENTER, inputPanel);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, labelPass, 20, 
                    SpringLayout.VERTICAL_CENTER, inputPanel);
            layout.putConstraint(SpringLayout.WEST, boxExpertPass, -20, 
                    SpringLayout.HORIZONTAL_CENTER, inputPanel);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, boxExpertPass, 20, 
                    SpringLayout.VERTICAL_CENTER, inputPanel);

            layout.putConstraint(SpringLayout.EAST, buttonLog, 0, 
                    SpringLayout.EAST, labelPass);
            layout.putConstraint(SpringLayout.NORTH, buttonLog, 40, 
                    SpringLayout.VERTICAL_CENTER, inputPanel);
            layout.putConstraint(SpringLayout.EAST, buttonReg, 0, 
                    SpringLayout.EAST, boxExpertPass);
            layout.putConstraint(SpringLayout.NORTH, buttonReg, 40, 
                    SpringLayout.VERTICAL_CENTER, inputPanel);

            inputPanel.setLayout(layout);
            inputPanel.add(labelName);
            inputPanel.add(boxExpertName);
            inputPanel.add(labelPass);
            inputPanel.add(boxExpertPass);
            inputPanel.add(buttonLog);
            inputPanel.add(buttonReg);
            return inputPanel;
        }
    
    protected JComponent makeConstructorMenu () {
        JPanel constructorPanel = new JPanel(false);
        JPanel createPanel = makeCreatePanel();
        JPanel viewPanel = makeViewPanel();
        JPanel submitPanel = makeSubmitPanel();

        SpringLayout constructorCreate = new SpringLayout();
        constructorCreate.putConstraint(SpringLayout.WIDTH, createPanel, 0,
                SpringLayout.WIDTH, constructorPanel);      
        constructorCreate.putConstraint(SpringLayout.NORTH, createPanel, 0,
                SpringLayout.NORTH, constructorPanel);      
        constructorCreate.putConstraint(SpringLayout.WIDTH, viewPanel, 0,
                SpringLayout.WIDTH, constructorPanel);      
        constructorCreate.putConstraint(SpringLayout.NORTH, viewPanel, 0,
                SpringLayout.SOUTH, createPanel);
        constructorCreate.putConstraint(SpringLayout.SOUTH, viewPanel, 0,
                SpringLayout.NORTH, submitPanel);
        constructorCreate.putConstraint(SpringLayout.WIDTH, submitPanel, 0,
                SpringLayout.WIDTH, constructorPanel);      
        constructorCreate.putConstraint(SpringLayout.SOUTH, submitPanel, 0,
                SpringLayout.SOUTH, constructorPanel);  
        constructorPanel.setLayout (constructorCreate);
        
        constructorPanel.add(createPanel);
        constructorPanel.add(viewPanel);
        constructorPanel.add(submitPanel);
        return constructorPanel;
    }
    
    protected JPanel makeCreatePanel () {
        JPanel createPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                 return new Dimension(700, 200);
             }
        };
        JLabel labelCreateQuestion = new JLabel("Додати питання типу: ");
        JComboBox boxQuestionType = new JComboBox(QuestionNames);
        boxQuestionType.setName("boxQuestionType");
        boxQuestionType.addActionListener(this);
        JButton buttonAddQuestion = new JButton("Додати питання");
        buttonAddQuestion.addActionListener(this);
        JLabel labelWeightQuestion = new JLabel("Складність питання:");
        boxWeightQuestion = new JTextField(5);
        boxQuestion = new JTextArea(2, 80);
        boxQuestion.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(boxQuestion);
        subCreatePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                 return new Dimension(700, 2048);
             }
         };
        JScrollPane scrollPane2 = new JScrollPane(subCreatePanel);
        
        boxAnswer = new ArrayList<>();
        boxAnswerWeight = new ArrayList<>();
        currentTypeSelected = 0;
        checkAnswers = new ArrayList<>();
        checkGroup = new CheckboxGroup();
        changeSubCreatePanel();
        
        SpringLayout layoutCreate = new SpringLayout();
        layoutCreate.putConstraint(SpringLayout.WEST, labelCreateQuestion, 5,
                SpringLayout.WEST, createPanel);                                
        layoutCreate.putConstraint(SpringLayout.VERTICAL_CENTER, labelCreateQuestion, 0,
                SpringLayout.VERTICAL_CENTER, boxQuestionType);        
        layoutCreate.putConstraint(SpringLayout.WEST, boxQuestionType, 5,
                SpringLayout.EAST, labelCreateQuestion);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, boxQuestionType, 0,
                SpringLayout.NORTH, createPanel);
        
        layoutCreate.putConstraint(SpringLayout.WEST, labelWeightQuestion, 5,
                SpringLayout.EAST, boxQuestionType);                                
        layoutCreate.putConstraint(SpringLayout.VERTICAL_CENTER, labelWeightQuestion, 0,
                SpringLayout.VERTICAL_CENTER, boxQuestionType);
        layoutCreate.putConstraint(SpringLayout.WEST, boxWeightQuestion, 5,
                SpringLayout.EAST, labelWeightQuestion);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, boxWeightQuestion, 0,
                SpringLayout.NORTH, createPanel);
        
        layoutCreate.putConstraint(SpringLayout.EAST, buttonAddQuestion, 0,
                SpringLayout.EAST, createPanel);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, buttonAddQuestion, 0,
                SpringLayout.NORTH, createPanel);
        layoutCreate.putConstraint(SpringLayout.WIDTH, scrollPane, 0,
                SpringLayout.WIDTH, createPanel);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, scrollPane, 0,
                SpringLayout.SOUTH, boxQuestionType);        
        layoutCreate.putConstraint(SpringLayout.WIDTH, scrollPane2, 0,
                SpringLayout.WIDTH, createPanel);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, scrollPane2, 0,
                SpringLayout.SOUTH, scrollPane);
        layoutCreate.putConstraint(SpringLayout.SOUTH, scrollPane2, 0,
                SpringLayout.SOUTH, createPanel);
        createPanel.setLayout (layoutCreate);
        
        createPanel.add(labelCreateQuestion);
        createPanel.add(boxQuestionType);
        createPanel.add(labelWeightQuestion);
        createPanel.add(boxWeightQuestion);
        createPanel.add(buttonAddQuestion);
        createPanel.add(scrollPane);
        createPanel.add(scrollPane2);
        
        return createPanel;
    }
    
    protected void changeSubCreatePanel() {
        subCreatePanel.removeAll();
        JLabel labelAnswer = new JLabel("Варіант відповіді");
        JLabel labelWeight = new JLabel("Вага відповіді");
        SpringLayout layoutSubCreate = new SpringLayout();
        switch (currentTypeSelected) {
            case 0:
                checkAnswers.add(new Checkbox("Правильна відповідь Так", false, checkGroup));
                checkAnswers.add(new Checkbox("Правильна відповідь Ні", false, checkGroup));
                subCreatePanel.add(checkAnswers.get(0));
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        checkAnswers.get(0), 5,
                        SpringLayout.WEST, subCreatePanel);                                
                layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                        checkAnswers.get(0), 0,
                        SpringLayout.NORTH, subCreatePanel);
                    
                subCreatePanel.add(checkAnswers.get(1));
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        checkAnswers.get(1), 5,
                        SpringLayout.WEST, subCreatePanel);                                
                layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                        checkAnswers.get(1),  25,
                        SpringLayout.NORTH, subCreatePanel);
                break;
            case 5:
                labelAnswer = new JLabel("Варіант відповіді");
                JLabel labelInterval = new JLabel("Чисельний інтервал");
                labelWeight = new JLabel("Правильність на кінцях інтервалу");
                boxAnswer.add(new JTextField(20));
                boxAnswerWeight.add(new JTextField(10));
                boxAnswerWeight.add(new JTextField(10));
                boxAnswerWeight.add(new JTextField(10));
                boxAnswerWeight.add(new JTextField(10));
                
                subCreatePanel.add(labelAnswer);
                subCreatePanel.add(labelInterval);
                subCreatePanel.add(labelWeight);
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        labelAnswer, 10,
                        SpringLayout.WEST, subCreatePanel);                                
                layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                        labelAnswer, 25,
                        SpringLayout.NORTH, subCreatePanel);
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        labelInterval, 120,
                        SpringLayout.EAST, labelAnswer);                                
                layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                        labelInterval, 25,
                        SpringLayout.NORTH, subCreatePanel);
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        labelWeight, 130,
                        SpringLayout.EAST, labelInterval);                                
                layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                        labelWeight, 25,
                        SpringLayout.NORTH, subCreatePanel);
                
                for(int i = 0; i < boxAnswer.size(); i++) {
                    subCreatePanel.add(boxAnswer.get(i));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswer.get(i), 5,
                            SpringLayout.WEST, subCreatePanel);                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswer.get(i), (i+2) * 25,
                            SpringLayout.NORTH, subCreatePanel);

                    subCreatePanel.add(boxAnswerWeight.get(4 * i));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswerWeight.get(4 * i), 5,
                            SpringLayout.EAST, boxAnswer.get(i));                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswerWeight.get(4 * i), (i+2) * 25,
                            SpringLayout.NORTH, subCreatePanel);
                    
                    subCreatePanel.add(boxAnswerWeight.get(4 * i+1));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswerWeight.get(4 * i+1), 5,
                            SpringLayout.EAST, boxAnswerWeight.get(4 * i));                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswerWeight.get(4 * i+1), (i+2) * 25,
                            SpringLayout.NORTH, subCreatePanel);
                    
                    subCreatePanel.add(boxAnswerWeight.get(4 * i+2));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswerWeight.get(4 * i+2), 5,
                            SpringLayout.EAST, boxAnswerWeight.get(4 * i+1));                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswerWeight.get(4 * i+2), (i+2) * 25,
                            SpringLayout.NORTH, subCreatePanel);
                    
                    subCreatePanel.add(boxAnswerWeight.get(4 * i+3));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswerWeight.get(4 * i+3), 5,
                            SpringLayout.EAST, boxAnswerWeight.get(4 * i+2));                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswerWeight.get(4 * i+3), (i+2) * 25,
                            SpringLayout.NORTH, subCreatePanel);
                }
            case 3:
            case 4:
                checkAnswers.add(null);
                JLabel labelCorrectAnswer = new JLabel("Правильна відповідь");
                JLabel labelCorrectInterval = new JLabel("Допустимий інтервал");
                boxCorrectAnswer = new JTextField(10);
                boxCorrectAnswerFrom = new JTextField(10);
                boxCorrectAnswerTo = new JTextField(10);
                subCreatePanel.add(boxCorrectAnswer);
                subCreatePanel.add(boxCorrectAnswerFrom);
                subCreatePanel.add(boxCorrectAnswerTo);
                subCreatePanel.add(labelCorrectAnswer);
                subCreatePanel.add(labelCorrectInterval);
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        labelCorrectAnswer, 4, SpringLayout.WEST, subCreatePanel);                                
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        boxCorrectAnswer, 4, SpringLayout.EAST, labelCorrectAnswer);
                layoutSubCreate.putConstraint(SpringLayout.VERTICAL_CENTER, 
                        boxCorrectAnswer, 0, 
                        SpringLayout.VERTICAL_CENTER, labelCorrectAnswer);
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        labelCorrectInterval, 4, SpringLayout.EAST, boxCorrectAnswer);                                
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        boxCorrectAnswerFrom, 4, SpringLayout.EAST, labelCorrectInterval);    
                layoutSubCreate.putConstraint(SpringLayout.VERTICAL_CENTER, 
                        boxCorrectAnswerFrom, 0, 
                        SpringLayout.VERTICAL_CENTER, labelCorrectInterval);    
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        boxCorrectAnswerTo, 4, SpringLayout.EAST, boxCorrectAnswerFrom);   
                layoutSubCreate.putConstraint(SpringLayout.VERTICAL_CENTER, 
                        boxCorrectAnswerTo, 0, 
                        SpringLayout.VERTICAL_CENTER, labelCorrectInterval);   
                break;
            case 2:
            case 1:
            case 6:
            default:
                if (currentTypeSelected == 2)
                    checkAnswers.add(new Checkbox("Правильно."));
                else
                    checkAnswers.add(new Checkbox("Правильно.", false, checkGroup));                    
                boxAnswer.add(new JTextField(20));
                boxAnswerWeight.add(new JTextField(5));
                subCreatePanel.add(labelAnswer);
                subCreatePanel.add(labelWeight);
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        labelAnswer, 10,
                        SpringLayout.WEST, subCreatePanel);                                
                layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                        labelAnswer, 0,
                        SpringLayout.NORTH, subCreatePanel);
                layoutSubCreate.putConstraint(SpringLayout.WEST, 
                        labelWeight, 100,
                        SpringLayout.EAST, labelAnswer);                                
                layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                        labelWeight, 0,
                        SpringLayout.NORTH, subCreatePanel);
                
                for(int i = 0; i < boxAnswer.size(); i++) {
                    subCreatePanel.add(boxAnswer.get(i));
                    subCreatePanel.add(boxAnswerWeight.get(i));
                    subCreatePanel.add(checkAnswers.get(i));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswer.get(i), 5,
                            SpringLayout.WEST, subCreatePanel);                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswer.get(i), (1 + i) * 25,
                            SpringLayout.NORTH, subCreatePanel);
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswerWeight.get(i), 5,
                            SpringLayout.EAST, boxAnswer.get(i));                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswerWeight.get(i), (1 + i) * 25,
                            SpringLayout.NORTH, subCreatePanel);
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            checkAnswers.get(i), 5,
                            SpringLayout.EAST, boxAnswerWeight.get(i));                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            checkAnswers.get(i), (1 + i) * 25,
                            SpringLayout.NORTH, subCreatePanel);
                }
        }
        
        for (JTextField boxAnswerOne : boxAnswer) {
            boxAnswerOne.getDocument().addDocumentListener(this);
        }
        for (JTextField boxAnswerWeightOne : boxAnswerWeight) {
            boxAnswerWeightOne.getDocument().addDocumentListener(this);
        }
        
        subCreatePanel.setLayout (layoutSubCreate);
        subCreatePanel.repaint();
        subCreatePanel.revalidate();
        validate();
    }
    
    protected JPanel makeViewPanel () {
        JPanel viewPanel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                 return new Dimension(700, 2042);
             }
        };
        JButton buttonPrevQuestion = new JButton("Попереднє Питання");
        buttonPrevQuestion.addActionListener(this);
        JButton buttonNextQuestion = new JButton("Наступне Питання");
        buttonNextQuestion.addActionListener(this);
        
        boxReadQuestion = new JTextArea(2, 100);
        boxReadQuestion.setLineWrap(true);
        boxReadQuestion.setEditable(false);
        boxReadQuestion.setBackground(new Color(255, 255, 255, 0));
        JScrollPane scrollPane = new JScrollPane(boxReadQuestion);
        scrollPane.setBorder(null);
        subViewPanelCreate = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                 return new Dimension(700, 2048);
             }
         };
        JScrollPane scrollPaneOptions = new JScrollPane(subViewPanelCreate);
        
        SpringLayout layoutCreate = new SpringLayout();  
        
        layoutCreate.putConstraint(SpringLayout.WEST, buttonPrevQuestion, 0,
                SpringLayout.WEST, viewPanel);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, buttonPrevQuestion, 0,
                SpringLayout.NORTH, viewPanel); 
        layoutCreate.putConstraint(SpringLayout.EAST, buttonNextQuestion, 0,
                SpringLayout.EAST, viewPanel);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, buttonNextQuestion, 0,
                SpringLayout.NORTH, viewPanel); 
        
        layoutCreate.putConstraint(SpringLayout.WIDTH, scrollPaneOptions, 0,
                SpringLayout.WIDTH, viewPanel);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, scrollPaneOptions, 0,
                SpringLayout.SOUTH, scrollPane);
        layoutCreate.putConstraint(SpringLayout.SOUTH, scrollPaneOptions, 0,
                SpringLayout.SOUTH, viewPanel);
        
        layoutCreate.putConstraint(SpringLayout.WIDTH, scrollPane, 0,
                SpringLayout.WIDTH, viewPanel);                                
        layoutCreate.putConstraint(SpringLayout.NORTH, scrollPane, 0,
                SpringLayout.SOUTH, buttonPrevQuestion);        
        viewPanel.setLayout (layoutCreate);

        viewPanel.add(buttonPrevQuestion);
        viewPanel.add(buttonNextQuestion);
        viewPanel.add(scrollPane);
        viewPanel.add(scrollPaneOptions);
       
        return viewPanel;
    }
    
    protected void changeSubViewPanel(JPanel subViewPanel, boolean isRandomQuestion) {
        if (currentQuestionSelected < 0 
                || currentQuestionSelected >= es.getSystem().getSize())
            return;
        subViewPanel.removeAll();
        Question question;
        if (isRandomQuestion)
            question = es.getSystem().getQuestion();
        else
            question = es.getSystem().getQuestion(currentQuestionSelected);
        if (question == null)
            statusLabel.setText("Питання не знайдено");
        boxReadQuestion.setText(question.getQuestion());
        boxReadQuestionPoll.setText(question.getQuestion());
        JRadioButton radioAnswer;
        SpringLayout layoutView;
        switch (question.getType()){
            case 1:
                JRadioButton radioYes = new JRadioButton("Так");
                JRadioButton radioNo = new JRadioButton("Ні");
                group = new ButtonGroup();
                group.add(radioNo);
                group.add(radioYes);
                subViewPanel.add(radioYes);
                subViewPanel.add(radioNo);
                layoutView = new SpringLayout(); 
                    layoutView.putConstraint(SpringLayout.WEST, radioYes, 0,
                            SpringLayout.WEST, subViewPanel);                                
                    layoutView.putConstraint(SpringLayout.NORTH, radioYes, 
                            0, SpringLayout.NORTH, subViewPanel); 
                    layoutView.putConstraint(SpringLayout.WEST, radioNo, 0,
                            SpringLayout.WEST, subViewPanel);                                
                    layoutView.putConstraint(SpringLayout.NORTH, radioNo, 
                            25, SpringLayout.NORTH, subViewPanel); 
                subViewPanel.setLayout(layoutView);
                break;
            case 2:
            case 7:
                radioAnswers = new ArrayList<>();
                group = new ButtonGroup();
                layoutView = new SpringLayout(); 
                for (String answer : question.getAnswers()) {
                    radioAnswer = new JRadioButton(answer);
                    radioAnswers.add(radioAnswer);
                    group.add(radioAnswer);
                    subViewPanel.add(radioAnswer);
                    layoutView.putConstraint(SpringLayout.WEST, radioAnswer, 0,
                            SpringLayout.WEST, subViewPanel);                                
                    layoutView.putConstraint(SpringLayout.NORTH, radioAnswer, 
                            (radioAnswers.size() - 1) * 25,
                            SpringLayout.NORTH, subViewPanel); 
                }
                subViewPanel.setLayout(layoutView);
                break;
            case 3:
                radioAnswers = new ArrayList<>();
                group = new ButtonGroup();
                layoutView = new SpringLayout(); 
                for (String answer : question.getAnswers()) {
                    radioAnswer = new JRadioButton(answer);
                    radioAnswers.add(radioAnswer);
                    subViewPanel.add(radioAnswer);
                    layoutView.putConstraint(SpringLayout.WEST, radioAnswer, 0,
                            SpringLayout.WEST, subViewPanel);                                
                    layoutView.putConstraint(SpringLayout.NORTH, radioAnswer, 
                            (radioAnswers.size() - 1) * 25,
                            SpringLayout.NORTH, subViewPanel); 
                }
                subViewPanel.setLayout(layoutView);
                break;
            case 4:
                boxYourAnswer1 = new JTextField(25);
                layoutView = new SpringLayout(); 
                subViewPanel.add(boxYourAnswer1);
                layoutView.putConstraint(SpringLayout.WEST, boxYourAnswer1, 0,
                        SpringLayout.WEST, subViewPanel);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxYourAnswer1, 0,
                        SpringLayout.NORTH, subViewPanel);
                subViewPanel.setLayout(layoutView);
                break;
            case 5:
                boxYourAnswer1 = new JTextField(10);
                boxYourAnswer2 = new JTextField(10);
                layoutView = new SpringLayout(); 
                subViewPanel.add(boxYourAnswer1);
                subViewPanel.add(boxYourAnswer2);
                layoutView.putConstraint(SpringLayout.WEST, boxYourAnswer1, 0,
                        SpringLayout.WEST, subViewPanel);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxYourAnswer1, 0,
                        SpringLayout.NORTH, subViewPanel);
                layoutView.putConstraint(SpringLayout.WEST, boxYourAnswer2, 50,
                        SpringLayout.EAST, boxYourAnswer1);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxYourAnswer2, 0,
                        SpringLayout.NORTH, subViewPanel);
                subViewPanel.setLayout(layoutView);
                break;
            case 6:
                String unimodifier[] = {"", "Більш", "Менш"};
                String duomodifier[] = {"", "І", "Або"};
                List<String> answers = new ArrayList<>(question.possibleAnswers);
                boxFirstModifier = new JComboBox(unimodifier);
                boxSecondModifier = new JComboBox(duomodifier);
                boxThirdModifier = new JComboBox(unimodifier);
                boxFirstWord = new JComboBox(answers.toArray());
                answers.add(0, "");
                boxSecondWord = new JComboBox(answers.toArray());

                layoutView = new SpringLayout(); 
                subViewPanel.add(boxFirstModifier);
                subViewPanel.add(boxFirstWord);
                subViewPanel.add(boxSecondModifier);
                subViewPanel.add(boxThirdModifier);
                subViewPanel.add(boxSecondWord);
                
                layoutView.putConstraint(SpringLayout.WEST, boxFirstModifier, 0,
                        SpringLayout.WEST, subViewPanel);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxFirstModifier, 0,
                        SpringLayout.NORTH, subViewPanel);
                
                layoutView.putConstraint(SpringLayout.WEST, boxFirstWord, 0,
                        SpringLayout.EAST, boxFirstModifier);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxFirstWord, 0,
                        SpringLayout.NORTH, subViewPanel);
                
                layoutView.putConstraint(SpringLayout.WEST, boxSecondModifier, 0,
                        SpringLayout.EAST, boxFirstWord);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxSecondModifier, 0,
                        SpringLayout.NORTH, subViewPanel);
                
                layoutView.putConstraint(SpringLayout.WEST, boxThirdModifier, 0,
                        SpringLayout.EAST, boxSecondModifier);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxThirdModifier, 0,
                        SpringLayout.NORTH, subViewPanel);
                
                layoutView.putConstraint(SpringLayout.WEST, boxSecondWord, 0,
                        SpringLayout.EAST, boxThirdModifier);                                
                layoutView.putConstraint(SpringLayout.NORTH, boxSecondWord, 0,
                        SpringLayout.NORTH, subViewPanel);
                subViewPanel.setLayout(layoutView);

                break;
        }
        subViewPanel.revalidate();
        subViewPanel.repaint();
        validate();
    }
    
    protected JPanel makeSubmitPanel () {
        JPanel submitPanel = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                 return new Dimension(700, 200);
             }
        };
        JLabel labelSystemName = new JLabel("Ім’я опитування: ");
        boxSystemName = new JTextField(25);
        JButton buttonReg = new JButton("Завершити створення опитування");
        buttonReg.addActionListener(this);

        SpringLayout submitLayout = new SpringLayout();
        submitPanel.setLayout(submitLayout);

        submitLayout.putConstraint(SpringLayout.WEST, labelSystemName, 5,
                SpringLayout.WEST, submitPanel);                                
        submitLayout.putConstraint(SpringLayout.VERTICAL_CENTER, labelSystemName, 0,
                SpringLayout.VERTICAL_CENTER, boxSystemName);

        submitLayout.putConstraint(SpringLayout.WEST, boxSystemName, 10,
                SpringLayout.EAST, labelSystemName);
        submitLayout.putConstraint(SpringLayout.SOUTH, boxSystemName, 0,
                SpringLayout.SOUTH, submitPanel);

        submitLayout.putConstraint(SpringLayout.EAST, buttonReg, 0,
                SpringLayout.EAST, submitPanel);
        submitLayout.putConstraint(SpringLayout.VERTICAL_CENTER, buttonReg, 0,
                SpringLayout.VERTICAL_CENTER, boxSystemName);

        submitPanel.add(labelSystemName);
        submitPanel.add(boxSystemName);
        submitPanel.add(buttonReg);

        return submitPanel;
    }

    protected JComponent makePollMenu () {
            JPanel viewPanel = new JPanel(false);
            JButton buttonAnswer = new JButton("Відповісти");
            buttonAnswer.addActionListener(this);
            boxAllSystems = new JComboBox(es.getAllSystems().toArray());
            boxAllSystems.setName("boxAllSystemsPoll");
            boxAllSystems.addActionListener(this);    

            boxReadQuestionPoll = new JTextArea(2, 300);
            boxReadQuestionPoll.setLineWrap(true);
            boxReadQuestionPoll.setEditable(false);
            boxReadQuestionPoll.setBackground(new Color(255, 255, 255, 0));
            JScrollPane scrollPane = new JScrollPane(boxReadQuestionPoll);
            scrollPane.setBorder(null);
            subViewPanelPoll = new JPanel() {
                @Override
                public Dimension getPreferredSize() {
                     return new Dimension(700, 2048);
                 }
            };
            JScrollPane scrollPaneOptions = new JScrollPane(subViewPanelPoll);

            SpringLayout layoutCreate = new SpringLayout();  

            layoutCreate.putConstraint(SpringLayout.WEST, boxAllSystems, 0,
                    SpringLayout.WEST, viewPanel);                                
            layoutCreate.putConstraint(SpringLayout.SOUTH, boxAllSystems, 0,
                    SpringLayout.SOUTH, boxReadQuestionPoll);
            layoutCreate.putConstraint(SpringLayout.EAST, buttonAnswer, -15,
                    SpringLayout.EAST, viewPanel);                                
            layoutCreate.putConstraint(SpringLayout.SOUTH, buttonAnswer, 0,
                    SpringLayout.SOUTH, boxReadQuestionPoll);

            layoutCreate.putConstraint(SpringLayout.WIDTH, scrollPaneOptions, 0,
                    SpringLayout.WIDTH, viewPanel);                                
            layoutCreate.putConstraint(SpringLayout.NORTH, scrollPaneOptions, 0,
                    SpringLayout.SOUTH, scrollPane);
            layoutCreate.putConstraint(SpringLayout.SOUTH, scrollPaneOptions, 0,
                    SpringLayout.SOUTH, viewPanel);

            layoutCreate.putConstraint(SpringLayout.WIDTH, scrollPane, 0,
                    SpringLayout.WIDTH, viewPanel);    
            layoutCreate.putConstraint(SpringLayout.NORTH, scrollPane, 0,
                    SpringLayout.SOUTH, boxAllSystems);
            viewPanel.setLayout (layoutCreate);

            viewPanel.add(boxAllSystems);
            viewPanel.add(buttonAnswer);
            viewPanel.add(scrollPane);
            viewPanel.add(scrollPaneOptions);

            return viewPanel;
        }
        
    protected JComponent makeViewMenu () {
            JPanel viewPanel = new JPanel(false);
            boxAllSystems = new JComboBox();
            boxAllSystems.setName("boxAllSystemsView");
            boxAllSystems.addActionListener(this);    

            subViewPanelView = new JPanel() {
                @Override
                public Dimension getPreferredSize() {
                     return new Dimension(700, 2048);
                 }
            };
            JScrollPane scrollPaneOptions = new JScrollPane(subViewPanelView);

            SpringLayout layoutView = new SpringLayout();  

            layoutView.putConstraint(SpringLayout.WEST, boxAllSystems, 0,
                    SpringLayout.WEST, viewPanel);                                

            layoutView.putConstraint(SpringLayout.WIDTH, scrollPaneOptions, 0,
                    SpringLayout.WIDTH, viewPanel);                                
            layoutView.putConstraint(SpringLayout.NORTH, scrollPaneOptions, 0,
                    SpringLayout.SOUTH, boxAllSystems);
            layoutView.putConstraint(SpringLayout.SOUTH, scrollPaneOptions, 0,
                    SpringLayout.SOUTH, viewPanel);

            viewPanel.setLayout (layoutView);

            viewPanel.add(boxAllSystems);
            viewPanel.add(scrollPaneOptions);
            changeSubViewPanelView();
            return viewPanel;
        }
    
    private void changeSubViewPanelView() {
        if (expertsWeight == null)
            return;
        if (expertsWeight.isEmpty())
            return;
        subViewPanelView.removeAll();
        SpringLayout layoutView = new SpringLayout();
        subViewPanelView.setLayout(layoutView);
        
        String delims = "_";
        for (int i = 0; i < expertsWeight.size(); i++) {
            String[] parsed = expertsWeight.get(i).split(delims);
            String expertName = "";
            String expertQuality;
            if (parsed.length != 2) {
                for (int j = 0; j < parsed.length - 1; j++)
                    expertName += parsed[j] + "_";
                expertQuality = parsed[parsed.length - 1];
            } else {
                expertName = parsed[0];
                expertQuality = parsed[1];
            }
            
            JLabel expertNameLabel = new JLabel(expertName);
            JLabel expertWeightLabel = new JLabel(expertQuality);
            subViewPanelView.add(expertNameLabel);
            subViewPanelView.add(expertWeightLabel);

            layoutView.putConstraint(SpringLayout.WEST, expertNameLabel, 5,
                    SpringLayout.WEST, subViewPanelView);                                
            layoutView.putConstraint(SpringLayout.NORTH, expertNameLabel, 
                    (1 + i) * 25, SpringLayout.NORTH, subViewPanelView);
            layoutView.putConstraint(SpringLayout.WEST, expertWeightLabel, 25,
                    SpringLayout.EAST, expertNameLabel);                                
            layoutView.putConstraint(SpringLayout.NORTH, expertWeightLabel, 
                    (1 + i) * 25, SpringLayout.NORTH, subViewPanelView);
        }
    }
    
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        for (int i = boxAnswer.size() - 1; i >= 0; i--) {
            if (currentTypeSelected == 1 || currentTypeSelected == 2 || currentTypeSelected == 6)
                if (boxAnswer.get(i).getText().isEmpty() ||
                        boxAnswerWeight.get(i).getText().isEmpty())
                    return;
            if (currentTypeSelected == 5)
                if (boxAnswer.get(i).getText().isEmpty() ||
                        boxAnswerWeight.get(4 * i).getText().isEmpty() ||
                        boxAnswerWeight.get(4 * i + 1).getText().isEmpty() ||
                        boxAnswerWeight.get(4 * i + 2).getText().isEmpty() ||
                        boxAnswerWeight.get(4 * i + 3).getText().isEmpty())
                    return;
        }
        changeSubCreatePanel();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        String panel = ContentUi.getSelectedComponent().getName();
        if (panel == null)
            return;
        switch (panel) {
            case "Вхід":
                boxExpertPass.setText(null);
                statusLabel.setText("Статус");
                break;
            case "Конструктор":
                subViewPanelCreate.removeAll();
                boxReadQuestion.setText(null);
                boxQuestion.setText(null);
                boxAnswer.clear();
                boxAnswerWeight.clear();
                if (radioAnswers != null)
                    radioAnswers.clear();
                if (es.getExpert() == null)
                    statusLabel.setText("Користувач не відомий. Опитування не буде збережене.");
                es.clearSystem();
                break;
            case "Опитування":
                subViewPanelPoll.removeAll();
                boxReadQuestionPoll.setText(null);
                boxAnswer.clear();
                boxAnswerWeight.clear();
                if (radioAnswers != null)
                    radioAnswers.clear();
                if (boxYourAnswer1 != null)
                    boxYourAnswer1.removeAll();
                if (boxYourAnswer2 != null)
                    boxYourAnswer2.removeAll();
                if (es.getExpert() == null)
                    statusLabel.setText("Користувач не відомий. Ваші відповіді не можливо зберегти");
                es.clearSystem();
                break;
            case "Перегляд":
                boxAllSystems.removeAllItems();
                boxQuestion.setText(null);
                boxReadQuestion.setText(null);
                boxAnswer.clear();
                boxAnswerWeight.clear();
                if (radioAnswers != null)
                    radioAnswers.clear();
                if (es.getExpert() == null)
                    statusLabel.setText("Користувач не відомий. Не має доступу для перегляду.");
                else {
                    nothingSelected = true;
                    List<String> systems = es.getAllSystemsOwned();
                    boxAllSystems.setModel(new JComboBox(systems.toArray()).getModel());
                }
                nothingSelected = false;
                es.clearSystem();
                break;        
        }
    }
    
    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        Locale.setDefault(new Locale("uk", "UA", "WIN"));
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExpertSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExpertSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExpertSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExpertSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExpertSystemUI().setVisible(true);
            }
        });
    }

    private final String[] QuestionNames = {
            "Так чи ні",
            "Вибір одного",
            "Вибір декількох",
            "Вкажіть число",
            "Вкажіть інтервал",
            "Вибір нечіткого інтервалу",
            "Вибір подібного"
        };

    private final ExpertSystem es;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane ContentUi;
    private javax.swing.JComponent panel1;
    private javax.swing.JComponent panel2;
    private javax.swing.JComponent panel3;
    private javax.swing.JComponent panel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
    private JTextField boxExpertName;
    private JPasswordField boxExpertPass;
    private JTextField boxSystemName;
    private JPanel subCreatePanel;
    private JPanel subViewPanelCreate;
    private JPanel subViewPanelPoll;
    private JPanel subViewPanelView;
    private JTextArea boxQuestion;
    private JTextArea boxReadQuestion;
    private JTextArea boxReadQuestionPoll;
    private List<JTextField> boxAnswer;
    private List<JTextField> boxAnswerWeight;
    private List<JRadioButton> radioAnswers;
    private List<Checkbox> checkAnswers;
    private JTextField boxWeightQuestion;
    private JTextField boxCorrectAnswer;
    private JTextField boxCorrectAnswerFrom;
    private JTextField boxCorrectAnswerTo;
    private JTextField boxYourAnswer1;
    private JTextField boxYourAnswer2;
    private ButtonGroup group;
    private CheckboxGroup checkGroup;
    private JComboBox boxFirstModifier;
    private JComboBox boxSecondModifier;
    private JComboBox boxThirdModifier;
    private JComboBox boxFirstWord;
    private JComboBox boxSecondWord;
    private JComboBox boxAllSystems;
    private boolean nothingSelected;
    private List<String> expertsWeight;
    
    private int currentTypeSelected;
    private int currentQuestionSelected;
}
