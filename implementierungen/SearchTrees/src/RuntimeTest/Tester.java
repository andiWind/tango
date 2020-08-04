/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RuntimeTest;
import SplayTree.SplayTree;
import TangoTree.TangoTree;
import RedBlackTree.RedBlackTree;
import TangoTree.BuildAuxTreeFaildException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andreas
 */
public class Tester extends Thread {
   private String test;
   private int par1;
   private int par2;
   private int par3;
   private List<Integer> workingSet;
   private long[] result;
  
   
   
   @Override
    public void run (){
        ResultFrame frame = null;
        switch(test){
                     case ("randomAccess"):
                        frame = new ResultFrame("randomAccess"); 
                        try {
                             result = randomAccess(par1, par2);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                    case ("staticFinger"):
                        frame = new ResultFrame("staticFinger"); 
                        try {
                             result = staticFinger(par1, par2);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                    case ("dynamicFinger"):
                        frame = new ResultFrame("dynamicFinger"); 
                        try {
                             result = dynamicFinger(par1, par2, par3);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                    case ("workingSet"):
                        frame = new ResultFrame("workingSet"); 
                        try {
                             result = workingSet(par1, par2, workingSet);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                    case ("bitReversalPermutation"):
                        frame = new ResultFrame("bitReversalPermutation");
                        try {
                             result = bitReversalPermutation(par1);
                            }
                         catch (BuildAuxTreeFaildException ex) {
                        }
                         break;
                 }
        
        
        
        if(test.equals("randomAccess")){
            
            
        }
        if (frame != null)
            frame.setTime(result[0], result[1]);
        
        
    }
    
    public long[] getResult(){
        return result;
    }
   
    public Tester(String t, int p1, int p2, int p3, List<Integer> workSet ){
        test = t;
        par1 = p1;
        par2 = p2;
        par3 = p3;
        workingSet = workSet;
        result = null;
    }
    private long[] workingSet (int numOfNodes, int lengthOfSeq, List<Integer> set) throws BuildAuxTreeFaildException{
        List<Integer> accessSequenz = new LinkedList();
        List<Integer> keyList = new LinkedList();
        for (int i = 1; i <= numOfNodes ; i++){
            keyList.add(i);
        }
        while(accessSequenz.size() < lengthOfSeq){
            for(Integer i : set){
                accessSequenz.add(i);
                if (accessSequenz.size() >= lengthOfSeq)
                    break;
            }
        }
        return runtimeTest(keyList, accessSequenz);
    }
    private long[] randomAccess (int numOfNodes, int lengthOfSeq) throws BuildAuxTreeFaildException{
        List<Integer> accessSequenz = new LinkedList();
        List<Integer> keyList = new LinkedList();
        for (int i = 1; i <= numOfNodes ; i++){
            keyList.add(i);
        }
        for (int i = 1; i < lengthOfSeq +1; i++){
            accessSequenz.add(randomNumber(numOfNodes));
        }
        return runtimeTest(keyList, accessSequenz);
    }
    private long[] dynamicFinger (int numOfNodes, int lengthOfSeq, int keyDistanz) throws BuildAuxTreeFaildException{
        List<Integer> accessSequenz = new LinkedList();
        List<Integer> keyList = new LinkedList();
        for (int i = 1; i <= numOfNodes ; i++){
            keyList.add(i);
        }
        int value = 1;
        for (int i = 1; i < lengthOfSeq +1; i++){
            accessSequenz.add(value);
            value += keyDistanz;
            if (value > numOfNodes)
                value -= numOfNodes;
        }
        return runtimeTest(keyList, accessSequenz);
    }
    private long[] staticFinger (int numOfNodes, int lengthOfSeq) throws BuildAuxTreeFaildException{
        List<Integer> accessSequenz = new LinkedList();
        int nOn = numOfNodes;
        if (nOn % 2== 0)
            nOn--;
        int lOs = lengthOfSeq;
        
        int[][] nodeArray = new int[nOn + 1][2];
        for(int i = 1; i < nodeArray.length; i++){
            nodeArray[i][1] = i;
        }
        int midKey = nOn / 2 + 1; 
        nodeArray[midKey][0] = lOs / 2;
        lOs -= nodeArray[midKey][0];
        for (int i = 1; i < midKey; i++){
            int numOfAccess = lOs / 2;
             nodeArray[midKey - i][0] = numOfAccess / 2;
             nodeArray[midKey + i][0] = numOfAccess / 2;
             lOs -= nodeArray[midKey - i][0];
             lOs -= nodeArray[midKey + i][0];
        }
        int sumAccess = 0;
        for (int i = 1; i < nodeArray.length; i++)
            sumAccess += nodeArray[i][0];
        nodeArray[midKey][0] += lengthOfSeq - sumAccess;
        double[] probs = new double[nOn +1];
        for (int j = 1; j < probs.length; j++){
                probs[j] = nodeArray[j][0] / ((double)lengthOfSeq);
        } 
        while(nodeArray != null){
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
            keyList.add(i);
        }
        return runtimeTest(keyList, accessSequenz);
    } 
  
    
    private long[] bitReversalPermutation (int numOfBits) throws BuildAuxTreeFaildException{
        List<Integer> keyList = new LinkedList();
        for (int i = 0; i < Math.pow(2, numOfBits); i++){
            keyList.add(i);
        }
        List<Integer> accessSequenz = new LinkedList();
        for(int i = 0; i < Math.pow(2, numOfBits); i++  ){
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
        return runtimeTest(keyList,accessSequenz);
    }
    
    private long[] runtimeTest (List<Integer> keyList, List<Integer> accessSequenz) throws BuildAuxTreeFaildException{
        long[] ret = new long[2];
        TangoTree tangoTree = new TangoTree(keyList, RedBlackTree.class);
        SplayTree splayTree = new SplayTree(keyList);
        long startTime = System.nanoTime();
        for(Integer i: accessSequenz ){
            tangoTree.access(i);
           
        }
        ret[0] = (System.nanoTime()- startTime) / 1000000 ;
        startTime = System.nanoTime();
        for(Integer i: accessSequenz ){
            splayTree.access(i);
           
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
}
