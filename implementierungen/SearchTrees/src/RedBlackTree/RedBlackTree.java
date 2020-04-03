/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;

import GUI.GUICanvas;

import GUI.GUITree;


/**
 *
 * @author andreas
 */
public class RedBlackTree  implements GUITree{
    private Node root;
    private final Node nullNode;
    
    
    //wieder entfernen 
    GUICanvas guiCanvas;
    public void setCanvas(GUICanvas c){
        guiCanvas = c;
        buildDebuggTree();
     /////////////////////////////////////   
    }
    private void randomTest(){
        for (int i = 0; i < 2000; i++){
            int k = (int) (Math.random() * 1000);
            if (i % 11 == 0)
               insert(k);
            else
               delete(k);   
        }
    }
    private void buildDebuggTree(){
      //  insert(40);insert(43);
       // insert(27);insert(30);
      //  insert(20);insert(19);
      //  insert(14);insert(7);
      //  insert(37);insert(3);
      //  insert(78);insert(11);
       // randomTest();
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    public String getName(){
        return "RED-BLACK";
    }
    public RedBlackTree (){
        nullNode = new Node (0, Node.RBColor.BLACK, true);
   
    }
    @Override
    public Node getRoot (){
        return root;
    }
    @Override
    public void insert (int key){
        Node insNode = new Node(key, Node.RBColor.RED, false);
        insNode.setLeftChild(nullNode);
        insNode.setRightChild(nullNode);
        Node search = search(key);
        if (search == null){
            root = insNode;  
            insNode.setParent(nullNode);
        }
        else if(search.getKey() == key){
            return;
        }
        else  {
            insNode.setParent(search);
            if (key < search.getKey())
                search.setLeftChild(insNode);        
            else
                search.setRightChild(insNode); 
        }
        
       
        insertFixup(insNode);
    }
    private void insertFixup (Node insNode){
        if(root == insNode){
            root.setColor(Node.RBColor.BLACK);
            return;
        }
        Node tempNode = insNode;
        while (tempNode != null && tempNode.getParent().isRed()){
            if (tempNode.getParent() == tempNode.getParent().getParent().getLeftChild() ){ //Der obere der zwei roten Knoten hängt links
               tempNode =  insertFixupLeftCase(tempNode);
            }
            else{ //Der obere der zwei roten Knoten hängt rechts
               tempNode =  insertFixupRightCase(tempNode); 
            }
        }
        if(root != null)
            root.setColor(Node.RBColor.BLACK);
    }
    
    private Node insertFixupLeftCase (Node insNode){
        Node lowerRedNode = insNode;
        Node higherRedNode = insNode.getParent(); //Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNode.getParent().getRightChild().isRed()){
            higherRedNode.getParent().getRightChild().setColor(Node.RBColor.BLACK);
            higherRedNode.setColor(Node.RBColor.BLACK);
            higherRedNode.getParent().setColor(Node.RBColor.RED);
            return higherRedNode.getParent();
        }
        if (lowerRedNode == higherRedNode.getRightChild()){ //Links Rotation
            rotateLeft(higherRedNode);
            Node temp = higherRedNode;
            higherRedNode = lowerRedNode;
            lowerRedNode = temp;   
        }
        higherRedNode.setColor(Node.RBColor.BLACK);
        higherRedNode.getParent().setColor(Node.RBColor.RED);
        rotateRight(higherRedNode.getParent());
        return null;
    }
    private Node insertFixupRightCase (Node insNode){
        Node lowerRedNode = insNode;
        Node higherRedNode = insNode.getParent(); //Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNode.getParent().getLeftChild().isRed()){
            higherRedNode.getParent().getLeftChild().setColor(Node.RBColor.BLACK);
            higherRedNode.setColor(Node.RBColor.BLACK);
            higherRedNode.getParent().setColor(Node.RBColor.RED);
            return higherRedNode.getParent();
        }
        if (lowerRedNode == higherRedNode.getLeftChild()){ //Rechts Rotation
            rotateRight(higherRedNode);
            Node temp = higherRedNode;
            higherRedNode = lowerRedNode;
            lowerRedNode = temp;   
        }
        higherRedNode.setColor(Node.RBColor.BLACK);
        higherRedNode.getParent().setColor(Node.RBColor.RED);
        rotateLeft(higherRedNode.getParent());
        return null;
    }
    
    
    private void rotateLeft (Node n){ 
      Node nullNodeParentSave = nullNode.getParent();
        
      Node nP = n.getParent();
      Node nRc = n.getRightChild();
      
      n.setRightChild(nRc.getLeftChild());
      n.getRightChild().setParent(n);
      
      if (nP.getLeftChild() == n)
          nP.setLeftChild(nRc);
      else if (nP.getRightChild() == n)
          nP.setRightChild(nRc);
      else //nP ist nullNode, n ist die Wurzel
          root = nRc;
      nRc.setParent(nP);
      
      nRc.setLeftChild(n);
      n.setParent(nRc);
      
      nullNode.setParent(nullNodeParentSave);
    }
    private void rotateRight (Node n){ 
      Node nullNodeParentSave = nullNode.getParent();  
        
      Node nP = n.getParent();
      Node nLc = n.getLeftChild();
      
      n.setLeftChild(nLc.getRightChild());
      n.getLeftChild().setParent(n);
      
      if (nP.getLeftChild() == n)
          nP.setLeftChild(nLc);
      else if (nP.getRightChild() == n)
          nP.setRightChild(nLc);
      else //nP ist nullNode, n ist die Wurzel
          root = nLc;

      nLc.setParent(nP);
      
      nLc.setRightChild(n);
      n.setParent(nLc);
      
      nullNode.setParent(nullNodeParentSave);
    }
    private void deleteFixup (Node node){
        Node tempNode = node;
        while (tempNode != root && tempNode.isBlack()){
            
            if (tempNode.getParent().getLeftChild() == tempNode)
                tempNode = deleteFixupLeftCase(tempNode);
            else
                tempNode = deleteFixupRightCase(tempNode);
        }
        tempNode.setColor(Node.RBColor.BLACK);
    }
    private Node deleteFixupLeftCase (Node inputNode){
        Node nodeBro = inputNode.getParent().getRightChild();
        if (nodeBro.isRed()){ //Fall 1
            nodeBro.setColor(Node.RBColor.BLACK);
            nodeBro.getParent().setColor(Node.RBColor.RED);
            rotateLeft(inputNode.getParent());
            nodeBro = inputNode.getParent().getRightChild();
       
            
        }
        if (nodeBro.getLeftChild().isBlack() && nodeBro.getRightChild().isBlack()){ //Fall 2
            nodeBro.setColor(Node.RBColor.RED);
      
            return inputNode.getParent();

        }
        else{ 
            if (nodeBro.getRightChild().isBlack()){
                nodeBro.getLeftChild().setColor(Node.RBColor.BLACK);
                nodeBro.setColor(Node.RBColor.RED);
                rotateRight(nodeBro);
                nodeBro = inputNode.getParent().getRightChild();

            
            }
            nodeBro.setColor(inputNode.getParent().getColor());
            inputNode.getParent().setColor(Node.RBColor.BLACK);
            nodeBro.getRightChild().setColor(Node.RBColor.BLACK);
            rotateLeft(inputNode.getParent());
            
            return root;
        }
        
    }  
    private Node deleteFixupRightCase (Node inputNode){

        Node nodeBro = inputNode.getParent().getLeftChild();
        if (nodeBro.isRed()){ //Fall 1
            nodeBro.setColor(Node.RBColor.BLACK);
            nodeBro.getParent().setColor(Node.RBColor.RED);
            rotateRight(inputNode.getParent());
            nodeBro = inputNode.getParent().getLeftChild();
        }
        if (nodeBro.getLeftChild().isBlack() && nodeBro.getRightChild().isBlack()){ //Fall 2
            nodeBro.setColor(Node.RBColor.RED);
 
            return inputNode.getParent();
        }
        else{ 
            if (nodeBro.getLeftChild().isBlack()){
                nodeBro.getRightChild().setColor(Node.RBColor.BLACK);
                nodeBro.setColor(Node.RBColor.RED);
                rotateLeft(nodeBro);
                nodeBro = inputNode.getParent().getLeftChild();
                
            }
            nodeBro.setColor(inputNode.getParent().getColor());
            inputNode.getParent().setColor(Node.RBColor.BLACK);
            nodeBro.getLeftChild().setColor(Node.RBColor.BLACK);
            rotateRight(inputNode.getParent());
            return root;
        }
        
    }  
    @Override
    public void delete (int key){
        Node delNode = search(key);
        if (delNode == null || delNode.getKey() != key)
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
            if (rightMin.getParent() == delNode){
                rightMin.getRightChild().setParent(rightMin); //Wird benötigt falls rightMin.getRightChild() der nullNode ist.
            }
            else{
                transplant(rightMin, rightMin.getRightChild());
                rightMin.setRightChild(delNode.getRightChild());
                rightMin.getRightChild().setParent(rightMin);
            }
            transplant(delNode, rightMin);
            rightMin.setLeftChild(delNode.getLeftChild());
            rightMin.getLeftChild().setParent(rightMin);
            rightMin.setColor(delNode.getColor());
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
        tree.setParent(placeParent); //Setzt evtl. NUllNode.parent, wichtig für FixUp
    }
   
    @Override
   public Node search (int key){
       if (root == null || root == nullNode) return null;
       Node searchNode = root;
       Node helpNode = root;
       while(helpNode != nullNode){
           searchNode = helpNode;
           if (searchNode.getKey() == key){
               return searchNode;
           }
           else if (key < searchNode.getKey() ){
               helpNode = searchNode.getLeftChild();
           }
           else{
               helpNode = searchNode.getRightChild();
           }
       }
       return searchNode;
   }
}
