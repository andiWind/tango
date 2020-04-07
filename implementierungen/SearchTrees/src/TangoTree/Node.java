
package TangoTree;

/**
 *
 * @author andre
 */
public class Node {
    boolean hanged;
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
    
}
