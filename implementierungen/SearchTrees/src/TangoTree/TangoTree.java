/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;

import GUI.GUICanvas;
import GUI.GUINode;
import GUI.GUITree;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andre
 */
public class TangoTree implements GUITree{
    private Node root;
    
   private int[] buildKeyArray(Collection <Integer> keyList){
       List<Integer> nullList = new LinkedList();
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
    
   public  TangoTree(List<Integer> keyList){
        int[] keyArray = buildKeyArray(keyList);
        int numOfNodes = keyArray.length;
        if (numOfNodes < 1)
            throw new IllegalArgumentException();
        Node perfectBalancedTree = buildPerfectBalancedTree(new Node(1), numOfNodes);
        root = perfectBalancedTree;
        setKeysInPBT(perfectBalancedTree, keyArray, 0);
        
        
    }
    private int setKeysInPBT(Node node, int[] sortedKeys, int writedNumbers){
        int tempWritedNumbers = writedNumbers;
        if (node.left != null)
            tempWritedNumbers = setKeysInPBT(node.left, sortedKeys, tempWritedNumbers);
        node.key = sortedKeys[tempWritedNumbers++];
        if (node.right != null)
            tempWritedNumbers = setKeysInPBT(node.right, sortedKeys, tempWritedNumbers);
        return  tempWritedNumbers;    
    }
    private Node buildPerfectBalancedTree (Node node, int numOfNodes ){
        if (node.key * 2 <= numOfNodes){
            Node child = new Node(node.key * 2);
            child.parent = node;
            node.left = buildPerfectBalancedTree(child, numOfNodes);
        }
        if (node.key * 2 + 1 <= numOfNodes){
            Node child = new Node(node.key * 2 + 1);
            child.parent = node;
            node.right = buildPerfectBalancedTree(child, numOfNodes);
        }
        return node;
     
    }

    @Override
    public GUINode getRoot() {
         return root;
    }

    @Override
    public void insert(int key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GUINode search(int key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return "Tango-Tree";
    }

    @Override
    public void setCanvas(GUICanvas c) {
        
    }
    
}
