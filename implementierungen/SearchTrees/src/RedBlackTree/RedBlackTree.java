/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;

import GUI.GUICanvas;
import GUI.I_GUITree;
import RedBlackTree.Node.RBColor;
import TangoTree.I_TangoAuxTree;



/**
 *
 * @author andreas
 */
public class RedBlackTree  implements I_GUITree, I_TangoAuxTree {
    private Node root;
    private Node nullNode;
    
    
    //wieder entfernen 
    GUICanvas guiCanvas;
    public void setCanvas(GUICanvas c){
        guiCanvas = c;
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
        insert(40);
        insert(43);
        insert(27);
        insert(30);
        insert(20);
        insert(19);
        insert(14);
        insert(7);
        insert(37);
        insert(3);
        insert(78);
        insert(11);
       // randomTest();
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    @Override
    public String getName(){
        return "RED-BLACK";
    }
    public RedBlackTree (){
        nullNode = new Node (0, Node.RBColor.BLACK, true,1);
   
    }
    @Override
    public Node getRoot (){
        return root;
    }
    
    @Override
    public void insert (int  key){
        Node insNode = new Node(key, Node.RBColor.RED, false,1);
        insNode.setLeftChild(nullNode);
        insNode.setRightChild(nullNode);
        Node search = search(insNode.getKey());
        if (search == null){
            root = insNode;  
            insNode.setParent(nullNode);
        }
        else if(search.getKey() == insNode.getKey()){
            return;
        }
        else  {
            insNode.setParent(search);
            if (insNode.getKey() < search.getKey())
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
            if (tempNode.getParent() == tempNode.getParent().getParent().getLeftIntern() ){ //Der obere der zwei roten Knoten hängt links
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
        if (higherRedNode.getParent().getRightIntern().isRed()){
            higherRedNode.getParent().getRightIntern().setColor(Node.RBColor.BLACK);
            higherRedNode.setColor(Node.RBColor.BLACK);
            higherRedNode.getParent().setColor(Node.RBColor.RED);
            higherRedNode.getParent().incBlackHigh();
            return higherRedNode.getParent();
        }
        if (lowerRedNode == higherRedNode.getRightIntern()){ //Links Rotation
            rotateLeft(higherRedNode);
            higherRedNode = lowerRedNode;
        }
        higherRedNode.setColor(Node.RBColor.BLACK);
        higherRedNode.getParent().setColor(Node.RBColor.RED);
        rotateRight(higherRedNode.getParent());
        return null;
    }
    private Node insertFixupRightCase (Node insNode){
        Node lowerRedNode = insNode;
        Node higherRedNode = insNode.getParent(); //Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNode.getParent().getLeftIntern().isRed()){
            higherRedNode.getParent().getLeftIntern().setColor(Node.RBColor.BLACK);
            higherRedNode.setColor(Node.RBColor.BLACK);
            higherRedNode.getParent().setColor(Node.RBColor.RED);
            higherRedNode.getParent().incBlackHigh();
            return higherRedNode.getParent();
        }
        if (lowerRedNode == higherRedNode.getLeftIntern()){ //Rechts Rotation
            rotateRight(higherRedNode);
            higherRedNode = lowerRedNode;
        }
        higherRedNode.setColor(Node.RBColor.BLACK);
        higherRedNode.getParent().setColor(Node.RBColor.RED);
        rotateLeft(higherRedNode.getParent());
        return null;
    }
    
    
    private void rotateLeft (Node n){ 
      Node nullNodeParentSave = nullNode.getParent();
        
      Node nP = n.getParent();
      Node nRc = n.getRightIntern();
      
      n.setRightChild(nRc.getLeftIntern());
      n.getRightIntern().setParent(n);
      
      if (nP.getLeftIntern() == n)
          nP.setLeftChild(nRc);
      else if (nP.getRightIntern() == n)
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
      Node nLc = n.getLeftIntern();
      
      n.setLeftChild(nLc.getRightIntern());
      n.getLeftIntern().setParent(n);
      
      if (nP.getLeftIntern() == n)
          nP.setLeftChild(nLc);
      else if (nP.getRightIntern() == n)
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
            
            if (tempNode.getParent().getLeftIntern() == tempNode)
                tempNode = deleteFixupLeftCase(tempNode);
            else
                tempNode = deleteFixupRightCase(tempNode);
        }
        tempNode.setColor(Node.RBColor.BLACK);
    }
    private Node deleteFixupLeftCase (Node inputNode){
        Node inputNodeBro = inputNode.getParent().getRightIntern();
        if (inputNodeBro.isRed()){ //Fall 1
            inputNodeBro.setColor(Node.RBColor.BLACK);
            inputNodeBro.getParent().setColor(Node.RBColor.RED);
            rotateLeft(inputNode.getParent());
            inputNodeBro = inputNode.getParent().getRightIntern();   
        }
        if (inputNodeBro.getLeftIntern().isBlack() && inputNodeBro.getRightIntern().isBlack()){ //Fall 2
            inputNodeBro.setColor(Node.RBColor.RED);
            inputNode.getParent().decBlackHigh();
            return inputNode.getParent();
        }
        else{ 
            if (inputNodeBro.getRightIntern().isBlack()){
                inputNodeBro.getLeftIntern().setColor(Node.RBColor.BLACK);
                inputNodeBro.setColor(Node.RBColor.RED);
                rotateRight(inputNodeBro);
                inputNodeBro = inputNode.getParent().getRightIntern();
            }
            inputNodeBro.setColor(inputNode.getParent().getColor());
            inputNode.getParent().setColor(Node.RBColor.BLACK);
            inputNodeBro.getRightIntern().setColor(Node.RBColor.BLACK);
            inputNode.getParent().decBlackHigh();
            inputNodeBro.incBlackHigh();
            rotateLeft(inputNode.getParent());
            return root;
        }
    }  
    private Node deleteFixupRightCase (Node inputNode){

        Node inputNodeBro = inputNode.getParent().getLeftIntern();
        if (inputNodeBro.isRed()){ //Fall 1
            inputNodeBro.setColor(Node.RBColor.BLACK);
            inputNodeBro.getParent().setColor(Node.RBColor.RED);
            rotateRight(inputNode.getParent());
            inputNodeBro = inputNode.getParent().getLeftIntern();
        }
        if (inputNodeBro.getLeftIntern().isBlack() && inputNodeBro.getRightIntern().isBlack()){ //Fall 2
            inputNodeBro.setColor(Node.RBColor.RED);
            inputNode.getParent().decBlackHigh();
            return inputNode.getParent();
        }
        else{ 
            if (inputNodeBro.getLeftIntern().isBlack()){
                inputNodeBro.getRightIntern().setColor(Node.RBColor.BLACK);
                inputNodeBro.setColor(Node.RBColor.RED);
                rotateLeft(inputNodeBro);
                inputNodeBro = inputNode.getParent().getLeftIntern();
                
            }
            inputNodeBro.setColor(inputNode.getParent().getColor());
            inputNode.getParent().setColor(Node.RBColor.BLACK);
            inputNodeBro.getLeftIntern().setColor(Node.RBColor.BLACK);
            inputNode.getParent().decBlackHigh();
            inputNodeBro.incBlackHigh();
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
        Node fixUpNode = delNode.getRightIntern(); 
        if (delNode.getLeftIntern() == nullNode){
            transplant(delNode, delNode.getRightIntern()); 
        }
        else if (delNode.getRightIntern() == nullNode){
            transplant(delNode, delNode.getLeftIntern());
            fixUpNode = delNode.getLeftIntern(); 
        }
        else{ //Beide Subtrees vorhanden. Das kleinste Element des rechten Baumes verwenden
            Node rightMin = getMinNode(delNode.getRightIntern());
            delFixup = rightMin.isBlack();
            fixUpNode = rightMin.getRightIntern();
            if (rightMin.getParent() == delNode){
                rightMin.getRightIntern().setParent(rightMin); //Wird benötigt falls rightMin.getRightIntern() der nullNode ist.
            }
            else{
                transplant(rightMin, rightMin.getRightIntern());
                rightMin.setRightChild(delNode.getRightIntern());
                rightMin.getRightIntern().setParent(rightMin);
            }
            transplant(delNode, rightMin);
            rightMin.setLeftChild(delNode.getLeftIntern());
            rightMin.getLeftIntern().setParent(rightMin);
            rightMin.setColor(delNode.getColor());
            rightMin.setBlackHigh(delNode.getBlackHigh());
        }
        if (delFixup)
            deleteFixup(fixUpNode);
       
    }
    private Node getMinNode(Node t){
        Node temp = t;
        while (temp.getLeftIntern() != nullNode && temp.getLeftIntern() != null)
            temp = temp.getLeftIntern();
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
        if (place == placeParent.getLeftIntern())
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
               helpNode = searchNode.getLeftIntern();
           }
           else{
               helpNode = searchNode.getRightIntern();
           }
       }
       return searchNode;
   }

    @Override
    //Kleiner als key dann in ret
    public I_TangoAuxTree split(int key) {
       RedBlackTree ret = new RedBlackTree(); 
       RedBlackTree newThis = new RedBlackTree(); 
       Node searchNodeRet = null;
       Node searchNodeNewThis = null;
       Node splitNode = root;
       while (splitNode != null){
           if (key < splitNode.getKey()){
                if(searchNodeRet != null)
                    while(searchNodeRet.getBlackHigh() > splitNode.getRight().getBlackHigh() )
                        searchNodeRet = searchNodeRet.getRight();
                ret.mergeForSplit(splitNode.getRight(), searchNodeRet, splitNode.getKey());
                if(searchNodeRet == null)
                    searchNodeRet = ret.root;
                splitNode = splitNode.getLeft();
           }
           else if (key > splitNode.getKey()) {
               if(searchNodeNewThis != null)
                    while(searchNodeNewThis.getBlackHigh() > splitNode.getLeft().getBlackHigh() )
                        searchNodeNewThis = searchNodeNewThis.getRight();
                newThis.mergeForSplit(splitNode.getRight(), searchNodeNewThis, splitNode.getKey());
                if(searchNodeNewThis == null)
                    searchNodeNewThis = ret.root;
               splitNode = splitNode.getRight();
           }
           else{
               if(searchNodeRet != null)
                    while(searchNodeRet.getBlackHigh() > splitNode.getRight().getBlackHigh() )
                        searchNodeRet = searchNodeRet.getRight();
                ret.mergeForSplit(splitNode.getRight(), searchNodeRet, splitNode.getKey());
                
                if(searchNodeNewThis != null)
                    while(searchNodeNewThis.getBlackHigh() > splitNode.getLeft().getBlackHigh() )
                        searchNodeNewThis = searchNodeNewThis.getRight();
                newThis.mergeForSplit(splitNode.getRight(), searchNodeNewThis, splitNode.getKey());
                break;
           }    
       }
       root = newThis.root;
       nullNode = newThis.nullNode;
       nullNode.setParent(null);
       nullNode.setLeftChild(null);
       nullNode.setRightChild(null);
       return ret;
    }
    private RedBlackTree mergeForSplit (Node t2, Node searchNode, int key ){
        int newNodeBlackHigh = t2.getBlackHigh();
        if(t2.isBlack())
            newNodeBlackHigh++;
        Node newNode = new Node(key, RBColor.RED, false, newNodeBlackHigh);
        if(searchNode == null){ //Erster Teilbaum wird abgespalten
            RedBlackTree ret = new RedBlackTree();
            ret.root = t2;
            t2.setParent(ret.nullNode);
            ret.nullNode.setParent(t2);
        }
        else if(searchNode.getKey() < key)
            return mergeLeftCase(t2, newNode, searchNode);
        return mergeRightCase(t2, newNode, searchNode);
    }

    @Override
    //Alle keys von tree müssen entweder kleiner oder größer als die von this sein
    //Der resultierende Baum enthält die nullNodes von beiden Bäumen, -> kein Problem
    //Nach der Operation gibt es nur noch den Returnbaum. Keiner der EInzelbäume existiert mehr
    public I_TangoAuxTree merge(I_TangoAuxTree tree, int key) {
        if (tree.getClass() != RedBlackTree.class)
            throw new IllegalArgumentException();
        RedBlackTree t2 = (RedBlackTree)tree;
        I_TangoAuxTree ret;
        if (root.getBlackHigh() < t2.root.getBlackHigh()){
            return t2.merge(this, key);
        }
        Node searchNode = root;
        Node newNode = new Node(key, RBColor.RED, false, t2.root.getBlackHigh() +1);
        if(key < root.getKey()){
            while (! (searchNode.isBlack() && searchNode.getBlackHigh() == t2.getRoot().getBlackHigh() ) ) {
                searchNode = searchNode.getLeft();
            }
            ret = mergeLeftCase(t2.root, newNode, searchNode);
        }
        else{
             while (! (searchNode.isBlack() && searchNode.getBlackHigh() == t2.getRoot().getBlackHigh() ) ) {
                searchNode = searchNode.getRight();
             }
            ret = mergeRightCase(t2.root, newNode, searchNode);
        }
    //Zugriffe über alte Zeiger auf den eingefügten Baum verhindern
        t2.root = null;
        t2.nullNode.setLeftChild(null);
        t2.nullNode.setRightChild(null);
        t2.nullNode.setParent(null);
        
        return ret;
    }

   
    private RedBlackTree mergeLeftCase(Node t2Root, Node newNode, Node searchNode){
        if (searchNode == null){
            root = t2Root;
            return this;
        }
        searchNode.getParent().setLeftChild(newNode);
        newNode.setParent(searchNode.getParent());
        newNode.setRightChild(searchNode);
        searchNode.setParent(newNode);
        newNode.setLeftChild(t2Root);
        t2Root.setParent(newNode);
        if (searchNode == root)
            root = newNode;
        insertFixup(newNode);
        return this;
    }
    private RedBlackTree mergeRightCase(Node t2Root, Node newNode,  Node searchNode){
         if (searchNode == null){
            root = t2Root;
            return this;
        }
        searchNode.getParent().setRightChild(newNode);
        newNode.setParent(searchNode.getParent());
        newNode.setLeftChild(searchNode);
        searchNode.setParent(newNode);
        newNode.setRightChild(t2Root);
        t2Root.setParent(newNode);
        if (searchNode == root)
            root = newNode;
        insertFixup(newNode);
        return this;
    }
   
}
