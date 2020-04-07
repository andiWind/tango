
package TangoTree;

import GUI.GUINode;
import java.awt.Color;

/**
 *
 * @author andre
 */
public class Node implements GUINode  {
    boolean auxRoot;
    int depth;
    int minDepth;
    int maxDepth;
    Node right;
    Node left; 
    Node parent;
    int key;


    Node(int i){
        key = i;
    }

    @Override
    public Color getGUIColor() {
        return  java.awt.Color.BLACK;
    }

    @Override
    public GUINode getLeftChild() {
        return left;
    }

    @Override
    public GUINode getRightChild() {
         return right;
    }

    @Override
    public GUINode getParent() {
        return parent;
    }

    @Override
    public String getKeyString() {
        return String.valueOf(key);
    }
    
}
