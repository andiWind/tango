/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;

/**
 *
 * @author andreas
 */
public interface GUINode {
   
   Color getGUIColor();
   GUINode getLeftChild();
   GUINode getRightChild();
   GUINode getParent();
   String getKeyString();
   
}
