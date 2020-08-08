/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SplayTree;


/**
 *
 * @author andreas
 */
public class SplayNode  {
    private SplayNode left;
    private SplayNode right;
    private SplayNode parent;
    private int key;
    private final boolean  keyFinal;
    
    SplayNode (int k, SplayNode l, SplayNode r, SplayNode p ){
        left = l;
        right = r;
        parent = p;
        key = k; 
        keyFinal = true;
    }
    SplayNode (int k ){
        key = k;
        keyFinal = true;
    }
     SplayNode (){  
         keyFinal = false;
    }
    int getKey (){
        return key;
    }
    void setKey(int k){
        if (!keyFinal)
            key = k;
    }
    SplayNode getLeft(){
        return left;
    }
    SplayNode getRight(){
        return right;
    }
    SplayNode getParent(){
        return parent;
    } 
    void setLeft(SplayNode l){
        left =l;
    }
    void setRight(SplayNode r){
        right = r;
    }
    void setParent(SplayNode p){
        parent = p;
    } 
    
}
