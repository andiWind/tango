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
    private boolean mostRecent;
    protected int depth;
    protected int minDepth;
    protected int maxDepth;
    private boolean isRoot;
    protected TangoNode leftAuxTree;
    protected TangoNode rightAuxTree;
    protected TangoNode parentNodeAuxTree;
    
    void setMinDepth(int m){
        minDepth = m;
    }
    protected void setMaxDepth(int m){
        maxDepth = m;
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
    void setMostRecent(boolean b){
        mostRecent = b;
    }
    boolean getMostRecent(){
        return mostRecent;
    }
    void setIsRoot(boolean b){
        isRoot = b;
    }
    boolean isRoot (){
        return isRoot;
    }
    protected void setLeftAuxTree (TangoNode node){
        leftAuxTree = node;
    }
    protected void setRightAuxTree (TangoNode node){
        rightAuxTree = node;
    }
    protected void setParentNodeAuxTree (TangoNode node){
        parentNodeAuxTree = node;
    }
    protected TangoNode getLeftAuxTree (){
        return leftAuxTree;
    }
    protected TangoNode getRightAuxTree (){
        return rightAuxTree;
    }
    protected TangoNode getParentNodeAuxTree (){
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

  
    public abstract TangoNode getParent();
    public abstract TangoNode getLeft();
    public abstract TangoNode getRight();
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
    TangoNode getParentIntern(){
        if (isRoot)
            return parentNodeAuxTree;
        return getParent();
    }
}
