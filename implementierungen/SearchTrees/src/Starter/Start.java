/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Starter;


import GUI.MainGUI;





/**
 *Enthält den Haupteinsprung.
 * @author andreas
 */
public class Start {
    /**
     * Haupteinsprung
     *
     * @param args Keine Parametrierung notwendig.
     */
    public static void main (String[] args) {
        double test = 0;
        for (int i = 1; i < 1000000; i++){
            test += 1 / Math.pow(i, 2);
        }
        
      
        new MainGUI();  
 
    }
}

