/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import RuntimeTest.Tester;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


/**
 *
 * @author andreas
 */
public class RuntimeFrame  extends JFrame{
    private int numOfNodes;
    private int lenOfSeq;
    private int lenOfBRP;
    private int dynFinger;
    private String activePanel ;
    private final JPanel  northPanel;
    private JPanel  randomPanel;
    private JPanel  staticFingerPanel;
    private JPanel  dynamicFingerPanel;
    private JPanel  workingSetPanel;
    private JPanel  bitReversalPermutationPanel;
    private final JSlider numOfNodesSli;
    private final JTextField numOfNodesText;
    private final JTextField dymFingerText;
    private final JTextField lenOfBRPText;
    private final JSlider lenOfSeqSli;
    private final JTextField lenOfSeqText;
    private final JButton startButton;
    private final JComboBox<String> comboBox ;
    private Tester tester;
    RuntimeFrame(){ 
        numOfNodes = 1000;
        lenOfSeq = 1000;
        lenOfBRP = 1;
        activePanel = "randomAccess";
        dynFinger = 1;
        startButton = new JButton();
        startButton.setText("Start");
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              if ((tester == null || tester.getResult() != null) ){
                  switch(activePanel){
                     case ("randomAccess"):
                         tester = new Tester("randomAccess", numOfNodes, lenOfSeq, -1);
                         break;
                    case ("staticFinger"):
                         add(staticFingerPanel, BorderLayout.CENTER);
                         break;
                    case ("dynamicFinger"):
                         tester = new Tester("dynamicFinger", numOfNodes, lenOfSeq, dynFinger);
                         break;
                    case ("workingSet"):
                         add(workingSetPanel, BorderLayout.CENTER);
                         break;
                    case ("bitReversalPermutation"):
                         tester = new Tester("bitReversalPermutation", lenOfBRP, -1, -1);
                         break;
                 }

                  tester.start();
                }
            }
        });
        lenOfSeqSli = new JSlider();
        lenOfSeqSli.setMinimum(1);
        lenOfSeqSli.setMaximum(Integer.MAX_VALUE);
        lenOfSeqSli.setValue(numOfNodes);
        lenOfSeqSli.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                lenOfSeq = lenOfSeqSli.getValue();
                lenOfSeqText.setText("" + lenOfSeq);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });
       
        numOfNodesSli = new JSlider();
        numOfNodesSli.setMinimum(1);
        numOfNodesSli.setMaximum(Integer.MAX_VALUE);
        numOfNodesSli.setValue(numOfNodes);
        numOfNodesSli.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                numOfNodes = numOfNodesSli.getValue();
                numOfNodesText.setText("" + numOfNodes);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });
        
        numOfNodesText = new JTextField();
        numOfNodesText.setColumns(20);
        numOfNodesText.setText("" + lenOfSeq);
        numOfNodesText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   lenOfSeq = Integer.parseInt(numOfNodesText.getText());
                   numOfNodesSli.setValue(lenOfSeq);
                }    
                catch(Exception ex){
                    numOfNodesText.setText("" + lenOfSeq);
                    
                }
            }
        });
        numOfNodesText.addFocusListener(new FocusListener() { 
    
            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                   numOfNodes = Integer.parseInt(numOfNodesText.getText());
                   numOfNodesSli.setValue(numOfNodes);
                }    
                catch(Exception ex){
                    numOfNodesText.setText("" + numOfNodes);
                    
                }
            }
        });
        lenOfSeqText = new JTextField();
        lenOfSeqText.setColumns(20);
        lenOfSeqText.setText("" + numOfNodes);
        lenOfSeqText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   lenOfSeq = Integer.parseInt(lenOfSeqText.getText());
                   lenOfSeqSli.setValue(lenOfSeq);
                }    
                catch(Exception ex){
                    lenOfSeqText.setText("" + lenOfSeq);
                    
                }
            }
        });
        lenOfSeqText.addFocusListener(new FocusListener() { 
    
            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                   lenOfSeq = Integer.parseInt(lenOfSeqText.getText());
                   lenOfSeqSli.setValue(lenOfSeq);
                }    
                catch(Exception ex){
                    lenOfSeqText.setText("" + lenOfSeq);
                    
                }
            }
        });
        lenOfBRPText = new JTextField();
        lenOfBRPText.setColumns(20);
        lenOfBRPText.setText("" + lenOfBRP);
        lenOfBRPText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   lenOfBRP = Integer.parseInt(lenOfBRPText.getText());
                }    
                catch(Exception ex){
                    lenOfBRPText.setText("" + lenOfBRP);
                    
                }
            }
        });
        lenOfBRPText.addFocusListener(new FocusListener() { 
    
            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                   numOfNodes = Integer.parseInt(numOfNodesText.getText());
                   numOfNodesSli.setValue(numOfNodes);
                }    
                catch(Exception ex){
                    numOfNodesText.setText("" + numOfNodes);
                    
                }
            }
        });
        dymFingerText = new JTextField();
        dymFingerText.setColumns(20);
        dymFingerText.setText("" + dynFinger);
        dymFingerText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   dynFinger = Integer.parseInt(dymFingerText.getText());
                }    
                catch(Exception ex){
                    dymFingerText.setText("" + dynFinger);
                    
                }
            }
        });
        dymFingerText.addFocusListener(new FocusListener() { 
    
            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                   dynFinger = Integer.parseInt(dymFingerText.getText());
                }    
                catch(Exception ex){
                    dymFingerText.setText("" + dynFinger);
                    
                }
            }
        });
        
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        northPanel.add(new JLabel("Grundauswahl der Zugriffsfolge für den Laufzeittest:"));
        comboBox = new JComboBox();
        comboBox.addItem("randomAccess");
        comboBox.addItem("staticFinger");
        comboBox.addItem("dynamicFinger");
        comboBox.addItem("workingSet");
        comboBox.addItem("bitReversalPermutation");
        comboBox.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                 String item = (String) e.getItem();
                 setVisible(false);
                 remove(randomPanel);
                 remove(staticFingerPanel);
                 remove(dynamicFingerPanel);
                 remove(workingSetPanel);
                 remove(bitReversalPermutationPanel);
                 switch(item){
                     case ("randomAccess"):
                         buildRandomPanel();
                         add(randomPanel, BorderLayout.CENTER);
                         activePanel = "randomAccess";
                         break;
                    case ("staticFinger"):
                         buildStaticFingerPanel();
                         add(staticFingerPanel, BorderLayout.CENTER);
                         activePanel = "staticFinger";
                         break;
                    case ("dynamicFinger"):
                         buildDinamicFingerPanel();
                         add(dynamicFingerPanel, BorderLayout.CENTER);
                         activePanel = "dynamicFinger";
                         break;
                    case ("workingSet"):
                         buildWorkingSetPanel();
                         add(workingSetPanel, BorderLayout.CENTER);
                         activePanel = "workingSet";
                         break;
                    case ("bitReversalPermutation"):
                         buildBRPPanel();
                         add(bitReversalPermutationPanel, BorderLayout.CENTER);
                         activePanel = "bitReversalPermutation";
                         break;
                 }
                 setVisible(true);
                 
            }
        });
        northPanel.add(comboBox);
        
        
       
        
       
        
        initFrame();
    }
     private void initFrame(){
        setTitle("Runtime Test");
        setBackground (Color.LIGHT_GRAY);
        setDefaultCloseOperation(HIDE_ON_CLOSE );
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);
        buildBRPPanel();
        buildWorkingSetPanel();
        buildDinamicFingerPanel();
        buildWorkingSetPanel();
        buildStaticFingerPanel();
        buildRandomPanel();
        add(randomPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
        
    }
    private void buildRandomPanel(){
        randomPanel = new JPanel();
        randomPanel.setLayout(new GridLayout(2, 3));
        randomPanel.add(numOfNodesSli);
        randomPanel.add(numOfNodesText);
        randomPanel.add(new JLabel("Anzahl der Knoten"));
        randomPanel.add(lenOfSeqSli);
        randomPanel.add(lenOfSeqText);
        randomPanel.add(new JLabel("Länge der Zugriffsfolge"));
    }
    private void buildBRPPanel(){
        bitReversalPermutationPanel = new JPanel();
        bitReversalPermutationPanel.setLayout(new GridLayout(1, 2));
        bitReversalPermutationPanel.add(lenOfBRPText);
        bitReversalPermutationPanel.add(new JLabel("Anzahl der Bits"));
    }
    private void buildStaticFingerPanel(){
         staticFingerPanel = new JPanel();
    }
    private void buildDinamicFingerPanel(){
        dynamicFingerPanel = new JPanel();
        dynamicFingerPanel.setLayout(new GridLayout(3, 3));
        dynamicFingerPanel.add(numOfNodesSli);
        dynamicFingerPanel.add(numOfNodesText);
        dynamicFingerPanel.add(new JLabel("Anzahl der Knoten"));
        dynamicFingerPanel.add(lenOfSeqSli);
        dynamicFingerPanel.add(lenOfSeqText);
        dynamicFingerPanel.add(new JLabel("Länge der Zugriffsfolge"));
        dynamicFingerPanel.add(dymFingerText);
        dynamicFingerPanel.add(new JLabel("Abstand der Schlüssel"));
    }
     private void buildWorkingSetPanel(){
           workingSetPanel = new JPanel();
       
    }
    
}
