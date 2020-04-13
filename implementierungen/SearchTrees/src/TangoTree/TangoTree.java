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
import RedBlackTree.RedBlackTree;
import GUI.I_GUINode;

/**
 *
 * @author andreas
 */
public class TangoTree implements I_GUITree{
    private TangoNode root;
   
    
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
    
   public  TangoTree(List<Integer> keyList){
        int[] keyArray = buildKeyArray(keyList);
        int numOfNodes = keyArray.length;
        if (numOfNodes < 1)
            throw new IllegalArgumentException();
        int numOfNode = 1;
        int depth = 1;
        Node pBT = buildPerfectBalancedTree(new Node(), numOfNodes, numOfNode, depth);
        setKeysInPBT(pBT, keyArray, 0);
    
        //Zum Start ist mostRecent immer false, also links
        List<List<Node>> pathLists = new LinkedList();
        pathLists.add(getNextLeftPath(pBT, true)); //Ersten Pfad zum linkesten Kind holen
        List<Node> aktPathList;
        while(!pathLists.isEmpty()){
            aktPathList = pathLists.get(0);
            for(Node node : aktPathList){
                List<Node> newPathList = getNextLeftPath(node, false);
                if(newPathList != null)
                    pathLists.add(newPathList);
            }
            TangoAuxTree auxTree = new RedBlackTree(); //Hier Bindung zum gewählten Hilfsbaum 
            for (Node node : aktPathList){
                auxTree.insert(node.key, node.depth);
            }
            writeMaxDepth(auxTree.getRoot());
            writeMinDepth(auxTree.getRoot());
            if (root == null)   {  
                root = auxTree.getRoot();
                root.setIsRoot(true);
            }
            else{
                insertAuxTree(auxTree);
            }
            pathLists.remove(0);
        } 
        
 
    }
    private int writeMaxDepth(TangoNode node){
        if (node.getLeft() != null)
            node.setMaxDepth(writeMaxDepth(node.getLeft()));
        else
            node.setMaxDepth(node.getDepth()); 
        if (node.getRight() != null)
            writeMaxDepth(node.getRight());
        return node.getMaxDepth();    
    }
     private int writeMinDepth(TangoNode node){
        if (node.getRight() != null)
            node.setMinDepth(writeMinDepth(node.getRight()));
        else
            node.setMinDepth(node.getDepth()); 
        if (node.getLeft() != null)
            writeMinDepth(node.getLeft());
        return node.getMinDepth();    
    }
   
    private List<Node> getNextLeftPath(Node node, boolean first){
        List<Node> ret = new LinkedList();
        if (!first){
            if (node.right != null)
                node = node.right;
            else
                return null;
        }
        ret.add(node);
        while(node.left != null){
            node = node.left;
            ret.add(node);
        }
         return ret;
    }
    private void cut(){
        
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
    private Node buildPerfectBalancedTree (Node node, int numOfNodes, int nodeNumber, int depth ){
         node.depth = depth;
        if (2 * nodeNumber <= numOfNodes){
            node.left = buildPerfectBalancedTree(new Node(), numOfNodes, 2 * nodeNumber, depth + 1);
        }
        if (2 * nodeNumber + 1  <= numOfNodes){
            node.right = buildPerfectBalancedTree(new Node(), numOfNodes,2 * nodeNumber + 1, depth + 1);
        }
        return node;
     
    }
    private void insertAuxTree(TangoAuxTree auxTree){
        TangoNode auxTreeRoot = auxTree.getRoot();
        auxTreeRoot.setIsRoot(true);
        TangoNode place = root; 
        TangoNode placeHelp = place;
        while(placeHelp != null){
                place = placeHelp;
            if (auxTreeRoot.getKey() < place.getKey() )
                placeHelp = place.getLeftTango();
            else
                placeHelp = place.getRightTango();
        }
        auxTreeRoot.setParentNodeAuxTree(place);
        if (auxTreeRoot.getKey() < place.getKey() )
            place.setLeftAuxTree(auxTreeRoot);  
        else
            place.setRightAuxTree(auxTreeRoot);
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
    public I_GUINode search(int key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public String getName() {
        return "Tango-Tree";
    }

    @Override
    public void setCanvas(GUICanvas c) {
        
    }

   
    private class Node{
       Node left;
       Node right;
       int key;
       int depth;
    }
}
