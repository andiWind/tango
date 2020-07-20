/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SplayTree;

import java.util.List;

/**
 *
 * @author andre
 */
public class SplayTree {
    private Node root;



    public SplayTree (List<Integer> keyList){
        if ( keyList != null && ! keyList.isEmpty() ){
            for (int key : keyList){
                insert(key);
            }
        }
    }
    private void insert (int key){
        splay(key);
        if (root == null){
            root = new Node(key);
            return;
        }
       if (root.getKey() == key )
           return;
       Node insertNode = new Node(key);
       if (root.getKey() < key){
           insertNode.setLeft(root);
           root.setParent(insertNode);
           
           insertNode.setRight(root.getRight());
           root.getRight().setParent(insertNode);
           root.setRight(null);
           
           root = insertNode;
           return;
       }
        insertNode.setRight(root);
        root.setParent(insertNode);
           
        insertNode.setLeft(root.getLeft());
        root.getLeft().setParent(insertNode);
        root.setLeft(null);
           
        root = insertNode;
        
       
    }
    
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
    rotateLeft(node.getParent());
    rotateLeft(node);
} 
private void zigZag (Node node){
    rotateLeft(node);
    rotateRight(node);
}  
private void zagZig (Node node){
    rotateRight(node);
    rotateLeft(node);
}  
private void zagZag (Node node){
    rotateRight(node.getParent());
    rotateRight(node);
}  
private void zig (Node node){
    rotateLeft(node);
       
}  
private void zag (Node node){
    rotateRight(node);
} 
private void rotateRight(Node node){
    Node par = node.getParent();
    Node parPar = par.getParent();
    
    par.setRight(node.getLeft());
    node.getLeft().setParent(par);
    
    node.setLeft(par);
    par.setParent(node);
    
    if (parPar == null){
        root = par;
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
    
    par.setLeft(node.getRight());
    node.getRight().setParent(par);
    
    node.setRight(par);
    par.setParent(node);
    
    if (parPar == null){
        root = par;
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
        else if (next.getKey() < key)
            next = next.getLeft();
        else 
             next = next.getRight();
    }
    return search;
}
   

}