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
 * Führt einen Laufzeittest zwischen dem Tango Baum und dem Splay Baum durch, bei dem entweder eine aufsteigend 
 * sortierte, eine absteigend sortierte oder eine geschachtelt sortierte Zugriffsfolge verwendet wird. 
 * @author andreas
 * 
 */

 

public class SortedTest extends RuntimeTest {
  private final int numOfNodes;
  private final int typ;
    /**
     * Führt einen Laufzeittest mit sortierten Zugriffsfolgen aus.
     * @param n Anzahl der Knoten.
     * @param t Untertyp des Laufzeittests: 1 - aufsteigend, 2 - absteigend, 3 - geschachtelt in Einerschritten
     * @param rf Frame mit dem das Ergebnis dargestellt wird.
     * @param d Details anzeigen. Wenn "true" werden die Anzahl der Veränderungen bei preferred children im Referenzbaum und die Anzahl der Rotationen beim Splay Baum mit ausgewertet. 
     */
    public SortedTest(int n, int t, RuntimeFrame rf,  boolean d){
        super(rf, "", d);
        typ = t;
        if(typ == 1)
            testName = "aufsteigend sortiert";
        else if(typ == 2)
            testName = "absteigend sortiert";
        else{
            testName = "auf- / absteigend sortiert";
        }
        numOfNodes = n;
       
        
    } 
    
  @Override
   /**
   * Bildet die Zugriffsfolge und führt den Laufzeittest aus.
   *@throws BuildAuxTreeFaildException Fehler beim Erzeugen des Tango Baumes.
   * @return Daten zum Laufzeittest. [0]Zeit des Tango Baumes, [1]Zeit des Splay Baumes, [2]Anzahl der Veränderungen bei preferred Children im Referenzbaum, [3] Anzahl der Rotationen beim Splay Baum. 
   */           
     long[] startTest()  throws BuildAuxTreeFaildException{
         List<Integer> accessSequenz = new LinkedList<>();
        List<Integer> keyList = new LinkedList<>();
        for (int i = 1; i <= numOfNodes ; i++){
            keyList.add(i);
        }
        if (typ == 1){
             
            for (int i = 1; i <= numOfNodes; i++){
                if ( exit)
                    return null;
                accessSequenz.add(i);
            } 
        }
        else if(typ== 2)
            
             for (int i = numOfNodes; i > 0; i--){
                if ( exit)
                    return null;
                accessSequenz.add(i);
            } 
        else{
             for (int i = 1; i <= numOfNodes; i++){
                if ( exit)
                    return null;
                accessSequenz.add(i);
                accessSequenz.add(numOfNodes - i + 1 );
            }    
        }    

        return runtimeTest(keyList, accessSequenz, 1);
     }
}
