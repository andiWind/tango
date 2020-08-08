/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import RuntimeTest.RuntimeTest;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.util.LinkedList;
import java.util.List;
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
    private List<Integer> workingSet;
    private List<String> workingSetStrings;
    private int numOfNodes;
    private int lenOfSeq;
    private int lenOfBRP;
    private int dynFinger;
    private JLabel workingSetLabel;
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
    private final JTextField lenOfSeqText;
    private final JTextField workingSetText;
    private final JButton startButton;
    private final JButton workingSetButton;
    private final JComboBox<String> comboBox ;   
    private RuntimeTest tester;
    RuntimeFrame(){ 
        numOfNodes = 1000;
        lenOfSeq = 1;
        lenOfBRP = 1;
        activePanel = "randomAccess";
        dynFinger = 1;
        startButton = new JButton();
        startButton.setText("Start");
        RuntimeFrame thisFrame = this;
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              if (startButton.getText().equals("Abbruch")){
                  tester.setExit();
                  startButton.setText("Start");
                  tester = null;
              }    
              else if ((tester == null || tester.getResult() != null) ){
                     
                    switch(activePanel){
                        case ("randomAccess"):
                            tester = new RuntimeTest("randomAccess", numOfNodes,  -1, null, thisFrame, lenOfSeq );
                            break;
                        case ("staticFinger"):
                          tester = new RuntimeTest("staticFinger", numOfNodes,  -1, null, thisFrame, lenOfSeq );
                          break;
                        case ("dynamicFinger"):
                         tester = new RuntimeTest("dynamicFinger", numOfNodes, dynFinger, null, thisFrame, lenOfSeq );
                           break;
                        case ("workingSet"):
                            buildWorkingSet();
                            if(!workingSet.isEmpty()){
                               tester = new RuntimeTest("workingSet", numOfNodes, -1, workingSet, thisFrame, lenOfSeq );
                               workingSet = new LinkedList();
                               workingSetStrings = new LinkedList();
                            }    
                            break;
                        case ("bitReversalPermutation"):
                             tester = new RuntimeTest("bitReversalPermutation", lenOfBRP, -1,  null, thisFrame, -1 );
                             break;
                    }
                    tester.start();
                    startButton.setText("Abbruch");
                }
            }
        });
       
        workingSetButton = new JButton();
        workingSetButton.setText("Hinzufügen");
        workingSetButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                workingSetStrings.add(workingSetText.getText());  
                workingSetLabel.setText("workingSet besteht aus " +workingSetStrings.size() + " Bereichen." );
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
                numOfNodesText.setText("" + setPointsInNumOfNodes(String.valueOf(numOfNodes)));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });
        
        numOfNodesText = new JTextField();
        numOfNodesText.setColumns(20);
        numOfNodesText.setText("" + setPointsInNumOfNodes(String.valueOf(numOfNodes)));
        numOfNodesText.setFont(new Font("", 1, 20));
       
        numOfNodesText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   int  value = Integer.parseInt(numOfNodesText.getText().replaceAll("\\.", ""));
                   if (value > -1){
                        numOfNodes = value;
                        numOfNodesSli.setValue(numOfNodes);
                        numOfNodesText.setText(setPointsInNumOfNodes(String.valueOf(numOfNodes)));
                   }
                   else{
                        numOfNodesText.setText( setPointsInNumOfNodes(String.valueOf(numOfNodes)));
                   }
                }    
                catch(Exception ex){
                    numOfNodesText.setText(setPointsInNumOfNodes(String.valueOf(numOfNodes)));
                    
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
                   int  value = Integer.parseInt(numOfNodesText.getText().replaceAll("\\.", ""));
                   if (value > -1){
                        numOfNodes = value;
                        numOfNodesSli.setValue(numOfNodes);
                        numOfNodesText.setText(setPointsInNumOfNodes(String.valueOf(numOfNodes)));
                   }
                   else{
                        numOfNodesText.setText(setPointsInNumOfNodes(String.valueOf(numOfNodes)));
                   }
                }    
                catch(Exception ex){
                    numOfNodesText.setText(setPointsInNumOfNodes(String.valueOf(numOfNodes)));
                    
                }
            }
        });
        lenOfSeqText = new JTextField();
        lenOfSeqText.setFont(new Font("", 1, 20));
        lenOfSeqText.setColumns(20);
        lenOfSeqText.setText("" + lenOfSeq);
        lenOfSeqText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   int value = Integer.parseInt(lenOfSeqText.getText());
                   if(value > -1){
                       lenOfSeq = Integer.parseInt(lenOfSeqText.getText());
                   }
                   else
                    lenOfSeqText.setText("" + lenOfSeq);   
                   
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
                   int value = Integer.parseInt(lenOfSeqText.getText());
                   if(value > -1){
                       lenOfSeq = Integer.parseInt(lenOfSeqText.getText());
                   }
                   else
                    lenOfSeqText.setText("" + lenOfSeq);   
                   
                }    
                catch(Exception ex){
                    lenOfSeqText.setText("" + lenOfSeq);
                    
                }
            }
        });
        lenOfBRPText = new JTextField();
        lenOfBRPText.setFont(new Font("", 1, 20));
        lenOfBRPText.setColumns(20);
        lenOfBRPText.setText("" + lenOfBRP);
        lenOfBRPText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   int value = Integer.parseInt(lenOfBRPText.getText());
                   if (value > -1)
                        lenOfBRP = Integer.parseInt(lenOfBRPText.getText());
                   else
                       lenOfBRPText.setText("" + lenOfBRP);
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
                   int value = Integer.parseInt(lenOfBRPText.getText());
                   if (value > -1)
                        lenOfBRP = Integer.parseInt(lenOfBRPText.getText());
                   else
                       lenOfBRPText.setText("" + lenOfBRP);
                }    
                catch(Exception ex){
                    lenOfBRPText.setText("" + lenOfBRP);
                    
                }
            }
        });
        dymFingerText = new JTextField();
        dymFingerText.setFont(new Font("", 1, 20));
        dymFingerText.setColumns(20);
        dymFingerText.setText("" + dynFinger);
        dymFingerText.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int value = Integer.parseInt(dymFingerText.getText());
                   if (value > -1) 
                        dynFinger = Integer.parseInt(dymFingerText.getText());
                   else
                       dymFingerText.setText("" + dynFinger);
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
                    int value = Integer.parseInt(dymFingerText.getText());
                   if (value > -1) 
                        dynFinger = Integer.parseInt(dymFingerText.getText());
                   else
                       dymFingerText.setText("" + dynFinger);
                }    
                catch(Exception ex){
                    dymFingerText.setText("" + dynFinger);
                    
                }
            }
        });
        workingSetText = new JTextField();
        workingSetText.setFont(new Font("", 1, 20));
        workingSetText.setColumns(43);
        workingSetText.setText("x - y");
        workingSetLabel = new JLabel();
        workingSetLabel.setText("workingSet besteht aus 0 Bereichen.");
        workingSet = new LinkedList();
        workingSetStrings = new LinkedList();
        
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
        randomPanel.add(new JLabel("Länge der Zugriffsfolge"));
        randomPanel.add(lenOfSeqText);
        randomPanel.add(new JLabel("(In Millionen)"));
    }
    private void buildBRPPanel(){
        bitReversalPermutationPanel = new JPanel();
        bitReversalPermutationPanel.setLayout(new GridLayout(1, 2));
        bitReversalPermutationPanel.add(lenOfBRPText);
        bitReversalPermutationPanel.add(new JLabel("Anzahl der Bits"));
    }
    public void setResult(long[] result, String testName){
          startButton.setText("Start");
          ResultFrame resultFrame = new ResultFrame(testName);
          resultFrame.setTime(result[0], result[1]);
    }
    private void buildStaticFingerPanel(){
        staticFingerPanel = new JPanel();
        staticFingerPanel.setLayout(new GridLayout(2, 3));
        staticFingerPanel.add(numOfNodesSli);
        staticFingerPanel.add(numOfNodesText);
        staticFingerPanel.add(new JLabel("Anzahl der Knoten"));
        staticFingerPanel.add(new JLabel("Länge der Zugriffsfolge"));
        staticFingerPanel.add(lenOfSeqText);
        staticFingerPanel.add(new JLabel("(In Millionen)"));
    }
    private void buildDinamicFingerPanel(){
        dynamicFingerPanel = new JPanel();
        dynamicFingerPanel.setLayout(new GridLayout(3, 3));
        dynamicFingerPanel.add(numOfNodesSli);
        dynamicFingerPanel.add(numOfNodesText);
        dynamicFingerPanel.add(new JLabel("Anzahl der Knoten"));
        dynamicFingerPanel.add(new JLabel("Länge der Zugriffsfolge"));
        dynamicFingerPanel.add(lenOfSeqText);
        dynamicFingerPanel.add(new JLabel("(In Millionen)"));
        dynamicFingerPanel.add(dymFingerText);
        dynamicFingerPanel.add(new JLabel("Abstand der Schlüssel"));
    }
    private void buildWorkingSetPanel(){
        workingSetPanel = new JPanel();
        workingSetPanel.setLayout(new GridLayout(3, 3));
        workingSetPanel.add(numOfNodesSli);
        workingSetPanel.add(numOfNodesText);
        workingSetPanel.add(new JLabel("Anzahl der Knoten"));
        workingSetPanel.add(new JLabel("Länge der Zugriffsfolge"));       
        workingSetPanel.add(lenOfSeqText);
        workingSetPanel.add(new JLabel("(In Millionen)"));
        workingSetPanel.add(workingSetText);
        workingSetPanel.add(workingSetButton);        
        workingSetPanel.add(workingSetLabel);
       
    }
    private void buildWorkingSet(){
        boolean[] workingSetBool = new boolean[numOfNodes + 1];
        int first = 0;
        int second = 0;
        for(String text : workingSetStrings){
            text = text.trim();
            if(text.lastIndexOf("-") < 0){
                try{
                    int value = Integer.parseInt(text);
                    if(value > 0 && value < numOfNodes && !workingSetBool[value]){
                        workingSet.add(value);
                        workingSetBool[value] = true;
                    }    
                }
                catch(Exception e){} 
            }
            else{
               boolean error = false; 
               
               try{   
                   first  = Integer.parseInt(text.substring(0, text.lastIndexOf("-")).trim());
                   second = Integer.parseInt(text.substring(text.lastIndexOf("-") +1 ).trim());
                }
                catch(Exception e){ error = true;}  
                if(!error ){
                    while (!(first > second) && (first <= numOfNodes)){
                        if(first > 0  && !workingSetBool[first]){
                            workingSet.add(first);
                            workingSetBool[first] = true;
                        }  
                    first++;
                    }
                }
            }
            
            
        }
    }
    private String setPointsInNumOfNodes(String nON){
        String ret = "";
        for (int i = 1; i <= nON.length(); i++){
            ret = nON.substring(nON.length() - i , nON.length() - i + 1 ) + ret;
            if (i  % 3 == 0 && i < nON.length())
                ret = "." + ret;
        }
        return ret;
    }
    
}
