/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;
import GUI.I_GUINode;
import TangoTree.TangoNode;
import java.awt.Color;

/**
 *
 * @author andreas
 */
public class Node extends TangoNode implements I_GUINode {


    private final boolean  nullNode;
    private Node parent;
    private Node left;
    private Node right;
    private final int key;
    private RBColor color;
    private int blackHigh;

    
    Node (int k, RBColor c, boolean nn){
        key = k;
        color = c;
        nullNode = nn;
        blackHigh = 1;
    }
     Node (int k, RBColor c, Node p, Node l, Node r, boolean nn){
        key = k;
        color = c;
        parent = p;
        left = l;
        right = r;
        nullNode = nn;
        blackHigh = 1;
    }
    public int getKey(){
        return key;
    } 
    int getBlackHigh(){
        return blackHigh;
    }
    void setBlackHigh(int bh){
        if(bh > 0);
            blackHigh = bh;
    }
    void decBlackHigh(){
        if (blackHigh > 1)
            blackHigh--;
    }
    void incBlackHigh(){
        blackHigh++;
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
    
    @Override
   public Node getParent(){
       return parent;
   }
    
    Node getLeftIntern(){ //With NullNode
        return left;
    }

    Node getRightIntern(){ // With NullNode
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

    @Override
    public I_GUINode getLeftFromGui() {
      
        if (left.nullNode)
            return super.getLeftFromGui();
        return left;
    }

    @Override
    public I_GUINode getRightFromGui() {
        if (right.nullNode)
            return super.getRightFromGui();
        return right;
    }

    @Override
    public I_GUINode getParentFromGui() {
        if (parent.nullNode)
            return super.getParentFromGui();
        return parent;
    }

    @Override
    public String getKeyStringFromGui() {
        if (nullNode) return "null";
       // return Integer.toString(key);
       return Integer.toString(key) + "_" + Integer.toString(blackHigh);
    }

    @Override
    public Node getLeft() {
        if (left.nullNode)
            return null;
        return left;
        
    }

    @Override
    public Node getRight() {
         if (right.nullNode)
             return null;
        return right;
    }

    enum RBColor
    {
        RED, BLACK
    }
    @Override
    public Color getColorFromGui (){
        Color ret = super.getColorFromGui(); 
        if (ret != null)
            return ret;
        return (color == RBColor.BLACK ? Color.BLACK : Color.RED );
    }
}
