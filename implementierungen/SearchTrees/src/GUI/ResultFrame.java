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
 * Fenster zur Darstelung des Ergebnis, eines Laufzeittests
 */
public class ResultFrame extends JFrame {
    private JLabel tango;
    private JLabel splay;
    void setTime (long timeTango, long timeSplay){
        tango.setText("Zeit Tango " + timeTango / 1000 + " s");
        splay.setText("Zeit Splay " + timeSplay / 1000 + " s");
     
    }
    ResultFrame(String testName){ 
      
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
