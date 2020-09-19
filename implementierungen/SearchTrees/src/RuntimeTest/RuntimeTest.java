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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andreas
 * Eine Instanz führt einen Laufzeit Test zwischen einem TangoTree und einem SplayTree durch. Der Laufzeittest wird von einem eigenen Thread 
 * ausgeführt.
 */
public abstract class RuntimeTest extends Thread {
   protected boolean exit;
   protected String testName;
   private long[] result;
   private final boolean details;
   private RuntimeFrame runtimeFrame;
   

  
   protected RuntimeTest(RuntimeFrame rf, String tn, boolean d){
       runtimeFrame = rf;
       testName = tn;
       details = d;
   }
   
   @Override
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
     * @return Das Ergebnis des Laufzeittest
     */
    public long[] getResult(){
        return result;
    }
    /**
     * 
     * @param t Name des auszuführenden Test.
     * @param p1 Erster Parameter der an die Methode zur Testausführung übergeben werden soll.
     * @param p2 Zweiter Parameter der an die Methode zur Testausführung übergeben werden soll.
     * @param workSet "set" Parameter der Methode "workingSet" 
     * @param rf Objekt mit dem das Ergebnis dargestellt werden soll.
     * @param r  Länge der Zugriffsfolge in Millionen Schritten.
     */


    
    /**
     * Führt einen Laufzeittest mit sortierten Zugriffsfolgen aus.
     * @param numOfNodes Anzahl der Knoten.
     * @param typ 1 -> aufsteigend, 2 -> absteigend, 3 -> geschachtelt in Einerschritten
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
     */
    private long[] sorted (int numOfNodes, int typ) throws BuildAuxTreeFaildException{
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
     /**
     * Führt einen Laufzeittest mit einer zufällig erzeugten Zugriffsfolge aus.
     * @param numOfNodes Anzahl der Knoten.
     * @param repeat Länge der Zugriffsfolge in Millionen.
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
     */
    private long[] randomAccess (int numOfNodes, int repeat) throws BuildAuxTreeFaildException{
        int lengthOfSeq = 1000000 * repeat;
        List<Integer> accessSequenz = new LinkedList<Integer>();
        List<Integer> keyList = new LinkedList<Integer>();
        for (int i = 1; i <= numOfNodes ; i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        for (int i = 1; i < lengthOfSeq +1; i++){
            if ( exit)
                return null;
            accessSequenz.add(randomNumber(numOfNodes));
           
        }
        return runtimeTest(keyList, accessSequenz, 1);

    }
     /**
     *  Führt einen Laufzeittest mit einer auf die dynamicFinger Eigenschaft zugeschnittenen Zugriffsfolge aus.
     * @param numOfNodes Anzahl der Knoten.
     * @param repeat Länge der Zugriffsfolge in Millionen.
     * @param keyDistanz Abstand der Schlüssel zwischen zwei aufeinanderfolgnder access() Operationen.
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
     */
    private long[] dynamicFinger (int numOfNodes, int keyDistanz, int repeat) throws BuildAuxTreeFaildException{
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
                value += keyDistanz;
                if(value + keyDistanz > numOfNodes)
                    reversal = true;
            }    
            else{
                value -= keyDistanz;
                if(value - keyDistanz < 1)
                    reversal = false;
            }    
             
        }
        
        return runtimeTest(keyList, accessSequenz, repeat);
    
    }
    /**
     *  Führt einen Laufzeittest mit einer auf die staticFinger Eigenschaft zugeschnittenen Zugriffsfolge aus.
     * @param numOfNodes Anzahl der Knoten.
     * @param repeat Länge der Zugriffsfolge in Millionen.
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
     */
   /**
     *  Führt einen Laufzeittest mit einer auf die staticFinger Eigenschaft zugeschnittenen Zugriffsfolge aus.
     * @param numOfNodes Anzahl der Knoten.
     * @param repeat Länge der Zugriffsfolge in Millionen.
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
     */
    
    /**
     *  Führt einen Laufzeittest mit einer bitReversalPermutation aus..
     * @param numOfBits Anzahl der Bitstellen zu denen die bitReversalPermutation generiert wird.
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
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
    
     protected int randomNumber (int max){
        Double random = Math.random() * max;  
        int value = (int) Math.round(random); 
        if (value <= random) //immer aufrunden x.0 -> x + 1, da 1, der min Wert ist  
            value++;
        return value;
    }
     /**
      * Gestarteten Laufzeittest abbrechen
      */
    public void setExit(){
        exit = true;
        
    } 
}





