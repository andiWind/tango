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
 * Getter für die GUI. ZU kann sich nochmal speziell entschieden werdn, was in der GUI dargestellt wird.
 */
public interface I_GUINode {
   
   Color getColorFromGui();
   I_GUINode getLeftFromGui();
   I_GUINode getRightFromGui();
   I_GUINode getParentFromGui();
   String getKeyStringFromGui();
   
}
