/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/**
 *
 * @author andreas
 */
public class GUICanvas extends Canvas {
    private I_GUITree tree;
    private I_GUINode[][] nodeArrays;
    
    void setTree (I_GUITree t){
        tree = t;
        repaint();
    }
    
    public void p_wiederLoeschen(){
        this.paint(this.getGraphics());
    } 
    
    private int getHeigh(I_GUINode root, int h){
        if (root == null) return h;
        h++;
        int hLeft = getHeigh(root.getLeftFromGui(), h);
        int hRight = getHeigh(root.getRightFromGui(), h);
        return hLeft > hRight ? hLeft : hRight;
    }
    
    GUICanvas (I_GUITree t){
        tree = t;
    
        
    }
    private void writeNodeArrays(I_GUINode root, int treeHeigh){
        nodeArrays = new I_GUINode[treeHeigh][];
        for (int i = 0; i < treeHeigh; i++){
           int numOfNodes = (int)Math.pow(2, i);
           nodeArrays[i] = new I_GUINode[numOfNodes];
       } 
       writeNodeArraysHelp(root, 0, 0);
    }
    private void writeNodeArraysHelp(I_GUINode root, int level, int numOfNode){
        if (root == null) return;
        nodeArrays[level][numOfNode] = root;
        int tempNumOfNode =  numOfNode * 2 ;
        writeNodeArraysHelp(root.getLeftFromGui(), level + 1, tempNumOfNode) ;
        writeNodeArraysHelp(root.getRightFromGui(), level + 1, tempNumOfNode + 1) ;
    }
    private boolean checkParentPointer(I_GUINode node, I_GUINode parent){
        if (node == null || "null".equals(node.getKeyStringFromGui())) return true;
        if(node.getParentFromGui() != parent){
            return false;
        }
        return checkParentPointer(node.getLeftFromGui(), node) && checkParentPointer(node.getRightFromGui(), node)  ;
    }
     @Override
    public void paint(Graphics g){
        super.paint(g);
        I_GUINode root = tree.getRoot();
        int heigh = getHeigh(root, 0);
        int width = this.getWidth();
        Font standardFont = g.getFont();
        g.setFont( new Font("", 1, 30));
        g.drawString(tree.getName(),width / 2 , 40 );
        g.setFont(standardFont);
        g.setColor(Color.red);
        
        if (root != null && !checkParentPointer(root, root.getParentFromGui())){
            g.setFont( new Font("", 1, 30));
            g.drawString("ParentpointerFehler", 40, 40 );
            g.setFont(standardFont);
        }
            
        
        writeNodeArrays(root, heigh);
        g.setColor(Color.black);
  
        int xCord = 0;
        //int recWidth = 20;
        //int recHeigh = 15;
        //int widthDiff = 10;
        //int heighDiff = 85;
        int recWidth = 10;
        int recHeigh = 15;
        int widthDiff = 5;
        int heighDiff = 85;
        int[] mids = null;
        g.setColor(Color.BLACK);
        for (int i = heigh; i > 0; i--){
            int numOfNodes = (int)Math.pow (2 , i - 1);
            if (i == heigh ){
                xCord = (width  - ( numOfNodes * recWidth +  (numOfNodes - 1) * widthDiff)) / 2;
                mids = new int[numOfNodes];
                for (int j = 0; j < numOfNodes; j++){
                    int x1 = xCord + (j * recWidth + j  * widthDiff) ;
                    if (nodeArrays[heigh - 1][j] != null){
                        g.setColor(nodeArrays[heigh - 1][j].getColorFromGui());
                        g.drawRect(x1 - (recWidth + widthDiff / 2), (i - 1) * heighDiff + 50, recWidth, recHeigh);
                        String key  =nodeArrays[heigh - 1][j].getKeyStringFromGui();
                        g.drawString(key, x1 - (recWidth + widthDiff / 2), (i - 1) * heighDiff + 50 + recHeigh );
                        g.setColor(Color.BLACK);
                    }
                    mids[j] = x1; 
                }
            }    
            else{
                for (int j = 0; j < numOfNodes; j++){
                    if (nodeArrays[i - 1][j] != null){
                        g.setColor(nodeArrays[i - 1][j].getColorFromGui());
                        g.drawRect(mids[2*j] - recWidth / 2, (i - 1 )* heighDiff + 50, recWidth, recHeigh);    
                        String key  = nodeArrays[i - 1][j].getKeyStringFromGui();
                        g.drawString(key, mids[2*j] - recWidth / 2, (i - 1 )* heighDiff + 50 + recHeigh);
                        g.setColor(Color.BLACK);
                        if(nodeArrays[i - 1][j].getLeftFromGui() != null)
                            g.drawLine(mids[2*j], (i -1) * heighDiff + 50 + recHeigh, mids[2*j] - widthDiff / 2, (i) *heighDiff + 50 );
                        
                        if(nodeArrays[i - 1][j].getRightFromGui() != null)
                            g.drawLine(mids[2*j],  (i- 1) * heighDiff + 50 + recHeigh, mids[2*j] + widthDiff / 2,  (i) *heighDiff+ 50 );
                        
                    }  
                }
                
                int[] helpMids = new int[mids.length / 2]; 
                for (int j = 0; j < helpMids.length; j++){
                    helpMids[j] = mids[1 + 2 * j];
                    
                }
                mids = helpMids;
                if (mids.length > 1)
                widthDiff = mids[1] - mids[0] - recWidth;
            }
        }
    } 
}  

