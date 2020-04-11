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
      
    }
    @Override
    public Node getRoot (){
        return root;
    }
    
    @Override
    public void insert (int  key){
        Node insNode = new Node(key, Node.RBColor.RED, 1);
        Node search = search(insNode.getKey());
        if (search == null){
            root = insNode;  
        }
        else if(search.getKey() == insNode.getKey()){
            return;
        }
        else  {
            insNode.setParent(search);
            if (insNode.getKey() < search.getKey())
                search.setLeft(insNode);        
            else
                search.setRight(insNode); 
        }
        insertFixup(insNode);
    }
   
    private void insertFixup (Node insNode){
        Node tempNode = insNode;
        while (tempNode != root && tempNode.getParent() != null && tempNode.getParent().isRed()){
            if (tempNode.getParent() == tempNode.getParent().getParent().getLeft() ){ //Der obere der zwei roten Knoten hängt links
               tempNode =  insertFixupLeftCase(tempNode);
            }
            else{ //Der obere der zwei roten Knoten hängt rechts
               tempNode =  insertFixupRightCase(tempNode); 
            }
        }
        root.setColor(Node.RBColor.BLACK);
    }
    
    private Node insertFixupLeftCase (Node insNode){
        Node lowerRedNode = insNode;
        Node higherRedNode = insNode.getParent(); 
        Node higherRedNodePar = higherRedNode.getParent();//Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNodePar.IsRightChildRed()){
            higherRedNodePar.getRight().setColor(Node.RBColor.BLACK);
            higherRedNode.setColor(Node.RBColor.BLACK);
            higherRedNodePar.setColor(Node.RBColor.RED);
            higherRedNodePar.incBlackHigh();
            return higherRedNodePar;
        }
        if (lowerRedNode == higherRedNode.getRight()){ //Links Rotation
            rotateLeft(higherRedNode);
            higherRedNode = lowerRedNode;
            higherRedNodePar = higherRedNode.getParent();
        }
        higherRedNode.setColor(Node.RBColor.BLACK);
        higherRedNodePar.setColor(Node.RBColor.RED);
        rotateRight(higherRedNodePar);
        return root;
    }
    private Node insertFixupRightCase (Node insNode){
        Node lowerRedNode = insNode;
        Node higherRedNode = insNode.getParent(); 
        Node higherRedNodePar = higherRedNode.getParent();//Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNodePar.IsLeftChildRed() ){
            higherRedNodePar.getLeft().setColor(Node.RBColor.BLACK);
            higherRedNode.setColor(Node.RBColor.BLACK);
            higherRedNodePar.setColor(Node.RBColor.RED);
            higherRedNodePar.incBlackHigh();
            return higherRedNodePar;
        }
        if (higherRedNodePar.getRight() != null &&  lowerRedNode == higherRedNode.getLeft()){ //Rechts Rotation
            rotateRight(higherRedNode);
            higherRedNode = lowerRedNode;
            higherRedNodePar = higherRedNode.getParent();
        }
        higherRedNode.setColor(Node.RBColor.BLACK);
        higherRedNodePar.setColor(Node.RBColor.RED);
        rotateLeft(higherRedNodePar);
        return root;
    }
    
    //Der obere Knoten der Rotation wird übergeben
    private void rotateLeft (Node n){ 
      Node nP = n.getParent();
      Node nRc = n.getRight(); //nRc muss existieren
      
      n.setRight(nRc.getLeft());
      if(n.getRight() != null)
            n.getRight().setParent(n);
      if (nP == null)
          root = nRc;
      else if (nP.getRight() == n)
          nP.setRight(nRc);
      else 
           nP.setLeft(nRc);
      nRc.setParent(nP);
      
      nRc.setLeft(n);
      n.setParent(nRc);
      
    }
    //Der obere Knoten der Rotation wird übergeben
    private void rotateRight (Node n){ 
      
      Node nP = n.getParent();
      Node nLc = n.getLeft(); //nLc muss existieren
      
      n.setLeft(nLc.getRight());
      if (n.getLeft() != null)
        n.getLeft().setParent(n);
      
      if (nP  == null)
          root = nLc;
      else if (nP.getRight() == n)
          nP.setRight(nLc);
      else //nP ist nullNode, n ist die Wurzel
            nP.setLeft(nLc);

      nLc.setParent(nP);
      
      nLc.setRight(n);
      n.setParent(nLc);
     
    }
     @Override
    public void delete (int key){
        Node delNode = search(key);
        if (delNode == null || delNode.getKey() != key)
            return; 
        if (delNode.getLeft() == null){
            if(delNode.isBlack()){
                if(delNode.IsRightChildRed())
                    delNode.getRight().setColor(RBColor.BLACK);
                else
                    deleteFixup(delNode);
            }    
            transplant(delNode, delNode.getRight()); 
        }
        else if (delNode.getRight() == null ){
            if(delNode.isBlack()){
                 if(delNode.IsLeftChildRed())
                    delNode.getLeft().setColor(RBColor.BLACK);
                else
                    deleteFixup(delNode);
            }    
            transplant(delNode, delNode.getLeft()); 
        }
        
        else{ //Beide Subtrees vorhanden. Das kleinste Element des rechten Baumes verwenden um DelNode zu ersetzen
            Node rightMin = getMinNode(delNode.getRight());
            // rightMin entfernen
           if(rightMin.isBlack()){
                 if(rightMin.IsRightChildRed())
                    rightMin.getRight().setColor(RBColor.BLACK);
                else
                    deleteFixup(rightMin);
            }    
            transplant(rightMin, rightMin.getRight()); 
            //RightMin anstelle von DelNode einfügen.
            //Wenn DelNode in der FixUP Methode oberer Knoten einer Rotation war, könnte nun ein Kind "null" sein,
            
            transplant(delNode, rightMin);
            rightMin.setRight(delNode.getRight());
            if ( rightMin.getRight() != null)
                rightMin.getRight().setParent(rightMin);
            rightMin.setLeft(delNode.getLeft());
            if ( rightMin.getLeft() != null)
                rightMin.getLeft().setParent(rightMin);
            rightMin.setColor(delNode.getColor());
            rightMin.setBlackHigh(delNode.getBlackHigh());
        }
    }
    private void deleteFixup (Node node){
        Node tempNode = node;
        while (tempNode != root && tempNode.isBlack()){
            
            if (tempNode.getParent().getLeft() == tempNode)
                tempNode = deleteFixupLeftCase(tempNode);
            else
                tempNode = deleteFixupRightCase(tempNode);
        }
        tempNode.setColor(Node.RBColor.BLACK);
    }
    private Node deleteFixupLeftCase (Node inputNode){
        //Hängt 
        Node inputNodeBro = inputNode.getParent().getRight(); 
        if (inputNodeBro.isRed()){ //Fall 1
            inputNodeBro.setColor(Node.RBColor.BLACK);
            inputNodeBro.getParent().setColor(Node.RBColor.RED);
            rotateLeft(inputNode.getParent());
            inputNodeBro = inputNode.getParent().getRight();   
        }
        if ( inputNodeBro.IsLeftChildBlack() && inputNodeBro.IsRightChildBlack()){ //Fall 2
            inputNodeBro.setColor(Node.RBColor.RED);
            inputNode.getParent().decBlackHigh();
            return inputNode.getParent();
        }
        else{ 
            if (inputNodeBro.IsRightChildBlack()){
                inputNodeBro.getLeft().setColor(Node.RBColor.BLACK);
                inputNodeBro.setColor(Node.RBColor.RED);
                rotateRight(inputNodeBro);
                inputNodeBro = inputNode.getParent().getRight();
            }
            inputNodeBro.setColor(inputNode.getParent().getColor());
            inputNode.getParent().setColor(Node.RBColor.BLACK);
            inputNodeBro.getRight().setColor(Node.RBColor.BLACK);
            inputNode.getParent().decBlackHigh();
            inputNodeBro.incBlackHigh();
            rotateLeft(inputNode.getParent());
            return root;
        }
    }  
    private Node deleteFixupRightCase (Node inputNode){
        //Null Prüfungen entfallen. Die Objekte müssen existieren, da ansonsten der Baum vor dem löschen bereits eine Eigenschaft verletzt hätte

        Node inputNodeBro = inputNode.getParent().getLeft();  //Kann nicht null sein, ansonsten wäre vor dem Löschen die Schwarz-Höhe verletzt gewesen
        if (inputNodeBro.isRed()){ //Fall 1
            inputNodeBro.setColor(Node.RBColor.BLACK);
            inputNodeBro.getParent().setColor(Node.RBColor.RED);
            rotateRight(inputNode.getParent());
            inputNodeBro = inputNode.getParent().getLeft();
        }
        if (inputNodeBro.IsLeftChildBlack() && inputNodeBro.IsRightChildBlack()){ //Fall 2
            inputNodeBro.setColor(Node.RBColor.RED);
            inputNode.getParent().decBlackHigh();
            return inputNode.getParent();
        }
        else{ 
            if (inputNodeBro.IsLeftChildBlack()){
                inputNodeBro.getRight().setColor(Node.RBColor.BLACK);
                inputNodeBro.setColor(Node.RBColor.RED);
                rotateLeft(inputNodeBro);
                inputNodeBro = inputNode.getParent().getLeft();
                
            }
            inputNodeBro.setColor(inputNode.getParent().getColor());
            inputNode.getParent().setColor(Node.RBColor.BLACK);
            inputNodeBro.getLeft().setColor(Node.RBColor.BLACK);
            inputNode.getParent().decBlackHigh();
            inputNodeBro.incBlackHigh();
            rotateRight(inputNode.getParent());
            return root;
        }
        
    }  
   
    private Node getMinNode(Node node){
        Node temp = node;
        while (temp.getLeft() !=  null)
            temp = temp.getLeft();
        return temp;
    }
    /**
     * 
     * @param place Darf nicht null sein.
     * @param tree  
     * 
     */
    private void transplant (Node place, Node tree){
        if (place.getParent() == null ){
            root = tree; 
            if(tree != null)
                tree.setParent(null);
            return;
        }
        Node placeParent = place.getParent();
        if (place == placeParent.getLeft())
            placeParent.setLeft(tree);
        else
            placeParent.setRight(tree);
        if (tree != null)
            tree.setParent(placeParent); 
    }
   
    @Override
   public Node search (int key){
       if (root == null ) return null;
       Node searchNode = root;
       Node helpNode = root;
       while(helpNode != null){
           searchNode = helpNode;
           if (searchNode.getKey() == key){
               return searchNode;
           }
           else if (key < searchNode.getKey() ){
               helpNode = searchNode.getLeft();
           }
           else{
               helpNode = searchNode.getRight();
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
           if (splitNode.getKey() < key){ 
                if(searchNodeRet != null)
                    while(searchNodeRet.getBlackHigh() > splitNode.getRight().getBlackHigh() )
                        searchNodeRet = searchNodeRet.getRight();
                Node mergeNode = splitNode.getRight();
                splitNode.setRight(null);
                mergeNode.setParent(null);
                ret = ret.mergeForSplit(mergeNode, searchNodeRet, splitNode.getKey());
                if(searchNodeRet == null)
                    searchNodeRet = ret.root;
                splitNode = splitNode.getLeft();
                
           }
           else if (splitNode.getKey() > key) {
                if(searchNodeNewThis != null)
                    while(searchNodeNewThis.getBlackHigh() > splitNode.getLeft().getBlackHigh() )
                        searchNodeNewThis = searchNodeNewThis.getRight();
                Node mergeNode = splitNode.getLeft();
                splitNode.setLeft(null);
                mergeNode.setParent(null);
                newThis = newThis.mergeForSplit(mergeNode, searchNodeNewThis, splitNode.getKey());
                if(searchNodeNewThis == null)
                    searchNodeNewThis = ret.root;   
                splitNode = splitNode.getRight();
                
           }
           else{
                if(searchNodeNewThis != null){
                    while(searchNodeNewThis.getBlackHigh() > splitNode.getLeft().getBlackHigh() )
                        searchNodeNewThis = searchNodeNewThis.getRight();
                newThis.mergeForSplit(splitNode.getRight(), searchNodeNewThis, splitNode.getKey());
                }
                if(splitNode.getRight() != null){ //Kommt der split Aufruf aus Tango, wird immer zu false ausgewertet, da der SplitKey zu einem Blatt gehört
                                                 
                    if(searchNodeRet != null){
                        while(searchNodeRet.getBlackHigh() > splitNode.getRight().getBlackHigh() )
                            searchNodeRet = searchNodeRet.getRight();
                    }
                    ret.mergeForSplit(splitNode.getRight(), searchNodeRet, splitNode.getKey());
                    ret.delete(splitNode.getKey());
                }
                break;
           }    
       }
       root = newThis.root;
       return ret;
    }
    private RedBlackTree mergeForSplit (Node t2, Node searchNode, int key ){
        int newNodeBlackHigh = t2.getBlackHigh();
        if(t2.isBlack())
            newNodeBlackHigh++;
        Node newNode = new Node(key, RBColor.RED,  newNodeBlackHigh);
        if(searchNode == null){ //Erster Teilbaum wird abgespalten
            RedBlackTree ret = new RedBlackTree();
            ret.root = t2;
            ret.root.setColor(RBColor.BLACK);
            ret.insert(key);
            return ret;
        }
        else if(searchNode.getKey() < key)
            return mergeLeftCase(t2, newNode, searchNode);
        return mergeRightCase(t2, newNode, searchNode);
    }

    @Override
    //Alle keys von tree müssen entweder kleiner oder größer als die von this sein
    //Nach der Operation gibt es nur noch den Returnbaum, d. h. die Wurzelzeiger der alten Instanzen werden abgehängt.
    public I_TangoAuxTree merge(I_TangoAuxTree tree, int key) {
        if (tree.getClass() != RedBlackTree.class)
            throw new IllegalArgumentException();
        RedBlackTree t2 = (RedBlackTree)tree;
        if(t2.root == null)
            return this;
        if(root == null)
            return t2;
        I_TangoAuxTree ret;
        if (root.getBlackHigh() < t2.root.getBlackHigh()){
            return t2.merge(this, key);
        }
        Node searchNode = root;
        Node newNode = new Node(key, RBColor.RED, t2.root.getBlackHigh() +1);
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
        t2.root = null;
        return ret;
    }

   
    private RedBlackTree mergeLeftCase(Node t2Root, Node newNode, Node searchNode){
        if (searchNode == root){
            root = newNode;
        }
        else{
            searchNode.getParent().setLeft(newNode);
            newNode.setParent(searchNode.getParent());
        }
        newNode.setRight(searchNode);
        searchNode.setParent(newNode);
        newNode.setLeft(t2Root);
        t2Root.setParent(newNode);
        insertFixup(newNode);
        return this;
    }
    private RedBlackTree mergeRightCase(Node t2Root, Node newNode,  Node searchNode){
        if (searchNode == root)
            root = newNode;
        else{
            searchNode.getParent().setRight(newNode);
            newNode.setParent(searchNode.getParent());
        }
        newNode.setLeft(searchNode);
        searchNode.setParent(newNode);
        newNode.setRight(t2Root);
        t2Root.setParent(newNode);
       
        insertFixup(newNode);
        return this;
    }
   
}
