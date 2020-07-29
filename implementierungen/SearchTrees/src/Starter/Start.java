/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Starter;

import GUI.GUI;
import RedBlackTree.RedBlackTree;
import SplayTree.SplayTree;
import TangoTest.Tester;
import TangoTree.BuildAuxTreeFaildException;
import TangoTree.TangoTree;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author andreas
 */
public class Start {
    public static void main (String[] args) throws BuildAuxTreeFaildException{
      RuntimeTest.Tester t = new RuntimeTest.Tester();
      t.staticFinger(10, 1600);
        
        
        
        
       List<Integer> keys1 = new LinkedList();
            for(int i = 1; i<= 12; i++){
                keys1.add(i);
            }
           SplayTree tt = new SplayTree(keys1);
           GUI g = new GUI(tt);
        //   new Tester(t, i); 
        //   g.dispose();
       
       
         
        
        
        
       
       
    }
}
