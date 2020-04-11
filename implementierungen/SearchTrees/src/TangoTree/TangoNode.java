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
    private int dep;
    private int minDep;
    private int maxDep;
    private boolean isRoot;
    private TangoNode leftAuxTree;
    private TangoNode rightAuxTree;
    private TangoNode parentNodeAuxTree;
    
    void setMostRecent(boolean b){
        mostRecent = b;
    }
    boolean getMostRecent(){
        return mostRecent;
    }
    void setRoot(boolean b){
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

  
    public abstract TangoNode getParent();
    public abstract TangoNode getLeft();
    public abstract TangoNode getRight();
    public abstract int getKey();
    
    TangoNode getLeftIntern(){
        TangoNode ret = getLeft();
        if (ret != null)
            return ret;
        return leftAuxTree; 
    }
    TangoNode getRightIntern(){
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
