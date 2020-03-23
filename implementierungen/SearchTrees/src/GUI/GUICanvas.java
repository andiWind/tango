/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;


/**
 *
 * @author andreas
 */
public class GUICanvas extends Canvas {
    private GUITree tree;
    
    private int getHeigh(GUINode root, int h){
        if (root == null) return h;
        h++;
        int hLeft = getHeigh(root.getLeftChild(), h);
        int hRight = getHeigh(root.getLeftChild(), h);
        return hLeft > hRight ? hLeft : hRight;
    }
    
    GUICanvas (GUITree t){
        tree = t;
    }
    
     @Override
    public void paint(Graphics g){
        super.paint(g);
        //int heigh = getHeigh(tree.getRoot(), 0);
        int heigh = 6;
        
        g.setColor(Color.black);
        int width = this.getWidth();
        int recWidth = 30;
        int recHeigh = 15;
        int xCord = 0;
        int widthDiff = 10;
        int[] mids = null;
        for (int i = heigh - 1; i >= 0; i--){
            int numOfNodes = (int)Math.pow (2 , i);
            if (i == heigh - 1){
                xCord = (width  - ( numOfNodes * recWidth +  (numOfNodes - 1) * widthDiff)) / 2;
                mids = new int[numOfNodes];
                for (int j = 0; j < numOfNodes; j++){
                    int x1 = xCord + (j * recWidth + j  * widthDiff) ;
                    g.drawRect(x1 - (recWidth + widthDiff / 2), i *80 + 50, recWidth, recHeigh);
                    mids[j] = x1;
                }
            }    
            else{
                for (int j = 0; j < numOfNodes; j++){
                    g.drawRect(mids[2*j] - recWidth / 2, i *80 + 50, recWidth, recHeigh);    
                }
                int[] helpMids = new int[mids.length / 2]; 
                for (int j = 0; j < helpMids.length; j++){
                    helpMids[j] = mids[1 + 2 * j];
                }
                mids = helpMids;
            }
        }
        

    }
    
    
}
