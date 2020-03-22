/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author andreas
 */
public class GUICanvas extends Canvas {
    private GUITree tree;
    
    GUICanvas (GUITree t){
        tree = t;
    }
    
     @Override
    public void paint(Graphics g){
        super.paint(g);

        g.setColor(Color.red);  
        g.fillOval(75, 75, 1500, 1500);  
        g.setColor(Color.black);  
        g.fillOval(75, 75, 1500, 1500);
        
    }
    
    
}
