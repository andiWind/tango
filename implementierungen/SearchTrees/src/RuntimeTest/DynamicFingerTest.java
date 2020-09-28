/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuntimeTest;

import GUI.RuntimeFrame;
import TangoTree.BuildAuxTreeFaildException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Führt einen Laufzeittest zwischen dem Tango Baum und dem Splay Baum durch, bei dem eine Zugriffsfolge verwendet
 * wird, bei der zwei aufeinanderfolgende Schlüssel einen festen Abstand haben.
 * @author andreas
 */
public class DynamicFingerTest extends RuntimeTest {
  private final int numOfNodes;
  private final int distance;
  private final int repeat;  
    /**
     * @param n Anzahl der Knoten der BSTs
     * @param d Fester Abstand zwischen zwei aufeinanderfolgender Schlüssel
     * @param r Länge der verwendeten Zugriffsfolge in Millionen.
     * @param rf Frame mit dem das Ergebnis dargestellt wird.
     * @param de Details anzeigen. Fallse "true" werden die Anzahl der Veränderungen bei preferred children und die Anzahl der Rotationen beim Splay Baum mit ausgewertet. 
     */
  
    public DynamicFingerTest(int n, int d,  int r, RuntimeFrame rf, boolean de){
        super(rf, "Ergebnis DynamicFinger", de);
        numOfNodes = n;
        distance = d;
        repeat  = r; 
        
    } 
    
  @Override
    
  /**
   * Bildet die Zugriffsfolge und führt den Laufzeittest durch.
   * @return Daten zum Laufzeittest. [0]Zeit des Tango Baumes, [1]Zeit des Splay Baumes, [2]Anzahl der Veränderungen bei preferred Children im Referenzbaum, [3] Anzahl der Rotationen beim Splay Baum. 
   */          
     long[] startTest()  throws BuildAuxTreeFaildException{
          int lengthOfSeq = 1000000;
        List<Integer> accessSequenz = new LinkedList<Integer>();
        List<Integer> keyList = new LinkedList<Integer>();
        for (int i = 1; i <= numOfNodes ; i++){
            if ( exit)
                return null;
            keyList.add(i);
            
        }
       
        int value = 1;
        boolean reversal = false;
        for (int i = 1; i < lengthOfSeq +1; i++){
            if ( exit)
                return null;
            accessSequenz.add(value);
            if (!reversal){
                value += distance;
                if(value + distance > numOfNodes)
                    reversal = true;
            }    
            else{
                value -= distance;
                if(value - distance < 1)
                    reversal = false;
            }    
             
        }
        
        return runtimeTest(keyList, accessSequenz, repeat);
     }
}
