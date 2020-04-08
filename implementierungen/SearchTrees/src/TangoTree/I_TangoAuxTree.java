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
public interface I_TangoAuxTree {
    
    I_TangoAuxTree split( int key);
    I_TangoAuxTree merge( I_TangoAuxTree tree);
    void insert (int key);
    TangoNode getRoot ();
 
}
