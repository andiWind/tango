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


    
    private final int key;
    private RBColor color;
    private int blackHigh;
    private Node left;
    private Node right;
    private Node parent;

    
    Node (int k, RBColor c, int bh){
        key = k;
        color = c;
        blackHigh = bh;
    }
    
    
    
    @Override
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
    public I_GUINode getLeftFromGui() {
      
        if (left == null)
            return super.getLeftFromGui();
        return left;
    }

    @Override
    public I_GUINode getRightFromGui() {
        if (right ==  null)
            return super.getRightFromGui();
        return right;
    }

    @Override
    public I_GUINode getParentFromGui() {
        if (parent == null)
            return super.getParentFromGui();
        return parent;
    }

    @Override
    public String getKeyStringFromGui() {
       // return Integer.toString(key);
       return Integer.toString(key) + "_" + Integer.toString(getDepth()) + "_" + Integer.toString(getMaxDepth()) + "_" + Integer.toString(getMinDepth());
    }

    
    
    @Override
    protected Node getLeft() {
        return left;
    }
    
    @Override
    protected Node getRight() {
        return right;
    }
    @Override
   protected Node getParent(){
       return parent;
   }
    boolean IsLeftChildBlack(){
        if (left == null)
            return true;
        return left.isBlack();
    }
    boolean IsRightChildBlack(){
        if (right == null)
            return true;
        return right.isBlack();
    }
    boolean IsLeftChildRed(){
        if (left == null)
            return false;
        return left.isRed();
    }
    boolean IsRightChildRed(){
        if (right == null)
            return false;
        return right.isRed();
    }

    @Override
    protected void setParent(TangoNode node) {
         if (node == null){
            parent = null;
            return;
        }   
        if(node.getClass() == Node.class)
            parent = (Node) node;
    }

    @Override
    protected void setLeft(TangoNode node) {
         if (node == null){
            left = null;
            return;
        }   
        if(node.getClass() == Node.class)
            left = (Node) node;
    }

    @Override
    protected void setRight(TangoNode node) {
        if (node == null){
            right = null;
            return;
        }   
        if(node.getClass() == Node.class)
            right = (Node) node;
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
