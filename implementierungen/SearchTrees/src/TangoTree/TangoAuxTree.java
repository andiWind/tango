/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;




/**
 *
 * @author andreas
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
    protected void attachNodeLeft (TangoNode parent, TangoNode left ){
        if (parent == null)
            return;
        parent.setLeft(left);
        if(left != null){    
            left.setParent(parent);    
        }
      
    }
    protected void attachNodeRight (TangoNode parent, TangoNode right  ){
        if (parent == null)
            return;
        parent.setRight(right);
        if(right != null){
            right.setParent(parent);
        }
    }
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
     //Der obere Knoten der Rotation wird übergeben
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
    protected boolean updateDepthSingleNode(TangoNode node){
       
        
        int c =0;
        if(node.getKey() == 52){
            c = 4; 
        }
        
        
        
        boolean noChange = true;
        if (updateMaxDepthSingleNode(node))
            noChange = false;
        if (updateMinDepthSingleNode(node))
            noChange = false;
        return noChange;    
        
    }
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
    /*Läuft von einem Übergebenen Auxtree Blatt nach oben und aktualisiert die Min. bzw. Maxtiefe so
     dass der übergebene Knoten entfernt werden kann
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
    TangoNode updatePreferredChildToLeft(TangoNode node, TangoNode auxTreeRoot){
        if (node == null )
            return null;
        auxTreeRoot.setIsRoot(false);
        TangoNode cutTree = cut(auxTreeRoot, node.getDepth());
        TangoNode ret = join(cutTree, getMarketPredecessor(node));
        ret.setIsRoot(true);
        return ret;
    }
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
   
    protected abstract TangoNode split( TangoNode Tree, int key);
    protected abstract TangoNode merge(TangoNode treeL, TangoNode mid, TangoNode treeR);
    protected abstract void insert (int key);
    protected abstract TangoNode search (TangoNode startNode, int key);
    protected abstract TangoNode getRoot ();
    protected abstract void setTree (TangoNode node);
    
    
}
