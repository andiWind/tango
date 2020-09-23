/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



/**
 *
 * @author andreas
 * Objekte dieser Klasse bestimmen die Anzahl der Veränderungen an preferred children zu einer Zugriffsfolge.
 */
public class TangoPrefChildCount {
    PerfectTreeNode refTreeRoot;
  
 /**
 *
 * @param keyList Die Elemente von "keyList" müssen genau mit den Schlüsseln des TangoTrees übereinstimmen, zu dem die  Anzahl der Veränderungen an preferred children
 * bestimmt werden soll.
 */
    public  TangoPrefChildCount(List<Integer> keyList) {
        //Test AuxTree Class
      
        int[] keyArray = buildKeyArray(keyList);
        int numOfNodes = keyArray.length;
        if (numOfNodes < 1)
            throw new IllegalArgumentException();
        int numOfNode = 1;
        int depth = 1;
        //Der Tango Baum wird nicht direkt erstellt. Zunächst wird ein Abbild von ihm mit den PerfectTreeNode erstellt.
        refTreeRoot = buildPerfectBalancedTree(new PerfectTreeNode(), numOfNodes, numOfNode, depth);
        setKeysInPBT(refTreeRoot, keyArray, 0);
        
    }   
    //Die keyList wird in ein duplikatfreies, sortiertes Array überführt.
    private int[] buildKeyArray(List <Integer> keyList){
       List<Integer> nullList = new LinkedList<Integer>();
       nullList.add(null);
       keyList.removeAll(nullList);
       int[] keyArray = new int[keyList.size()];
       int j = 0;
       for (Integer key : keyList)
           keyArray[j++] = key;
       Arrays.sort(keyArray);
       if (keyArray.length < 2 ){
           int[] ret = new int[1];
           ret[0] = keyArray[0];
           return ret;
       }
       //Duplikate filtern 
       int numOfDup = 0;
       for (int i = 1; i < keyArray.length; i++ ){
           if (keyArray[i-1] == keyArray[i])
               numOfDup++;
       }
       int[] ret = new int[keyArray.length - numOfDup];
       ret[0] = keyArray[0];
       int k = 1;
        for (int i = 1; i < keyArray.length; i++ ){
           if (keyArray[i] > keyArray[i - 1]){
               ret[k++] = keyArray[i];
           }       
       }
      return ret;
   }
    
   /**
    *Bestimmt die Anzahl der Veränderungen an preferred children zu der von "accessSeq" und "repeat" bestimmten Zugriffsfolge. 
    * @param accessSeq Aufsteigend nach dem Index sortiert bilden die Elemente von  accessSeq" den ersten Teil der Zugriffsfolge. 
    * @param repeat Die durch "accessSeq" bestimmte Zugriffsfolge wird "repeat" mal wiederholt, so bildet sich die Zugriffsfolge, auf die sich die
    * der return Wert bezieht.
    * @return Die Anzahl der Veränderungen an preferred children zu der von "accessSeq" und "repeat" bestimmten Zugriffsfolge.
    */
    public long numOfPrevChildChanges(List<Integer> accessSeq, int repeat){  
        long countPref = 0;
        for(int i = 0; i < repeat; i++){
            for(Integer key : accessSeq){
                PerfectTreeNode search = refTreeRoot;
                while (key != search.key){
                    if (key > search.key){
                        if (!(search.prefChild == 2)){
                        countPref++;
                        search.prefChild = 2;
                   
                        }  
                        search = search.right;
                    }
                    else{
                        if (!(search.prefChild == 1)){
                        countPref++;
                        search.prefChild = 1;
                   
                        }  
                    search = search.left;
                    }
                }
                if (!(search.prefChild == 1)){
                    countPref++;
                    search.prefChild = 1;
                }      
            }
        }
         return countPref;
    }   
    //Hilfsmethode zum erzeugen der Abbildstruktur. Einziger Aufruf im Konstruktor
    private int setKeysInPBT(PerfectTreeNode node, int[] sortedKeys, int writedNumbers){
        int tempWritedNumbers = writedNumbers;
        if (node.left != null)
            tempWritedNumbers = setKeysInPBT(node.left, sortedKeys, tempWritedNumbers);
        node.key = sortedKeys[tempWritedNumbers++];
        if (node.right != null)
            tempWritedNumbers = setKeysInPBT(node.right, sortedKeys, tempWritedNumbers);
        return  tempWritedNumbers;    
    }
    //Erzeugt die Referenzstruktur. Einziger Aufruf im Konstruktor
    private PerfectTreeNode buildPerfectBalancedTree (PerfectTreeNode node, int numOfNodes, int nodeNumber, int depth ){
        node.depth = depth;
        if (2 * nodeNumber <= numOfNodes){
            node.left = buildPerfectBalancedTree(new PerfectTreeNode(), numOfNodes, 2 * nodeNumber, depth + 1);
        }
        if (2 * nodeNumber + 1  <= numOfNodes){
            node.right = buildPerfectBalancedTree(new PerfectTreeNode(), numOfNodes,2 * nodeNumber + 1, depth + 1);
        }
        return node;
     
    }

    private class PerfectTreeNode{
       PerfectTreeNode left;
       PerfectTreeNode right;
       int key;
       int depth;
       int prefChild; //0: keines, 1: das linke, 2: das rechte
    }
 
}
