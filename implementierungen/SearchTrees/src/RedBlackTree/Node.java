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

    private final boolean  nullNode;
    private Node parent;
    private Node left;
    private Node right;
    private final int key;
    private RBColor color;
    
    Node (int k, RBColor c, boolean nn){
        key = k;
        color = c;
        nullNode = nn;
    }
     Node (int k, RBColor c, Node p, Node l, Node r, boolean nn){
        key = k;
        color = c;
        parent = p;
        left = l;
        right = r;
        nullNode = nn;
    }
    public int getKey(){
        return key;
    } 
    public String getKeyString(){
        if (nullNode) return "null";
        return Integer.toString(key);
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
   public Node getParent(){
       return parent;
   }
    public Node getLeftChild(){
        return left;
    }
    public Node getRightChild(){
        return right;
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
