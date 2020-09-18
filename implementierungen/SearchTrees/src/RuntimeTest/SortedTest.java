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
public class SortedTest extends RuntimeTest {
  int numOfNodes;
  int typ;
    
    public SortedTest(int n, int t, RuntimeFrame rf){
        super(rf, "");
        typ = t;
        if(typ == 1)
            testName = "aufsteigend sortiert";
        else if(typ == 1)
            testName = "absteigend sortiert";
        else{
            testName = "auf- / absteigend sortiert";
        }
        numOfNodes = n;
       
        
    } 
    
  @Override
     long[] startTest()  throws BuildAuxTreeFaildException{
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
}
