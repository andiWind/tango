/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newGUI;

import GUI.GUICanvas;
import GUI.I_GUITree;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/**
 *
 * @author andre
 */
public class GUI extends JFrame{
    I_GUITree guiTree;
    private void init(){
        setTitle("Dynamische Suchbäume");
        setBackground (Color.LIGHT_GRAY);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE );
        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        JLayer layer = new JLayer();
        JScrollPane scrollPane = new JScrollPane(layer);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
    public GUI (I_GUITree guiT){
        guiTree = guiT;
        init();
 
    }
}
