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
public class RandomTest extends RuntimeTest {
  int numOfNodes;
  int lengthOfSeq;
 
    
    public RandomTest(int n, int r, RuntimeFrame rf, boolean d){
        super(rf, "Ergebnis Random", d);
        numOfNodes = n; 
        lengthOfSeq = r;
    } 
    
  @Override
     long[] startTest()  throws BuildAuxTreeFaildException{
        int lOs = 1000000 * this.lengthOfSeq;
        List<Integer> accessSequenz = new LinkedList<Integer>();
        List<Integer> keyList = new LinkedList<Integer>();
        for (int i = 1; i <= numOfNodes ; i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        for (int i = 1; i < lOs +1; i++){
            if ( exit)
                return null;
            accessSequenz.add(randomNumber(numOfNodes));
           
        }
        return runtimeTest(keyList, accessSequenz, 1);
     }
}
