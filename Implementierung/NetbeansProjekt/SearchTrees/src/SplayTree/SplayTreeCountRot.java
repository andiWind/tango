/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SplayTree;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *  Implementierung des Splay Baumes Diese Variante enthält einen rücksetzbaren Zähler für die Anzahl der Rotationen.
 * @author andreas
 * 
 */
public class SplayTreeCountRot  {
    private SplayNode root;
    private long numOfRotations;

  /**
   * 
   * @return Die Anzahl der ausgeführten Rotationen, seit dem letzten Zurücksetzen. 
   */  
 public long getNumOfRotations(){
     return numOfRotations;
 }   
 public void resetNumOfRotations(){
     numOfRotations = 0;
 }
    
//Gleiches Vorgehen wie beim TangoBaum
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
private SplayNode buildPerfectBalancedTree (SplayNode node, int numOfNodes, int nodeNumber ){
        if (2 * nodeNumber <= numOfNodes){
            node.setLeft(buildPerfectBalancedTree(new SplayNode(), numOfNodes, 2 * nodeNumber));
            node.getLeft().setParent(node);
        }
        if (2 * nodeNumber + 1  <= numOfNodes){
            node.setRight(buildPerfectBalancedTree(new SplayNode(), numOfNodes,2 * nodeNumber + 1)); 
             node.getRight().setParent(node);
        }
        return node;
    }
  private int setKeys(SplayNode node ,int[] keys, int count){
        if (node.getLeft() != null)
            count = setKeys(node.getLeft(), keys, count);
        node.setKey(keys[count++]);
        if (node.getRight() != null)
            count = setKeys(node.getRight(), keys, count );
        return count;
    }  
 /**
 *
 * @param keyList Der zu erzeugende Splay Baum enthält alle in der Liste enthaltenen Schlüssel.   
 * 
 */
 public SplayTreeCountRot (List<Integer> keyList){
        int[] keyArray = buildKeyArray(keyList);
        root = buildPerfectBalancedTree(new SplayNode(),keyArray.length,1 );
        setKeys(root, keyArray, 0);
        
       
    }

     /**
 *  
 * @param key Es dürfen nur Werte verwendet werten die als Schlüssel im Splay Baum vorhanden sind.   
 * @return Der Knoten mit dem Schlüssel "key".  
 */ 
public SplayNode access (int key){
    splay(key);
    return root;
}    
    
private void splay(int key){
    SplayNode splayNode = search(key);
    if (splayNode == null)
        return;
    while(root != splayNode){
        SplayNode sP = splayNode.getParent();
        SplayNode sPP = sP .getParent();
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
private void zigZig (SplayNode node){
    rotateRight(node.getParent());
    rotateRight(node);
} 
private void zigZag (SplayNode node){
    rotateRight(node);
    rotateLeft(node);
}  
private void zagZig (SplayNode node){
    rotateLeft(node);
    rotateRight(node);
}  
private void zagZag (SplayNode node){
    rotateLeft(node.getParent());
    rotateLeft(node);
}  
private void zig (SplayNode node){
    rotateRight(node);
       
}  
private void zag (SplayNode node){
    rotateLeft(node);
} 
private void rotateRight(SplayNode node){
    numOfRotations++;
    SplayNode par = node.getParent();
    SplayNode parPar = par.getParent();
    
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
private void rotateLeft(SplayNode node){
    numOfRotations++;
    SplayNode par = node.getParent();
    SplayNode parPar = par.getParent();
    
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


private SplayNode search(int key){
    SplayNode search = root;
    SplayNode next = root;
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

}