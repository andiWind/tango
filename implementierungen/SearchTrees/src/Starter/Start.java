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
 * @author andre
 */
public class Start {
    public static void main (String[] args) throws BuildAuxTreeFaildException{
     
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

         
        
        TangoTree t = new TangoTree(keys1, RedBlackTree.class);
        
       
        new GUI(t);
        new Tester(t, 32);
    }
}
