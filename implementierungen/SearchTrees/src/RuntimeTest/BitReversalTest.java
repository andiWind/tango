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
public class BitReversalTest extends RuntimeTest {
  int numOfBits;
  
    
    public BitReversalTest(int n,  RuntimeFrame rf){
        super(rf, "Ergebnis Bit Reversal Permutation");
        numOfBits = n;
       
        
    } 
    
  @Override
     long[] startTest()  throws BuildAuxTreeFaildException{
       List<Integer> keyList = new LinkedList<Integer>();
        for (int i = 0; i < Math.pow(2, numOfBits); i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        List<Integer> accessSequenz = new LinkedList<Integer>();
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
}
