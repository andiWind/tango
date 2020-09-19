/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;

/**
 *@author andreas
 *Wird verwendet wenn beim Erzeugen einer Auxtree Instanz ein Fehler auftritt.
 */

public class BuildAuxTreeFaildException extends Exception{
    
    BuildAuxTreeFaildException(String message){
        super(message);
    }
}
