/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;

import GUI.GUINode;

/**
 *
 * @author andreas
 */
class Node implements GUINode {

    private Node parent;
    private Node left;
    private Node right;
    private final double key;
    private RBColor color;
    
    Node (double k, RBColor c){
        key = k;
        color = c;
    }
     Node (double k, RBColor c, Node p, Node l, Node r){
        key = k;
        color = c;
        parent = p;
        left = l;
        right = r;
    }
    void setColor (RBColor c){
        color = c;
    }
    RBColor getColor (){
        return color;
    }
    boolean isRed (){
        return color == RBColor.RED;
    }
    boolean isBlack (){
         return color == RBColor.BLACK;
    }
    double getKey (){
        return key;
    }
    public Node getLeftChild(){
        return left;
    }
    public Node getRightChild(){
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
   
    enum RBColor
    {
        RED, BLACK
    }
    public java.awt.Color getGUIColor (){
        return (color == RBColor.BLACK ? java.awt.Color.BLACK : java.awt.Color.RED );
    }
}
