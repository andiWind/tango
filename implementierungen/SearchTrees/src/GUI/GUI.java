/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.SwingConstants.VERTICAL;
import static javax.swing.SwingConstants.HORIZONTAL;

/**
 *
 * @author andreas
 */
public class GUI extends JFrame{
    private I_GUITree guiTree;
    private GUICanvas canvas;
    private GUICanvas guiCanvas;
    private JMenuBar guiMenuBar; 
    private JMenu menu;
    private JMenuItem aktionOpen;
    private JDialog aktion;
    private JScrollPane scrollPane;

    private void initCanvas(){
        add(canvas, BorderLayout.CENTER);
        canvas.setSize(this.getSize().width, this.getSize().height);
        canvas.setVisible(true);
    }
   
    
    public GUI (I_GUITree guiT){
        guiTree = guiT;
        setTitle("Dynamische Suchbäume");
        setBackground (Color.LIGHT_GRAY);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE );
        setLayout(new BorderLayout());
        JPanel scrollPane = new JPanel();
        canvas = new GUICanvas(guiTree, scrollPane);
        canvas.setVisible(true);
        canvas.setSize(100000,10000);
        add(canvas, BorderLayout.CENTER);
        JScrollBar hScrollBar = new JScrollBar(HORIZONTAL, 0, 10, 0, 100000);
        hScrollBar.setValue(5000);
        hScrollBar.setUnitIncrement(100);
        JScrollBar vScrollBar = new JScrollBar(VERTICAL, 0, 10, 0, 100000);
        ScrollListener scrollListener = new ScrollListener(canvas, hScrollBar, vScrollBar);
        hScrollBar.addAdjustmentListener(scrollListener);
        vScrollBar.addAdjustmentListener(scrollListener);
        scrollPane.add(hScrollBar);
        scrollPane.add(canvas);
        add(scrollPane);
        
        guiMenuBar = new JMenuBar();
        menu = new JMenu("Menü");
        aktionOpen = new JMenuItem("Aktion");
        menu.add(aktionOpen);
        aktionOpen.addMouseListener(new MouseListener() {
              
                @Override
                 public void mouseExited(MouseEvent e) {     
                }
                @Override
                 public void mouseEntered(MouseEvent e) {
                } 
                @Override
                 public void mouseReleased(MouseEvent e) {
                }  
                @Override
                 public void mousePressed(MouseEvent e) {
                    aktion.setVisible(true);
                    //guiCanvas.repaint();
                }  
                @Override
                 public void mouseClicked(MouseEvent e) {
                }   
            }
        );
       // openAktion.addActionListener();
        guiMenuBar.add(menu);
        guiMenuBar.setVisible(true);
        this.setJMenuBar(guiMenuBar);
       
        
        aktion = new JDialog(this);
        aktion.setLayout(new GridLayout(3,3));
        aktion.setDefaultCloseOperation(HIDE_ON_CLOSE );
        aktion.setSize(400, 200);
        
        aktion.add(new JLabel("Suchen:"));
        JSpinner searchSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        aktion.add(searchSpinner);
        JButton searchButton = new JButton("OK");
        searchButton.addActionListener(new ActionListener(){
               
            public void actionPerformed(ActionEvent e) {
                guiTree.access((int)searchSpinner.getValue());
                canvas.repaint();
                } 
            }    
        );
        aktion.add(searchButton);
        
        aktion.add(new JLabel("Einfügen:"));
        JSpinner insertSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        aktion.add(insertSpinner);
        JButton insertButton = new JButton("OK");
        insertButton.addActionListener(new ActionListener(){
               
            public void actionPerformed(ActionEvent e) {
                guiTree.insert((int)insertSpinner.getValue());
                canvas.repaint();
               
                } 
            }    
        );
        aktion.add(insertButton);
        
        aktion.add(new JLabel("Löschen:"));
        JSpinner deleteSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        aktion.add(deleteSpinner);
        JButton deleteButton = new JButton("OK");
        aktion.add(deleteButton);
        deleteButton.addActionListener(new ActionListener(){
               
            public void actionPerformed(ActionEvent e) {
                guiTree.delete((int)deleteSpinner.getValue());
                canvas.repaint();
                } 
            }    
        );
        
        aktion.setVisible(true);
        setVisible(true);
        
        
    }

}
