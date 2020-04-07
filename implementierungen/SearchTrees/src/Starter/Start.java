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
        keys.add(null);
        keys.add(4);
        keys.add(11);
        keys.add(-12);
        keys.add(12);
        keys.add(42);
        keys.add(23);
        keys.add(null);
        keys.add(12);
        keys.add(1);
        keys.add(0);
        keys.add(null);
        
        new TangoTree.TangoTree(keys);
//  new GUI(new RedBlackTree.RedBlackTree());
    }
}
