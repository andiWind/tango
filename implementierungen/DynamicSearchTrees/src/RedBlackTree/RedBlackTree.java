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
        Node search = root;
        Node help = root;
        while (help != nullNode){
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

    public void delete (double key){
        Node delNode = root;
        while (delNode){
            
        }
    }
    
    
    
}
