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
import java.util.ArrayList;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author drevlen
 */
public class ExpertSystemUI extends javax.swing.JFrame implements ActionListener, MouseMotionListener {

    /**
     * Creates new form ExpertSystemUI
     */
    public ExpertSystemUI() {
        es = new ExpertSystem();
        initComponents();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int i = boxAnswer.size() - 1; i >= 0; i--) {
            if (boxAnswer.get(i).getText().isEmpty() &&
                    boxAnswerWeight.get(i).getText().isEmpty())
                return;
        }
        changeSubCreatePanel();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
//        String actionCommand = ((JButton) e.getSource()).getActionCommand();
        statusLabel.setText("Натиснуто клавішу: " + actionCommand);
        switch (actionCommand) {
            case "Увійти" : 
                statusLabel.setText(es.login(boxExpertName.getText(), 
                        new String(boxExpertPass.getPassword())));
                break;
            case "Зареєструвати" : 
                statusLabel.setText(es.addExpert(boxExpertName.getText(),
                        new String(boxExpertPass.getPassword())));
                break;
            case "Завершити створення опитування" : 
                statusLabel.setText(es.createQuestionSystem(boxSystemName.getText()));
                break;
            case "comboBoxChanged":
                String choosen = 
                        (String)((JComboBox) e.getSource()).getSelectedItem();
                statusLabel.setText("Обрано варіант: " + choosen);
                for (int i = 0; i < QuestionNames.length; i++) {
                    if (QuestionNames[i].equals(choosen)) {
                        boxAnswer = new ArrayList<>();
                        boxAnswerWeight = new ArrayList<>();
                        currentTypeSelected = i;
                        changeSubCreatePanel();
                        break;
                    }                        
                }
                subCreatePanel.revalidate();
                validate();
                break;
            case "Додати Питання":
                List<String> stringAnswers = new ArrayList();
                List<String> stringWeights = new ArrayList();
                for(int i = 0; i < boxAnswer.size(); i++) {
                    stringAnswers.add(boxAnswer.get(i).getText());
                    stringWeights.add(boxAnswerWeight.get(i).getText());
                }
                statusLabel.setText(es.addQuestion(boxQuestion.getText(), 
                        currentTypeSelected, stringAnswers, stringWeights));
                break;                
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

        ContentUi.setMinimumSize(new java.awt.Dimension(800, 600));
        ContentUi.setName("TabName"); // NOI18N
        getContentPane().add(ContentUi, java.awt.BorderLayout.CENTER);
        /////////////////////////////////
        panel1 = makeInputMenu();
        ContentUi.addTab("Вхід", panel1);
        panel1.setPreferredSize(new Dimension(600, 400));

        panel2 = makeConstructorMenu();
        ContentUi.addTab("Конструктор", panel2);
        panel2.setPreferredSize(new Dimension(600, 400));

        panel3 = makePollMenu();
        ContentUi.addTab("Опитування", panel3);
        panel3.setPreferredSize(new Dimension(600, 400));

        panel4 = makeViewMenu();
        ContentUi.addTab("Перегляд", panel4);
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
        JPanel viewPanel = new JPanel(false);
        JPanel submitPanel = makeSubmitPanel();

        constructorPanel.setLayout (new GridLayout(3,1));

        constructorPanel.add(createPanel);
        constructorPanel.add(viewPanel);
        constructorPanel.add(submitPanel);

        constructorPanel.addMouseMotionListener(this);
        return constructorPanel;
    }
    
    protected JPanel makeCreatePanel () {
        JPanel createPanel = new JPanel(false);
        JLabel labelCreateQuestion = new JLabel("Додати питання типу: ");
        JComboBox boxQuestionType = new JComboBox(QuestionNames);
        boxQuestionType.addActionListener(this);
        JButton buttonAddQuestion = new JButton("Додати Питання");
        buttonAddQuestion.addActionListener(this);
        boxQuestion = new JTextArea(2, 100);
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
        createPanel.add(buttonAddQuestion);
        createPanel.add(scrollPane);
        createPanel.add(scrollPane2);
       
        return createPanel;
    }
    
    protected void changeSubCreatePanel() {
        subCreatePanel.removeAll();
        boxAnswer.add(new JTextField(20));
        boxAnswerWeight.add(new JTextField(5));
        SpringLayout layoutSubCreate = new SpringLayout();
        switch (currentTypeSelected) {
            case 0:
            case 3:
                break;
            case 4:
                for(int i = 0; i < boxAnswer.size(); i++) {
                    subCreatePanel.add(boxAnswer.get(i));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswer.get(i), 5,
                            SpringLayout.WEST, subCreatePanel);                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswer.get(i), i * 25,
                            SpringLayout.NORTH, subCreatePanel);
                }
                break;
            default:
                for(int i = 0; i < boxAnswer.size(); i++) {
                    subCreatePanel.add(boxAnswer.get(i));
                    subCreatePanel.add(boxAnswerWeight.get(i));
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswer.get(i), 5,
                            SpringLayout.WEST, subCreatePanel);                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswer.get(i), i * 25,
                            SpringLayout.NORTH, subCreatePanel);
                    layoutSubCreate.putConstraint(SpringLayout.WEST, 
                            boxAnswerWeight.get(i), 5,
                            SpringLayout.EAST, boxAnswer.get(i));                                
                    layoutSubCreate.putConstraint(SpringLayout.NORTH, 
                            boxAnswerWeight.get(i), i * 25,
                            SpringLayout.NORTH, subCreatePanel);
                }
        }
        subCreatePanel.setLayout (layoutSubCreate);
    }
    
    protected JPanel makeSubmitPanel () {
        JPanel submitPanel = new JPanel(false);
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
            JPanel panel = new JPanel(false);
            JLabel filler = new JLabel("");
            filler.setHorizontalAlignment(JLabel.CENTER);
            SpringLayout layout = new SpringLayout();
            panel.setLayout (layout);
            panel.add(filler);
            return panel;
        }

        
    protected JComponent makeViewMenu () {
            JPanel panel = new JPanel(false);
            JLabel filler = new JLabel("");
            filler.setHorizontalAlignment(JLabel.CENTER);
            SpringLayout layout = new SpringLayout();
            panel.setLayout (layout);
            panel.add(filler);
            return panel;
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
    private JTextArea boxQuestion;
    private List<JTextField> boxAnswer;
    private List<JTextField> boxAnswerWeight;
    private int currentTypeSelected;
    
}
