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
public class DynamicFingerTest extends RuntimeTest {
  int numOfNodes;
  int distance;
  int repeat;  
    
    public DynamicFingerTest(int n, int d,  int r, RuntimeFrame rf){
        super(rf, "Ergebnis DynamicFinger");
        numOfNodes = n;
        distance = d;
        repeat  = r; 
        
    } 
    
  @Override
     long[] startTest()  throws BuildAuxTreeFaildException{
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
                value += distance;
                if(value + distance > numOfNodes)
                    reversal = true;
            }    
            else{
                value -= distance;
                if(value - distance < 1)
                    reversal = false;
            }    
             
        }
        
        return runtimeTest(keyList, accessSequenz, repeat);
     }
}
