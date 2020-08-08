/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;
import TangoTree.TangoNode;
import java.awt.Color;

/**
 *
 * @author andreas
 */
public class RBTNode extends TangoNode  {


    private RBColor color;
    private int blackHigh;


    
    RBTNode (int k, RBColor c, int bh){
        super(k);
        color = c;
        blackHigh = bh;
    }
    
    @Override
    protected RBTNode getAuxTreeLeft(){
        return (RBTNode) super.getAuxTreeLeft();
    }
    @Override
    protected RBTNode getAuxTreeRight(){
        return (RBTNode) super.getAuxTreeRight();
    }
    @Override
    protected RBTNode getAuxTreeParent(){
        return (RBTNode) super.getAuxTreeParent();
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
    protected RBTNode getLeft() {
        return (RBTNode) super.getLeft();
    }
    
    @Override
    protected RBTNode getRight() {
        return (RBTNode) super.getRight();
    }
    @Override
   protected RBTNode getParent(){
       return (RBTNode) super.getParent();
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
    
    
    
    
}
