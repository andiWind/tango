/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;



/**
 *
 * @author andre
 */
public abstract class TangoAuxTree {
   
    
    
    private Integer getSplitterLeft(int depth){
        TangoNode node = getRoot();
        while(node.getLeft() != null && node.getDepth() > depth ){
            node = node.getLeft();
        }
        if(node.getRight() == null)
            return null;
        else{
            node = node.getRight();
            while(node.getLeft() != null)
                node = node.getLeft();
        } 
        return node.getKey();
    }
    private Integer getSplitterRight(int depth){
        TangoNode node = getRoot();
        while(node.getRight() != null && node.getDepth() > depth ){
            node = node.getRight();
        }
        if(node.getLeft() == null)
            return null;
        else{
            node = node.getLeft();
            while(node.getRight() != null)
                node = node.getRight();
        } 
        return node.getKey();
    }
    
    private void cut (int depth){
        Integer splitterLeft = getSplitterLeft(depth);
        Integer splitterRight = getSplitterRight(depth);
        TangoNode splitNodeL;
        TangoNode splitNodeR;
        if (splitterLeft != null)
            splitNodeL = split(splitterLeft);
        if (splitterRight != null)
            splitNodeR = split(splitterRight);
        if ()
        
            
    }
    private concat()
    private TangoNode split(){
        return null;
    }
    
    
    public abstract TangoNode split( int key);
    public abstract void merge(TangoAuxTree tree, int key);
    public abstract void insert (int key, int depth);
    public abstract TangoNode getRoot ();
    
    
}
