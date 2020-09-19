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
 * @author andreas
 * Führt einen Laufzeittest zwischen dem Tango Baum und dem Splay Baum mit einer Bit Reversal Permutation durch.
 */
public class BitReversalTest extends RuntimeTest {
  int numOfBits;
  
    /**
     * @param n Länge der Binärdarstellungen der Elemente der BRP.
     * @param rf Frame mit dem das Ergebnis dargestellt wird.
     * @param d Details anzeigen. Falls "true" werden die Anzahl der Veränderungen bei preferred children und die Anzahl der Rotationen beim Splay Baum mit ausgewertet. 
     */
    public BitReversalTest(int n,  RuntimeFrame rf, boolean d){
        super(rf, "Ergebnis Bit Reversal Permutation", d);
        numOfBits = n;  
    } 
    
  @Override
  /**
   * Bildet die Zugriffsfolge und führt den Laufzeittest durch.
   * @throws BuildAuxTreeFaildException Fehler beim Erzeugen des Tango Baumes
   * @return Daten zum Laufzeittest. [0]Zeit des Tango Baumes, [1]Zeit des Splay Baumes, [2]Anzahl der Veränderungen bei preferred Children im Referenzbaum, [3] Anzahl der Rotationen beim Splay Baum. 
   */        
     long[] startTest()  throws BuildAuxTreeFaildException{
       List<Integer> keyList = new LinkedList<Integer>();
        for (int i = 0; i < Math.pow(2, numOfBits); i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        List<Integer> accessSequenz = new LinkedList<Integer>();
        for(int i = 0; i < Math.pow(2, numOfBits); i++  ){
           if ( exit)
                return null;
            String bitString = Integer.toBinaryString(i);
            if (bitString.length() > numOfBits){
                bitString = bitString.substring(bitString.length() - numOfBits);
            }
            while (bitString.length() < numOfBits){
                bitString = "0" + bitString;
            }
            int value = 0;
            for (int j = numOfBits - 1; j > -1; j--){
                if (bitString.charAt(j) == '1')
                    value =(int) (value + Math.pow(2, j));
            }
            accessSequenz.add(value);
        }    
        return runtimeTest(keyList,accessSequenz, 1);
     }
}
