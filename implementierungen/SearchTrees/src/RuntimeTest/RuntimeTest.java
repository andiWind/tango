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
import TangoTree.BuildAuxTreeFaildException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andreas
 * Eine Instanz führt einen Laufzeit Test zwischen einem TangoTree und einem SplayTree durch. Der Laufzeittest wird von einem eigenen Thread 
 * ausgeführt.
 */
public class RuntimeTest extends Thread {
   private final String test;
   private final int par1;
   private final int par2;
   private final int repeat;
   private boolean exit;
   private final List<Integer> workingSet;
   private long[] result;
   private final RuntimeFrame runtimeFrame;
  
   
   
   @Override
    public void run (){
        switch(test){
                     case ("randomAccess"):
                        try {
                             result = randomAccess(par1, repeat);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                        case ("staticFinger"):
                         try {
                             result = staticFinger(par1, repeat);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                        case ("dynamicFinger"):
                        try {
                             result = dynamicFinger(par1, par2, repeat);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                    case ("workingSet"): 
                        try {
                             result = workingSet(par1, workingSet, repeat);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                    case ("bitReversalPermutation"):
                        try {
                             result = bitReversalPermutation(par1);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                 }
        if(!exit)
            runtimeFrame.setResult(result, test);
   
    }
    
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
    public RuntimeTest(String t, int p1, int p2,  List<Integer> workSet, RuntimeFrame rf, int r ){
        test = t;
        par1 = p1;
        par2 = p2;
        repeat = r;
        workingSet = workSet;
        result = null;
        exit = false;
        runtimeFrame = rf;
    }
    /**
     * Führt einen Laufzeittest mit einer auf die workingSet Eigenschaft zugeschnittenen Zugriffsfolge aus.
     * @param numOfNodes Anzahl der Knoten.
     * @param set Schlüssel der Knoten, die zum WorkingSet gehören.
     * @param repeat Länge der Zugriffsfolge in Millionen.
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
     */
    private long[] workingSet (int numOfNodes,  List<Integer> set, int repeat) throws BuildAuxTreeFaildException{
        int lengthOfSeq = 1000000;
        List<Integer> accessSequenz = new LinkedList();
        List<Integer> keyList = new LinkedList();
        for (int i = 1; i <= numOfNodes ; i++){
            keyList.add(i);
        }
        while(accessSequenz.size() < lengthOfSeq ){
            if ( exit)
                return null;
            for(Integer i : set){
                accessSequenz.add(i);
                if (accessSequenz.size() >= lengthOfSeq)
                    break;
            }
        }
        
        return runtimeTest(keyList, accessSequenz, repeat);
     
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
        List<Integer> accessSequenz = new LinkedList();
        List<Integer> keyList = new LinkedList();
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
        List<Integer> accessSequenz = new LinkedList();
        List<Integer> keyList = new LinkedList();
        for (int i = 1; i <= numOfNodes ; i++){
            if ( exit)
                return null;
            keyList.add(i);
            
        }
       
        int value = 1;
        for (int i = 1; i < lengthOfSeq +1; i++){
            if ( exit)
                return null;
            accessSequenz.add(value);
            value += keyDistanz;
            if (value > numOfNodes)
                value -= numOfNodes;
            
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
   private long[] staticFinger (int numOfNodes,  int repeat) throws BuildAuxTreeFaildException{
        //Es wird eine Zugriffsfolge erzeugt bei der 2% der Zugriffe auf den mittleren Schlüssel entfallen. Dann 2% der restlichen Zugriffe auf die beiden Schlüssel mit Abstand 1 
        //zum mittleren Schlüssel usw.. Die Reihenfolge der Zugriffe ist dann Zufall.
        int lengthOfSeq = 1000000;
        List<Integer> accessSequenz = new LinkedList();
        int nOn = numOfNodes;
        if (nOn % 2== 0)
            nOn--;
        int lOs = lengthOfSeq;
        
        //Hauptdatenstruktur der Methode
        int[][] nodeArray = new int[nOn + 1][2];
        for(int i = 1; i < nodeArray.length; i++){
            if ( exit)
                return null;
            nodeArray[i][1] = i;  
        }
        
        //Anzahl der Zugriffe den Schlüsseln zuordnen 
        int midKey = nOn / 2 + 1; 
        nodeArray[midKey][0] = lOs / 50;
        lOs -= nodeArray[midKey][0];
        for (int i = 1; i < midKey; i++){
            if(exit)
                return null;
            int numOfAccess = lOs / 50;
             nodeArray[midKey - i][0] = numOfAccess / 2;
             nodeArray[midKey + i][0] = numOfAccess / 2;
             lOs -= nodeArray[midKey - i][0];
             lOs -= nodeArray[midKey + i][0];
             
        }
        int sumAccess = 0;
        for (int i = 1; i < nodeArray.length; i++){
            if ( exit){
                return null;
            }
            sumAccess += nodeArray[i][0];
        }     
        //Den Schlüsseln die Zugriffswahrscheinlichkeit zuordnen
        nodeArray[midKey][0] += lengthOfSeq - sumAccess;
        double[] probs = new double[nOn +1];
        for (int j = 1; j < probs.length; j++){
                if ( exit)
                    return null;
                probs[j] = nodeArray[j][0] / ((double)lengthOfSeq);
        } 
        //Zugriffsfolge erstellen
        while(nodeArray != null ){
            if ( exit)
                return null;
            int value = randomNumber(probs); 
            accessSequenz.add(nodeArray[value][1]);
            nodeArray[value][0]--;
            if (nodeArray[value][0] < 1){
                if (nodeArray.length == 2)
                    nodeArray =  null;
                else{
                    int[][] temp = nodeArray;
                    nodeArray = new int[nodeArray.length - 1][2];
                    int nodeArrayIndex = 1;
                    lOs = 0;
                    for (int i = 1; i < temp.length; i++ ){
                        if (temp[i][0] > 0){
                            nodeArray[nodeArrayIndex][0] = temp[i][0];
                            nodeArray[nodeArrayIndex][1] = temp[i][1];
                            nodeArrayIndex++;
                            lOs += temp[i][0];
                        }  
                    }
                    probs = new double[nodeArray.length];
                    for (int j = 1; j < probs.length; j++){
                        probs[j] = nodeArray[j][0] / ((double)lOs);
                    }      
                }    
            }
        }  
        List<Integer> keyList = new LinkedList();
        for(int i = 1; i <= numOfNodes; i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        return runtimeTest(keyList, accessSequenz, repeat);
    } 
    /**
     *  Führt einen Laufzeittest mit einer bitReversalPermutation aus..
     * @param numOfBits Anzahl der Bitstellen zu denen die bitReversalPermutation generiert wird.
     * @return Ein Array der Größe 2. Index 0 -> Laufzeit in ms des TangoTree. Index 1 -> Laufzeit in ms des SplayTree.
     * @throws BuildAuxTreeFaildException 
     */
    private long[] bitReversalPermutation (int numOfBits) throws BuildAuxTreeFaildException{
        List<Integer> keyList = new LinkedList();
        for (int i = 0; i < Math.pow(2, numOfBits); i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        List<Integer> accessSequenz = new LinkedList();
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
    
    private long[] runtimeTest (List<Integer> keyList, List<Integer> accessSequenz, int repeat) throws BuildAuxTreeFaildException{
        long[] ret = new long[2];
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
        SplayTree splayTree = new SplayTree(keyList);
        startTime = System.nanoTime();
            for (int j = 1; j <= repeat; j++){
                for(Integer i: accessSequenz ){
                    if ( exit)
                        return null;
                    splayTree.access(i);
           
                }
            }    
        ret[1] = (System.nanoTime()- startTime) / 1000000 ;
        
        return ret;
    }
    private int randomNumber (double[] probs){
        Double random = Math.random();  
        int index = 0;
        double sumProb = 0;
        while(sumProb <= random){
            index++;
            sumProb += probs[index];
        }
        return index;
    }
    
     private int randomNumber (int max){
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
