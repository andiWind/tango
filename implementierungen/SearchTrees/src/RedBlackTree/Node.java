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


    private RBColor color;
    private int blackHigh;


    
    public  Node (int k, RBColor c, int bh){
        super(k);
        color = c;
        blackHigh = bh;
    }
    
    @Override
    protected Node getAuxTreeLeft(){
        return (Node) super.getAuxTreeLeft();
    }
    @Override
    protected Node getAuxTreeRight(){
        return (Node) super.getAuxTreeRight();
    }
    @Override
    protected Node getAuxTreeParent(){
        return (Node) super.getAuxTreeParent();
    }
    @Override
    public int getKey(){
        return super.getKey();
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
      
        if (getLeft() == null)
            return super.getLeftFromGui();
        return getLeft();
    }

    @Override
    public I_GUINode getRightFromGui() {
        if (getRight() ==  null)
            return super.getRightFromGui();
        return getRight();
    }

    @Override
    public I_GUINode getParentFromGui() {
        if (getParent() == null)
            return super.getParentFromGui();
        return getParent();
    }

    @Override
    public String getKeyStringFromGui() {
       // return Integer.toString(key);
       return Integer.toString(getKey()) + "_" + Integer.toString(getDepth());
    }

    
    
    @Override
    protected Node getLeft() {
        return (Node) super.getLeft();
    }
    
    @Override
    protected Node getRight() {
        return (Node) super.getRight();
    }
    @Override
   protected Node getParent(){
       return (Node) super.getParent();
   }
    boolean IsLeftChildBlack(){
        if (getLeft() == null)
            return true;
        return getLeft().isBlack();
    }
    boolean IsRightChildBlack(){
        if (getRight() == null)
            return true;
        return getRight().isBlack();
    }
    boolean IsLeftChildRed(){
        if (getLeft() == null)
            return false;
        return getLeft().isRed();
    }
    boolean IsRightChildRed(){
        if (getRight() == null)
            return false;
        return getRight().isRed();
    }

    @Override
    protected void setParent(TangoNode node) {
        super.setParent(node);
    }

    @Override
    protected void setLeft(TangoNode node) {
       super.setLeft(node);
    }

    @Override
    protected void setRight(TangoNode node) {
        super.setRight(node);   
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
    boolean isAroot( ){
        return super.isRoot();
    }
    
    
    /////wieder löschen///////////////////
      public int getBlackHighh(){
          return getBlackHigh();
      }
      public static RBColor rbColorRed(){
          return RBColor.RED;
      }
      public static RBColor rbColorBlack(){
          return RBColor.BLACK;
      }
    
}
