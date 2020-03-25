/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author andreas
 */
public interface GUITree {
    GUINode getRoot ();
    void insert(int key);
    void delete(int key);
    GUINode search (int key);
    String getName();
}
