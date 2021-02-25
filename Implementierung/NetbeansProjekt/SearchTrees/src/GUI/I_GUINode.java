/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;

/**
 ** Getter für die GUI. 
 * @author andreas
 */
public interface I_GUINode {
   
   Color getColorFromGui();
   I_GUINode getLeftFromGui();
   I_GUINode getRightFromGui();
   I_GUINode getParentFromGui();
   String getKeyStringFromGui();
   
}
