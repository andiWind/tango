/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Starter;

import GUI.GUI;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author andre
 */
public class Start {
    public static void main (String[] args){
        List<Integer> keys = new LinkedList();
        
        keys.add(1);
        keys.add(2);
        keys.add(3);
        keys.add(4);
        keys.add(5);
        keys.add(6);
        keys.add(7);
        keys.add(8);
        keys.add(9);
        keys.add(10);
        keys.add(11);
        keys.add(12);
        keys.add(13);
        keys.add(14);
        keys.add(15);
        keys.add(16);
        keys.add(17);
        keys.add(18);
        keys.add(19);
        keys.add(20);
        keys.add(21);
        keys.add(22);
   
        
       new GUI(new TangoTree.TangoTree(keys));
        //new GUI(new RedBlackTree.RedBlackTree());
    }
}
