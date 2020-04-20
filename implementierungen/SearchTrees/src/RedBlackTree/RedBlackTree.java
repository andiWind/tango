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
        if(root == n)
            root = n.getRight();
         super.rotateLeft(n);
      
    }
    //Der obere Knoten der Rotation wird übergeben
    private void rotateRight (Node n){ 
        if(root == n)
            root = n.getLeft();
        super.rotateRight(n);
     
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
    private Node getMergeNodeLeft(Node node, int blackHigh){
        while(node != null){
            if(node.isBlack() && node.getBlackHigh() == blackHigh)
                return node;
            node = node.getLeft();
        }
        return null;
    }
     private Node getMergeNodeRight(Node node, int blackHigh){
        while(node != null){
            if(node.isBlack() && node.getBlackHigh() == blackHigh)
                return node;
            node = node.getRight();
        }
        return null;
    }
  
    @Override //Implementiert split für TangoCut
    public Node split(TangoNode in, int key) {
        if (in == null)
            return null;
        Node searchL = null; //Hilfsvariable damit merge nicht immer ab der Wurzel suchen muss 
        Node searchR = null;
        Node treeL = null;
        Node treeR = null;
        Node input = (Node) in; 
        Node splitNode = input;
        Node pivotL = null; //Vorherige Splitnodes die für Merge verwendet werden können
        Node pivotR = null; 
        while(splitNode != null){ //Bei einem split füt TangoCut ist der Splitkey immer im Baum vorhanden und die Schleife bricht immer mit break ab
            Node splitL = splitNode.getLeft(); 
            Node splitR = splitNode.getRight();
            //Knoten von einander lösen, Wurzeln schwarz färben
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
                treeL = merge(treeL, pivotL, splitL, searchL);
                pivotL = splitNode;
                splitNode = splitNode.getLeft();
            }    
            else if (splitNode.getKey() > key){
                treeR = merge(treeR, pivotR, splitR, searchR);
                pivotR = splitNode;
                splitNode = splitNode.getRight();
            }
            else{
                treeL = merge(treeL, pivotL, splitL, searchL);
                treeR = merge(treeR, pivotR, splitR, searchR);
                break;
            }
        } 
       // Bricht die Schleife mit null ab, müssen übrig gebliebene Pivots eingefügt werden. 
       //Wird für eine Tango Implementierung nicht benötigt
       // if(pivotR != null)  
       //     insert(pivotR.getKey());
       // if(pivotL != null)
       //     insert(pivotL.getKey());
        
        splitNode.setLeft(treeL);
        if(treeL != null)
            treeL.setParent(splitNode);
        splitNode.setRight(treeR);
        if(treeR != null)
            treeR.setParent(splitNode);    
        return splitNode;  
    }
    @Override
    public Node merge(TangoNode treeL, TangoNode mid, TangoNode treeR) {
        return merge((Node) treeL,(Node)  mid, (Node) treeR, null); 
    }
    private Node merge(Node treeL, Node mid, Node treeR, Node searchPar) {
        if(mid == null){
            if(treeL ==  null)
                return treeR;
            else
                return treeL;
        }
        mid.setBlackHigh(1);
        if (treeL == null && treeR == null){
            mid.setColor(RBColor.BLACK);
            return mid;
        }
        Node ret;
        mid.setColor(RBColor.RED);
        Node search = searchPar;
        Node fixUpNode = null;
        if(treeR == null){
            ret = treeL;
            if(search == null){
                search = treeL;
            }
            while (!(search.isBlack() && search.getBlackHigh() == 1))
                search = search.getRight();
            mid.setBlackHigh(1);
            if(search.getParent() == null){
                mid.setColor(RBColor.BLACK);
                ret = mid;
                fixUpNode = search;
            }
            else{
                search.getParent().setRight(mid);
                mid.setParent(search.getParent());
                fixUpNode = mid;
            }
            mid.setLeft(search);
            search.setParent(mid);
 
        }
        else if (treeL == null){
            ret = treeR;
            if(search == null){
                search = treeR;
            }
            while (!(search.isBlack() && search.getBlackHigh() == 1))
                search = search.getLeft();
            mid.setBlackHigh(1);
            if(search.getParent() == null){
                mid.setColor(RBColor.BLACK);
                ret = mid;
                fixUpNode = search;
            }
            else{
                search.getParent().setLeft(mid);
                mid.setParent(search.getParent());
                fixUpNode = mid;
            }   
            mid.setRight(search);
            search.setParent(mid);
   
        }
        else{
            if(treeR.getBlackHigh() > treeL.getBlackHigh()){ //treeL wird links bei treeR angefügt
                ret = treeR;
                if(search == null){
                    search = treeR;
                }
                while (!(search.isBlack() && search.getBlackHigh() == treeR.getBlackHigh()))
                    search = search.getLeft();
                mid.setBlackHigh(treeR.getBlackHigh() + 1);
                mid.setRight(search);
                if(search.getParent() == null){
                    ret = mid;
                }
                else{    
                search.setParent(mid);
                search.getParent().setLeft(mid);
                fixUpNode = mid;
                }
                mid.setParent(search.getParent());
                mid.setLeft(treeL);
                treeL.setParent(mid);
            }
            else{
                ret = treeL;
                if(search == null){
                    search = treeL;
                }
                while (!(search.isBlack() && search.getBlackHigh() == treeL.getBlackHigh()))
                    search = search.getRight();
                mid.setBlackHigh(treeL.getBlackHigh() + 1);
                mid.setLeft(search);
                if(search.getParent() == null){
                    ret = mid;
                }
                else{    
                    search.setParent(mid);
                    search.getParent().setRight(mid);
                    fixUpNode = mid;
                }
                mid.setParent(search.getParent());
                mid.setRight(treeR);
                treeR.setParent(mid);
                
            }  
        }  
        insertFixup(fixUpNode);
        super.aktDepthsSingleNode(mid);
        return ret;
    }

    @Override
    protected void setTree(TangoNode node) {
        root = (Node)node; 
    }

   

   
   

}
