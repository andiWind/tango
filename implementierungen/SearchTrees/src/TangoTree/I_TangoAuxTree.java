/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;

import GUI.I_GUITree;

/**
 *
 * @author andre
 */
public interface I_TangoAuxTree extends I_GUITree{
    
    I_TangoAuxTree split( int key);
    void merge( I_TangoAuxTree tree, int key);
    void insert (int key);
    TangoNode getRoot ();
 
}
