/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SplayTree;

import GUI.I_GUINode;
import GUI.I_GUITree;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andreas
 */
public class SplayTree implements I_GUITree {
    private Node root;


      private Node buildPerfectBalancedTree (Node node, int numOfNodes, int nodeNumber ){
        if (2 * nodeNumber <= numOfNodes){
            node.setLeft(buildPerfectBalancedTree(new Node(), numOfNodes, 2 * nodeNumber));
            node.getLeft().setParent(node);
        }
        if (2 * nodeNumber + 1  <= numOfNodes){
            node.setRight( buildPerfectBalancedTree(new Node(), numOfNodes,2 * nodeNumber + 1)); 
             node.getRight().setParent(node);
        }
        return node;
    }
    private int setKeys(Node node ,int[] keys, int count){
        if (node.getLeft() != null)
            count = setKeys(node.getLeft(), keys, count);
        node.setKey(count++);
        if (node.getRight() != null)
            count = setKeys(node.getRight(), keys, count );
        return count;
    }  
    public SplayTree (List<Integer> keyList){
        int[] keyArray = buildKeyArray(keyList);
        root = buildPerfectBalancedTree(new Node(),keyArray.length,1 );
        setKeys(root, keyArray, 1);
        
       
    }
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
    public void insert (int key){
    }
    
    @Override
    public Node access (int key){
    splay(key);
    return root;
}    
    
private void splay(int key){
    Node splayNode = search(key);
    if (splayNode == null)
        return;
    while(root != splayNode){
        Node sP = splayNode.getParent();
        Node sPP = sP .getParent();
        if (sPP == null){
            if (sP.getLeft() == splayNode)
                zig(splayNode);
            else
                zag(splayNode);
                    
        }
        else{
        if ( sP.getLeft() == splayNode && sPP.getLeft() == sP )
            zigZig(splayNode);
        else if (sP.getLeft() == splayNode && sPP.getRight() == sP)
            zigZag(splayNode);
        else if (sP.getRight() == splayNode && sPP.getLeft() == sP)
            zagZig(splayNode);
        else 
            zagZag(splayNode);
        }
    }
}  
private void zigZig (Node node){
    rotateRight(node.getParent());
    rotateRight(node);
} 
private void zigZag (Node node){
    rotateRight(node);
    rotateLeft(node);
}  
private void zagZig (Node node){
    rotateLeft(node);
    rotateRight(node);
}  
private void zagZag (Node node){
    rotateLeft(node.getParent());
    rotateLeft(node);
}  
private void zig (Node node){
    rotateRight(node);
       
}  
private void zag (Node node){
    rotateLeft(node);
} 
private void rotateRight(Node node){
    Node par = node.getParent();
    Node parPar = par.getParent();
    
    par.setLeft(node.getRight());
    if(par.getLeft() != null)
        par.getLeft().setParent(par);
    
    node.setRight(par);
    par.setParent(node);
    
    if (parPar == null){
        root = node;
        node.setParent(null);
        return;
    }   
    else if(parPar.getRight() == par )
        parPar.setRight(node);
    else
        parPar.setLeft(node);
    node.setParent(parPar);
    
}
private void rotateLeft(Node node){
    Node par = node.getParent();
    Node parPar = par.getParent();
    
    par.setRight(node.getLeft());
    if(par.getRight() != null)
        par.getRight().setParent(par);
    
    node.setLeft(par);
    par.setParent(node);
    
    if (parPar == null){
        root = node;
        node.setParent(null);
        return;
    }   
    else if(parPar.getLeft() == par )
        parPar.setLeft(node);
    else
        parPar.setRight(node);
    node.setParent(parPar);
    
}


private Node search(int key){
    Node search = root;
    Node next = root;
    while (next != null){
        search = next;
        if (next.getKey() == key)
            return next;
        else if (next.getKey() > key)
            next = next.getLeft();
        else 
            next = next.getRight();
    }
    return search;
}

    @Override
    public I_GUINode getRoot() {
        return root;
    }

    @Override
    public void delete(int key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return "SplayTree";
    }
   

}