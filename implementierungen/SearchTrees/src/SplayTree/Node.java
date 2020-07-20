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
public class Node {
    private Node left;
    private Node right;
    private Node parent;
    private final int key;
    
    Node (int k, Node l, Node r, Node p ){
        left = l;
        right = r;
        parent = p;
        key = k;   
    }
     Node (int k ){
        key = k;   
    }
    int getKey (){
        return key;
    }
    Node getLeft(){
        return left;
    }
     Node getRight(){
        return right;
    }
    Node getParent(){
        return parent;
    } 
    void setLeft(Node l){
        left =l;
    }
    void setRight(Node r){
        right = r;
    }
    void setParent(Node p){
        parent = p;
    } 
}
