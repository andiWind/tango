/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import RedBlackTree.RedBlackTree;
import TangoTree.BuildAuxTreeFaildException;
import TangoTree.TangoTree;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author andreas
 * Zeichnet einen Referenzbaum und den zugehörigen TangoBaum 
 */
public class TangoCanvas extends Canvas {
    private RefTreeNode refRoot;
    private TangoTree  tangoTree;
    private int canWidth;
    
    
    TangoCanvas (){
        reset();        
    }
    private void buildRefTree(RefTreeNode node){
        int key = node.key;
        if(key == 8){
            node.left = new RefTreeNode(4);
            node.right = new RefTreeNode(12);
            buildRefTree(node.left);
            buildRefTree(node.right);
        }
        else if (key == 4 || key == 12){
            node.left = new RefTreeNode(key - 2);
            node.right = new RefTreeNode(key + 2);
            buildRefTree(node.left);
            buildRefTree(node.right);
        }
        else if(key == 2 || key == 6 || key == 10 || key == 14){
             node.left = new RefTreeNode(key - 1);
            node.right = new RefTreeNode(key + 1);
            buildRefTree(node.left);
            buildRefTree(node.right);
        }
    }
    //Refenzbam wird selbst gepflegt, dann aufruf in TangoTree
    void access(int key){
        if(key < 1 || key > 15)
            return;
         RefTreeNode node = refRoot;
         while (node != null){
             if (key < node.key){
                 node.prefChild = 1;
                 node = node.left;
             }    
             else if (key > node.key){
                node.prefChild = 2;
                node = node.right;
             }
             else{
                 node.prefChild = 1;
                 break;
             }
        }
         tangoTree.access(key);
    }
    private void drawRefTree(Graphics g,  RefTreeNode node, int posWidth, int posHeigth,  int level){
        drawNode(g, posWidth, posHeigth,  String.valueOf(node.key), Color.BLACK);
        if (node.left != null){
            g.setColor(Color.BLACK);
            if (node.prefChild == 1){
                g.setColor(Color.GREEN);
            }
           // g.drawLine(posWidth, posHeigth, posWidth - (canWidth - 10)  /(int)Math.pow(2, level + 1), posHeigth + 40 );
           g.drawRect( posWidth - (canWidth - 10)  /(int)Math.pow(2, level + 1), posHeigth, (canWidth - 10)  /(int)Math.pow(2, level + 1), 0  ); 
           g.drawRect( posWidth - (canWidth - 10)  /(int)Math.pow(2, level + 1), posHeigth, 0, 40  ); 
           drawRefTree(g, node.left, posWidth - (canWidth - 10)  /(int)Math.pow(2, level + 1) , posHeigth + 70, level + 1);
        }
        if (node.right != null){
            g.setColor(Color.BLACK);
             if (node.prefChild == 2){
                g.setColor(Color.GREEN);
            }
            //g.drawLine(posWidth, posHeigth,  posWidth + (canWidth - 10) /(int)Math.pow(2, level + 1) , posHeigth + 40);
            g.drawRect( posWidth  , posHeigth, (canWidth - 10)  /(int)Math.pow(2, level + 1), 0  ); 
            g.drawRect( posWidth + (canWidth - 10)  /(int)Math.pow(2, level + 1), posHeigth, 0, 40  ); 
            drawRefTree(g,  node.right, posWidth + (canWidth - 10) /(int)Math.pow(2, level + 1), posHeigth + 70, level + 1);
        }
    }
    /**
     * Zurücksetzen der Zeichnung
     */
    final void reset (){
        List<Integer> keys = new LinkedList<Integer>();
        for(int i = 1;i < 16; i++)
            keys.add(i);
        try {
            tangoTree = new TangoTree( keys, RedBlackTree.class);
        }
        catch (BuildAuxTreeFaildException ex) {}  
        refRoot = new RefTreeNode(8);
        buildRefTree(refRoot);
    }
    private void drawNode(Graphics g, int posWidth, int posHeigth,  String key, Color color){
        g.setFont(new Font("", 1, 16));
        g.setColor(color);
        g.drawString(key, posWidth, posHeigth);
    }
    private void drawTango (Graphics g, int posWidth, int posHeigth, I_GUINode node, int level){
        drawNode(g, posWidth, posHeigth, node.getKeyStringFromGui(), node.getColorFromGui());
        if (node.getLeftFromGui() != null){
            g.setColor(Color.BLACK);
            g.drawRect( posWidth - (canWidth - 10)  /(int)Math.pow(2, level + 1), posHeigth, (canWidth - 10)  /(int)Math.pow(2, level + 1), 0  ); 
            g.drawRect( posWidth - (canWidth - 10)  /(int)Math.pow(2, level + 1), posHeigth, 0, 40  );   
            
            drawTango(g, posWidth - (canWidth - 10)  /(int)Math.pow(2, level + 1) , posHeigth + 70, node.getLeftFromGui(), level + 1);
        }
        if (node.getRightFromGui() != null){
            g.setColor(Color.BLACK);
            g.drawRect( posWidth  , posHeigth, (canWidth - 10)  /(int)Math.pow(2, level + 1), 0  ); 
            g.drawRect( posWidth + (canWidth - 10)  /(int)Math.pow(2, level + 1), posHeigth, 0, 40  ); 
            drawTango(g, posWidth + (canWidth - 10) /(int)Math.pow(2, level + 1), posHeigth + 70, node.getRightFromGui(), level + 1);
        }
    }
    @Override
    public void paint(Graphics g){
        canWidth = this.getWidth();
        int canHeigth = this.getHeight();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canWidth, canHeigth / 3);
        g.setColor(Color.GREEN);
        g.setFont(new Font("", 1, 26));
        g.drawString("preferred child", 10, 50);
        g.drawString("Wurzel", 10, canHeigth / 3 + 50);
        drawRefTree(g, refRoot, canWidth / 2,   20, 1 );
        drawTango(g, canWidth / 2, canHeigth / 3 + 20, tangoTree.getRoot(), 1);
 
    } 
    
    private class RefTreeNode{
        int key;
        RefTreeNode left;
        RefTreeNode right;
        int prefChild;
        
        RefTreeNode(int k){
            key = k;
        }
    }    
    
}
