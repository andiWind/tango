/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RuntimeTest;
import GUI.RuntimeFrame;
import SplayTree.SplayTree;
import TangoTree.TangoTree;
import RedBlackTree.RedBlackTree;
import SplayTree.SplayTreeCountRot;
import TangoTree.BuildAuxTreeFaildException;
import TangoTree.TangoPrefChildCount;
import java.util.List;

/**
 *
 * @author andreas
 * Klassen deren Instanzen einen Laufzeittest zwischen dem Tango Baum und dem  Splay Baum durchführen, müssen diese Klasse erweitern. Der Laufzeittest wird in einem eigenen Thread 
 * ausgeführt.
 */
public abstract class RuntimeTest extends Thread {
   protected boolean exit;
   protected String testName;
   private long[] result;
   private final boolean details;
   private final RuntimeFrame runtimeFrame;
   

   /**
    * 
    * @param rf Frame mit dem das Ergebnis dargestellt wird.
    * @param tn Name der zur Visualisierung durchgereicht wird.
    * @param d Details anzeigen. Falls "true" werden die Anzahl der Veränderungen bei preferred children und die Anzahl der Rotationen beim Splay Baum mit ausgewertet. 
    */
   protected RuntimeTest(RuntimeFrame rf, String tn, boolean d){
       runtimeFrame = rf;
       testName = tn;
       details = d;
   }
   
   @Override
   /**
    * Startmethode des Threads
    */
    public void run () {   
        try{
            result = startTest();   
        }
        catch(BuildAuxTreeFaildException e){
            result = new long[1];
            result[0] = -1;
        }
        catch(java.lang.OutOfMemoryError e){
            result = new long[1];
            result[0] = -2;
        }
        if(!exit)
                runtimeFrame.setResult(result, testName);
   
    }
    
    
    abstract long[] startTest()  throws BuildAuxTreeFaildException;
            
    /**
     * 
     * @return  Daten zum Laufzeittest. [0]Zeit des Tango Baumes, [1]Zeit des Splay Baumes, [2]Anzahl der Veränderungen bei preferred Children im Referenzbaum, [3] Anzahl der Rotationen beim Splay Baum. 
     */
    public long[] getResult(){
        return result;
    }
  
  
 
   
    /**
     * 
     * @param keyList Schlüsselmenge der zu erzeugenden BSTs.
     * @param accessSequenz Verwendete Zugriffsfolge zum Laufzeittest.
     * @param repeat  accessSequenz wird beim Laufzeittest "repeat" mal abgearbeitet.
     * @return Daten zum Laufzeittest. [0]Zeit des Tango Baumes, [1]Zeit des Splay Baumes, [2]Anzahl der Veränderungen bei preferred Children im Referenzbaum, [3] Anzahl der Rotationen beim Splay Baum. 
     * @throws BuildAuxTreeFaildException Fehler beim Erzeugen des Tango Baumes.
     */
    protected long[] runtimeTest (List<Integer> keyList, List<Integer> accessSequenz, int repeat) throws BuildAuxTreeFaildException{
        long[] ret = new long[4];
        ret[2] = -1;
        ret[3] = -1; 
        TangoTree tangoTree = new TangoTree(keyList, RedBlackTree.class);
        long startTime = System.nanoTime();
        for (int j = 1; j <= repeat; j++){
            for(Integer i: accessSequenz ){
                if ( exit)
                    return null;
                tangoTree.access(i);
            }
        }
        ret[0] = (System.nanoTime()- startTime) / 1000000 ;
        tangoTree = null; //speicher freigeben
        SplayTree splayTree;
        SplayTreeCountRot splayTreeCountRot; 
        if (details){
            splayTreeCountRot = new SplayTreeCountRot(keyList);
            startTime = System.nanoTime();
                for (int j = 1; j <= repeat; j++){
                    for(Integer i: accessSequenz ){
                        if ( exit)
                            return null;
                        splayTreeCountRot.access(i);
                    }
                }  
            ret[1] = (System.nanoTime()- startTime) / 1000000 ;
            ret[3] = splayTreeCountRot.getNumOfRotations();
            // Zählen der Anzahl der Wechsel von prefChilds
            ret[2] = new TangoPrefChildCount(keyList).numOfPrevChildChanges(accessSequenz, repeat);
        }
        else{
            splayTree = new SplayTree(keyList);
            startTime = System.nanoTime();
                for (int j = 1; j <= repeat; j++){
                    for(Integer i: accessSequenz ){
                        if ( exit)
                            return null;
                        splayTree.access(i);
                    }
                }    
            ret[1] = (System.nanoTime()- startTime) / 1000000 ;
            }
        
        
        
        return ret;
    }
    /**
     * Gibt eine Zufallszahl zurück.
     * @param probs Hier können Wahrscheinlichkeiten zwichen 0 und 1 eigetragen werden. Diese Wahrscheinlichkeiten beziehen sich auf den jeweiligen Array Index. Der Index 0 wird nicht berücksichtigt.
     * @return Eine Zahl größer 0 und kleiner "probs.length"
     */
    protected int randomNumber (double[] probs){
        Double random = Math.random();  
        int index = 0;
        double sumProb = 0;
        while(sumProb <= random){
            index++;
            sumProb += probs[index];
        }
        return index;
    }
    /**
     *  Gibt eine Zufallszahl zurück.
     * @param max Begrenzung der Rückgabe nach oben.
     * @return Eine Zahl größer  0 und kleiner gleich "max"
     */
     protected int randomNumber (int max){
        Double random = Math.random() * max;  
        int value = (int) Math.round(random); 
        if (value <= random) //immer aufrunden x.0 -> x + 1, da 1, der min Wert ist  
            value++;
        return value;
    }
     /**
      * Einen gestarteten Laufzeittest abbrechen.
      */
    public void setExit(){
        exit = true;
        
    } 
}





