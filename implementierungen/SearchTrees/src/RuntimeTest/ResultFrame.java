/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuntimeTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/** *
 * @author andreas
 */
public class ResultFrame extends JFrame {
    private JLabel tango;
    private JLabel splay;
    void setTime (long timeTango, long timeSplay){
        tango.setText("Zeit Tango " + timeTango / 1000 + " s");
        splay.setText("ZeitSplay " + timeSplay / 1000 + " s");
     
    }
    ResultFrame(){ 
      
       tango = new JLabel ("Zeit Tango " + "____"+ " s");
       splay = new JLabel ("Zeit Splay " + "____"+ " s");
       initFrame();
       
    }
   
    private void initFrame(){
        setTitle("Ergebnis");
        setBackground (Color.LIGHT_GRAY);
        setDefaultCloseOperation(HIDE_ON_CLOSE );
        setSize(400,400);
        setLayout(new GridLayout(2,1));
        add(tango);
        add(splay);
        setVisible(true);
       
    }
}