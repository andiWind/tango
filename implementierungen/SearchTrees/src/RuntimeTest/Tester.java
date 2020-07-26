/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RuntimeTest;
import java.lang.StringBuffer;
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

    public long[] sequentialAccess (int numOfNodes) throws BuildAuxTreeFaildException{
        long[] ret = new long[2];
        List<Integer> accessSequenz = new LinkedList();
        for (int i = 1; i < numOfNodes +1; i++){
            accessSequenz.add(i);
        }
        TangoTree tangoTree = new TangoTree(accessSequenz, RedBlackTree.class);
        SplayTree splayTree = new SplayTree(accessSequenz);
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
    
    public long[] bitReversalPermutation (int numOfBits) throws BuildAuxTreeFaildException{
        long[] ret = new long[2];
        List<Integer> keyList = new LinkedList();
        for (int i = 0; i < Math.pow(2, numOfBits); i++){
            keyList.add(i);
        }
        TangoTree tangoTree = new TangoTree(keyList, RedBlackTree.class);
        SplayTree splayTree = new SplayTree(keyList);
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
    


}
