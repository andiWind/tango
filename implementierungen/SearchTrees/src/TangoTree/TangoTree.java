/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;

import GUI.GUICanvas;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import GUI.I_GUITree;
import GUI.I_GUINode;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author andreas
 */
public class TangoTree implements I_GUITree{
    private TangoNode root;
    private final Class<? extends TangoAuxTree > auxTreeClass;
    private TangoAuxTree auxTree;
    
   private int[] buildKeyArray(List <Integer> keyList){
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
    
   public  TangoTree(List<Integer> keyList, Class<? extends TangoAuxTree > auxTreeClass) throws BuildAuxTreeFaildException{
        //Test AuxTree Class
        try {
            auxTree = auxTreeClass.newInstance();  
        } 
        catch (Exception ex) {
            throw new BuildAuxTreeFaildException("Von der übergebenen Klasse konnte kein TangoAuxTree gebildet werden. Parameterloser Konstruktor notwendig.");
        }
        this.auxTreeClass = auxTreeClass;
        int[] keyArray = buildKeyArray(keyList);
        int numOfNodes = keyArray.length;
        if (numOfNodes < 1)
            throw new IllegalArgumentException();
        int numOfNode = 1;
        int depth = 1;
        PerfectTreeNode pBT = buildPerfectBalancedTree(new PerfectTreeNode(), numOfNodes, numOfNode, depth);
        setKeysInPBT(pBT, keyArray, 0);
        //Die Hilfstruktur wird nun nachgebaut
        //Zum Start ist jeder Knoten ein eigener Hifsbaum
         root = buildStartTango(pBT);
        
        
        
        
      
 
    }
    private TangoNode buildStartTango(PerfectTreeNode helpNode) {
        if (helpNode == null)
            return null;
        TangoAuxTree auxTree = null;
        try {
            auxTree = auxTreeClass.newInstance();
        } catch (Exception ex) { }
        
      
        auxTree.insert(helpNode.key);
        TangoNode node = auxTree.getRoot();
        node.setDepth(helpNode.depth);
        node.setMaxDepth(helpNode.depth);
        node.setMinDepth(helpNode.depth);
        node.setIsRoot(true);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(null);
        node.setLeftAuxTree(buildStartTango(helpNode.left));
        if( node.getLeftTango() != null)
            node.getLeftTango().setParentNodeAuxTree(node);
        node.setRightAuxTree(buildStartTango(helpNode.right));
        if( node.getRightTango() != null)
            node.getRightTango().setParentNodeAuxTree(node);  
        return node;
    }
    @Override
     public TangoNode search(int key){
         
        TangoNode search = root;
        while(search != null && search.getKey() != key ){
            if (search.getKey() < key)
                search = search.getLeftTango();
            else if((search.getKey() > key))
                search = search.getRightTango();
            else 
                break;
            
        }   
            
            
            
            
           
        
        return search;
    }      
   
 
    private int setKeysInPBT(PerfectTreeNode node, int[] sortedKeys, int writedNumbers){
        int tempWritedNumbers = writedNumbers;
        if (node.left != null)
            tempWritedNumbers = setKeysInPBT(node.left, sortedKeys, tempWritedNumbers);
        node.key = sortedKeys[tempWritedNumbers++];
        if (node.right != null)
            tempWritedNumbers = setKeysInPBT(node.right, sortedKeys, tempWritedNumbers);
        return  tempWritedNumbers;    
    }
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
    
    @Override
    public I_GUINode getRoot() {
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
    public String getName() {
        return "Tango-Tree";
    }

    @Override
    public void setCanvas(GUICanvas c) {
        
    }
 
   
    private class PerfectTreeNode{
       PerfectTreeNode left;
       PerfectTreeNode right;
       int key;
       int depth;
    }
}
