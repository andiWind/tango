/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;


import RedBlackTree.RBTNode.RBColor;
import TangoTree.TangoAuxTree;
import TangoTree.TangoNode;

/**
 *
 * Klasse zum Erstellen eines Rot-Schwarz-Baumes
 * @author andreas
 *  
 */
public class RedBlackTree extends TangoAuxTree {
    private RBTNode root;

   /**
    * 
    */
    public RedBlackTree (){
      
    }
    @Override
    public RBTNode getRoot (){
        return root;
    }
     
   
  
    /**
     * Einfügeoperation
     * @param key Fügt einen Knoten mit Schlüssel "key" in den Rot-Schwarz-Baum ein.
     */
     @Override
    public void insert (int key){
        int blackHigh = 1;
        RBTNode insNode = new RBTNode(key, RBTNode.RBColor.RED, blackHigh);
        RBTNode search = search(root,insNode.getKey());
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
   //Erstellt aus einem Rot-Schwarz-Baum mit zwei roten Knoten in Eltern-Kind-Beziehung oder einer roten Wurzel wieder eine gültige Darstellung
    private void insertFixup (RBTNode node){
        if(node == null)
            return;
        RBTNode tempNode = node;
        while (tempNode != root && tempNode.getParent() != null && tempNode.getParent().isRed()){
            if (tempNode.getParent() == tempNode.getParent().getParent().getLeft() ){ //Der obere der zwei roten Knoten hängt links
               tempNode =  insertFixupLeftCase(tempNode);
            }
            else{ //Der obere der zwei roten Knoten hängt rechts
               tempNode =  insertFixupRightCase(tempNode); 
            }
        }
        root.setColor(RBTNode.RBColor.BLACK);
    }
    
    private RBTNode insertFixupLeftCase (RBTNode node){
        RBTNode lowerRedNode = node;
        RBTNode higherRedNode = node.getParent(); 
        RBTNode higherRedNodePar = higherRedNode.getParent();//Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNodePar.isRightChildRed()){
            higherRedNodePar.getRight().setColor(RBTNode.RBColor.BLACK);
            higherRedNode.setColor(RBTNode.RBColor.BLACK);
            higherRedNodePar.setColor(RBTNode.RBColor.RED);
            higherRedNodePar.incBlackHigh();
            return higherRedNodePar;
        }
        if (lowerRedNode == higherRedNode.getRight()){ //Links Rotation
            rotateLeft(higherRedNode);
            higherRedNode = lowerRedNode;
            higherRedNodePar = higherRedNode.getParent();
        }
        higherRedNode.setColor(RBTNode.RBColor.BLACK);
        higherRedNodePar.setColor(RBTNode.RBColor.RED);
        rotateRight(higherRedNodePar);
        return root;
    }
    private RBTNode insertFixupRightCase (RBTNode node){
        RBTNode lowerRedNode = node;
        RBTNode higherRedNode = node.getParent(); 
        RBTNode higherRedNodePar = higherRedNode.getParent();//Wurzel ist schwarz, also liegt higherRedNode tiefer
        if (higherRedNodePar.isLeftChildRed() ){
            higherRedNodePar.getLeft().setColor(RBTNode.RBColor.BLACK);
            higherRedNode.setColor(RBTNode.RBColor.BLACK);
            higherRedNodePar.setColor(RBTNode.RBColor.RED);
            higherRedNodePar.incBlackHigh();
            return higherRedNodePar;
        }
        if (higherRedNodePar.getRight() != null &&  lowerRedNode == higherRedNode.getLeft()){ //Rechts Rotation
            rotateRight(higherRedNode);
            higherRedNode = lowerRedNode;
            higherRedNodePar = higherRedNode.getParent();
        }
        higherRedNode.setColor(RBTNode.RBColor.BLACK);
        higherRedNodePar.setColor(RBTNode.RBColor.RED);
        rotateLeft(higherRedNodePar);
        return root;
    }
     //Der obere Knoten der Rotation wird übergeben
    private void rotateLeft (RBTNode node){ 
        if(root == node)
            root = node.getRight();
         super.rotateLeft(node);
      
    }
    //Der obere Knoten der Rotation wird übergeben
    private void rotateRight (RBTNode node){ 
        if(root == node)
            root = node.getLeft();
        super.rotateRight(node);
     
    }

    //  Entfert einen Teilbaum aus dem Rot-Schwarz-Baum und fügt ihn an anderer Stelle wieder ein
     // @param place Darf nicht "null" sein. Dies ist die Einfügestelle
    //  @param tree  Der verwendete Teilbaum.

    private void transplant (RBTNode place, RBTNode tree){
        if (place.getParent() == null ){
            root = tree; 
            if(tree != null)
                tree.setParent(null);
            return;
        }
        RBTNode placeParent = place.getParent();
        if (place == placeParent.getLeft())
            placeParent.setLeft(tree);
        else
            placeParent.setRight(tree);
        if (tree != null)
            tree.setParent(placeParent); 
    }
     /**
     * @param startNode Es muss die Wurzel eines RBT übergeben werden.
     * @param key Der Schlüssel des gesuchten Knotens.
     * @return Der Knoten mit Schlüssel "key"
     */
    @Override
   public RBTNode search (TangoNode startNode, int key){
        RBTNode searchNode = (RBTNode) startNode;
        if (searchNode == null ) return null;
            RBTNode helpNode = searchNode;
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

  
   
   /**
     * Spaltet den TangoAuxTree mit Wurzel "tree" in zwei TangoAuxTree "T1" und "T2" auf. T1 enthält die Schlüssel kleiner "key", T2 die Schlüssel 
     * größer "key"
     * @param node Wurzel eines RBT
     * @param key Der Wert von "key" muss als Schlüssel im RBT mit Wurzel "node"  enthalten sein. 
     * @return Den Knoten mit Schlüssel "key". Die Wurzel von "T1" ist dessen linkes Kind, die Wurzel von "T2" das rechte. 
     */
    @Override 
    public RBTNode split(TangoNode node, int key) {
         //Es werden bottom up Teilbäume abgespalten, die dann  mit "concatnate" zu RBT zusammengefügt werden.
        RBTNode keyNode = search (node, key); 
        if (keyNode == null)
            return null;
        RBTNode splitNode = keyNode;
        RBTNode nextSplitNode = splitNode;
        RBTNode treeLroot = null;
        RBTNode treeRroot = null;
        do {
            splitNode = nextSplitNode;
            nextSplitNode = splitNode.getParent();
            detachNode(splitNode);
            RBTNode splitL = splitNode.getLeft(); 
            if (splitL == null )
                splitL = splitNode.getAuxTreeLeft();
            RBTNode splitR = splitNode.getRight();
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
                treeLroot = concatenate(splitL, splitNode, treeLroot);
            }    
            else if (splitNode.getKey() > key){
                treeRroot =  concatenate(treeRroot, splitNode, splitR);
            }
            else{
                treeLroot = concatenate(splitL, null, treeLroot);
                treeRroot = concatenate(treeRroot, null, splitR);
            }
        } while(nextSplitNode != null && splitNode != node);
        //Nun wird die Rückgabe noch zusammengfügt
        keyNode.setBlackHigh(0); //Sonderwert für undefiniert, da treeLroot und treeRroot unterschiedliche Schwarz-Höhen haben können
        attachNodeLeft(keyNode, treeLroot);
        attachNodeRight(keyNode, treeRroot);
        return keyNode;  
    }

    /**
     * "treeLinput" bzw. "treeRinput" sind die Wurzeln von den Rot-Schwarz-Bäumen  "TR" bzw "TL". Es werden die Knoten von "TR", "TL"und "mid" zu einem Rot-Schwarz-Baum
     * vereinigt.
     * @param treeLinput Jeder Schlüssel in "TL" muss kleiner als der Schlüssel von "mid" sein.
     * @param midInput Der Schlüssel von "mid" muss größer als alle Schlüssel aus "TL" und kleiner als alle Schlüssel aus "TR" sein.
     * @param treeRinput treeL Jeder Schlüssel in "TR" muss größer als der Schlüssel von "mid" sein.
     * @return Die Wurzel eines RBT der aus den Knoten von "TL", "TR" und "mid" besteht.
     */
    @Override 
    public RBTNode concatnate(TangoNode treeLinput, TangoNode midInput, TangoNode treeRinput) {
        //Fügt treeLinput an treeRInput seitlich an, oder umgekehrt< je nachdem weiche Schwarz-Höhe größer ist
        RBTNode mid = (RBTNode) midInput;
        RBTNode treeLroot = (RBTNode) treeLinput;
        RBTNode treeRroot = (RBTNode) treeRinput;
        return concatenate(treeLroot, mid, treeRroot); 
    }
   
    private RBTNode concatenate(RBTNode treeLroot, RBTNode mid, RBTNode treeRroot) {
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
        //normaler Ablauf
        mid.setBlackHigh(1);
        if ((treeLroot == null || treeLisExtern) && (treeRroot == null || treeRisExtern)){ 
            mid.setColor(RBColor.BLACK);
            attachNodeLeft(mid, treeLroot);
            attachNodeRight(mid, treeRroot);
            super.updateDepthSingleNode(mid);
            return mid;
        }
        RBTNode ret;
        RBTNode search;
        mid.setColor(RBColor.RED);
        RBTNode fixUpNodeRB = mid;
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
    //Knoten aus dem Rot-Schwarz-Baum abhängen.
    private void detachNode(RBTNode node){
        if (node == null || node.getParent() == null)
            return;
        if (node.getParent().getLeft() == node)
            node.getParent().setLeft(null);
        else
            node.getParent().setRight(null);
    }
    @Override
    /**Hiermit kann der Zeiger auf die Wurzel gesetzt werden. 
     * @param node Die Wurzel einer RBT Struktur
     */
    protected void setTree(TangoNode node) {
        root = (RBTNode)node; 
    }

   

   
   

}
