/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;

import GUI.I_GUINode;
import java.awt.Color;


/**
 *
 * @author andreas
 */
public abstract class TangoNode implements I_GUINode{

    private int depth;
    private int minDepth;
    private int maxDepth;
    private boolean isRoot;
    private TangoNode leftAuxTree;
    private TangoNode rightAuxTree;
    private TangoNode parentNodeAuxTree;

    
 
    protected final void setMinDepth(int m){
        minDepth = m;
    }
    protected final void setMaxDepth(int m){
        maxDepth = m;
    }  
    protected final void setDepth(int d){
        depth = d;
    }  
    protected int getDepth(){
        return depth;
    }  
    protected int getMinDepth(){
        return minDepth;
    }  
    protected int getMaxDepth(){
        return maxDepth;
    }  
   
    void setIsRoot(boolean b){
        isRoot = b;
    }
    boolean isRoot (){
        return isRoot;
    }
     void setLeftAuxTree (TangoNode node){
        leftAuxTree = node;
    }
    void setRightAuxTree (TangoNode node){
        rightAuxTree = node;
    }
    void setParentNodeAuxTree (TangoNode node){
        parentNodeAuxTree = node;
    }
    TangoNode getLeftAuxTree (){
        return leftAuxTree;
    }
    TangoNode getRightAuxTree (){
        return rightAuxTree;
    }
    TangoNode getParentNodeAuxTree (){
        return parentNodeAuxTree;
    }
    @Override
    public Color getColorFromGui() {
        if(isRoot )
            return Color.GREEN;
        return null;
    }

    @Override
    public I_GUINode getLeftFromGui() {
        return leftAuxTree;
    }

    @Override
    public I_GUINode getRightFromGui() {
        return rightAuxTree;
    }

    @Override
    public I_GUINode getParentFromGui(){
        return parentNodeAuxTree;
    } 

  
    protected abstract TangoNode getParent();
    protected abstract TangoNode getLeft();
    protected abstract TangoNode getRight();
    protected abstract void setParent(TangoNode node);
    protected abstract void setLeft(TangoNode node);
    protected abstract void setRight(TangoNode node);
    public abstract int getKey();
  
    protected TangoNode getLeftTango(){
        TangoNode ret = getLeft();
        if (ret != null)
            return ret;
        return leftAuxTree; 
    }
    protected TangoNode getRightTango(){
        TangoNode ret = getRight();
        if (ret != null)
            return ret;
        return rightAuxTree; 
    }
    TangoNode getParentTango(){
        if (isRoot)
            return parentNodeAuxTree;
        return getParent();
    }
   
}
