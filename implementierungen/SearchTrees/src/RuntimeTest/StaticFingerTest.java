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
public class StaticFingerTest extends RuntimeTest {
  int numOfNodes;
  int repeat;  
    
    public StaticFingerTest(int n, int r, RuntimeFrame rf, boolean d){
        super(rf, "Ergebnis StaticFinger", d);
        numOfNodes = n;
        repeat  = r; 
        
    } 
    
  @Override
     long[] startTest()  throws BuildAuxTreeFaildException{
         //Es wird eine Zugriffsfolge erzeugt bei der 2% der Zugriffe auf den mittleren Schlüssel entfallen. Dann 2% der restlichen Zugriffe auf die beiden Schlüssel mit Abstand 1 
        //zum mittleren Schlüssel usw.. Die Reihenfolge der Zugriffe ist dann Zufall.
        int lengthOfSeq = 1000000;
        int lOs = lengthOfSeq;
        if(numOfNodes % 2 == 0)
            numOfNodes--;
        List<Integer> accessSequenz = new LinkedList<Integer>();
        //Hauptdatenstruktur der Methode
        int[][] nodeArray = new int[numOfNodes + 1][2];
        //Keys eintragen
        for(int i = 1; i < nodeArray.length; i++){
            if ( exit)
                return null;
            nodeArray[i][1] = i;  
        }
        
        //Anzahl der Zugriffe den Schlüsseln zuordnen 
        int midKey = numOfNodes / 2 + 1; 
        nodeArray[midKey][0] = lOs / 50;
        if(nodeArray[midKey][0] < 1)
            nodeArray[midKey][0]++;
        lOs -= nodeArray[midKey][0];
        int indexMin = midKey; //Index des kleinsten Schlüssel auf den zugegriffen wird.
        int indexMax = midKey;
        for (int i = 1; i < midKey; i++){
            if(exit)
                return null;
             int numOfAccess = lOs / 50;
             nodeArray[midKey - i][0] = numOfAccess / 2;
             nodeArray[midKey + i][0] = numOfAccess / 2;
             lOs -= nodeArray[midKey - i][0];
             lOs -= nodeArray[midKey + i][0];
             if (nodeArray[midKey - i][0] > 0){
                 indexMin = midKey - i;
                 indexMax = midKey +i;
            }
        }
        int[][] temp = nodeArray;
        nodeArray = new int[indexMax - indexMin + 2][2];
        for(int i = indexMin; i <= indexMax; i++){
            nodeArray[i - indexMin + 1][0] = temp[i][0];
            nodeArray[i - indexMin + 1][1] = temp[i][1];
        }
        int sumAccess = 0;
        for (int i = 1; i < nodeArray.length; i++){
            if ( exit){
                return null;
            }
            sumAccess += nodeArray[i][0];
        }          
        nodeArray[midKey - indexMin +1][0] += (lengthOfSeq - sumAccess ); 
        
        //Den Schlüsseln die Zugriffswahrscheinlichkeit zuordnen
        double[] probs = new double[nodeArray.length];
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
                    temp = nodeArray;
                    nodeArray = new int[nodeArray.length - 1][2];
                    int nodeArrayIndex = 1;
                    for (int i = 1; i < temp.length; i++ ){
                        if (temp[i][0] > 0){
                            nodeArray[nodeArrayIndex][0] = temp[i][0];
                            nodeArray[nodeArrayIndex][1] = temp[i][1];
                            nodeArrayIndex++;
                        }  
                    }
                    probs = new double[nodeArray.length];

                    for (int j = 1; j < probs.length; j++){
                        probs[j] = nodeArray[j][0] /(double) (lengthOfSeq - accessSequenz.size());
                    } 
                   
                }    
            }
        }  
        List<Integer> keyList = new LinkedList<Integer>();
        for(int i = 1; i <= numOfNodes; i++){
            if ( exit)
                return null;
            keyList.add(i);
        }
        return runtimeTest(keyList, accessSequenz, repeat);
     }
}
