/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 *
 * @author andreas
 */
public class RuntimeFrame  extends JFrame{
    private int numOfNodes;
    private int lenOfSeq;
    private final JPanel  northPanel;
    private final JPanel  randomPanel;
    private final JSlider numOfNodesSli;
    private final JTextField numOfNodesText;
    private final JSlider lenOfSeqSli;
    private final JTextField lenOfSeqText;
    RuntimeFrame(){  
        numOfNodes = 1000;
        lenOfSeq = 1000;
        lenOfSeqSli = new JSlider();
        lenOfSeqSli.setMinimum(1);
        lenOfSeqSli.setMaximum(Integer.MAX_VALUE);
        lenOfSeqSli.setValue(numOfNodes);
        lenOfSeqSli.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lenOfSeq = lenOfSeqSli.getValue();
                lenOfSeqText.setText("" + lenOfSeq);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
              
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        
        numOfNodesSli = new JSlider();
        numOfNodesSli.setMinimum(1);
        numOfNodesSli.setMaximum(Integer.MAX_VALUE);
        numOfNodesSli.setValue(numOfNodes);
        numOfNodesSli.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                numOfNodes = numOfNodesSli.getValue();
                numOfNodesText.setText("" + numOfNodes);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
              
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
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
 
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        northPanel.add(new JLabel("Grundauswahl der Zugriffsfolge für den Laufzeittest:"));
        JComboBox<String> comboBox = new JComboBox();
        comboBox.addItem("randomAccess");
        comboBox.addItem("staticFinger");
        comboBox.addItem("dynamicFinger");
        comboBox.addItem("workingSet");
        comboBox.addItem("bitReversalPermutation");
        northPanel.add(comboBox);
        randomPanel = new JPanel();
        randomPanel.setLayout(new GridLayout(2, 3));
        randomPanel.add(numOfNodesSli);
        randomPanel.add(numOfNodesText);
        randomPanel.add(new JLabel("Anzahl der Knoten:"));
        randomPanel.add(lenOfSeqSli);
        randomPanel.add(lenOfSeqText);
        randomPanel.add(new JLabel("Länge der Zugriffsfolge:"));
        initFrame();
    }
     private void initFrame(){
        setTitle("Runtime Test");
        setBackground (Color.LIGHT_GRAY);
        setDefaultCloseOperation(HIDE_ON_CLOSE );
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);
        add(randomPanel, BorderLayout.CENTER);
   
        
    }
}
