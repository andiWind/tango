/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Starter;

import GUI.GUI;
import RedBlackTree.RedBlackTree;
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
     
       List<Integer> keys1 = new LinkedList();
       for(int i = 24; i<=100; i++){
            for(int j = 1; j<= i; j++){
                keys1.add(j);
            }
           TangoTree t = new TangoTree(keys1, RedBlackTree.class);
       //    GUI g = new GUI(t);
           new Tester(t, i); 
        //   g.dispose();
       } 
       
         
        
        
        
       
       
    }
}
