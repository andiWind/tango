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
        return null;
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

    //Diese Methode wird den Auxtrees angeboten um normal innerhalb ihrer Struktur arbeiten zu können
    protected TangoNode getParent(){
        if (parent == null || isRoot )
            return null;
        return parent;
    }
    //Diese Methode wird den Auxtrees angeboten um normal innerhalb ihrer Struktur arbeiten zu können
    protected TangoNode getLeft(){
        if (left == null || left.isRoot )
            return null;
        return left;
    }
    //Diese Methode wird den Auxtrees angeboten um normal innerhalb ihrer Struktur arbeiten zu können
    protected TangoNode getRight(){
        if (right == null || right.isRoot )
            return null;
        return right;
    }
     //Diese Methode kann von den ableitenden Klassen verwendet werden um bei Veränderungen an der Baumstruktur 
    //die Zeiger nach ausen mitzupflegen
    protected TangoNode getAuxTreeLeft(){
        if(getLeftTango() != null && getLeftTango().isRoot()){
            return  getLeftTango();
        }
        return null;
    }
     //Diese Methode kann von den ableitenden Klassen verwendet werden um bei Veränderungen an der Baumstruktur 
    //die Zeiger nach ausen mitzupflegen
    protected TangoNode getAuxTreeRight(){
        if(getRightTango() != null && getRightTango().isRoot()){
            return  getRightTango();
        }
        return null;
    }
     //Diese Methode kann von den ableitenden Klassen verwendet werden um bei Veränderungen an der Baumstruktur 
    //die Zeiger nach ausen mitzupflegen
    protected TangoNode getAuxTreeParent(){
       
        if(isRoot() && getParent() != null ){
            return  getParent();
        }
        return null;
    }
    
    public int getKey(){
        return key;
    }
  
    TangoNode getLeftTango(){
        return left;
    }
    TangoNode getRightTango(){
        return right; 
    }
    TangoNode getParentTango(){
       return parent;
    }
    
    
    
    //wieder löschen/////////////////////////////
    public abstract int getBlackHighh();
    public int getDepthh(){
        return depth;
    }
    public void setLeftt (TangoNode node){
        left = node;
    }
    public void setRightt (TangoNode node){
        right = node;
    }
    public void setParentt (TangoNode node){
        parent = node;
    }
    public void setIsRoott(boolean b){
        isRoot = b;
    }
    public void setMinDepthh(int m){
        minDepth = m;
    }
    public void setMaxDepthh(int m){
        maxDepth = m;
    }  
    public void setDepthh(int d){
        depth = d;
    } 
    public int getMinDepthh(){
        return minDepth;
    }  
    public int getMaxDepthh(){
        return maxDepth;
    }  
      public TangoNode getLeftt(){
        if (left == null || left.isRoot )
            return null;
        return left;
    }
    //Diese Methode wird den Auxtrees angeboten um normal innerhalb ihrer Struktur arbeiten zu können
    public TangoNode getRightt(){
        if (right == null || right.isRoot )
            return null;
        return right;
    }
    //////////////////////////////////////////////
   
}
