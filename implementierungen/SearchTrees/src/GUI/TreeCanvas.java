/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author andre
 */
public class TreeCanvas extends Canvas{
    private JFrame frame;
    
    public TreeCanvas (){
    setBackground (Color.LIGHT_GRAY);  
    
    setSize(getMaximumSize());  
    frame= new JFrame("Dynamische Suchbäume");  
    frame.add(this);  
    frame.setLayout(null);  
    frame.setSize(getMaximumSize());  
    frame.setVisible(true);  
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.red);  
        g.fillOval(75, 75, 150, 75);  
    }
   
}
