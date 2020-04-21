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
    protected void attachNodes (TangoNode parent, TangoNode left, TangoNode right  ){
        if (parent == null)
            return;
        if(left != null){
            parent.setLeft(left);
            left.setParent(parent);
        }
        if(right != null){
            parent.setRight(right);
            right.setParent(parent);
        }
    }
     //Der obere Knoten der Rotation wird übergeben
    protected void rotateLeft (TangoNode node){ 
        TangoNode nodeParent = node.getParent();
        TangoNode nodeRightChild = node.getRight(); //nRc muss existieren
      
        node.setRight(nodeRightChild.getLeftTango());
        if(node.getRight() != null)
            node.getRight().setParent(node);
        if(nodeParent != null){
            if (nodeParent.getRight() == node)
                nodeParent.setRight(nodeRightChild);
            else 
                nodeParent.setLeft(nodeRightChild);
        }
        nodeRightChild.setParent(nodeParent);
        nodeRightChild.setLeft(node);
        node.setParent(nodeRightChild);
        
        aktDepthsSingleNode(node);
        aktDepthsSingleNode(nodeRightChild);
      
    }
     //Der obere Knoten der Rotation wird übergeben
    protected void rotateRight (TangoNode node){ 
      
        TangoNode nodeParent = node.getParent();
        TangoNode nodeLeftChild = node.getLeft(); //nLc muss existieren
        
        node.setLeft(nodeLeftChild.getRightTango());
        
        if (node.getLeft() != null)
            node.getLeft().setParent(node);

        if (nodeParent  != null){
            if (nodeParent.getRight() == node)
                nodeParent.setRight(nodeLeftChild);
            else 
                nodeParent.setLeft(nodeLeftChild);
        }
        nodeLeftChild.setParent(nodeParent);
        nodeLeftChild.setRight(node);
        node.setParent(nodeLeftChild);
        
        aktDepthsSingleNode(node);
        aktDepthsSingleNode(nodeLeftChild);

    }
    protected void aktDepthsSingleNode(TangoNode node){
        node.setMaxDepth(node.getDepth());
        node.setMinDepth(node.getDepth());
        if (node.getLeft() != null && node.getLeft().getMaxDepth() > node.getMaxDepth())
            node.setMaxDepth(node.getLeft().getMaxDepth());
        if (node.getRight() != null && node.getRight().getMaxDepth() > node.getMaxDepth())
            node.setMaxDepth(node.getRight().getMaxDepth());
        
        if (node.getLeft() != null && node.getLeft().getMinDepth() < node.getMinDepth())
            node.setMinDepth(node.getLeft().getMinDepth());
        if (node.getRight() != null && node.getRight().getMinDepth() < node.getMinDepth())
            node.setMinDepth(node.getRight().getMinDepth());
            
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
        newTree.setIsRoot(true);
        return newTree;
    }
    TangoNode changePaths(TangoNode rootTango, TangoNode rootMergePath){
        if (rootTango == null || rootMergePath == null )
            return null;
        //MergePath abhängen
        detachAuxtree(rootMergePath);
        TangoNode newRoot = cut(rootTango, rootMergePath.getMinDepth() - 1 );
        return join(newRoot, rootMergePath );
        
        
        
        
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
        if(tree1 == null || tree2 == null)
            return null;
        tree1.setIsRoot(false);
        tree2.setIsRoot(false);
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
        
        TangoNode l;
        TangoNode r;
        TangoNode newTree;
        if(biggerKeyNode == null){
            l = split(tree1, smallerKeyNode.getKey());
            attachNodes(l, null, tree2);
            newTree = concatenate(l);
        }
        else if (smallerKeyNode == null){
            r = split(tree1, biggerKeyNode.getKey());
            attachNodes(r, tree2, null);  
            newTree = concatenate(r);
        }
        else{
            l = split(tree1, smallerKeyNode.getKey());
            r = split(l.getRight(), biggerKeyNode.getKey());
            attachNodes(r, tree2, null);
            newTree = concatenate(r);
             attachNodes(l, null, newTree);
        }
        return newTree;
    }
    TangoNode cut (TangoNode node, int depth){
        //Wurde keine Wurzel übergeben, muss diese erstmel gesucht werden 
        while(!node.isRoot())
            node = node.getParentTango();
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
             attachNodes(l, r, null);
             r.getLeft().setIsRoot(true);
             attachNodes(l, concatenate(r), null);
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
