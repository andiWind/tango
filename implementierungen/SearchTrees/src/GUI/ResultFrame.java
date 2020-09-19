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
 * Fenster zur Darstellung des Ergebnisses eines Laufzeittests.
 */

public class ResultFrame extends JFrame {
    private  final JLabel tangoTime;
    private  final JLabel tangoCount;
    private final JLabel splayTime;
    private final JLabel splayCount;
    
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

   /**
    * Überträgt die im Array result enthaltenen Informationen ins Ergebnisfenster. 
    * @param result Array mit den Ergebnissen eines Laufzeittests.
    */ 
    void setValues (long[] result){
        if(result.length < 4){
            if(result[0] == -1)
                tangoTime.setText("Fehler beim bilden des Tango Baumes.");
            else
                tangoTime.setText("Zu wenig Speicher vorhanden.");
            add(tangoTime);
            return;
        }
        
        tangoTime.setText("Zeit Tango " + getTimeString(result[0]) +  " s");
        splayTime.setText("Zeit Splay " +  getTimeString(result[1]) + " s");
        if (result[2] != -1){
            tangoCount.setText(setPoints(""+ result[2]) +  " Veränderungen  bei preferred Children.");
            splayCount.setText(setPoints(""+ result[3]) +  " Rotationen beim Splay Baum. Das Auswerten dieses Wertes hat einen leichten negativen Einfluss auf die Laufzeit des Splay Baumes.");
            add(tangoTime);
            add(splayTime);
            add(tangoCount);
            add(splayCount);
        }
     
    }
    /**
     * Constructor
     * @param testName Name der als Titel des Ergebnisfensters verwendet wird.
     */
    ResultFrame( String testName){ 
      
       tangoTime = new JLabel ("Zeit Tango " + "____"+ " s");
       splayTime = new JLabel ("Zeit Splay " + "____"+ " s");
       tangoCount = new JLabel();
       splayCount = new JLabel();
       initFrame(testName);
       
    }
   
    private void initFrame(String testName){
        setTitle("Ergebnis " + testName);
        setBackground (Color.LIGHT_GRAY);
        setDefaultCloseOperation(HIDE_ON_CLOSE );
        setSize(900,200);
        setLayout(new GridLayout(4,1));
        setVisible(true);
       
    }
    private String setPoints(String num){
        String ret = "";
        for (int i = 1; i <= num.length(); i++){
            ret = num.substring(num.length() - i , num.length() - i + 1 ) + ret;
            if (i  % 3 == 0 && i < num.length())
                ret = "." + ret;
        }
        return ret;
    }
}
