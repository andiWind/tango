/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;

import GUI.I_GUINode;
import java.awt.Color;


/**
 * Knoten eines TangoTrees
 * @author andreas
 *
 */
public abstract class TangoNode implements I_GUINode{

    private int depth;
    private int minDepth;
    private int maxDepth;
    private boolean isRoot;
    private TangoNode left;
    private TangoNode right;
    private TangoNode parent;
    private final int key;

    protected TangoNode (int k){
        key = k;
    }
 
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
    protected boolean isRoot (){
        return isRoot;
    }
    protected void setLeft (TangoNode node){
        left = node;
    }
    protected void setRight (TangoNode node){
        right = node;
    }
    protected void setParent (TangoNode node){
        parent = node;
    }
  
    @Override
    public Color getColorFromGui() {
        if(isRoot )
            return Color.GREEN;
        return Color.BLACK;
    }
     @Override
    public String getKeyStringFromGui() {
       return Integer.toString(key); 
    }
    @Override
    public I_GUINode getLeftFromGui() {
        return left;
    }

    @Override
    public I_GUINode getRightFromGui() {
        return right;
    }

    @Override
    public I_GUINode getParentFromGui(){
        return parent;
    } 

    /**
     * Diese Methode berücksichtigt nur Knoten innerhalb eines TangoAuxTrees
     * 
     * @return 
     */
    protected TangoNode getParent(){
        if (parent == null || isRoot )
            return null;
        return parent;
    }
    /**
     * Diese Methode berücksichtigt nur Knoten innerhalb eines TangoAuxTrees
     * 
     * @return 
     */
    protected TangoNode getLeft(){
        if (left == null || left.isRoot )
            return null;
        return left;
    }
 /**
     * Diese Methode berücksichtigt nur Knoten innerhalb eines TangoAuxTrees
     * 
     * @return 
     */
    protected TangoNode getRight(){
        if (right == null || right.isRoot )
            return null;
        return right;
    }
     


    /**
     * Gibt das linke Kind zurück, falls dieses die Wurzel eines TangoAuxTrees ist, ansonsten "null"
     * @return  
     */
    protected TangoNode getAuxTreeLeft(){
        if(getLeftTango() != null && getLeftTango().isRoot()){
            return  getLeftTango();
        }
        return null;
    }
   
    /**
     * Gibt das rechte Kind zurück, falls dieses die Wurzel eines TangoAuxTrees ist, ansonsten "null"
     * @return  
     */
    protected TangoNode getAuxTreeRight(){
        if(getRightTango() != null && getRightTango().isRoot()){
            return  getRightTango();
        }
        return null;
    }
     
    /**
     * Gibt den Elternknoten zurück, falls dieser die Wurzel eines TangoAuxTrees ist, ansonsten "null"
     * @return  
     */
    protected TangoNode getAuxTreeParent(){
       
        if(isRoot() && getParent() != null ){
            return  getParent();
        }
        return null;
    }
    
    public int getKey(){
        return key;
    }
    /**
     * Gibt das linke Kind zurück, unabhängig davon in welchem "Auxtree"  sich dieses befindet 
     * @return 
     */
    TangoNode getLeftTango(){
        return left;
    }
     /**
     * Gibt das rechte Kind zurück, unabhängig davon in welchem "Auxtree"  sich dieses befindet 
     * @return 
     */
    TangoNode getRightTango(){
        return right; 
    }
    /**
     * Gibt den Elternknoten zurück, unabhängig davon in welchem "Auxtree"  sich dieses befindet 
     * @return 
     */
    TangoNode getParentTango(){
       return parent;
    }

    
}
