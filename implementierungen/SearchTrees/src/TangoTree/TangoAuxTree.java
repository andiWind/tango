/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;




/**
 *
 * @author andreas
 * Hilfsbaum im TangoTree
 */
public abstract class TangoAuxTree {

    private TangoNode getPredecessor(TangoNode node){
        if (node == null)
            return null;
         if(node.getLeft() != null){
            node = node.getLeft();
            TangoNode help = node.getRight();
            while(help != null){
                node = help;
                help = help.getRight();
            }
            return node;
        }
        else{ //Gesuchter Knoten ist der erste Knoten in Pfad nach oben bei dem rechts angehängt wurde
            while(node.getParent() != null){
                if(node.getParent().getRight() == node){
                    return node.getParent();
                }
                node = node.getParent();
            }
        }
        //Node ist der Kleinste Knoten im Baum, es gibt keinen Vorgänger
        return null;
    }
    private TangoNode getSuccessor(TangoNode node){
        if (node == null)
            return null;
         if(node.getRight() != null){
            node = node.getRight();
            TangoNode help = node.getLeft();
            while(help != null){
                node = help;
                help = help.getLeft();
            }
            return node;
        }
        else{ //Gesuchter Knoten ist der erste Knoten in Pfad nach oben bei dem rechts angehängt wurde
            while(node.getParent() != null){
                if(node.getParent().getLeft() == node){
                    return node.getParent();
                }
                node = node.getParent();
            }
        }
        //Node ist der Kleinste Knoten im Baum
        return null;
    }
    /**
     *  Fügt "left" als linkes Kind an "parent" an, unabhängig davon, ob "parent" bereits ein linkes Kind hat.
     * @param parent 
     * @param left 
     */
    protected void attachNodeLeft (TangoNode parent, TangoNode left ){
        if (parent == null)
            return;
        parent.setLeft(left);
        if(left != null){    
            left.setParent(parent);    
        }
      
    }
    /**
     *  Fügt "right" als rechtes Kind an "parent" an, unabhängig davon, ob "parent" bereits ein rechtes Kind hat.
     * @param parent 
     * @param right 
     */
    protected void attachNodeRight (TangoNode parent, TangoNode right  ){
        if (parent == null)
            return;
        parent.setRight(right);
        if(right != null){
            right.setParent(parent);
        }
    }
    /**
     * Führt eine Linksrotation durch.
     * @param node Der höherliegende der beiden an der Rotation beteiligten Knoten.
     */
     //Der obere Knoten der Rotation wird übergeben
    protected void rotateLeft (TangoNode node){ 
        TangoNode nodeParent = node.getParent();
        TangoNode nodeRightChild = node.getRight(); //nRc muss existieren
        attachNodeRight(node, nodeRightChild.getLeftTango());
        if(nodeParent != null){
            if (nodeParent.getRight() == node)
                nodeParent.setRight(nodeRightChild);
            else 
                nodeParent.setLeft(nodeRightChild);
        }
        nodeRightChild.setParent(nodeParent);
        attachNodeLeft(nodeRightChild, node);
        updateDepthSingleNode(node);
        updateDepthsPath(node.getParent());

    }
     /**
     * Führt eine Rechtsrotation durch und pflegt dabei die 
     * @param node Der höherliegende der beiden an der Rotation beteiligten Knoten.
     */
    protected void rotateRight (TangoNode node){ 
      
        TangoNode nodeParent = node.getParent();
        TangoNode nodeLeftChild = node.getLeft(); //nLc muss existieren
        attachNodeLeft(node, nodeLeftChild.getRightTango());
        if (nodeParent  != null){
            if (nodeParent.getRight() == node)
                nodeParent.setRight(nodeLeftChild);
            else 
                nodeParent.setLeft(nodeLeftChild);
        }
        nodeLeftChild.setParent(nodeParent);
        attachNodeRight(nodeLeftChild, node);
        updateDepthSingleNode(node);
        updateDepthsPath(node.getParent());
    

    }
    private int getMax (int i1, int i2, int i3){
        if(i1 > i2 && i1 > i3)
            return i1;
        return i2 > i3 ? i2 : i3;
    }
    private int getMin (int i1, int i2, int i3){
        if(i1 < i2 && i1 < i3)
            return i1;
        return i2 < i3 ? i2 : i3;
    }
    //Diese Methoden werden verwendet um die "minDepth" und "maxDepth" Attribute der im TangoAuxTree enthaltenen Knoten zu pflegen.
    private boolean updateMaxDepthSingleNode(TangoNode node){
        TangoNode left = node.getLeft() ;
        TangoNode right = node.getRight() ;
        int newValue;
        //MaxDepth pflegen
            int leftVal = 0;
            int rightVal = 0;
            if(left != null)
                leftVal = left.getMaxDepth();
            if(right != null)
                rightVal = right.getMaxDepth();
            newValue = getMax(node.getDepth(), leftVal, rightVal);
            if (node.getMaxDepth() != newValue){
                node.setMaxDepth(newValue); 
                return true;
            }
            return false;
    }
    private boolean updateMinDepthSingleNode(TangoNode node){
        TangoNode left = node.getLeft() ;
        TangoNode right = node.getRight() ;
        int newValue;
        //MinDepth pflegen
            int leftVal = Integer.MAX_VALUE;
            int rightVal = Integer.MAX_VALUE;
            if(left != null)
                leftVal = left.getMinDepth();
            if(right != null)
                rightVal = right.getMinDepth();
            newValue = getMin(node.getDepth(), leftVal, rightVal);
            if (node.getMinDepth() != newValue){
                node.setMinDepth(newValue); 
                return true;
            }
            return false;
    }
    /**
     * Überprüft und pflegt "minDepth" und "maxDepth" von "node". Dabei werden nur Informationen der Kinder von "node" berücksichtigt.
     * @param node
     * @return "true" wenn eine Veränderung festgestellt wurde.
     */
    protected boolean updateDepthSingleNode(TangoNode node){
        boolean noChange = true;
        if (updateMaxDepthSingleNode(node))
            noChange = false;
        if (updateMinDepthSingleNode(node))
            noChange = false;
        return noChange;    
        
    }
    /**
     * Überprüft und pflegt "minDepth" und "maxDepth" der Knoten die im Pfad von der Wurzel zu "node" enthalten sind.
     * @param node
     * 
     */
    protected void updateDepthsPath(TangoNode node){
        boolean noChange = false;
        while(node != null && !noChange){
            noChange = updateDepthSingleNode(node);
            node = node.getParent();
        }      
    } 
    
    
    
    private TangoNode getSplitterLeft(TangoNode node ,int depth){
        while(node != null){
            if(node.getLeft() != null && node.getLeft().getMaxDepth() > depth){
                node = node.getLeft();
            }
            else if(node.getDepth() > depth){
                break;
            }
            else{
                node = node.getRight(); 
            }
        }
        //node zeigt jetzt auf den kleinsten Knoten der abgespaltet werden soll, wenn ein solcher existiert
        // benötigt wird aber der Vorgänger
        return getPredecessor(node);
    }
    private TangoNode getSplitterRight(TangoNode node ,int depth){
        while(node != null){
            if(node.getRight() != null && node.getRight().getMaxDepth() > depth){
                node = node.getRight();
            }
            else if(node.getDepth() > depth){
                break;
            }
            else{
                node = node.getLeft();
            }
        }
        //node zeigt jetzt auf den größten Knoten der abgespaltet werden soll, wenn ein solcher existiert
        // benötigt wird aber der Nachfolger
        return getSuccessor(node);
    }
    
    /**
     * Bildet einen einzigen TangoAuxTree aus "node" und den TangoAuxTrees die an "node" angefügt sind. 
     * @param node Die Kinder von "node" müssen Wurzeln von TangoAuxTrees sein. Auch der leere Baum ist ein TangoAuxTree.
     * @return Wurzel des erstellten TangoAuxTree
     */
    TangoNode concatenate (TangoNode node){
       
        TangoNode left = node.getLeftTango(); 
        TangoNode right = node.getRightTango(); 
        detachAuxtree(left);
        detachAuxtree(right); 
        TangoNode newTree;
        newTree = merge(left, node, right);
        return newTree;
    }
    /**
     * Wird verwendetet um einen TangoTree bei einer Veränderung eines preferred child zu aktualisieren. 
     * @param rootMergePath Wurzel des TangoAuxTree, dessen Knoten im TangoAuxTree mit der Wurzel des TangoTree enthalten sein sollen.
     * @param tangoRoot Wurzel des TangoTree
     * @return Wurzel des aktualisierten TangoTree
     */
    TangoNode updatePaths( TangoNode rootMergePath, TangoNode tangoRoot){
        if (rootMergePath == null || tangoRoot == null   )
            return null;
        //MergePath abhängen
        tangoRoot.setIsRoot(false);
        rootMergePath.setIsRoot(false);
        detachAuxtree(rootMergePath);
        TangoNode newRoot = cut(tangoRoot, rootMergePath.getMinDepth() - 1 ); 
        TangoNode ret = join(newRoot, rootMergePath );
        ret.setIsRoot(true);
        return ret;   
    }
    /**
     * Erreicht access beim TangoTree den gesuchten Knoten, wird das entsprechende preferred child auf "links" gesetzt, dies übernimmt diese Methode 
     * @param rootMergePath Wurzel des TangoAuxTree, dessen Knoten im TangoAuxTree mit der Wurzel des TangoTree enthalten sein sollen.
     * @param tangoRoot Wurzel des TangoTree
     * @return Wurzel des aktualisierten TangoTree
     */
    TangoNode updatePreferredChildToLeft(TangoNode node, TangoNode auxTreeRoot){
        if (node == null )
            return null;
        auxTreeRoot.setIsRoot(false);
        TangoNode cutTree = cut(auxTreeRoot, node.getDepth());
        TangoNode ret = join(cutTree, getMarketPredecessor(node));
        ret.setIsRoot(true);
        return ret;
    }
    //Liefert die Wurzel des TangoAuxtree für das am Schluss auf links gesetzte preferred Child
    private TangoNode getMarketPredecessor(TangoNode node){
        if(node == null)
            return null;
        node = node.getLeftTango();
        while(node != null){
            if (node.isRoot())
                break;
            node = node.getRightTango();
            
        }
        return node;
    } 
    private TangoNode getAuxRoot(TangoNode node){
        while(!node.isRoot()){
            node = node.getParentTango();
        }
        return node;
    }
    private void detachAuxtree(TangoNode root){
        if(root == null)
            return;
        TangoNode rootParent = root.getParentTango();
        if(rootParent == null)
            return;
        if(rootParent.getLeftTango() == root)
            rootParent.setLeft(null);
        else
            rootParent.setRight(null);
        root.setParent(null);
    }
    
    /**
     * Vereinigt die TangoAuxTree mit Wurzel "tree1" und "tree2" zu einem TangoAuxTree. Die Schlüssel der über die
     * beiden Parameter erreichbaren Bäume müssen denen eines Pfades im Refernzbum entsprechen.
     * @param tree1 Die Wurzel von "tree1"
     * @param tree2 Die Wurzel von "tree2"
     * @return Die Wurzel des vereingten TangoAuxTree
     */
    TangoNode join (TangoNode tree1, TangoNode tree2){
        if(tree1 == null )
            return tree2;
        if(tree2 == null )
            return tree1;
        if(tree2.getMinDepth() < tree1.getMinDepth()){
            TangoNode temp = tree1;
            tree1 = tree2;
            tree2 = temp;
        }
        //Die Schlüsselwerte von tree2 befinden sich alle zwischen zwei  Schlüsselwerten innerhalb tree1.
        //Die Knoten mit diesen beiden Schlüsseln werden gesucht
        TangoNode searchNode = search(tree1, tree2.getKey());
        TangoNode biggerKeyNode;
        TangoNode smallerKeyNode;
        if(searchNode.getKey() > tree2.getKey()){
            biggerKeyNode = searchNode;
            smallerKeyNode = getPredecessor(biggerKeyNode);
        }
        else{
            smallerKeyNode = searchNode;
            biggerKeyNode = getSuccessor(smallerKeyNode);
        }
        tree1.setIsRoot(false);
        TangoNode l;
        TangoNode r;
        if(biggerKeyNode == null){
            l = split(tree1, smallerKeyNode.getKey());
            tree2.setIsRoot(false);
            attachNodeRight(l, tree2);
            return concatenate(l);
        }
        else if (smallerKeyNode == null){
            r = split(tree1, biggerKeyNode.getKey());
            tree2.setIsRoot(false);
            attachNodeLeft(r, tree2);  
            return concatenate(r);
        }
        else{
            l = split(tree1, smallerKeyNode.getKey());
            r = split(l.getRight(), biggerKeyNode.getKey());
            tree2.setIsRoot(false);
            attachNodeLeft(r, tree2);
            attachNodeRight(l, concatenate(r));
            return concatenate(l);
        }
    }
    /**
     * node ist die Wurzel eines TangoAuxTree "T" . Ein solcher repräsentiert einen preferred path im Referenzbaum "P". Diese Methode spaltet die 
     * Knoten aus "T" ab, deren Tiefe in "P" größer "depth" ist. Aus diesen Knoten wird ein TangoAuxTree gebildet.  
     * @param node 
     * @param depth
     * @return Die Wurzel auf den TangoAuxTree der die übrigen Knoten aus "T" enthält. An diesen TangoAuxTree ist ein TangoAuxTree mit den 
     * abgespaltenen Knoten angefügt.
     */
    TangoNode cut (TangoNode node, int depth){
       
        TangoNode l;
        TangoNode r;
        TangoNode splitterLeft = getSplitterLeft(node, depth);
        TangoNode splitterRight = getSplitterRight(node, depth);
        if (splitterRight == null && splitterLeft == null){
            return node; //Es gibt keinen Pfad der abgetrennt werden soll, es wird der unveränderte AuxTree zurückgegeben
        }
        else if(splitterRight == null){
            //spliterLeft kann nich auch  null sein           
            l = split(node, splitterLeft.getKey());
            l.getRight().setIsRoot(true);
            return concatenate(l);
        }
        else if (splitterLeft == null){
            r = split(node, splitterRight.getKey());
            r.getLeft().setIsRoot(true);
            return concatenate(r);
        }
        else{
             l = split(node, splitterLeft.getKey());
             r = split(l.getRight(), splitterRight.getKey());
             attachNodeRight(l,  r);
             r.getLeft().setIsRoot(true);
             attachNodeRight(l, concatenate(r));
             return concatenate(l);
        }
  
    }
   
    /**
     * Spaltet den TangoAuxTree mit Wurzel "tree" in zwei TangoAuxTree "T1" und "T2" auf. Der eine enthält die Schlüssel kleiner "key", der andere die Schlüssel 
     * größer "key"
     * @param Tree
     * @param key Der Wert von "key" muss als Schlüssel im TangoAuxTree mit Wurzel "Tree" enthalten sein. 
     * @return Den Knoten mit Schlüssel "key". Die Wurzel von "T1" ist dessen linkes Kind, die Wurzel von "T2" das rechte. 
     */
    protected abstract TangoNode split( TangoNode Tree, int key);
    /**
     * "treel" bzw. "treeR" sind Wurzeln von TangoAuxTree "TR" bzw "TL". Es werden die Knoten von "TR" "TL".und "mid" zu einem TangAuxTree
     * vereinigt.
     * @param treeL Jeder Schlüssel in "TL" muss kleiner als der Schlüssel von "mid" sein.
     * @param mid Der Schlüssel von "mid" muss größer als alle Schlüssel aus "TL" und kleiner als alle Schlüssel aus "TR" sein.
     * @param treeR treeL Jeder Schlüssel in "TR" muss größer als der Schlüssel von "mid" sein.
     * @return Die Wurzel einen TangoAuxTree der aus den Knoten von "TL", "TR" und "mid" besteht.
     */
    protected abstract TangoNode merge(TangoNode treeL, TangoNode mid, TangoNode treeR);
    /**
     * Der Schlüssel "key" wird in den TangoAuxTree eingefügt.
     * @param key 
     */
    protected abstract void insert (int key);
    /**
     * 
     * @param key Der Schlüssel des gesuchten Knoten
     * @return Der Knoten mit Schlüssel "key"
     */
    protected abstract TangoNode search (TangoNode startNode, int key);
    /**
     * 
     * @return Die Wurzel des TangoAuxTree.
     */
    protected abstract TangoNode getRoot ();
    /**
     * Setzt den TangoAuxTree mit Wurzel "node", als neue BST Struktur dieser Instanz. Die bisherige wird verworfen.
     * @param node Muss die Wurzel eines TangoAuxTree sein
     */
    protected abstract void setTree (TangoNode node);
    
    
}
