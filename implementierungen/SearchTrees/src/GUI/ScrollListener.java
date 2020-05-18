/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.Canvas;
import javax.swing.JScrollBar;
/**
 *
 * @author andreas
 */


public class ScrollListener implements AdjustmentListener {
    private JScrollBar vert;
    private JScrollBar hor;
    private GUICanvas canvas;
    
    
    ScrollListener(GUICanvas c, JScrollBar h ,JScrollBar v){
        canvas = c;
        vert = v;
        hor = h;
    }
    
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
       if (e.getSource() == hor)
           canvas.setScrollValue(e.getValue(), hor.getWidth(), false);
       else
           canvas.setScrollValue(e.getValue(), vert.getWidth(), true);
       canvas.repaint();
    }
  
}
