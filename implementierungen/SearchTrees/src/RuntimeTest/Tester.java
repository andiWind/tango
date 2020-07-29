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
public class Tester {
   
    public Tester(){
        
    }
    public long[] randomAccess (int numOfNodes, int lengthOfSeq) throws BuildAuxTreeFaildException{
        List<Integer> accessSequenz = new LinkedList();
        List<Integer> keyList = new LinkedList();
        for (int i = 1; i <= numOfNodes ; i++){
            keyList.add(i);
        }
        for (int i = 1; i < lengthOfSeq +1; i++){
            double[] probs = new double[numOfNodes +1];
            for (int j = 1; j < probs.length; j++){
                probs[j] = 1 / numOfNodes;
            } 
            accessSequenz.add(randomNumber(probs));
        }
        return runtimeTest(keyList, accessSequenz);
    }
    public long[] staticFinger (int numOfNodes, int lengthOfSeq) throws BuildAuxTreeFaildException{
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
        double[] probs = new double[numOfNodes +1];
        for (int j = 1; j < probs.length; j++){
                probs[j] = nodeArray[j][1] / lengthOfSeq;
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
                    probs = new double[nodeArray.length];
                    for (int j = 1; j < probs.length; j++){
                        probs[j] = nodeArray[j][1] / lOs;
                    }     
                       
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
  
    
    public long[] bitReversalPermutation (int numOfBits) throws BuildAuxTreeFaildException{
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
        ret[0] = (System.nanoTime()- startTime) / 1000 ;
        startTime = System.nanoTime();
        for(Integer i: accessSequenz ){
            splayTree.access(i);
        }
        ret[1] = (System.nanoTime()- startTime) / 1000 ;
        
        return ret;
    }
    private int randomNumber (double[] probs){
        Double random = Math.random();  
        int index = 1;
        double sumProb = 0;
        while(sumProb <= random){
            sumProb += probs[index];
            index++;
        }
        return index;
    }
}
