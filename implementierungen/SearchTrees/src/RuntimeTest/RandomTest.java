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
 * Führt einen Laufzeittest zwischen dem Tango Baum und dem Splay Baum durch, bei dem eine zufällig erzeugte Zugriffsfolge verwendet
 * wird.
 * @author andreas
 * 
 */
public class RandomTest extends RuntimeTest {
  private final int  numOfNodes;
  private final int lengthOfSeq;
 
  /**
     * @param n Anzahl der Knoten der BSTs.
     * @param r Länge der verwendeten Zugriffsfolge in Millionen.
     * @param rf Frame mit dem das Ergebnis dargestellt wird.
     * @param d Details anzeigen. Falls "true" werden die Anzahl der Veränderungen bei preferred children und die Anzahl der Rotationen beim Splay Baum mit ausgewertet. 
   */  
    public RandomTest(int n, int r, RuntimeFrame rf, boolean d){
        super(rf, "Ergebnis Random", d);
        numOfNodes = n; 
        lengthOfSeq = r;
    } 
    
  @Override
   /**
   * Bildet die Zugriffsfolge und führt den Laufzeittest aus.
   * * @throws BuildAuxTreeFaildException Fehler beim Erzeugen des Tango Baumes.
   * @return Daten zum Laufzeittest. [0]Zeit des Tango Baumes, [1]Zeit des Splay Baumes, [2]Anzahl der Veränderungen bei preferred Children im Referenzbaum, [3] Anzahl der Rotationen beim Splay Baum. 
   */          
     long[] startTest()  throws BuildAuxTreeFaildException{
        int lOs = 1000000 * this.lengthOfSeq;
        List<Integer> accessSequenz = new LinkedList<Integer>();
        List<Integer> keyList = new LinkedList<Integer>();
        for (int i = 1; i <= numOfNodes ; i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        for (int i = 1; i < lOs +1; i++){
            if ( exit)
                return null;
            accessSequenz.add(randomNumber(numOfNodes));
           
        }
        return runtimeTest(keyList, accessSequenz, 1);
     }
}
