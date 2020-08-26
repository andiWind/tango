/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TangoTree;

/**
 *Wird verwendet wenn beim erzeugen einer Auxtree Instanz ein Fehler auftritt.
 * @author andreas
 */

public class BuildAuxTreeFaildException extends Exception{
    
    BuildAuxTreeFaildException(String message){
        super(message);
    }
}
