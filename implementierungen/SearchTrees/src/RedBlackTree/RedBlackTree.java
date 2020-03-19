/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;

/**
 *
 * @author andreas
 */
public class RedBlackTree {
    private Node root;
    private final Node nullNode;
    
    public RedBlackTree (){
        nullNode = new Node (0, Node.Colour.BLACK);
        root = nullNode;
    }
    
    public void insert (double key){
        Node insNode = new Node(key, Node.Colour.RED);
        insNode.setLeftChild(nullNode);
        insNode.setRightChild(nullNode);
        Node search = nullNode;
        Node help = root;
        while (help != nullNode){ //Platz suchen
            search = help;
            if (key == help.getKey()) return;
            if (key < help.getKey())
                help = help.getLeftChild();
            else
                help = help.getRightChild();
        }
        insNode.setParent(search);
        if (key < search.getKey())
            search.setLeftChild(insNode);        
        else
             search.setRightChild(insNode); 
        if (root == nullNode) 
            root = insNode;  
        insertFixup(insNode);
    }
    private void insertFixup (Node insNode){
        Node tempNode = insNode;
        while (tempNode != null || tempNode.getParent().isRed()){
            if (tempNode.getParent() == tempNode.getParent().getParent().getLeftChild() ){ //Der obere der zwei roten Knoten hängt links
               tempNode =  insertFixupLeftCase(tempNode);
            }
            else{ //Der obere der zwei roten Knoten hängt rechts
               tempNode =  insertFixupRightCase(tempNode); 
            }
        }
        
    }
    
    private Node insertFixupLeftCase (Node insNode){
        Node lowerRedNode = insNode;
        Node higherRedNode = insNode.getParent(); //Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNode.getParent().getRightChild().isRed()){
            higherRedNode.getParent().getRightChild().setColour(Node.Colour.BLACK);
            higherRedNode.setColour(Node.Colour.BLACK);
            higherRedNode.getParent().setColour(Node.Colour.RED);
            return higherRedNode.getParent();
        }
        if (lowerRedNode == higherRedNode.getRightChild()){ //Links Rotation
            rotateLeft(higherRedNode);
            Node temp = higherRedNode;
            higherRedNode = lowerRedNode;
            lowerRedNode = temp;   
        }
        higherRedNode.setColour(Node.Colour.BLACK);
        higherRedNode.getParent().setColour(Node.Colour.RED);
        rotateRight(higherRedNode.getParent());
        return null;
    }
    private Node insertFixupRightCase (Node insNode){
        Node lowerRedNode = insNode;
        Node higherRedNode = insNode.getParent(); //Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNode.getParent().getLeftChild().isRed()){
            higherRedNode.getParent().getLeftChild().setColour(Node.Colour.BLACK);
            higherRedNode.setColour(Node.Colour.BLACK);
            higherRedNode.getParent().setColour(Node.Colour.RED);
            return higherRedNode.getParent();
        }
        if (lowerRedNode == higherRedNode.getLeftChild()){ //Rechts Rotation
            rotateRight(higherRedNode);
            Node temp = higherRedNode;
            higherRedNode = lowerRedNode;
            lowerRedNode = temp;   
        }
        higherRedNode.setColour(Node.Colour.BLACK);
        higherRedNode.getParent().setColour(Node.Colour.RED);
        rotateLeft(higherRedNode.getParent());
        return null;
    }
    
    
    private void rotateLeft (Node n){ // Wurzel wird nie übergeben
        Node nP = n.getParent();
        Node nRc = n.getRightChild();
        nP.setLeftChild(nRc);
        nRc.setParent(nP);
        
        n.setRightChild(nRc.getLeftChild());
        nRc.setParent(n);
        
        nRc.setLeftChild(nP);
        nP.setParent(nRc);
    }
    private void rotateRight (Node n){ // Wurzel wird nie übergeben
        Node nP = n.getParent();
        Node nLc = n.getLeftChild();
        nP.setRightChild(nLc);
        nLc.setParent(nP);
        
        n.setLeftChild(nLc.getRightChild());
        nLc.setParent(n);
        
        nLc.setRightChild(nP);
        nP.setParent(nLc);
    }
    private void deleteFixup (Node node){
        
    }
    public void delete (double key){
        Node delNode = root;
            while (delNode != nullNode ){
                if (key == delNode.getKey())
                    break;
                if (key < delNode.getKey())
                    delNode = delNode.getLeftChild();
                else
                    delNode = delNode.getRightChild();
        }
        if (delNode == nullNode)
            return;
        boolean delFixup = delNode.isBlack();
        Node fixUpNode = delNode.getRightChild(); 
        if (delNode.getLeftChild() == nullNode){
            transplant(delNode, delNode.getRightChild()); 
        }
        else if (delNode.getRightChild() == nullNode){
            transplant(delNode, delNode.getLeftChild());
             fixUpNode = delNode.getLeftChild(); 
        }
        else{ //Beide Subtrees vorhanden. Das kleinste Element des rechten Baumes verwenden
            Node rightMin = getMinNode(delNode.getRightChild());
            delFixup = rightMin.isBlack();
            fixUpNode = rightMin.getRightChild();
            if (rightMin.getParent() != delNode){
                transplant(rightMin, rightMin.getRightChild());
                rightMin.setRightChild(delNode.getRightChild());
                rightMin.getRightChild().setParent(rightMin);
            }
            transplant(delNode, rightMin);
            rightMin.setLeftChild(delNode.getLeftChild());
            rightMin.getLeftChild().setParent(rightMin);
            rightMin.setColour(delNode.getColour());
        }
        if (delFixup)
            deleteFixup(fixUpNode);
    }
    private Node getMinNode(Node t){
        Node temp = t;
        while (temp.getLeftChild() != nullNode && temp.getLeftChild() != null)
            temp = temp.getLeftChild();
        return temp;
    }
    /**
     * 
     * @param place Darf nicht null sein.
     * @param tree Darf nicht null sein  oder auf nullNode zeigen
     */
    private void transplant (Node place, Node tree){
        if (place.getParent() == nullNode || place.getParent() == null ){
            root = tree; 
            tree.setParent(nullNode);
            return;
        }
        Node placeParent = place.getParent();
        if (place == placeParent.getLeftChild())
            placeParent.setLeftChild(tree);
        else
            placeParent.setRightChild(tree);
        tree.setParent(placeParent);
    }
    
    
}
