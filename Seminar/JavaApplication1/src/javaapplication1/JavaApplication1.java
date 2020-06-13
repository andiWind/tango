/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andre
 */
public class JavaApplication1 {
       static JavaApplication1 ja = new JavaApplication1(); 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Node tree = ja.buildTree(new int[]{20, 30, 15 , 5, 32, 5, 7, 1,11, 44, 12,13,3,6,43, 88,27});
       
       tree = ja.splay(7, tree);
        
        
        ja.ausgeben(tree);
    }
    private void ausgeben (Node tree){
        List<Node> outpar = new LinkedList<Node> ();
        outpar.add(tree);
        List<String> zeilen = ja.outputStrings(new LinkedList<String>(),outpar, 0);
        zeilen = ja.leerRein (zeilen);
        for (String s : zeilen )
         if (s != null) 
            System.out.println(s + "\n");   
         System.out.println();
    } 
    
    private List<String> leerRein (List<String> strings) {
        List<String> rets = new LinkedList<String>();
        int anzahlZeichen = 32 * 5;
        for (String s : strings){
            String newString = " " + s;
            while (newString.length()  < anzahlZeichen){
                String oneString = " ";
                char lastChar = ' ';
                for (int i = 0; i < newString.length(); i++){
                    boolean lwt = isTrennzeichen(lastChar);
                    boolean akt = isTrennzeichen(newString.charAt(i));
                    if (lwt && !akt) oneString = oneString + " ";
                    oneString = oneString + newString.charAt(i) ;
                    lastChar = newString.charAt(i);
                }
                newString = oneString;
            }
            rets.add(newString);
            
        }
        
        
        return rets;
    }
    private boolean isTrennzeichen (char a){
        return  ! (a == ' ');
    }
    
    private Node splay(int i, Node t) {
        //suchen
        Node x = t;
        while (x != null && i != x.item) {
            if (i < x.item) 
                x = x.left;
            else 
                x = x.right;
        }
        //Baum nach oben neu anordnen
        while (x.parent != null){ 
            Node xp = x.parent;
            if (xp.left == x && xp.parent == null)
                zig(x);
            else if (xp.right == x && xp.parent == null)
                zag(x);
            else if (xp.left == x && xp.parent.right  == xp)
                zigZag(x);
            else if (xp.right == x && xp.parent.left  == xp)
                zagZig(x);
            else if (xp.left == x && xp.parent.left  == xp)
                zigZig(x);
            else if (xp.right == x && xp.parent.right  == xp)
                zagZag(x);
          
        }
        return x;
        
        
    }
    private void zig(Node x){
      Node y = x.parent;
      Node z = null;
      if (y != null)
          z = y.parent;
      y.left = x.right;
      if (x.right != null)
        x.right.parent = y;
      
      if (z != null && z.left == y)
          z.left = x;
      else if (z != null && z.right == y)
          z.right = x;
      x.parent = z;
      
      x.right = y;
      y.parent = x;
     
   
    }
     private void zag(Node x){
      Node y = x.parent;
      Node z = null;
      if (y != null)
          z = y.parent;
      y.right = x.left;
      if (x.left != null)
        x.left.parent = y; 
      if (z != null && z.left == y)
          z.left = x;
      else if (z != null && z.right == y)
          z.right = x;
      x.parent = z;
      
      x.left = y;
      y.parent = x;
    }
     private void zigZag(Node x){
        zig(x);
        Node y = x;
        while (y.parent != null) y = y.parent;
        ja.ausgeben(y);
        zag(x);
         y = x;
        while (y.parent != null) y = y.parent;
         ja.ausgeben(y);   
    } 
     private void zagZig(Node x){
        zag(x);
        Node y = x;
         while (y.parent != null) y = y.parent;
         ja.ausgeben(y);
        zig(x);
        y = x;
        while (y.parent != null) y = y.parent;
         ja.ausgeben(y);    
    }
    private void zigZig(Node x){
        zig(x.parent);
        Node y = x;
        while (y.parent != null) y = y.parent;
        ja.ausgeben(y);
        zig(x);
        y = x;
        while (y.parent != null) y = y.parent;
         ja.ausgeben(y);   
} 
   

private void zagZag(Node x){
        zag(x.parent);
          Node y = x;
        while (y.parent != null) y = y.parent;
        ja.ausgeben(y);
        zag(x);
         y = x;
        while (y.parent != null) y = y.parent;
         ja.ausgeben(y);   
        
    }
    
    private List<String> outputStrings(List<String> outs, List<Node> nodes, int ebene){
        String outString = ""; 
        String leerraum = "KK";
        if (nodes == null) return outs;
       List<Node> nextNodes = new LinkedList<Node>();
       
       System.out.println();
       boolean nodeVorhanden = false;
       for (Node n : nodes){
           if (n == null){
               nextNodes.add (null);
              nextNodes.add (null);
               outString = outString + leerraum +" ";
           }
           else{
              if (n.item < 10)
                outString = outString + n.item  + " " + " "; 
              else
                 outString = outString + n.item  + "  ";  
              nextNodes.add (n.left);
              nextNodes.add (n.right);
              if (n.left != null || n.right != null) 
                nodeVorhanden = true; 
           }
 
       }
       if (!nodeVorhanden) return outs;
       outs.add(outString);
       return outputStrings (outs, nextNodes, ebene + 1);
       
    }
    
    
    
    
    
    
    
    
    
    private Node buildTree (int []items){
        Node x = new Node(items[0], null, null, null);
        int j = 1;
        while(items.length > j){
            einfügen(x ,items[j++]);
            
        }
        return x;
    }
    private void einfügen(Node x, int i){
        Node s = x;
        Node sp = s;
        while (s != null ){
            sp = s;
            if (i < s.item) s = s.left;
            else if (i > s.item) s = s.right;
            else return;
            
        }
        if (i < sp.item) sp.left = new Node (i, null, null, sp);
        else sp.right = new Node (i, null, null, sp);
        
    }
  
}
