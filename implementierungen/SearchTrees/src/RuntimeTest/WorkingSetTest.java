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
 * @author andre
 */
public class WorkingSetTest extends RuntimeTest {
  int numOfNodes;
  int distance;
  int length;  
    
    public WorkingSetTest(int n, int d,  int l, RuntimeFrame rf, boolean de){
        super(rf, "Ergebnis WorkingSet", de);
        numOfNodes = n;
        distance = d;
        length  = l; 
        
    } 
    
  @Override
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
