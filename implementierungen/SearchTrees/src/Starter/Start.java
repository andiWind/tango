/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Starter;

import GUI.GUI;
import RedBlackTree.RedBlackTree;
import TangoTree.BuildAuxTreeFaildException;
import TangoTree.TangoTree;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author andre
 */
public class Start {
    public static void main (String[] args){
     
        List<Integer> keys1 = new LinkedList();
        
        keys1.add(1);
        keys1.add(2);
        keys1.add(3);
        keys1.add(4);
        keys1.add(5);
        keys1.add(6);
        keys1.add(7);
        keys1.add(8);
        keys1.add(9);
        keys1.add(10);
        keys1.add(11);
        keys1.add(12);
        keys1.add(13);
        keys1.add(14);
        keys1.add(15);
        keys1.add(16);
        keys1.add(17);
        keys1.add(18);
        keys1.add(19);
        keys1.add(20);
        keys1.add(21);
        keys1.add(22);
        keys1.add(23);
        keys1.add(24);
        keys1.add(25);
        keys1.add(26);
        keys1.add(27);
        keys1.add(28);
        keys1.add(29);
        keys1.add(30);
        keys1.add(31);
        keys1.add(32);
        
        
        List<Integer> keys2 = new LinkedList();
        keys2.add(23);
        keys2.add(24);
        keys2.add(25);
        keys2.add(26);
        keys2.add(27);
        keys2.add(28);
        keys2.add(29);
        keys2.add(30);
        keys2.add(31);
        
         RedBlackTree rbt1 = new RedBlackTree();
         rbt1.insert(1);
         rbt1.insert(2);
         rbt1.insert(3);
         rbt1.insert(4);
         rbt1.insert(5);
         rbt1.insert(6);
         rbt1.insert(7);
         rbt1.insert(8);
         rbt1.insert(9);
         rbt1.insert(10);
        
          RedBlackTree rbt2 = new RedBlackTree();
         rbt1.insert(21);
         rbt1.insert(22);
         rbt1.insert(23);
         rbt1.insert(24);
         rbt1.insert(25);
         rbt1.insert(26);
         rbt1.insert(27);
         rbt1.insert(28);
         rbt1.insert(29);
         rbt1.insert(30);
         
        try {
            TangoTree t = new TangoTree(keys1, RedBlackTree.class);
            new GUI(t);
            t.search(1);
            t.search(14);
            t.search(8);
            t.search(5);
            t.search(10);
            t.search(6);
            t.search(3);
            t.search(1);
            
            
            
           
            new GUI(t);
            
            // new GUI(rbt1);
            
            //new GUI(rbt1.split(8));
        }
        catch (BuildAuxTreeFaildException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
