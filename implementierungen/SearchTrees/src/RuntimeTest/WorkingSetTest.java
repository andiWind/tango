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
 * Führt einen Laufzeittest zwischen Tango Baum und dem Splay Baum durch bei dem eine Zugriffsfolge verwendet
 * wird, bei der die Working Set Property relevant wird.
 */
public class WorkingSetTest extends RuntimeTest {
  private int numOfNodes;
  private int distance;
  private int length;  
    
  /**
     * 
     * @param n Anzahl der Knoten der BSTs.
     * @param d Abstand mit dem sich die Schlüssel in der Zugriffsfolge wiederholen.
     * @param l Länge der verwendeten Zugriffsfolge in Millionen.
     * @param rf Frame mit dem das Ergebnis dargestellt wird.
     * @param de Details anzeigen. Wenn "true" werden die Anzahl der Veränderungen bei preferred children im Referenzbaum und die Anzahl der Rotationen beim Splay Baum mit ausgewertet. 
     */
    public WorkingSetTest(int n, int d,  int l, RuntimeFrame rf, boolean de){
        super(rf, "Ergebnis WorkingSet", de);
        numOfNodes = n;
        distance = d;
        length  = l; 
        
    } 
    
  @Override
   /**
   * Bildet die Zugriffsfolge und führt den Laufzeittest aus. Ein Schlüssel tritt entweder zum ersten mal in der Zugriffsfolge auf oder mit dem Abstand "distance" zum vorherigen mal.
   *@throws BuildAuxTreeFaildException Fehler beim Erzeugen des Tango Baumes.
   * @return Daten zum Laufzeittest. [0]Zeit des Tango Baumes, [1]Zeit des Splay Baumes, [2]Anzahl der Veränderungen bei preferred Children im Referenzbaum, [3] Anzahl der Rotationen beim Splay Baum. 
   */    
     long[] startTest()  throws BuildAuxTreeFaildException{
        List<Integer> accessSequenz = new LinkedList<>();
        List<Integer> keyList = new LinkedList<>();
        for (int i = 1; i <= numOfNodes ; i++){
            keyList.add(i);
        }
        int lengthOfSeq = 1000000 * length;
        int numOfSets = numOfNodes / distance;
        int setSize = numOfNodes / numOfSets;
        
        int index = 1;
        while (accessSequenz.size() <  lengthOfSeq){
            if ( exit)
                return null;
            List<Integer> set = new LinkedList<>();
            //Das Set bilden aus dem die nächsten Schlüssel hinzugefügt werden.
            while(set.size() < setSize){
                set.add(index);
                if(index <= numOfNodes / 2){
                    index = numOfNodes / 2 + index ;
                }
                else{
                    index =  index - numOfNodes / 2 + 1;
                }    
            }
            int j = 0;
            for (int i = 1; i <= lengthOfSeq / numOfSets; i++){
                accessSequenz.add(set.get(j++));
                if (j > set.size() - 1)
                    j = 0;
                if (accessSequenz.size() >= lengthOfSeq)
                    break;
            }   
        }                 
        return runtimeTest(keyList, accessSequenz, 1);
     }
    
}
