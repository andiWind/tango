/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SplayTree;


/**
 *
 * @author andreas
 * Knoten eines SplayTree
 */
public class SplayNode  {
    private SplayNode left;
    private SplayNode right;
    private SplayNode parent;
    private int key;
    private final boolean  keyFinal;
    /**
     * Erzeugt einen SplayNode mit Schlüssel "k". "l" wird das linke Kind, "r" das rechte. "p" wird als Elternknoten gesetzt.
     * @param k 
     * @param l Der Schlüssel von "l" muss kleiner "k" sein.
     * @param r Der Schlüssel von "r" muss größer "k" sein.
     * @param p 
     */
    SplayNode (int k, SplayNode l, SplayNode r, SplayNode p ){
        left = l;
        right = r;
        parent = p;
        key = k; 
        keyFinal = true;
    }
      /**
     * Erzeugt einen SplayNode mit Schlüssel "k". 
     */
    SplayNode (int k ){
        key = k;
        keyFinal = true;
    }
    /**
     * Erzeugt einen SplayNode. Der Schlüssel kann nachträglich einmalig gesetzt werden. 
     */
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
