/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;

/**
 *
 * @author andreas
 */
class Node {

    private Node parent;
    private Node left;
    private Node right;
    private final double key;
    private Colour colour;
    
    Node (double k, Colour c){
        key = k;
        colour = c;
    }
     Node (double k, Colour c, Node p, Node l, Node r){
        key = k;
        colour = c;
        parent = p;
        left = l;
        right = r;
    }
    void setColour (Colour c){
        colour = c;
    } 
    boolean isRed (){
        return colour == Colour.RED;
    }
    boolean isBlack (){
         return colour == Colour.BLACK;
    }
    double getKey (){
        return key;
    }
    Node getLeftChild(){
        return left;
    }
    Node getRightChild(){
        return right;
    }
    Node getParent(){
        return parent;
    }
    void setLeftChild(Node l){
        left = l;
    }
    void setRightChild(Node r){
        right = r;
    }
    void setParent(Node p){
        parent = p;
    }
   
    enum Colour
    {
        RED, BLACK
    }
}
