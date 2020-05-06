/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newGUI;

/**
 *
 * @author andreas
 */
public interface I_GUITree {
    I_GUINode getRoot ();
    void insert(int key);
    void delete(int key);
    I_GUINode search (int key);
    String getName();
    

}
