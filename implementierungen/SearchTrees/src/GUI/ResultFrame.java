/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/** *
 * @author andreas
 * Fenster zur Darstellung des Ergebnis, eines Laufzeittests
 */

public class ResultFrame extends JFrame {
    private  final JLabel tango;
    private final JLabel splay;
    
    private String getTimeString(long time){
        long value = time % 1000;
        if (value != 0){
            if(value % 100 > 49){
                value = value - (value % 100 ) + 100;
            }
            else
                value = value - (value % 100 );
        }
        if (value != 0){
            value = value / 100;
        }
        String t = time / 1000 + "," + value;
        return time / 1000 + "," + value;
    }

    
    void setTime (long timeTango, long timeSplay){
        tango.setText("Zeit Tango " + getTimeString(timeTango) +  " s");
        splay.setText("Zeit Splay " +  getTimeString(timeSplay) + " s");
     
    }
    ResultFrame( String testName){ 
      
       tango = new JLabel ("Zeit Tango " + "____"+ " s");
       splay = new JLabel ("Zeit Splay " + "____"+ " s");
       initFrame(testName);
       
    }
   
    private void initFrame(String testName){
        setTitle("Ergebnis " + testName);
        setBackground (Color.LIGHT_GRAY);
        setDefaultCloseOperation(HIDE_ON_CLOSE );
        setSize(400,200);
        setLayout(new GridLayout(2,1));
        add(tango);
        add(splay);
        setVisible(true);
       
    }
}
