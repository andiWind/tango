/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author andreas
 */
public class GUI extends JFrame{
    private GUITree guiTree;
    private GUICanvas guiCanvas;
    private JMenuBar guiMenuBar; 
    private JMenu menu;
    private JMenuItem aktionOpen;
    private JDialog aktion;
    
        
        
       
    
    private void initCanvas(){
        
        add(guiCanvas, BorderLayout.CENTER);
       
        guiCanvas.setSize(this.getSize().width, this.getSize().height);
        guiCanvas.setVisible(true);
       
    }
   
    
    public GUI (GUITree guiT){
        guiTree = guiT;
        guiCanvas = new GUICanvas(guiTree);
        setTitle("Dynamische Suchbäume");
        setBackground (Color.LIGHT_GRAY);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE );
        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        
        guiCanvas.setSize(this.getSize().width, this.getSize().height);
        guiCanvas.setVisible(true);
        add(guiCanvas, BorderLayout.CENTER);
      
        
        guiMenuBar = new JMenuBar();
        menu = new JMenu("Menü");
        aktionOpen = new JMenuItem("Aktion");
        menu.add(aktionOpen);
        aktionOpen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    
                    aktion.setVisible(true);
                    System.out.print("hallo");
                }
            }
        );
        
        
        
        
       // openAktion.addActionListener();
        guiMenuBar.add(menu);
        guiMenuBar.setVisible(true);
        this.setJMenuBar(guiMenuBar);
       
        
        aktion = new JDialog(this);
        aktion.setDefaultCloseOperation(HIDE_ON_CLOSE );
        aktion.setVisible(true);
      
        
        setVisible(true); 
    }
    
   
}
