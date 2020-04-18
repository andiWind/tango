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
        }
        else{ //Gesuchter Knoten ist der erste Knoten in Pfad nach oben bei dem rechts angehängt wurde
            while(node.getParent() != null){
                if(node.getParent().getRight() == node){
                    return node.getParent();
                }
                node = node.getParent();
            }
        }
        //Node ist der Kleinste Knoten im Baum
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
     //Der obere Knoten der Rotation wird übergeben
    protected void rotateLeft (TangoNode node){ 
        TangoNode nodeParent = node.getParent();
        TangoNode nodeRightChild = node.getRight(); //nRc muss existieren
      
        node.setRight(nodeRightChild.getLeft());
        node.setLeftAuxTree(nodeRightChild.getLeftAuxTree());
      
        if(node.getRight() != null)
            node.getRight().setParent(node);
        if (node.getRightAuxTree() != null)
            node.getRightAuxTree().setParentNodeAuxTree(node);
        
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
        
        node.setLeft(nodeLeftChild.getRight());
        node.setLeftAuxTree(nodeLeftChild.getLeftAuxTree());
        
        if (node.getLeft() != null)
            node.getLeft().setParent(node);
        if (node.getLeftAuxTree() != null)
            node.getLeftAuxTree().setParentNodeAuxTree(node);
        
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
    private TangoNode getSplitterLeft(int depth){
        TangoNode node = getRoot();
        while(node != null){
            if(node.getLeft() != null && node.getLeft().getMaxDepth() > depth){
                node = node.getLeft();
            }
            else if(node.getDepth() > depth){
                break;
            }
            else{
                node = node.getRight(); //Kann nicht null sein
            }
        }
        //node zeigt jetzt auf den kleinsten Knoten der abgespaltet werden soll
        // benötigt wird aber der Vorgänger
        return getPredecessor(node);
    }
    private TangoNode getSplitterRight(int depth){
        TangoNode node = getRoot();
        while(node != null){
            if(node.getRight() != null && node.getRight().getMaxDepth() > depth){
                node = node.getRight();
            }
            else if(node.getDepth() > depth){
                break;
            }
            else{
                node = node.getLeft(); //Kann nicht null sein
            }
        }
        //node zeigt jetzt auf den größten Knoten der abgespaltet werden soll
        // benötigt wird aber der Nachfolger
        return getSuccessor(node);
    }
    //Läuft von einem Übergebenen Auxtree Blatt nach oben und aktualisiert die Min. bzw. Maxtiefe
    private void actDepthsAfterConcatenate(TangoNode node){
        node.setMaxDepth(node.getDepth());
        node.setMinDepth(node.getDepth());
        TangoNode nodeParent = node.getParent();
        while(nodeParent != null){
            if(nodeParent.getMaxDepth() < node.getMaxDepth()){
                nodeParent.setMaxDepth(node.getMaxDepth());
            }
            if(nodeParent.getMinDepth() < node.getMinDepth()){
                nodeParent.setMinDepth(node.getMinDepth());
            }
            node = nodeParent;
            nodeParent = node.getParent();
        }
    }
    TangoNode concatenate (TangoNode node){
        if (node.getLeft() != null && node.getLeft().isRoot() ){
            node.setLeftAuxTree(node.getLeft());
            node.getLeftAuxTree().setParentNodeAuxTree(node);
            node.setLeft(null);
        }
        if (node.getRight() != null && node.getRight().isRoot() ){
            node.setRightAuxTree(node.getRight());
            node.getRightAuxTree().setParentNodeAuxTree(node);
            node.setRight(null);
        }
        TangoNode left = node.getLeft(); 
        TangoNode right = node.getRight(); 
        detachAuxtree(node.getLeft());
        detachAuxtree(node.getRight());
        
        TangoNode newTree;
        newTree = merge(left, node, right);
        actDepthsAfterConcatenate(node);
        newTree.setIsRoot(true);
        return newTree;
    }
    void changePaths(TangoNode rootHigherPath, TangoNode rootMergePath){
        if (rootHigherPath == null || rootMergePath == null )
            return;
        boolean isTangoRoot = false;
        boolean isRightAuxTreeChild = false;
        TangoNode higherPathParent = rootHigherPath.getParentNodeAuxTree();
        //alte Auxtree Struktur auftrennen
        detachAuxtree(rootHigherPath);
        detachAuxtree(rootMergePath);
        TangoNode rootAfterCut = cut(rootHigherPath, rootMergePath.getMinDepth() - 1 );
        
        
    }
    private void detachAuxtree(TangoNode root){
        if(root == null)
            return;
        root.setIsRoot(false);
        TangoNode rootParent = root.getParentNodeAuxTree();
        if(rootParent == null)
            return;
        root.setIsRoot(false);
        if(rootParent.getLeftAuxTree() == root){
            rootParent.setLeftAuxTree(null); 
        } 
        else{
            rootParent.setLeftAuxTree(null);
        }
        root.setParentNodeAuxTree(null);
    }
    TangoNode join (TangoNode tree1, TangoNode tree2){
        if(tree1 == null || tree2 == null)
            return null;
        tree1.setIsRoot(false);
        tree2.setIsRoot(false);
        if(tree2.getMaxDepth() > tree1.getMaxDepth()){
            TangoNode temp = tree1;
            tree1 = tree2;
            tree2 = temp;
        }
        //Die Schlüsselwerte von tree2 befinden sich alle zwischen zwei aufeinanderfolgenden Schlüsselwerten innerhalb tree1.
        //Die Knoten mit diesen beiden Schlüsseln werden gesucht
        TangoNode smallerKeyNode = search(tree2.getKey());
        TangoNode biggerKeyNode;
        if(smallerKeyNode.getKey() > tree2.getKey()){
            biggerKeyNode = smallerKeyNode;
            smallerKeyNode = getPredecessor(biggerKeyNode);
        }
        else{
            biggerKeyNode = getSuccessor(smallerKeyNode);
        }
        
        TangoNode l;
        TangoNode r;
        TangoNode newTree;
        if(biggerKeyNode == null){
            l = split(tree1, smallerKeyNode.getKey());
            l.setRight(tree2);
            tree2.setParent(l);
            newTree = concatenate(l);
            newTree.setIsRoot(true);
        }
        else if (smallerKeyNode == null){
            r = split(tree1, biggerKeyNode.getKey());
            r.setLeft(tree2);
            tree2.setParent(r);  
            newTree = concatenate(r);
            newTree.setIsRoot(true);
        }
        else{
            l = split(tree1, smallerKeyNode.getKey());
            r = split(l.getRight(), biggerKeyNode.getKey());
            r.setLeft(tree2);
            tree2.setParent(r); 
            newTree = concatenate(r);
            l.setRight(newTree);
            newTree.setParent(l);
            newTree.setIsRoot(true);
        }
        return newTree;
    }
    TangoNode cut (TangoNode node, int depth){
  
        TangoNode l;
        TangoNode r;
        TangoNode splitterLeft = getSplitterLeft(depth);
        TangoNode splitterRight = getSplitterRight(depth);
        if(splitterRight == null){
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
             l.setLeft(r);
             r.setParent(l);
             r.getLeft().setIsRoot(true);
             l.setLeft(concatenate(r));
             l.getLeft().setParent(l);
             return concatenate(l);
        }
  
    }
  
    protected abstract TangoNode split( TangoNode Tree, int key);
    protected abstract TangoNode merge(TangoNode treeL, TangoNode mid, TangoNode treeR);
    protected abstract void insert (int key);
    protected abstract TangoNode search (int key);
    protected abstract TangoNode getRoot ();
    
    
    
}
