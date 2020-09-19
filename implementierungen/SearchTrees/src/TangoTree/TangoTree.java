/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import GUI.I_GUITree;
import GUI.I_GUINode;


/**
 *
 * @author andreas
 *  Implementierung des Tango Baumes.
 */
public class TangoTree implements I_GUITree{

    private final Class<? extends TangoAuxTree > auxTreeClass;
    private TangoAuxTree auxTree;
 /**
 *
 * @param keyList Der erzeugte Tango Baum enthält alle in der Liste enthaltenen Schlüssel.   
 * @param auxTreeClass Mit diesem Paramter wird die verwendete Hilfsstruktur festgelegt.  
 * @throws BuildAuxTreeFaildException Beim erstellen einer Instanz von "auxTreeClass" ist ein Fehler aufgetreten. 
 */
    public  TangoTree(List<Integer> keyList, Class<? extends TangoAuxTree > auxTreeClass) throws BuildAuxTreeFaildException{
        //Test AuxTree Class
        try {
            auxTree = auxTreeClass.newInstance();  
        } 
        catch (Exception ex) {
            throw new BuildAuxTreeFaildException("Von der übergebenen Klasse konnte kein TangoAuxTree gebildet werden. Parameterloser Konstruktor notwendig.");
        }
        this.auxTreeClass = auxTreeClass;
        int[] keyArray = buildKeyArray(keyList);
        int numOfNodes = keyArray.length;
        if (numOfNodes < 1)
            throw new IllegalArgumentException();
        int numOfNode = 1;
        int depth = 1;
        //Der Tango Baum wird nicht direkt erstellt. Zunächst wird ein Abbild von ihm mit den PerfectTreeNode erstellt.
        PerfectTreeNode pBT = buildPerfectBalancedTree(new PerfectTreeNode(), numOfNodes, numOfNode, depth);
        setKeysInPBT(pBT, keyArray, 0);
        //Die Hilfstruktur wird nun nachgebaut
        //Zum Start ist jeder Knoten ein eigener Hilfsbaum
         auxTree.setTree(buildStartTango(pBT));
    }   
    //Die keyList wird in ein duplikatfreies, sortiertes Array überführt.
    private int[] buildKeyArray(List <Integer> keyList){
       List<Integer> nullList = new LinkedList<Integer>();
       nullList.add(null);
       keyList.removeAll(nullList);
       int[] keyArray = new int[keyList.size()];
       int j = 0;
       for (Integer key : keyList)
           keyArray[j++] = key;
       Arrays.sort(keyArray);
       if (keyArray.length < 2 ){
           int[] ret = new int[1];
           ret[0] = keyArray[0];
           return ret;
       }
       //Duplikate filtern 
       int numOfDup = 0;
       for (int i = 1; i < keyArray.length; i++ ){
           if (keyArray[i-1] == keyArray[i])
               numOfDup++;
       }
       int[] ret = new int[keyArray.length - numOfDup];
       ret[0] = keyArray[0];
       int k = 1;
        for (int i = 1; i < keyArray.length; i++ ){
           if (keyArray[i] > keyArray[i - 1]){
               ret[k++] = keyArray[i];
           }       
       }
      return ret;
   }
    
    //rekusive Methode. Erstellt den TangoBaum als Abbild des BST mit Wurzel "helpNode"
    private TangoNode buildStartTango(PerfectTreeNode helpNode) throws BuildAuxTreeFaildException {
        if (helpNode == null)
            return null;
        TangoAuxTree auxiTree = null;
        try {
            auxiTree = auxTreeClass.newInstance();
        } catch (Exception ex) { throw new BuildAuxTreeFaildException("Von " + auxTreeClass.getName() + " konnte keine Instanz gebildet werden"); }
        
      
        auxiTree.insert(helpNode.key);
        TangoNode node = auxiTree.getRoot();
        node.setDepth(helpNode.depth);
        node.setMaxDepth(helpNode.depth);
        node.setMinDepth(helpNode.depth);
        node.setIsRoot(true);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(null);
        node.setLeft(buildStartTango(helpNode.left));
        if( node.getLeftTango() != null)
            node.getLeftTango().setParent(node);
        node.setRight(buildStartTango(helpNode.right));
        if( node.getRightTango() != null)
            node.getRightTango().setParent(node);  
        return node;
    }
    @Override
    /**
 *  
 * @param key Es dürfen nur Werte verwendet werten die als Schlüssel im Tango Baum vorhanden sind.   
 * @return Der Knoten mit dem Schlüssel "key".  
 */
    public TangoNode access(int key){  
        TangoNode search = auxTree.getRoot();
        if(search == null )
            return null;
        while(true){
            if (search.getKey() < key && search.getRightTango() != null){
                search = search.getRightTango();
            }
            else if(search.getKey() > key  && search.getLeftTango() != null){
                search = search.getLeftTango();
            }
            else{ 
                break;
            }
            if(search.isRoot()){
                search = auxTree.updatePaths(search, auxTree.getRoot() );
                auxTree.setTree(search);
            }
        }
        //Das prefchild des Knoten mit Schlüssel "key" auf links setzen.
        auxTree.setTree(auxTree.updatePreferredChildToLeft(search,auxTree.getRoot()));
        return search;
    }   
    //Hilfsmethode zum erzeugen der Abbildstruktur. Einziger Aufruf im Konstruktor
    private int setKeysInPBT(PerfectTreeNode node, int[] sortedKeys, int writedNumbers){
        int tempWritedNumbers = writedNumbers;
        if (node.left != null)
            tempWritedNumbers = setKeysInPBT(node.left, sortedKeys, tempWritedNumbers);
        node.key = sortedKeys[tempWritedNumbers++];
        if (node.right != null)
            tempWritedNumbers = setKeysInPBT(node.right, sortedKeys, tempWritedNumbers);
        return  tempWritedNumbers;    
    }
    //Erzeugt die Abbildstruktur. Einziger Aufruf im Konstruktor
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
    
    @Override
    /**
     * 
     * @return Die Wurzel des Tango Baum
     * 
     */
    public I_GUINode getRoot() {
         return auxTree.getRoot();
    }
   
    private class PerfectTreeNode{
       PerfectTreeNode left;
       PerfectTreeNode right;
       int key;
       int depth;
    }
 
}
