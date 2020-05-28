/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;

import GUI.GUICanvas;
import GUI.I_GUITree;
import RedBlackTree.Node.RBColor;
import TangoTree.TangoAuxTree;
import TangoTree.TangoNode;






/**
 *
 * @author andreas
 */
public class RedBlackTree extends TangoAuxTree implements I_GUITree {
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
    public void insert (int key){
        int blackHigh = 1;
        Node insNode = new Node(key, Node.RBColor.RED, blackHigh);
        Node search = search(insNode.getKey());
        if (root == null){
            root = insNode;  
        }
        else if(search.getKey() == insNode.getKey()){
            return;
        }
        else  {
            insNode.setParent(search);
            if (insNode.getKey() < search.getKey())
                attachNodeLeft(search, insNode);        
            else
                attachNodeRight(search, insNode);  
        }
        insertFixup(insNode);
    }
   
    private void insertFixup (Node node){
        if(node == null)
            return;
        Node tempNode = node;
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
    
    private Node insertFixupLeftCase (Node node){
        Node lowerRedNode = node;
        Node higherRedNode = node.getParent(); 
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
    private Node insertFixupRightCase (Node node){
        Node lowerRedNode = node;
        Node higherRedNode = node.getParent(); 
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
    private void rotateLeft (Node node){ 
        if(root == node)
            root = node.getRight();
         super.rotateLeft(node);
      
    }
    //Der obere Knoten der Rotation wird übergeben
    private void rotateRight (Node node){ 
        if(root == node)
            root = node.getLeft();
        super.rotateRight(node);
     
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
            Node rightMin = getPredecessor(delNode);
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
            attachNodeLeft(rightMin, delNode.getRight());
            attachNodeRight(rightMin,  rightMin.getLeft() );
            rightMin.setColor(delNode.getColor());
            rightMin.setBlackHigh(delNode.getBlackHigh());
        }
    }
    private void deleteFixup (Node node){
        if(node == null)
            return;
        while (node != root && node.isBlack()){
            
            if (node.getParent().getLeft() == node)
                node = deleteFixupLeftCase(node);
            else
                node = deleteFixupRightCase(node);
        }
        node.setColor(Node.RBColor.BLACK);
    }
    private Node deleteFixupLeftCase (Node inputNode){
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
   
    private Node getPredecessor(Node node){
        Node temp = node.getRight();
        if (temp == null)
            return null;
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
   public Node search (TangoNode startNode, int key){
        Node searchNode = (Node) startNode;
        if (searchNode == null ) return null;
            Node helpNode = searchNode;
        while(helpNode != null){
            searchNode = helpNode;
            if (searchNode.getKey() == key){
                break;
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
   public Node search (int key){
      return search(root, key);
   }
 
   
  
    @Override //Implementiert split für TangoCut
    public Node split(TangoNode node, int key) {
        Node keyNode = search (node, key); 
        if (keyNode == null)
            return null;
        Node splitNode = keyNode;
        Node nextSplitNode = splitNode;
        Node treeLroot = null;
        Node treeRroot = null;
        do {
            splitNode = nextSplitNode;
            nextSplitNode = splitNode.getParent();
            detachNode(splitNode);
            Node splitL = splitNode.getLeft(); 
            if (splitL == null )
                splitL = splitNode.getAuxTreeLeft();
            Node splitR = splitNode.getRight();
            if (splitR == null )
                splitR = splitNode.getAuxTreeRight();
            if(splitL != null){
                splitL.setParent(null);
                splitL.setColor(RBColor.BLACK);
            }
            if(splitR != null){
                splitR.setParent(null);
                splitR.setColor(RBColor.BLACK);
            }
            splitNode.setLeft(null);
            splitNode.setRight(null);
            splitNode.setParent(null);
        ////////////////////////////////////
            if(splitNode.getKey() < key){
                treeLroot = merge(splitL, splitNode, treeLroot);
            }    
            else if (splitNode.getKey() > key){
                treeRroot =  merge(treeRroot, splitNode, splitR);
            }
            else{
                treeLroot = merge(splitL, null, treeLroot);
                treeRroot = merge(treeRroot, null, splitR);
            }
        } while(nextSplitNode != null && splitNode != node);
        keyNode.setBlackHigh(0); //Sonderwert für undefiniert, da treeLroot und treeRroot unterschiedliche Schwarz-Höhen haben können
        attachNodeLeft(keyNode, treeLroot);
        attachNodeRight(keyNode, treeRroot);
        return keyNode;  
    }
    @Override
    public Node merge(TangoNode treeLinput, TangoNode midInput, TangoNode treeRinput) {
        Node mid = (Node) midInput;
        Node treeLroot = (Node) treeLinput;
        Node treeRroot = (Node) treeRinput;
        return merge(treeLroot, mid, treeRroot); 
    }
   
    private Node merge(Node treeLroot, Node mid, Node treeRroot) {
        //Es kann nichts verbunden werden. 
        if(mid == null){ 
            if(treeLroot != null)
                return treeLroot;
            return treeRroot;
        }
        boolean treeLisExtern = false;
        if(treeLroot != null && treeLroot.isAroot()){
               treeLisExtern = true;
        }
        boolean treeRisExtern = false;
        if(treeRroot != null && treeRroot.isAroot()){
               treeRisExtern = true;
        }
        mid.setBlackHigh(1);
        if ((treeLroot == null || treeLisExtern) && (treeRroot == null || treeRisExtern)){ 
            mid.setColor(RBColor.BLACK);
            attachNodeLeft(mid, treeLroot);
            attachNodeRight(mid, treeRroot);
            super.updateDepthSingleNode(mid);
            return mid;
        }
        Node ret;
        Node search;
        mid.setColor(RBColor.RED);
        Node fixUpNodeRB = mid;
        //Node fixUpNodeDepth = mid;

        if(treeRroot == null || treeRisExtern){ //mid rechts unten bei treeLroot einfügen
            ret = treeLroot;
            search = treeLroot;
            while (!(search.isBlack() && search.getBlackHigh() == 1))
                search = search.getRight();
            if(search.getRight() == null){ //Ganz unten anhängen, es wird praktisch eingefügt
                attachNodeLeft(mid, search.getAuxTreeRight()); 
                attachNodeRight(mid, treeRroot); 
                attachNodeRight(search, mid); 
                
                
                fixUpNodeRB = search;
            }
            else{ //
                search = search.getRight();
                attachNodeRight(search.getParent(), mid);
                attachNodeLeft(mid, search);
                attachNodeRight(mid, treeRroot);
                fixUpNodeRB = search;
            }
        }
        else if (treeLroot == null  || treeLisExtern){ //mid links unten bei treeRroot einfügen
            ret = treeRroot;
            search = treeRroot;
            while (!(search.isBlack() && search.getBlackHigh() == 1))
                search = search.getLeft();
            if(search.getLeft() == null){ //Ganz unten anhängen, es wird praktisch eingefügt
                attachNodeLeft(mid, treeLroot); 
                attachNodeRight(mid, search.getAuxTreeLeft()); 
                attachNodeLeft(search, mid);
 
            
                fixUpNodeRB = search;
            }
            else{
                search = search.getLeft();
                attachNodeLeft(search.getParent(), mid);
                attachNodeLeft(mid, treeLroot);
                attachNodeRight(mid,  search);
                fixUpNodeRB = search;
            }   
        }
        else if(treeRroot.getBlackHigh() > treeLroot.getBlackHigh()){ //treeL wird links bei treeRroot angefügt
            ret = treeRroot;
            search = treeRroot;
            while (!(search.isBlack() && search.getBlackHigh() == treeLroot.getBlackHigh()))
                search = search.getLeft();
            mid.setBlackHigh(treeLroot.getBlackHigh() + 1);
            if(search.getParent() == null){
                ret = mid;
                mid.setColor(RBColor.BLACK);
                fixUpNodeRB = null;
            }
            attachNodeLeft(search.getParent(), mid);
            attachNodeLeft(mid, treeLroot);
            attachNodeRight(mid,search);
        }
        else{ //treeR wird rechts bei treeLroot angefügt
            ret = treeLroot;
            search = treeLroot;
            while (!(search.isBlack() && search.getBlackHigh() == treeRroot.getBlackHigh()))
                search = search.getRight();
            mid.setBlackHigh(treeRroot.getBlackHigh() + 1);
            if(search.getParent() == null){
                ret = mid;
                mid.setColor(RBColor.BLACK);
                fixUpNodeRB = null;
            }
            attachNodeRight(search.getParent(), mid);
            attachNodeLeft(mid, search);
            attachNodeRight(mid,treeRroot);
        }  
        RedBlackTree fixUpTree = new RedBlackTree(); //wird verwendet um die FixUp routinen mit der richtigen Wurzel anzustoßen
        super.updateDepthSingleNode(mid);
        super.updateDepthsPath(mid.getParent());
        fixUpTree.setTree(ret);
        fixUpTree.insertFixup(fixUpNodeRB);
        return fixUpTree.getRoot();
    }
    private void detachNode(Node node){
        if (node == null || node.getParent() == null)
            return;
        if (node.getParent().getLeft() == node)
            node.getParent().setLeft(null);
        else
            node.getParent().setRight(null);
    }
    @Override
    protected void setTree(TangoNode node) {
        root = (Node)node; 
    }

   

   
   

}
