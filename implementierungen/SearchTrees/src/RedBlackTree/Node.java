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
    Node (int k, RBColor c, int bh, int d){
        depth = d;
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
   public Node getParent(){
       return parent;
   }
    
    void setLeft(Node l){
        left = l;
    }
    void setRight(Node r){
        right = r;
    }
    void setParent(Node p){
        parent = p;
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
       return Integer.toString(key) + "_" + Integer.toString(depth) + "_" + Integer.toString(maxDepth) + "_" + Integer.toString(minDepth);
    }

    
    
    @Override
    public Node getLeft() {
        return left;
    }
    
    @Override
    public Node getRight() {
        return right;
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
