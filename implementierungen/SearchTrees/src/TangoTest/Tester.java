/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTest;

import GUI.GUI;
import RedBlackTree.RedBlackTree;
import RedBlackTree.Node;
import TangoTree.BuildAuxTreeFaildException;
import TangoTree.TangoTree;
import TangoTree.TangoNode;
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.Color;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author andreas
 */
public class Tester {
    TangoTree tangoTree;
    static int c = 0;
    int size;
    RedBlackTree rb;
    PerfectTreeNode pbt;
    HashMap<String,String> map;
    
    public Tester( TangoTree t, int s) throws BuildAuxTreeFaildException{
        size = s;
       // LinkedList<Integer> keys = new LinkedList(); 
       // for(int i = 0; i < size; i++)
       //     keys.add(i);
        tangoTree = t; 
        int numOfNode = 1;
        int depth = 1;
        pbt = buildPerfectBalancedTree(new PerfectTreeNode(), size, numOfNode, depth); 
         int[] keyArray = new int[size];
        String prefString = "";
        for(int i = 0; i < size; i++){
            keyArray[i] = i+1;
            prefString += "n";   
        }
        setKeysInPBT(pbt, keyArray, 0);         
        map = new HashMap();
        
        map.put(buildTreeString((TangoNode)tangoTree.getRoot()), prefString);
        test();
    }
    private String buildTreeString(TangoNode t){
        String left = "";
        String right = "";
        if ((TangoNode)t.getLeftFromGui() != null)
            left = buildTreeString((TangoNode)t.getLeftFromGui());
        if ((TangoNode)t.getRightFromGui() != null)    
            right = buildTreeString((TangoNode)t.getRightFromGui());
        String c = "b";
        if (t.getColorFromGui() == Color.RED)
            c = "r";   
        String r = "f";
        if (t.getColorFromGui() == Color.GREEN )  
            r = "t";
     //   if (!left.isEmpty())
      //      left = left + "(";
      //  if (!right.isEmpty())
      //      right = ")" + right;
        return "(" + left + "(" + "k" + t.getKey() +"_"  + c +"_"  + r +"_" + t.getBlackHighh()+ "_" +t.getDepthh() + "_" +t.getMinDepthh()+ "_" + t.getMaxDepthh() + ")" + right + ")";
    }
    private String getNextTreeString (){
        String ret = null;
        for (String key : map.keySet()){
            if (map.get(key) != null)
                return key;
        }
        return null;
    }
  
    private TangoNode buildTreeFromString(String treeString){
        if (  treeString == null|| treeString.equals(""))
            return null;
        int count = 0;
        int indexRootKey = 0;
        //Wurzel suchen
        for(int i = 0; i < treeString.length(); i++){
            String s = treeString.substring(i, i +1);
            if ("(".equals(s))
                count++;
            else if (")".equals(s))
                count--;
            else if ("k".equals(s) && count == 2){
                indexRootKey = i;
                break;
            }
        }
        int lastIndexLeftChild = indexRootKey - 2;
        indexRootKey++;
        TangoNode node;
        String key ="";
        while(!"_".equals(treeString.substring(indexRootKey,indexRootKey +1 ))  ){
            key += treeString.substring(indexRootKey,indexRootKey +1 );
            indexRootKey++; 
        }
        indexRootKey++; 
        Boolean isBlack = "b".equals(treeString.substring(indexRootKey,indexRootKey +1 ));
        indexRootKey++; 
        indexRootKey++;
        Boolean isRoot =  "t".equals(treeString.substring(indexRootKey,indexRootKey +1 )); 
        indexRootKey++; 
        indexRootKey++;
        String bh = "";
        while(!"_".equals(treeString.substring(indexRootKey,indexRootKey +1 ))  ){
            bh += treeString.substring(indexRootKey,indexRootKey +1 );
            indexRootKey++; 
        }
        indexRootKey++;
        if (isBlack)
            node = new Node(Integer.parseInt(key), Node.rbColorBlack(),Integer.parseInt(bh)  );
        else    
            node = new Node(Integer.parseInt(key), Node.rbColorRed(),Integer.parseInt(bh)  );       
        node.setIsRoott(isRoot);
        String depth = "";
        String minDepth = "";
        String maxDepth = "";
        while(!"_".equals(treeString.substring(indexRootKey,indexRootKey +1 ))  ){
            depth += treeString.substring(indexRootKey,indexRootKey +1 );
            indexRootKey++; 
        }
        indexRootKey++;
        while(!"_".equals(treeString.substring(indexRootKey,indexRootKey +1 ))  ){
            minDepth += treeString.substring(indexRootKey,indexRootKey +1 );
            indexRootKey++; 
        }
        indexRootKey++;
        while(!")".equals(treeString.substring(indexRootKey,indexRootKey +1 ))  ){
            maxDepth += treeString.substring(indexRootKey,indexRootKey +1 );
            indexRootKey++; 
        }
        indexRootKey++;
        node.setDepthh(Integer.parseInt(depth));
        node.setMinDepthh(Integer.parseInt(minDepth));
        node.setMaxDepthh(Integer.parseInt(maxDepth)); 
        node.setLeftt(buildTreeFromString(treeString.substring(1, lastIndexLeftChild + 1)));
        if( node.getLeftTangoo() != null)
            node.getLeftTangoo().setParentt(node);
        node.setRightt(buildTreeFromString(treeString.substring(indexRootKey, treeString.length()- 1)));
        if( node.getRightTangoo() != null)
            node.getRightTangoo().setParentt(node);
        return node;
    }
    public void test (){
        int count = 0;
        while(true){ 
            count++;
            String treeString = getNextTreeString();
            String newTreeString = "";
            if (treeString == null)     
                return;
            for (int searchKey = 1; searchKey <= size; searchKey++ ){
                c++;
                if (c == 4051){
                 //   GUI g = new GUI(tangoTree);
                    c = c;
                }
            //    System.out.println( c);
                TangoNode root = buildTreeFromString(treeString);
                buildPBTfromString(pbt, map.get(treeString));
                tangoTree.setTree(root);
                tangoTree.search(searchKey);
                setPrefChilds(searchKey);
                boolean io = checkKonst((TangoNode)tangoTree.getRoot()); 
                if (!io){
                    System.out.println(treeString + "_______" + searchKey + "_________" +  map.get(treeString) +  "_________" +c);
                }
                newTreeString = buildTreeString((TangoNode)tangoTree.getRoot());
                if(!map.containsKey(newTreeString)){
                    map.put(newTreeString, getPrefString(pbt, ""));
                }
                 
           }
            map.replace(treeString, null);
            if(count % 5000 == 0)
                System.out.println(size + "  " + map.size() + "___" + count );
        }
    }
    private String buildPBTfromString(PerfectTreeNode node, String s){
        if (node == null)
            return s;
        s = buildPBTfromString(node.left, s);
        String p = s.substring(0,1);
        s = s.substring(1);
        if(p.equals("l"))
            node.prefChild = preferredChild.LEFT;
        else if(p.equals("r"))
            node.prefChild = preferredChild.RIGHT;
        else
            node.prefChild = preferredChild.NONE;
        s = buildPBTfromString(node.right, s);
        return s;
    }    
    
    private String getPrefString(PerfectTreeNode node ,String s){
        if (node == null)
            return s;
        String l = getPrefString(node.left, s);
        String p = "";
        switch (node.prefChild) {
            case LEFT:
                p += "l";
                break;
            case RIGHT:
                p += "r";
                break;
            default:
                p += "n";
                break;
        }
        s += p;
        return l + p + getPrefString(node.right, "");
    }
    private void setPrefChilds(int key){
        PerfectTreeNode node = pbt;
        while(node.key != key){
            if (node.key > key){
                node.prefChild = preferredChild.LEFT;
                node = node.left;
            }
            else{
                node.prefChild = preferredChild.RIGHT;
                node = node.right;
            }
        }
        node.prefChild = preferredChild.LEFT;
    }
    private boolean checkKonst(TangoNode root){
        boolean checkedList = false;
        boolean checkedLeftRight = false;
        List<List<Integer>> pathLists = buildPathLists(pbt, null, null);
        List<List<Integer>> auxtreeLists;
        try {
            auxtreeLists = buildAuxtreeLists((TangoNode)tangoTree.getRoot(), new LinkedList(),new LinkedList() );
        } catch (Exception ex) {
            return false;
        }
        checkedList = equalLists(auxtreeLists, pathLists);
        checkedLeftRight =leftRight(root);
        return checkedList && checkedLeftRight;
              
    }
    private boolean leftRight(TangoNode node){
        if (node == null)
            return true;
        if (node.getLeftFromGui() != null  && ((TangoNode)node.getLeftFromGui()).getKey() >= node.getKey()   )
            return false;
        if (node.getRightFromGui() != null  && ((TangoNode)node.getRightFromGui()).getKey() <= node.getKey()   )
            return false;
        return leftRight((TangoNode)node.getLeftFromGui())  && leftRight((TangoNode)node.getRightFromGui());
    }
    private boolean checkDepthsReku(TangoNode node){
        if (node == null)
            return true;
        int min = node.getDepthh();
        int max = node.getDepthh();
        if (node.getLeftt() != null && node.getLeftt().getMinDepthh() < min)
            min = node.getLeftt().getMinDepthh();
        if (node.getRightt() != null && node.getRightt().getMinDepthh() < min)
            min = node.getRightt().getMinDepthh();
        if (node.getLeftt() != null && node.getLeftt().getMaxDepthh() > max)
            max = node.getLeftt().getMaxDepthh();
        if (node.getRightt() != null && node.getRightt().getMaxDepthh() > max)
            max = node.getRightt().getMaxDepthh();
        if(min != node.getMinDepthh() || max != node.getMaxDepthh() )
            return false;
        return checkDepthsReku(node.getLeftt()) && checkDepthsReku(node.getRightt()); 
    }
    private boolean checkDepths(TangoNode node){
        if (node == null || node.getColorFromGui() != Color.GREEN )
            return true;
        return checkDepthsReku(node);
       
    }
    private boolean checkRedBlack(TangoNode node){
        boolean one = isRedBlackTreeEig4 (node);
        boolean two = isRedBlackTreeEig5 (node);
        boolean three = checkDepths (node);
        
        
        if(!(one && two && three)) 
            one = one;
        
        
        
        return one && two && three;
    }
    private boolean isRedBlackTreeEig4 (TangoNode node){
        if(node == null)
            return true;
        if(node.getColorFromGui() == Color.RED && ((TangoNode)node.getParentFromGui()).getColorFromGui() == Color.RED  )
            return false;
        return isRedBlackTreeEig4(  ((TangoNode)node.getLeftt()) )&&   isRedBlackTreeEig4(  ((TangoNode)node.getRightt()));
    }
    private boolean isRedBlackTreeEig5 (TangoNode node){
        if (node == null)
           return true;
        int bh = node.getBlackHighh();
        if(bh > 1){
            if(((TangoNode)node.getRightt()).getColorFromGui() == Color.BLACK ){
                if(!(((TangoNode)node.getRightt()).getBlackHighh() == bh -1))
                    return false;
            }
            else if(((TangoNode)node.getRightt()).getColorFromGui() == Color.RED ){
                if(!(((TangoNode)node.getRightt()).getBlackHighh() == bh ))
                    return false;
            }
            else 
                return false;
             if(((TangoNode)node.getLeftt()).getColorFromGui() == Color.BLACK ){
                if(!(((TangoNode)node.getLeftt()).getBlackHighh() == bh -1))
                    return false;
            }
            else if(((TangoNode)node.getLeftt()).getColorFromGui() == Color.RED ){
                if(!(((TangoNode)node.getLeftt()).getBlackHighh() == bh ))
                    return false;
            }
            else 
                return false;
            return isRedBlackTreeEig5 (node.getRightt()) && isRedBlackTreeEig5 (node.getLeftt()) ;
        } 
        else if(bh == 1){
            if(node.getRightt() != null ) {
                if((node.getRightt()).getColorFromGui() != Color.RED  ){
                    return false;
                }
                else if (node.getRightt().getBlackHighh() != 1)
                    return false;
            }
            if(node.getLeftt() != null ) {
                if((node.getLeftt()).getColorFromGui() != Color.RED  ){
                    return false;
                }
                else if ((node.getLeftt()).getBlackHighh() != 1)
                    return false;
            }
        } 
        return isRedBlackTreeEig5 (node.getLeftt()) && isRedBlackTreeEig5 (node.getRightt()); 
        
    }
                
    
    private boolean equalLists(List<List<Integer>> aux, List<List<Integer>> path){
        boolean found;
        if (aux.size() != path.size())
            return false;
        for (List<Integer> auxlist : aux){
            found = false;
            for (List<Integer> pathlist : path){
                if (auxlist.size() == pathlist.size() && auxlist.containsAll(pathlist)){
                    found = true;
                    break;
                }    
            }
            if (found)
                continue;
            return false;
        }
        return true;
    } 
    private List<List<Integer>> buildPathLists(PerfectTreeNode node, List<Integer> aktList, List<List<Integer>> lists ){
        if (node == null)
            return lists;
        if (node == pbt){
            aktList = new LinkedList();
            aktList.add(node.key);
            lists = new LinkedList();
            lists.add(aktList);
        }
        if (node.prefChild == preferredChild.LEFT){
            if(node.left != null){
                aktList.add(node.left.key);
                lists = buildPathLists(node.left, aktList, lists);
            }
            if(node.right != null){
                List<Integer> newList = new LinkedList();
                newList.add(node.right.key);
                lists.add(newList);
                lists = buildPathLists(node.right, newList, lists);
            }
        }
        else if (node.prefChild == preferredChild.RIGHT){
            if(node.right != null){
                aktList.add(node.right.key);
                lists = buildPathLists(node.right, aktList, lists);
            }
            if(node.left != null){
                List<Integer> newList = new LinkedList();
                lists.add(newList);
                newList.add(node.left.key);
                lists = buildPathLists(node.left, newList, lists);
            }
        }
        else{
            if(node.right != null){
                List<Integer> newList = new LinkedList();
                newList.add(node.right.key);
                lists.add(newList);
                lists = buildPathLists(node.right, newList, lists);
            }
             if(node.left != null){
                List<Integer>  newList = new LinkedList();
                newList.add(node.left.key);
                lists.add(newList);
                lists = buildPathLists(node.left, newList, lists);
            }
        }
        return lists;
    }
    private List<List<Integer>> buildAuxtreeLists(TangoNode node, List<Integer> aktList, List<List<Integer>> lists ) throws Exception{
        if (node == null)
            return null;
        if((node.getColorFromGui() == Color.GREEN) ){
            if(!checkRedBlack(node)){
                throw new Exception();
            }
            List<Integer> newList = new LinkedList();
            newList.add(node.getKey());
            lists.add(newList); 
            buildAuxtreeLists((TangoNode)node.getLeftFromGui(), newList, lists );
            buildAuxtreeLists((TangoNode)node.getRightFromGui(), newList, lists );
        }
        else {
            aktList.add(node.getKey());
            buildAuxtreeLists((TangoNode)node.getLeftFromGui(), aktList, lists );
            buildAuxtreeLists((TangoNode)node.getRightFromGui(), aktList, lists );
        }
        return lists;
    }        
    private PerfectTreeNode buildPerfectBalancedTree (PerfectTreeNode node, int numOfNodes, int nodeNumber, int depth ){
         node.depth = depth;
        if (2 * nodeNumber <= numOfNodes){
            node.left = buildPerfectBalancedTree(new PerfectTreeNode(), numOfNodes, 2 * nodeNumber, depth + 1);
        }
        if (2 * nodeNumber + 1  <= numOfNodes){
            node.right = buildPerfectBalancedTree(new PerfectTreeNode(), numOfNodes,2 * nodeNumber + 1, depth + 1);
        }
        return node;
    }
    private int setKeysInPBT(PerfectTreeNode node, int[] sortedKeys, int writedNumbers){
        int tempWritedNumbers = writedNumbers;
        if (node.left != null)
            tempWritedNumbers = setKeysInPBT(node.left, sortedKeys, tempWritedNumbers);
        node.key = sortedKeys[tempWritedNumbers++];
        if (node.right != null)
            tempWritedNumbers = setKeysInPBT(node.right, sortedKeys, tempWritedNumbers);
        return  tempWritedNumbers;    
    }
     private class PerfectTreeNode{
       PerfectTreeNode left;
       PerfectTreeNode right;
       preferredChild prefChild;
       int key;
       int depth;
       private PerfectTreeNode(){
       left = null;
       right = null;
       prefChild = preferredChild.NONE;
       key = 0;
       depth = 0;
       }
    }
    private enum preferredChild {
        LEFT, RIGHT, NONE 
    } 
}
